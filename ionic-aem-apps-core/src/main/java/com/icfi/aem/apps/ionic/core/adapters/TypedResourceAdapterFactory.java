package com.icfi.aem.apps.ionic.core.adapters;

import com.icfi.aem.apps.ionic.core.resource.impl.DefaultTypedResource;
import org.apache.commons.lang3.StringUtils;
import org.apache.felix.scr.annotations.*;
import org.apache.sling.api.SlingConstants;
import org.apache.sling.api.adapter.AdapterFactory;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.osgi.framework.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
@Service(AdapterFactory.class)
@Properties({
        @Property(name = Constants.SERVICE_DESCRIPTION, value = "Typed Resource Adapter Factory"),
        @Property(name = SlingConstants.PROPERTY_ADAPTABLE_CLASSES, value = {
                "org.apache.sling.api.resource.Resource"
        }),
        @Property(name = SlingConstants.PROPERTY_ADAPTER_CLASSES, value = {
                "com.icfi.aem.apps.ionic.api.resource.TypedResource"
        })
})
public class TypedResourceAdapterFactory implements AdapterFactory {

    private static final Logger LOG = LoggerFactory.getLogger(TypedResourceAdapterFactory.class);

    @Reference
    private ResourceResolverFactory resourceResolverFactory;

    private ResourceResolver resourceResolver;

    public <AdapterType> AdapterType getAdapter(Object adaptable, Class<AdapterType> type) {
        if (adaptable instanceof Resource) {
            return getResourceAdapter((Resource) adaptable, type);
        }

        return null;
    }

    public <AdapterType> AdapterType getResourceAdapter(Resource resource, Class<AdapterType> type) {
        try {
            if (StringUtils.isNotBlank(resource.getResourceType()) && getAdministrativeResourceResolver().getResource(resource.getResourceType()) != null) {
                return (AdapterType) new DefaultTypedResource(resource, getAdministrativeResourceResolver());
            }
        } catch (LoginException e) {
            LOG.error("Login Exception encountered acquiring an administrative Resource Resolver", e);
        }

        return null;
    }

    @Deactivate
    protected void deactivate() {

        closeResourceResolver();

    }

    protected final ResourceResolver getAdministrativeResourceResolver() throws LoginException {
        if (resourceResolver == null) {
            resourceResolver = resourceResolverFactory.getAdministrativeResourceResolver(null);
        }

        return resourceResolver;
    }

    protected final void closeResourceResolver() {
        if (resourceResolver != null) {
            resourceResolver.close();
        }
    }
}
