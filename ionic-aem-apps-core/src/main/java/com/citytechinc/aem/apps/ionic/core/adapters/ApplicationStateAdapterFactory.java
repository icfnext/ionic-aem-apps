package com.citytechinc.aem.apps.ionic.core.adapters;

import com.citytechinc.aem.apps.ionic.core.models.application.state.impl.DefaultApplicationState;
import com.citytechinc.aem.apps.ionic.core.predicates.application.root.ApplicationRootPagePredicate;
import com.citytechinc.aem.apps.ionic.core.predicates.application.root.ApplicationStatePagePredicate;
import com.citytechinc.aem.bedrock.api.page.PageDecorator;
import com.google.common.base.Optional;
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
        @Property(name = Constants.SERVICE_DESCRIPTION, value = "ApplicationState Adapter Factory"),
        @Property(name = SlingConstants.PROPERTY_ADAPTABLE_CLASSES, value = {
                "org.apache.sling.api.resource.Resource",
                "com.citytechinc.aem.bedrock.api.page.PageDecorator"
        }),
        @Property(name = SlingConstants.PROPERTY_ADAPTER_CLASSES, value = {
                "com.citytechinc.aem.apps.ionic.api.models.application.state.ApplicationState"
        })
})
public class ApplicationStateAdapterFactory implements AdapterFactory {

    private static final ApplicationStatePagePredicate applicationStatePagePredicate = new ApplicationStatePagePredicate();
    private static final ApplicationRootPagePredicate applicationRootPagePredicate = new ApplicationRootPagePredicate();

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
        if (applicationStatePagePredicate.apply(pageDecorator)) {

            Optional<PageDecorator> rootPageOptional = pageDecorator.findAncestor(applicationRootPagePredicate);

            if (rootPageOptional.isPresent()) {
                return (AdapterType) new DefaultApplicationState(pageDecorator, rootPageOptional.get());
            }

        }

        return null;
    }

}
