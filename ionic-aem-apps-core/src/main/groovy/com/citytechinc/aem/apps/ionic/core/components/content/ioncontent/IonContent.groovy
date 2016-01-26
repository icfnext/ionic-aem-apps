package com.citytechinc.aem.apps.ionic.core.components.content.ioncontent

import com.citytechinc.aem.bedrock.api.components.annotations.AutoInstantiate
import com.citytechinc.cq.component.annotations.Component
import com.citytechinc.cq.component.annotations.DialogField
import com.citytechinc.cq.component.annotations.widgets.Switch
import org.apache.sling.api.resource.Resource
import org.apache.sling.models.annotations.Default
import org.apache.sling.models.annotations.Model

import javax.inject.Inject

@Component(value = "Ion Content", group = ".hidden", disableTargeting = true, actions = ["text:Ion Content", "-", "edit"])
@AutoInstantiate(instanceName = "ioncontent")
@Model(adaptables = Resource)
public class IonContent {

    @DialogField(fieldLabel = "Disable Scrolling")
    @Switch
    @Inject @Default(booleanValues = false)
    boolean scrollDisabled

}
