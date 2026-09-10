import os
import re

ENTITY_DIR = "src/main/java/sv/edu/ues/occ/ing/ppi115_2026/pos/cafefe/entity"

def fix_file(filepath):
    filename = os.path.basename(filepath)
    classname = filename.replace(".java", "")
    
    with open(filepath, "r", encoding="utf-8") as f:
        content = f.read()
    
    # 1. Corregir firmas de equals erróneas como equals(UUID id) -> equals(Object object)
    content = re.sub(
        r'public\s+boolean\s+equals\s*\(\s*UUID\s+\w+\s*\)',
        'public boolean equals(Object object)',
        content
    )
    
    # 2. Corregir casts en equals de UUID a la Entidad
    # Cambia: ClassName other = (ClassName) object; si estaba mal tipeado
    pattern_cast = r'(\s+)(\w+)\s+other\s*=\s*\(\2\)\s*object;'
    
    # 3. Eliminar métodos completamente duplicados (mismo nombre y argumentos)
    lines = content.split('\n')
    cleaned_lines = []
    seen_methods = set()
    inside_method = False
    current_method_sig = ""
    current_method_buffer = []

    for line in lines:
        # Detectar firma de método getter/setter/equals/hashCode
        match = re.match(r'^\s*public\s+[\w<>, \[\]]+\s+(\w+\s*\([^)]*\))\s*\{', line)
        if match:
            method_sig = match.group(1).strip()
            if method_sig in seen_methods and not method_sig.startswith("equals") and not method_sig.startswith("hashCode"):
                # Omitir método duplicado
                inside_method = True
                current_method_sig = method_sig
                continue
            else:
                seen_methods.add(method_sig)
                inside_method = False
                cleaned_lines.append(line)
        elif inside_method:
            if line.strip() == "}":
                inside_method = False
            continue
        else:
            cleaned_lines.append(line)

    new_content = "\n".join(cleaned_lines)

    with open(filepath, "w", encoding="utf-8") as f:
        f.write(new_content)

    print(f"Procesado: {filename}")

def main():
    if not os.path.exists(ENTITY_DIR):
        print(f"No se encontró la ruta: {ENTITY_DIR}")
        return

    for file in os.listdir(ENTITY_DIR):
        if file.endswith(".java"):
            fix_file(os.path.join(ENTITY_DIR, file))

    print("\n¡Limpieza masiva completada!")

if __name__ == "__main__":
    main()
