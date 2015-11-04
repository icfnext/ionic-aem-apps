package com.icfi.aem.apps.ionic.core.sightly.bindings

import com.day.cq.wcm.api.WCMMode
import org.apache.sling.api.SlingHttpServletRequest

import javax.script.Bindings

class IonicAppBindings implements Bindings {

    public static final String IS_APP_MODE = "isIonicAemAppMode"

    @Delegate
    private final Map<String, Object> map = [:]

    IonicAppBindings(SlingHttpServletRequest request) {
        def mode = WCMMode.fromRequest(request)

        map.put(IS_APP_MODE, mode == WCMMode.DISABLED || mode == WCMMode.PREVIEW)
    }

    @Override
    def put(String key, value) {
        map.put(key, value)
    }
}
