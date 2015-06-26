package com.icfi.aem.apps.ionic.api.models.application.state;

import org.apache.sling.api.resource.Resource;

import java.util.Set;

public interface ApplicationState {

    public static final String RESOURCE_TYPE = "ionic-aem-apps/components/page/application-state";
    public static final String ANGULAR_CONTROLLER_KEY = "angularController";

    public String getUrl();

    public String getTemplate();

    public String getId();

    public Resource getContentResource();

    public String getAngularController();

    public boolean isAbstract();

    public boolean isStructuralState();

}
