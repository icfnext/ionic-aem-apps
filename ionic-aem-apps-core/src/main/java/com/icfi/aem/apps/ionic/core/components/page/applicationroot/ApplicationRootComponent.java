package com.icfi.aem.apps.ionic.core.components.page.applicationroot;

import com.citytechinc.aem.bedrock.core.components.AbstractComponent;
import com.icfi.aem.apps.ionic.api.models.application.root.ApplicationRoot;

public class ApplicationRootComponent extends AbstractComponent {

    public ApplicationRoot getApplicationRoot() {
        return getCurrentPage().adaptTo(ApplicationRoot.class);
    }

}
