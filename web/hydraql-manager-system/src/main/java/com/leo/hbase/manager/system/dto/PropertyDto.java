package com.leo.hbase.manager.system.dto;

import java.util.Objects;

/**
 * @author leojie 2023/7/9 21:25
 */
public class PropertyDto {
    public PropertyDto() {
    }

    public PropertyDto(String propertyName, String propertyValue) {
        this.propertyName = propertyName;
        this.propertyValue = propertyValue;
    }

    private String propertyName;
    private String propertyValue;

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    public String getPropertyValue() {
        return propertyValue;
    }

    public void setPropertyValue(String propertyValue) {
        this.propertyValue = propertyValue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PropertyDto that = (PropertyDto) o;
        return propertyName.equals(that.propertyName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(propertyName, propertyValue);
    }

    @Override
    public String toString() {
        return "PropertyDto{" +
                "propertyName='" + propertyName + '\'' +
                ", propertyValue='" + propertyValue + '\'' +
                '}';
    }
}
