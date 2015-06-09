package com.icfi.aem.apps.ionic.components.content;

import com.citytechinc.aem.bedrock.api.components.annotations.AutoInstantiate;
import com.citytechinc.cq.component.annotations.Component;
import com.citytechinc.cq.component.annotations.DialogField;
import com.citytechinc.aem.bedrock.core.components.AbstractComponent;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;

import javax.inject.Inject;

@Component("Test Component")
@AutoInstantiate(instanceName = "test")
@Model(adaptables = Resource.class)
public class TestComponent extends AbstractComponent {

    @Inject
    private String title;

    @DialogField(fieldLabel = "Test Title", fieldDescription = "Our title")
    public String getTitle() {
        return title;
    }

}
