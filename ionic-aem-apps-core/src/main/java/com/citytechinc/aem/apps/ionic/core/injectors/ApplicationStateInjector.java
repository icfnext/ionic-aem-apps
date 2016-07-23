package com.citytechinc.aem.apps.ionic.core.injectors;

import com.citytechinc.aem.apps.ionic.api.models.application.root.ApplicationRoot;
import com.citytechinc.aem.apps.ionic.api.models.application.state.ApplicationState;
import com.icfolson.aem.library.api.page.PageDecorator;
import com.icfolson.aem.library.api.page.PageManagerDecorator;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Service;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.spi.DisposalCallbackRegistry;
import org.apache.sling.models.spi.Injector;
import org.osgi.framework.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Type;

@Component
@Service
@Property(name = Constants.SERVICE_RANKING, intValue = 1000)
public class ApplicationStateInjector implements Injector {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationStateInjector.class);

    @Nonnull
    @Override
    public String getName() {
        return "application-state";
    }

    @Override
    public Object getValue(Object adaptable, String name, Type declaredType, AnnotatedElement annotatedElement, DisposalCallbackRegistry disposalCallbackRegistry) {

        if(declaredType == ApplicationState.class){

            LOGGER.debug("ApplicationState injector activated.  Attempting to inject ApplicationState");

            Resource resource = getResource(adaptable);
            if(resource == null){
                LOGGER.error("Unable to get resource from adaptable argument of type: " + adaptable.getClass().toString());
                return null;
            }

            PageManagerDecorator pageManager = resource.getResourceResolver().adaptTo(PageManagerDecorator.class);

            if(pageManager == null){
                LOGGER.error("Unable to get page manager from adaptable argument of type: " + adaptable.getClass().toString());
                return null;
            }

            PageDecorator containingPage = pageManager.getPage(pageManager.getContainingPage(resource));

            if(containingPage == null){
                LOGGER.error("Unable to get containing page from resource of type: " + resource.getResourceType().toString());
                return null;
            }

            ApplicationState applicationState = containingPage.adaptTo(ApplicationState.class);

            if(applicationState == null){
                LOGGER.error("Unable to adapt ApplicationState from page at path: " + containingPage.getPath().toString());
                return null;
            }

            return applicationState;
        }

        return null;
    }

    private Resource getResource(Object adaptable) {
        if (adaptable instanceof Resource) {
            return ((Resource) adaptable);
        } else {
            return null;
        }
    }
}
