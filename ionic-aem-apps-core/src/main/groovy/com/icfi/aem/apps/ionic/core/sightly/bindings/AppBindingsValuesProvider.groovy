package com.icfi.aem.apps.ionic.core.sightly.bindings

import org.apache.felix.scr.annotations.Component
import org.apache.felix.scr.annotations.Properties
import org.apache.felix.scr.annotations.Property
import org.apache.felix.scr.annotations.PropertyUnbounded
import org.apache.felix.scr.annotations.Service
import org.apache.sling.api.SlingHttpServletRequest
import org.apache.sling.scripting.api.BindingsValuesProvider
import org.osgi.framework.Constants

import javax.script.Bindings

import static org.apache.sling.api.scripting.SlingBindings.REQUEST

@Component(immediate = true)
@Service(BindingsValuesProvider)
@Properties([
        @Property(name = "javax.script.name", value = ["sightly", "jsp"], unbounded = PropertyUnbounded.ARRAY),
        @Property(name = Constants.SERVICE_RANKING, intValue = 0)
])
class AppBindingsValuesProvider implements BindingsValuesProvider {

    @Override
    void addBindings(Bindings bindings) {
        def slingRequest = bindings.get(REQUEST) as SlingHttpServletRequest
        bindings.putAll(new IonicAppBindings(slingRequest))
    }

}
