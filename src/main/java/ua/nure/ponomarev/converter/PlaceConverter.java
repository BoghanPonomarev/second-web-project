package ua.nure.ponomarev.converter;

import ua.nure.ponomarev.enity.PlaceStatus;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * @author Bogdan_Ponamarev.
 */
@Converter(autoApply = true)
public class PlaceConverter implements AttributeConverter<PlaceStatus, Character> {
    @Override
    public Character convertToDatabaseColumn(PlaceStatus placeStatus) {
        if (placeStatus == PlaceStatus.FREE) {
            return 'f';
        } else if (placeStatus == PlaceStatus.BUSY) {
            return 'b';
        } else {
            return 'r';
        }
    }

    @Override
    public PlaceStatus convertToEntityAttribute(Character character) {
        if (character == 'f') {
            return PlaceStatus.FREE;
        } else if (character == 'b') {
            return PlaceStatus.BUSY;
        } else {
            return PlaceStatus.RESERVED;
        }
    }
}
