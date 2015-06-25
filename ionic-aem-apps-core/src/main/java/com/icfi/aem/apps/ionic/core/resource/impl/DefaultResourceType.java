package com.icfi.aem.apps.ionic.core.resource.impl;

import com.citytechinc.aem.bedrock.api.node.ComponentNode;
import com.google.common.base.Optional;
import com.icfi.aem.apps.ionic.api.resource.ResourceType;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;

import java.util.List;


public class DefaultResourceType implements ResourceType {

    private final ComponentNode componentNode;

    public DefaultResourceType(Resource resource) {
        this.componentNode = resource.adaptTo(ComponentNode.class);
    }

    public String getPath() {
        return componentNode.getPath();
    }

    public ValueMap asMap() {
        return componentNode.asMap();
    }

    public <T> T get(String propertyName, T defaultValue) {
        return componentNode.get(propertyName, defaultValue);
    }

    public <T> Optional<T> get(String propertyName, Class<T> type) {
        return componentNode.get(propertyName, type);
    }

    public <T> List<T> getAsList(String propertyName, Class<T> type) {
        return componentNode.getAsList(propertyName, type);
    }

}
