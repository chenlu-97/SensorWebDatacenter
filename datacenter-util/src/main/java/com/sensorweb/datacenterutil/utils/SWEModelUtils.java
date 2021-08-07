package com.sensorweb.datacenterutil.utils;

import net.opengis.swe.v20.*;
import org.apache.commons.lang3.StringUtils;
import org.vast.data.DateTimeOrDouble;
import org.vast.data.UnitReferenceImpl;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

public class SWEModelUtils {
    /**
     * 设置Text属性
     * @param text
     * @param value
     */
    public static void setText(Text text, String value, String definition) {
        if (!StringUtils.isBlank(definition)) {
            text.setDefinition(definition);
        }
        if (!StringUtils.isBlank(value)) {
            text.setValue(value);
        }
    }

    /**
     * 设置Time属性
     * @param time
     * @param value
     */
    public static void setTime(Time time, Instant value, String definition) {
        if (!StringUtils.isBlank(definition)) {
            time.setDefinition(definition);
        }
        if (value!=null) {
            DateTimeOrDouble sDateTime = new DateTimeOrDouble();
            ZoneOffset zoneOffset = OffsetDateTime.now().getOffset();
            sDateTime.setDateTime(value, zoneOffset);
            time.setValue(sDateTime);
        }
    }

    /**
     * 设置Quantity属性
     * @param quantity
     * @param value
     */
    public static void setQuantity(Quantity quantity, String value, String definition, String uom) {
        if (!StringUtils.isBlank(definition)) {
            quantity.setDefinition(definition);
        }
        if (!StringUtils.isBlank(value)) {
            quantity.setValue(Double.parseDouble(value));
        }
        if (!StringUtils.isBlank(uom)) {
            UnitReference unitReference = new UnitReferenceImpl();
            unitReference.setCode(uom);
            quantity.setUom(unitReference);
        }
    }
    /**
     * 设置Category属性
     * @param category
     * @param value
     */
    public static void setCategory(Category category, String value, String definition) {
        if (!StringUtils.isBlank(definition)) {
            category.setDefinition(definition);
        }
        if (!StringUtils.isBlank(value)) {
            category.setValue(value);
        }
    }
}
