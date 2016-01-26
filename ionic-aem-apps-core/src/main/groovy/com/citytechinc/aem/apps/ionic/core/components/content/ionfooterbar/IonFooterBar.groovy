package com.citytechinc.aem.apps.ionic.core.components.content.ionfooterbar

import com.citytechinc.aem.bedrock.api.components.annotations.AutoInstantiate
import com.citytechinc.cq.component.annotations.Component
import com.citytechinc.cq.component.annotations.DialogField
import com.citytechinc.cq.component.annotations.widgets.Switch
import org.apache.sling.api.resource.Resource
import org.apache.sling.models.annotations.Default
import org.apache.sling.models.annotations.Model
import org.apache.sling.models.annotations.Optional

import javax.inject.Inject
import javax.inject.Named

@Component(value = "Ion Footer Bar", group = ".hidden")
@AutoInstantiate(instanceName = "ionfooterbar")
@Model(adaptables = Resource)
public class IonFooterBar {

    @DialogField(fieldLabel = "Always Show", fieldDescription = "Always show the footer bar, even when empty.  By default the footer bar will be suppressed if no content is included")
    @Switch
    @Inject @Default(booleanValues = false)
    boolean alwaysShow

    @Optional
    @Inject @Named("footer-content")
    List<Resource> footerContent

    public boolean isHasContent() {
        footerContent.size() > 0
    }

}
