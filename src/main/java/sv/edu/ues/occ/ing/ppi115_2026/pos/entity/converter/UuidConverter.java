package sv.edu.ues.occ.ing.ppi115_2026.pos.entity.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.util.UUID;

@Converter(autoApply = true)
public class UuidConverter implements AttributeConverter<UUID, UUID> {

    @Override
    public UUID convertToDatabaseColumn(UUID attribute) {
        return attribute;
    }

    @Override
    public UUID convertToEntityAttribute(UUID dbData) {
        return dbData;
    }
}