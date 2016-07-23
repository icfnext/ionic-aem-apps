package com.citytechinc.aem.apps.ionic.core.contentsync.handler;

import com.adobe.granite.ui.clientlibs.ClientLibrary;
import com.adobe.granite.ui.clientlibs.HtmlLibrary;
import com.adobe.granite.ui.clientlibs.LibraryType;
import com.citytechinc.aem.apps.ionic.api.models.application.root.ApplicationRoot;
import com.day.cq.commons.jcr.JcrUtil;
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
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.Binary;
import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import java.io.IOException;
import java.util.*;

@Component(factory="com.day.cq.contentsync.handler.ContentUpdateHandler/cloudserviceclientlib", inherit=true)
@Service
public class CloudServiceClientLibraryUpdateHandler extends AbstractSlingResourceUpdateHandler {

    private static final Logger LOG = LoggerFactory.getLogger(CloudServiceClientLibraryUpdateHandler.class);

    @Reference
    private HtmlLibraryManager htmlLibraryManager;

    @Override
    public boolean updateCacheEntry(ConfigEntry configEntry, Long lastUpdated, String configCacheRoot, Session admin, Session session) {
        configCacheRoot = getConfigCacheRoot(configEntry, configCacheRoot);
        boolean changed = false;

        try {
            ResourceResolver resourceResolver = resourceResolverFactory.getResourceResolver(Collections.<String, Object>singletonMap("user.jcr.session", admin));
            PageManagerDecorator pageManagerDecorator = resourceResolver.adaptTo(PageManagerDecorator.class);
            ConfigurationManager configurationManager = resourceResolver.adaptTo(ConfigurationManager.class);
            ComponentManager componentManager = resourceResolver.adaptTo(ComponentManager.class);

            PageDecorator page = pageManagerDecorator.getPage(getResolvedContentPath(configEntry));

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
                    htmlLibraryManager.getLibraries(clientLibraryCategories.toArray(new String[clientLibraryCategories.size()]), null, false, false);

            //render the libraries to appropriate locations
            for(ClientLibrary currentClientLibrary : requiredLibraries) {
                //TODO: allow for CSS as well
                //TODO: allow for minification property?
                //TODO: the oob HtmlLibraryUpdateHandler is checking an isProxy property on the library - figure out what that's about
                HtmlLibrary currentJsLibrary = htmlLibraryManager.getLibrary(LibraryType.JS, currentClientLibrary.getPath());

                if (currentJsLibrary != null && lastUpdated < currentJsLibrary.getLastModified()) {
                    String cachePath = configCacheRoot + currentJsLibrary.getPath();

                    JcrUtil.createPath(cachePath, "sling:Folder", "nt:file", admin, false);
                    Node contentNode = JcrUtil.createPath(cachePath + "/jcr:content", "nt:resource", admin);
                    Binary bin = admin.getValueFactory().createBinary(currentJsLibrary.getInputStream());
                    contentNode.setProperty("jcr:data", bin);
                    contentNode.setProperty("jcr:lastModified", Calendar.getInstance());

                    changed = true;
                }
            }


        } catch (LoginException e) {
            LOG.error("Login Exception caught processing Cloud Service client libraries", e);
        } catch (RepositoryException e) {
            LOG.error("Repository Exception caught processing Cloud Service client libraries", e);
        } catch (IOException e) {
            LOG.error("IO Exception caught processing Cloud Service client libraries", e);
        }
        return changed;
    }
}
