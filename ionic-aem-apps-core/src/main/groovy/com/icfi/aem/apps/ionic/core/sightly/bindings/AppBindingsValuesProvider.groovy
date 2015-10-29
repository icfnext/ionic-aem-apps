package com.icfi.aem.apps.ionic.core.sightly.bindings

import com.adobe.cq.sightly.SightlyWCMMode
import com.adobe.cq.sightly.WCMBindings
import org.apache.felix.scr.annotations.Component
import org.apache.felix.scr.annotations.Service
import org.apache.sling.scripting.api.BindingsValuesProvider

import javax.script.Bindings

@Component
@Service
class AppBindingsValuesProvider implements BindingsValuesProvider {

    public static final String IS_IONIC_APP_MODE = "isIonicAemAppMode"
    @Override
    void addBindings(Bindings bindings) {
        def mode = bindings.get(WCMBindings.WCM_MODE) as SightlyWCMMode
        bindings.put(IS_IONIC_APP_MODE, mode.disabled || mode.preview)
    }

}
