package com.citytechinc.aem.apps.ionic.core.resource.impl;

import com.icfolson.aem.library.api.node.ComponentNode;
import com.google.common.base.Optional;
import com.google.common.collect.Lists;
import com.citytechinc.aem.apps.ionic.api.resource.ResourceType;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.RepositoryException;
import java.util.List;

public class DefaultResourceType implements ResourceType {

    private static final Logger LOG = LoggerFactory.getLogger(DefaultResourceType.class);

    private final ComponentNode componentNode;
    private List<ResourceType> resourceSuperTypes;
    private Optional<ResourceType> resourceSuperType;

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

    public <T> T getInherited(String propertyName, T defaultValue) {
        if (!hasProperty(propertyName) && getResourceSuperType().isPresent()) {
            return getResourceSuperType().get().getInherited(propertyName, defaultValue);
        }

        return get(propertyName, defaultValue);
    }

    public <T> Optional<T> getInherited(String propertyName, Class<T> type) {
        if (!hasProperty(propertyName) && getResourceSuperType().isPresent()) {
            return getResourceSuperType().get().getInherited(propertyName, type);
        }

        return get(propertyName, type);
    }

    public <T> List<T> getAsListInherited(String propertyName, Class<T> type) {
        if (!hasProperty(propertyName) && getResourceSuperType().isPresent()) {
            return getResourceSuperType().get().getAsListInherited(propertyName, type);
        }

        return getAsList(propertyName, type);
    }

    public Optional<ResourceType> getResourceSuperType() {

        if (resourceSuperType != null) {
            return resourceSuperType;
        }

        resourceSuperType = Optional.absent();

        Resource superTypeResource = searchForResourceSuperType(componentNode.getResource());

        if (superTypeResource != null) {
            resourceSuperType = Optional.of((ResourceType) new DefaultResourceType(superTypeResource));
        }

        return resourceSuperType;

    }

    public Iterable<ResourceType> getResourceSuperTypes() {

        if (resourceSuperTypes != null) {
            return resourceSuperTypes;
        }

        resourceSuperTypes = Lists.newArrayList();
        Optional<ResourceType> currentResourceType = this.getResourceSuperType();

        while (currentResourceType.isPresent()) {
            resourceSuperTypes.add(currentResourceType.get());
        }

        return resourceSuperTypes;

    }

    private boolean hasProperty(String name) {
        try {
            return componentNode.getNode().isPresent() && componentNode.getNode().get().hasProperty(name);
        } catch (RepositoryException e) {
            LOG.error("Repository Exception encountered checking for property name " + name + " on node " + componentNode.getPath(), e);
            return false;
        }
    }

    private Resource searchForResourceSuperType(Resource currentResourceType) {
        if (StringUtils.isBlank(currentResourceType.getResourceSuperType())) {
            return null;
        }

        for (String searchPath : currentResourceType.getResourceResolver().getSearchPath()) {
            Resource potentialSuperType = currentResourceType.getResourceResolver().getResource(searchPath + "/" + currentResourceType.getResourceSuperType());

            if (potentialSuperType != null) {
                return potentialSuperType;
            }
        }

        return null;
    }

}
