package com.icfi.aem.apps.ionic.core.components.page.contentsyncroot;

import com.citytechinc.aem.bedrock.api.page.PageDecorator;
import com.citytechinc.aem.bedrock.core.components.AbstractComponent;
import com.icfi.aem.apps.ionic.api.models.contentsync.root.ContentSyncRoot;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;

import javax.inject.Inject;

@Model(adaptables = Resource.class)
public class ContentSyncRootComponent  extends AbstractComponent {

    @Inject
    private PageDecorator currentPage;

    public ContentSyncRoot getContentSyncRoot() {
        return currentPage.adaptTo(ContentSyncRoot.class);
    }

}
