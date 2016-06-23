package com.citytechinc.aem.apps.ionic.core.contentsync.handler;

import com.adobe.granite.ui.clientlibs.ClientLibrary;
import com.citytechinc.aem.apps.ionic.api.models.application.root.ApplicationRoot;
import com.day.cq.contentsync.config.ConfigEntry;
import com.day.cq.contentsync.handler.AbstractSlingResourceUpdateHandler;
import com.day.cq.wcm.api.components.ComponentManager;
import com.day.cq.wcm.webservicesupport.Configuration;
import com.day.cq.wcm.webservicesupport.ConfigurationManager;
import com.google.common.collect.Sets;
import com.icfolson.aem.library.api.page.PageDecorator;
import com.icfolson.aem.library.api.page.PageManagerDecorator;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.Service;
import com.adobe.granite.ui.clientlibs.HtmlLibraryManager;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;

import javax.jcr.Session;
import java.util.*;

@Component(factory="com.day.cq.contentsync.handler.ContentUpdateHandler/cloudserviceclientlib", inherit=true)
@Service
public class CloudServiceClientLibraryUpdateHandler extends AbstractSlingResourceUpdateHandler {

    @Reference
    private HtmlLibraryManager htmlLibraryManager;

    @Reference
    private ResourceResolverFactory resourceResolverFactory;

    @Override
    public boolean updateCacheEntry(ConfigEntry configEntry, Long lastUpdated, String configCacheRoot, Session admin, Session session) {
        try {
            ResourceResolver resourceResolver = resourceResolverFactory.getResourceResolver(Collections.<String, Object>singletonMap("user.jcr.session", admin));
            PageManagerDecorator pageManagerDecorator = resourceResolver.adaptTo(PageManagerDecorator.class);
            ConfigurationManager configurationManager = resourceResolver.adaptTo(ConfigurationManager.class);
            ComponentManager componentManager = resourceResolver.adaptTo(ComponentManager.class);

            PageDecorator page = pageManagerDecorator.getPage(configEntry.getContentPath());

            //lookup client libraries associated with cloud configs
            Set<String> clientLibraryCategories = Sets.newHashSet();

            for(Iterator<Configuration> configurations = configurationManager.getConfigurations(page.getContentResource()); configurations.hasNext();) {
                Configuration currentConfiguration = configurations.next();

                com.day.cq.wcm.api.components.Component configurationComponent = componentManager.getComponentOfResource(currentConfiguration.getContentResource());

                if (configurationComponent != null) {
                    clientLibraryCategories.addAll(Sets.newHashSet(configurationComponent.getProperties().get(ApplicationRoot.REQUIRED_CLIENT_LIBRARY_CATEGORIES, new String[]{})));
                }
            }

            //lookup libraries for categories
            Collection<ClientLibrary> requiredLibraries =
                    htmlLibraryManager.getLibraries(clientLibraryCategories.toArray(new String[clientLibraryCategories.size()]), null, false, true);

            //render the libraries to appropriate locations

        } catch (LoginException e) {
            e.printStackTrace();
        }
        return false;
    }
}
