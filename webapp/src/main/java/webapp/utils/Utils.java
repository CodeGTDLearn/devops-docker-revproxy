package webapp.utils;

import org.springframework.stereotype.Component;
import java.lang.reflect.Field;


@Component
public class Utils implements UtilsInt {

    @Override
    public Object UpdatePatchFields(Object current, Object given) throws IllegalArgumentException, IllegalAccessException
    {

        Field[] fieldsCurrentObj = current.getClass().getDeclaredFields();
        Field[] fieldsGivenObj = given.getClass().getDeclaredFields();

        for (int i = 0; i < fieldsGivenObj.length; i++) {

            fieldsCurrentObj[i].setAccessible(true);
            fieldsGivenObj[i].setAccessible(true);

            if (fieldsGivenObj[i].get(given) == null) {
                fieldsGivenObj[i].set(given, fieldsCurrentObj[i].get(current));
            }
        }

        return given;
    }
}
