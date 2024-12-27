/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

/**
 *
 * @author ploc2
 */
import java.lang.reflect.Field;
import java.sql.ResultSet;

public class EntityMapper {
    public static <T> T mapRowToEntity(ResultSet rs, Class<T> clazz) throws Exception {
        T instance = clazz.getDeclaredConstructor().newInstance();
                System.out.println("aaaaaaaaaaaaaa");

        System.out.println(rs.toString());
        for (Field field : clazz.getDeclaredFields()) {
            field.setAccessible(true);
            Object value = rs.getObject(field.getName());
            field.set(instance, value);
        }
        
        return instance;
    }
}