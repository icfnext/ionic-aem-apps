package com.icfi.aem.apps.ionic.core.contentsync.handler;

import com.adobe.cq.mobile.platform.MobileResource;
import com.adobe.cq.mobile.platform.MobileResourceLocator;
import com.adobe.cq.mobile.platform.MobileResourceType;
import com.adobe.cq.mobile.platform.impl.AppInstanceProviderImpl;
import com.adobe.cq.mobile.platform.impl.MobileAppProvider;
import com.adobe.cq.mobile.platform.impl.WidgetConfigDocument;
import com.citytechinc.aem.bedrock.api.page.PageDecorator;
import com.citytechinc.aem.bedrock.api.page.PageManagerDecorator;
import com.day.cq.commons.jcr.JcrUtil;
import com.day.cq.contentsync.handler.AbstractSlingResourceUpdateHandler;
import com.day.cq.contentsync.config.ConfigEntry;
import com.day.cq.wcm.api.Page;
import com.icfi.aem.apps.ionic.api.models.application.root.ApplicationRoot;
import com.icfi.aem.apps.ionic.core.models.application.root.impl.DefaultApplicationRoot;
import org.apache.commons.io.IOUtils;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Service;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.commons.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Calendar;
import java.util.Set;

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

            Resource configResource = resourceResolver.getResource(resource.getPath() + "/www/config.xml");

            PageDecorator decorator = resourceResolver.adaptTo(PageManagerDecorator.class).getPage("/content/phonegap/circuit-2015-app/en/home");

            /*
            MobileResourceLocator mobileResMgr = (MobileResourceLocator)resourceResolver.adaptTo(MobileResourceLocator.class);
            MobileResource instanceResource = mobileResMgr.findClosestResourceByType(resource, MobileResourceType.INSTANCE.getType(), new String[0]);
            if (instanceResource != null) {
                MobileAppProvider appProvider = (MobileAppProvider) instanceResource.adaptTo(MobileAppProvider.class);
                if ((appProvider instanceof AppInstanceProviderImpl)) {
                    String widgetConfigPath = (String) appProvider.getProperties().get("widgetConfigPath", String.class);
                    Resource appContent = ((AppInstanceProviderImpl) appProvider).getContentResource();
                    WidgetConfigDocument configDocument = WidgetConfigDocument.loadConfigDocument(widgetConfigPath, appContent);
                }
            }*/

            ApplicationRoot root = decorator.adaptTo(ApplicationRoot.class);

            Set<String> plugins = root.getRequiredPlugins();

            /* XML Parsing code */
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();

            String configXML = "<?xml version='1.0' encoding='utf-8'?>"+
            "<widget id=\"com.citytechinc.things.beermunity\" version=\"1.0.0\" xmlns=\"http://www.w3.org/ns/widgets\" xmlns:gap=\"http://phonegap.com/ns/1.0\">" +
            "<name>Circuit 2015 App</name>" +
            "</widget>";

            InputStream stream = IOUtils.toInputStream(configXML,"UTF-8");
            Document doc = db.parse(stream);


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
