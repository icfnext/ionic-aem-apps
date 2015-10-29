package com.icfi.aem.apps.ionic.api.errors;

public class InvalidContentSyncConfigurationException extends Exception {

    public InvalidContentSyncConfigurationException(String m) {
        super(m);
    }

    public InvalidContentSyncConfigurationException(String m, Exception e) {
        super(m, e);
    }

}
