package com.icfi.aem.apps.ionic.core.components.content.ionfooterbar;

import com.citytechinc.aem.bedrock.api.components.annotations.AutoInstantiate;
import com.citytechinc.aem.bedrock.core.components.AbstractComponent;
import com.citytechinc.cq.component.annotations.Component;
import com.citytechinc.cq.component.annotations.DialogField;
import com.citytechinc.cq.component.annotations.Option;
import com.citytechinc.cq.component.annotations.widgets.Selection;

@Component(value = "Ion Footer Bar", group = ".hidden")
@AutoInstantiate(instanceName = "ionfooterbar")
public class IonFooterBar extends AbstractComponent {

    @DialogField(fieldLabel = "Always Show", fieldDescription = "Always show the footer bar, even when empty.  By default the footer bar will be suppressed if no content is included")
    @Selection(
            type = Selection.CHECKBOX,
            options = {
                    @Option(value = "true")
            }
    )
    public boolean isAlwaysShow() {
        return get("alwaysShow", false);
    }

    public boolean isHasContent() {
        return getComponentNodes("footer-content").size() > 0;
    }

    public String getPageName() {
        return getCurrentPage().getName();
    }

}
