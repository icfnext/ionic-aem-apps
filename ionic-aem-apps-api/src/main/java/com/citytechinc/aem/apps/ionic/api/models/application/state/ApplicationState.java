package com.citytechinc.aem.apps.ionic.api.models.application.state;

import com.citytechinc.aem.apps.ionic.api.models.application.root.ApplicationRoot;
import com.google.common.base.Optional;
import org.apache.sling.api.resource.Resource;

public interface ApplicationState {

    String RESOURCE_TYPE = "ionic-aem-apps/components/page/application-state";
    String ANGULAR_CONTROLLER_KEY = "angularController";

    String getUrl();

    String getTemplate();

    String getId();

    String getPath();

    Resource getContentResource();

    String getAngularController();

    String getTitle();

    boolean isAbstract();

    boolean isStructuralState();

    Optional<ApplicationState> getDirectParentState();

    Optional<ApplicationState> getParentState();

    ApplicationRoot getApplicationRoot();

}
