package com.icfi.aem.apps.ionic.core.contentsync.handler;

import com.day.cq.commons.jcr.JcrUtil;
import com.day.cq.contentsync.handler.AbstractSlingResourceUpdateHandler;
import com.day.cq.contentsync.config.ConfigEntry;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Service;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.commons.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import java.io.ByteArrayInputStream;
import java.util.Calendar;

@Component(metatype=true, factory="com.day.cq.contentsync.handler.ContentUpdateHandler/cordovaplugins", inherit=true)
@Service
public class CordovaPluginUpdateHandler  extends AbstractSlingResourceUpdateHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(CordovaPluginUpdateHandler.class);
    private static final String CONTENT_CONFIG_NAME = "pat-config.xml";

    public boolean updateCacheEntry(ConfigEntry configEntry, Long lastUpdated, String configCacheRoot, Session adminSession, Session userSession)
    {
        configCacheRoot = getConfigCacheRoot(configEntry, configCacheRoot);

        try
        {
            Node cacheFolder = adminSession.getNode(configCacheRoot);
            ResourceResolver resourceResolver = this.resolverFactory.getResourceResolver(adminSession);
            Resource resource = resourceResolver.getResource(getResolvedContentPath(configEntry));

            addListingFile("This is pat's file.", cacheFolder.getPath() + "/" + CONTENT_CONFIG_NAME, adminSession);

            LOGGER.debug("resource: " + resource.getPath());
        }
        catch (Exception ex) {
            LOGGER.error("Unexpected error while updating cache for config xml: " + configEntry.getPath(), ex);
        }
        return false;
    }

    private void addListingFile(String fileData, String cachePath, Session adminSession) throws RepositoryException, JSONException {
        JcrUtil.createPath(cachePath, "sling:Folder", "nt:file", adminSession, false);
        Node cacheContentNode = JcrUtil.createPath(cachePath + "/jcr:content", "nt:resource", adminSession);
        Calendar calTS = Calendar.getInstance();
        cacheContentNode.setProperty("jcr:data", adminSession.getValueFactory().createBinary(new ByteArrayInputStream(fileData.getBytes())));

        cacheContentNode.setProperty("jcr:lastModified", calTS);
        adminSession.save();
    }

}
