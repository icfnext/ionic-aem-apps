package com.icfi.aem.apps.ionic.core.adapters;

import com.citytechinc.aem.bedrock.api.page.PageDecorator;
import com.icfi.aem.apps.ionic.core.models.application.root.impl.DefaultApplicationRoot;
import com.icfi.aem.apps.ionic.core.predicates.application.root.ApplicationRootPagePredicate;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Properties;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Service;
import org.apache.sling.api.SlingConstants;
import org.apache.sling.api.adapter.AdapterFactory;
import org.apache.sling.api.resource.Resource;
import org.osgi.framework.Constants;

@Component
@Service(AdapterFactory.class)
@Properties({
        @Property(name = Constants.SERVICE_DESCRIPTION, value = "ApplicationRoot Adapter Factory"),
        @Property(name = SlingConstants.PROPERTY_ADAPTABLE_CLASSES, value = {
                "org.apache.sling.api.resource.Resource",
                "com.citytechinc.aem.bedrock.api.page.PageDecorator"
        }),
        @Property(name = SlingConstants.PROPERTY_ADAPTER_CLASSES, value = {
                "com.icfi.aem.apps.ionic.api.models.application.root.ApplicationRoot"
        })
    })
public class ApplicationRootAdapterFactory implements AdapterFactory {

    private static final ApplicationRootPagePredicate applicationRootPagePredicate = new ApplicationRootPagePredicate();

    @Override
    public <AdapterType> AdapterType getAdapter(Object adaptable, Class<AdapterType> type) {
        if (adaptable instanceof Resource) {
            return getPageDecoratorAdapter(((Resource) adaptable).adaptTo(PageDecorator.class), type);
        }
        if (adaptable instanceof PageDecorator) {
            return getPageDecoratorAdapter((PageDecorator) adaptable, type);
        }

        return null;
    }

    public <AdapterType> AdapterType getPageDecoratorAdapter(PageDecorator pageDecorator, Class<AdapterType> type) {
        if (applicationRootPagePredicate.apply(pageDecorator)) {
            return (AdapterType) new DefaultApplicationRoot(pageDecorator);
        }

        return null;
    }

}