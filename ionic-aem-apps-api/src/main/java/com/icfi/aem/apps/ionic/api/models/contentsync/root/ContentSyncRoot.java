package com.icfi.aem.apps.ionic.api.models.contentsync.root;

import com.icfi.aem.apps.ionic.api.models.application.root.ApplicationRoot;

public interface ContentSyncRoot {

    String RESOURCE_TYPE = "ionic-aem-apps/components/page/content-sync-root";

    String getApplicationRootPath();

    String getApplicationName();

    boolean isApplicationRootConfigured();
}
