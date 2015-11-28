package com.icfi.aem.apps.ionic.api.models.contentsync.root;

public interface ContentSyncRoot {

    String RESOURCE_TYPE = "ionic-aem-apps/components/page/content-sync-root";

    String getApplicationRootPath();

    String getApplicationName();

    boolean isApplicationRootConfigured();
}
