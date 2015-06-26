package com.icfi.aem.apps.ionic.api.resource;

import com.google.common.base.Optional;
import org.apache.sling.api.resource.ValueMap;

import java.util.Iterator;
import java.util.List;

public interface ResourceType {

    String getPath();

    ValueMap asMap();

    <T> T get(String propertyName, T defaultValue);

    <T> Optional<T> get(String propertyName, Class<T> type);

    <T> List<T> getAsList(String propertyName, Class<T> type);

    <T> T getInherited(String propertyName, T defaultValue);

    <T> Optional<T> getInherited(String propertyName, Class<T> type);

    <T> List<T> getAsListInherited(String propertyName, Class<T> type);

    Optional<ResourceType> getResourceSuperType();

    Iterable<ResourceType> getResourceSuperTypes();

}
