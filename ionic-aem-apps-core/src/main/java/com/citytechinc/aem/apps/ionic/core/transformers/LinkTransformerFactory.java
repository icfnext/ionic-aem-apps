package com.citytechinc.aem.apps.ionic.core.transformers;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Properties;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Service;
import org.apache.sling.rewriter.Transformer;
import org.apache.sling.rewriter.TransformerFactory;

@Component(
        label = "Ionic AEM Apps Link Transformer Factory",
        description = "Transforms a tag hrefs into fragment references when in Preview and Publish.",
        metatype = true)
@Service
@Properties(
        value = {
                @Property(name="pipeline.mode",value="global") ,
                @Property(name="service.ranking",value="-5000")
        })
public class LinkTransformerFactory implements TransformerFactory {

    public Transformer createTransformer() {
        return new LinkTransformer();
    }

}
