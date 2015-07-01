package com.icfi.aem.apps.ionic.core.components.content.ioncontent;


import com.citytechinc.aem.bedrock.api.components.annotations.AutoInstantiate;
import com.citytechinc.aem.bedrock.core.components.AbstractComponent;
import com.citytechinc.cq.component.annotations.Component;
import com.citytechinc.cq.component.annotations.DialogField;
import com.citytechinc.cq.component.annotations.Option;
import com.citytechinc.cq.component.annotations.widgets.Selection;

@Component(value = "Ion Content", group = ".hidden", disableTargeting = true, actions = {"text:Ion Content", "-", "edit"})
@AutoInstantiate(instanceName = "ioncontent")
public class IonContent extends AbstractComponent {

    @DialogField(fieldLabel = "Disable Scrolling")
    @Selection(type = Selection.CHECKBOX, options = {@Option(value = "true")})
    public boolean isScrollDisabled() {
        return get("scrollDisabled", false);
    }

    public String getPageName() {
        return getCurrentPage().getName();
    }

}
