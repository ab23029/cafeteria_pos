package sv.edu.ues.occ.ing.ppi115_2026.pos.cafefe.converters;

import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.FacesConverter;
import jakarta.inject.Named;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Named("entityConverter")
@FacesConverter(value = "entityConverter", managed = true)
public class EntityConverter implements Converter<Object> {

    private static final String KEY = "sv.edu.ues.occ.ing.ppi115_2026.pos.cafefe.converters.EntityConverter";

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null || value.trim().isEmpty()) {
            return null;
        }
        return getAttributes(component).get(value);
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value == null) {
            return "";
        }

        String id = UUID.randomUUID().toString();
        getAttributes(component).put(id, value);
        return id;
    }

    private Map<String, Object> getAttributes(UIComponent component) {
        Map<String, Object> viewMap = FacesContext.getCurrentInstance().getViewRoot().getViewMap();
        @SuppressWarnings("unchecked")
        Map<String, Object> attributes = (Map<String, Object>) viewMap.get(KEY);
        if (attributes == null) {
            attributes = new HashMap<>();
            viewMap.put(KEY, attributes);
        }
        return attributes;
    }
}