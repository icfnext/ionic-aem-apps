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
    private final String applicationName;
    private final boolean applicationRootConfigured;
    private final String applicationParentDirectoy;

    public DefaultContentSyncRoot(PageDecorator rootPage) {

        this.rootPage = rootPage;

        PageDecorator parentPage = this.rootPage.getParent();

        List<PageDecorator> applicationRootList = parentPage.getChildren(new ApplicationRootPagePredicate());

        this.applicationRoot = (applicationRootList.size() == 1) ?
                applicationRootList.get(0).adaptTo(ApplicationRoot.class) : null;

        this.applicationRootPath = (this.applicationRoot == null) ?
                "" : this.applicationRoot.getPath() + ".html";

        this.applicationRootConfigured = (this.applicationRoot == null) || applicationRootPath.isEmpty()  ?
                false : true;

        this.applicationParentDirectoy = this.applicationRoot.getApplicationDirectory();

        this.applicationRoot.getPath();

        this.applicationName = (this.applicationRoot != null) ? this.applicationRoot.getApplicationName() : null;
    }

    public String getApplicationRootPath() {
        return applicationRootPath;
    }

    public String getApplicationName() {
        return applicationName;
    }

    public boolean isApplicationRootConfigured() {
        return applicationRootConfigured;
    }

    public String getApplicationParentDirectoy() {
        return applicationParentDirectoy;
    }

    public boolean isCloseSplashPage() {
        return rootPage.get("autoCloseSplashScreen", false);
    }
}
