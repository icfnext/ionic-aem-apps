package com.citytechinc.aem.apps.ionic.core.transformers;

import com.day.cq.wcm.api.WCMMode;
import com.citytechinc.aem.apps.ionic.api.models.application.root.ApplicationRoot;
import com.citytechinc.aem.apps.ionic.api.models.application.state.ApplicationState;
import org.apache.cocoon.xml.sax.AbstractSAXPipe;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.rewriter.ProcessingComponentConfiguration;
import org.apache.sling.rewriter.ProcessingContext;
import org.apache.sling.rewriter.Transformer;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import java.io.IOException;

public class LinkTransformer extends AbstractSAXPipe implements Transformer {

    private boolean transform = false;
    private ProcessingContext processingContext;

    private static final String HREF_ATTRIBUTE = "href";
    private static final String A_TAG = "a";

    public void init(ProcessingContext processingContext, ProcessingComponentConfiguration processingComponentConfiguration) throws IOException {
        this.processingContext = processingContext;

        Resource requestedResource = processingContext.getRequest().getResource();
        WCMMode currentMode = WCMMode.fromRequest(processingContext.getRequest());

        if (WCMMode.PREVIEW.equals(currentMode) || WCMMode.DISABLED.equals(currentMode)) {
            if (requestedResource != null && (requestedResource.isResourceType(ApplicationRoot.RESOURCE_TYPE) || requestedResource.isResourceType(ApplicationState.RESOURCE_TYPE))) {
                transform = true;
            }
        }
    }

    public void dispose() {

    }

    @Override
    public void startElement (String nsUri, String localname, String rawName, Attributes atts) throws SAXException {
        AttributesImpl linkAttributes = new AttributesImpl(atts);

        if (transform && A_TAG.equals(localname)) {
            for (int i=0; i < linkAttributes.getLength(); i++) {
                if (HREF_ATTRIBUTE.equals(linkAttributes.getLocalName(i))) {
                    linkAttributes.setValue(i, transformHref(linkAttributes.getValue(i)));
                }
            }
        }

        super.startElement(nsUri, localname, rawName, linkAttributes);
    }

    //TODO: This is a rather naive implementation at the moment - specifically it does not handle links to pages which are not ApplicationStates well
    private String transformHref(String href) {
        //TODO: This is a hack to get around an OOB transformer which is jacking with our hrefs before we get a stab at them
        if (href.startsWith("application/")) {
            return "#" + href.substring("application".length()).replace(".html", "");
        }
        if (!href.startsWith("/") && !href.startsWith("../")) {
            return href;
        }

        String hrefPath = getResourcePathForHref(href);

        Resource referencedResource = processingContext.getRequest().getResourceResolver().getResource(hrefPath);

        if (referencedResource == null) {
            return "#" + hrefPath;
        }

        ApplicationState referencedState = referencedResource.adaptTo(ApplicationState.class);

        if (referencedState == null) {
            return "#" + hrefPath;
        }

        return "#" + hrefPath.substring(referencedState.getApplicationRoot().getPath().length());

    }

    private static String getResourcePathForHref(String href) {
        if (href.startsWith("../")) {
            return href.substring(href.lastIndexOf("../") + 2, href.lastIndexOf("."));
        }

        return href.split("\\.")[0];
    }

}
