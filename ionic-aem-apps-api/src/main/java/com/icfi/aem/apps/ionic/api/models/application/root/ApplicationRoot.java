package com.icfi.aem.apps.ionic.api.models.application.root;

import com.icfi.aem.apps.ionic.api.models.application.state.ApplicationState;

import java.util.List;
import java.util.Set;

public interface ApplicationRoot {

    String RESOURCE_TYPE = "ionic-aem-apps/components/page/application-root";
    String ANGULAR_REQUIRED_MODULES_KEY = "requiredAngularModules";

    String getInitialStateUrl();

    String getSanatizedControllerName();

    String getRelativePathToRoot();

    List<ApplicationState> getApplicationStates();

    String getApplicationName();

    Set<String> getRequiredAngularModules();

    String getPath();
}
