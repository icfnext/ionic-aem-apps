package com.icfi.aem.apps.ionic.api.resource;

import com.google.common.base.Optional;
import org.apache.sling.api.resource.ValueMap;

import java.util.List;

public interface ResourceType {

    public String getPath();

    ValueMap asMap();

    <T> T get(String propertyName, T defaultValue);

    <T> Optional<T> get(String propertyName, Class<T> type);

    <T> List<T> getAsList(String propertyName, Class<T> type);

}
