package com.icfi.aem.apps.ionic.core.components.page.contentsyncroot;

import com.citytechinc.aem.bedrock.core.components.AbstractComponent;
import com.icfi.aem.apps.ionic.api.models.contentsync.root.ContentSyncRoot;

public class ContentSyncRootComponent  extends AbstractComponent {

    public ContentSyncRoot getContentSyncRoot() {
        return getCurrentPage().adaptTo(ContentSyncRoot.class);
    }

}
