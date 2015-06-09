package com.icfi.aem.apps.ionic.api.models.application.root;

import com.icfi.aem.apps.ionic.api.models.application.state.ApplicationState;

import java.util.List;
import java.util.Set;

public interface ApplicationRoot {

    public static final String RESOURCE_TYPE = "ionic-aem-apps/components/page/application-root";

    public String getInitialStateUrl();

    public String getSanatizedControllerName();

    public String getRelativePathToRoot();

    public List<ApplicationState> getApplicationStates();

    public String getApplicationName();

    public Set<String> getRequiredAngularModules();

}
