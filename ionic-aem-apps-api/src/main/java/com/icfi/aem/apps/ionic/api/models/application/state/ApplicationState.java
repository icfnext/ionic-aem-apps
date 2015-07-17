package com.icfi.aem.apps.ionic.api.models.application.state;

import com.icfi.aem.apps.ionic.api.models.application.root.ApplicationRoot;
import org.apache.sling.api.resource.Resource;

import java.util.Set;

public interface ApplicationState {

    String RESOURCE_TYPE = "ionic-aem-apps/components/page/application-state";
    String ANGULAR_CONTROLLER_KEY = "angularController";

    String getUrl();

    String getTemplate();

    String getId();

    String getPath();

    Resource getContentResource();

    String getAngularController();

    boolean isAbstract();

    boolean isStructuralState();

    ApplicationRoot getApplicationRoot();

}
