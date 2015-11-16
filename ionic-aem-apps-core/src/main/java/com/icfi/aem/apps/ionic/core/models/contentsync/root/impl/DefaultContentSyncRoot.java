package com.icfi.aem.apps.ionic.core.models.contentsync.root.impl;

import com.citytechinc.aem.bedrock.api.page.PageDecorator;
import com.icfi.aem.apps.ionic.api.models.application.root.ApplicationRoot;
import com.icfi.aem.apps.ionic.api.models.contentsync.root.ContentSyncRoot;
import com.icfi.aem.apps.ionic.core.predicates.application.root.ApplicationRootPagePredicate;

import java.util.List;

public class DefaultContentSyncRoot implements ContentSyncRoot {

    private final PageDecorator rootPage;

    private final ApplicationRoot applicationRoot;
    private final String applicationRootPath;

    public DefaultContentSyncRoot(PageDecorator rootPage) {

        this.rootPage = rootPage;

        List<PageDecorator> applicationRootList = this.rootPage.getChildren(new ApplicationRootPagePredicate());

        this.applicationRoot = (applicationRootList.size() == 1) ?
                applicationRootList.get(0).adaptTo(ApplicationRoot.class) : null;

        this.applicationRootPath = rootPage.get("applicationRootPath", String.class).get();
    }

    public ApplicationRoot getApplicationRoot() {
        return applicationRoot;
    }

    public String getApplicationRootPath() {
        return applicationRootPath;
    }
}
