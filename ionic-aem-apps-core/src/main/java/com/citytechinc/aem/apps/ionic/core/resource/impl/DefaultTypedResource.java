package com.citytechinc.aem.apps.ionic.core.resource.impl;

import com.citytechinc.aem.apps.ionic.api.resource.ResourceType;
import com.citytechinc.aem.apps.ionic.api.resource.TypedResource;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;

public class DefaultTypedResource implements TypedResource {

    private final Resource resource;
    private final ResourceResolver administrativeResourceResolver;

    public DefaultTypedResource(Resource resource, ResourceResolver administrativeResourceResolver) {
        this.resource = resource;
        this.administrativeResourceResolver = administrativeResourceResolver;
    }

    public ResourceType getResourceType() {
        for (String currentSearchPath : administrativeResourceResolver.getSearchPath()) {
            Resource potentialResourceType = administrativeResourceResolver.getResource(currentSearchPath + resource.getResourceType());
            if (potentialResourceType != null) {
                return new DefaultResourceType(potentialResourceType);
            }
        }

        return null;
    }
}
