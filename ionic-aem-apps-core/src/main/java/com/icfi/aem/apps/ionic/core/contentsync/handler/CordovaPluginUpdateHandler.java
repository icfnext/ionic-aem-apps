package com.icfi.aem.apps.ionic.core.contentsync.handler;

import com.citytechinc.aem.bedrock.api.page.PageDecorator;
import com.citytechinc.aem.bedrock.api.page.PageManagerDecorator;
import com.day.cq.commons.jcr.JcrUtil;
import com.day.cq.contentsync.handler.AbstractSlingResourceUpdateHandler;
import com.day.cq.contentsync.config.ConfigEntry;
import com.icfi.aem.apps.ionic.api.models.application.root.ApplicationRoot;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Service;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.commons.json.JSONArray;
import org.apache.sling.commons.json.JSONException;
import org.apache.sling.commons.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import java.io.ByteArrayInputStream;
import java.util.Calendar;
import java.util.Iterator;
import java.util.Set;

@Component(metatype=true, factory="com.day.cq.contentsync.handler.ContentUpdateHandler/cordovaplugins", inherit=true)
@Service
public class CordovaPluginUpdateHandler extends AbstractSlingResourceUpdateHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(CordovaPluginUpdateHandler.class);
    private static final String APPLICATION_ROOT_PATH_KEY = "applicationRootPath";
    private static final String PLUGINS_ROOT_KEY = "plugins";
    private static final String PLUGIN_ITEM_KEY = "plugin";
    private static final String PLUGINS_FILE_NAME = "pge-plugins.json";


    public boolean updateCacheEntry(ConfigEntry configEntry, Long lastUpdated, String configCacheRoot, Session adminSession, Session userSession)
    {
        configCacheRoot = getConfigCacheRoot(configEntry, configCacheRoot);

        try
        {
            Node cacheFolder = adminSession.getNode(configCacheRoot);
            ResourceResolver resourceResolver = this.resolverFactory.getResourceResolver(adminSession);

            String applicationRootPath = configEntry.getValue(APPLICATION_ROOT_PATH_KEY);

            if(applicationRootPath == null){
                LOGGER.error("applicationRootPath not configured for: " + configEntry.getPath());
                return false;
            }

            PageDecorator decorator = resourceResolver.adaptTo(PageManagerDecorator.class).getPage(applicationRootPath);

            JSONObject contentPackagesJSON = new JSONObject();
            JSONArray contentPackagesJSONArray = new JSONArray();
            contentPackagesJSON.put(PLUGINS_ROOT_KEY, contentPackagesJSONArray);

            ApplicationRoot root = decorator.adaptTo(ApplicationRoot.class);
            Set<String> plugins = root.getRequiredPlugins();

            addPluginsResource(contentPackagesJSONArray, plugins);

            addListingFile(contentPackagesJSON, cacheFolder.getPath() + "/" + PLUGINS_FILE_NAME, adminSession);
        }
        catch (Exception ex) {
            LOGGER.error("Unexpected error while updating cache for config xml: " + configEntry.getPath(), ex);
        }
        return false;
    }

    private void addListingFile(JSONObject fileListing, String cachePath, Session adminSession)
            throws RepositoryException, JSONException
    {
        JcrUtil.createPath(cachePath, "sling:Folder", "nt:file", adminSession, false);
        Node cacheContentNode = JcrUtil.createPath(cachePath + "/jcr:content", "nt:resource", adminSession);
        Calendar calTS = Calendar.getInstance();
        cacheContentNode.setProperty("jcr:data", adminSession.getValueFactory().createBinary(new ByteArrayInputStream(fileListing.toString(3).getBytes())));

        cacheContentNode.setProperty("jcr:lastModified", calTS);
        adminSession.save();
    }

    private void addPluginsResource(JSONArray pluginContentJSON, Iterable<String> plugins)
            throws JSONException
    {
        Iterator<String> pluginContentIterator = plugins.iterator();
        while (pluginContentIterator.hasNext())
        {
            String plugin = pluginContentIterator.next();
            JSONObject updatePluginJSON = new JSONObject();
            updatePluginJSON.put(PLUGIN_ITEM_KEY, plugin);
            pluginContentJSON.put(updatePluginJSON);
        }
    }

}
