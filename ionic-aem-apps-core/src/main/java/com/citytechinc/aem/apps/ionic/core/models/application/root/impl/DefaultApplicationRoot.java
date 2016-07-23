package com.citytechinc.aem.apps.ionic.core.models.application.root.impl;

import com.adobe.cq.mobile.angular.data.util.FrameworkContentExporterUtils;
import com.citytechinc.aem.apps.ionic.core.models.application.state.impl.DefaultApplicationState;
import com.day.cq.wcm.api.components.ComponentManager;
import com.day.cq.wcm.webservicesupport.Configuration;
import com.day.cq.wcm.webservicesupport.ConfigurationManager;
import com.icfolson.aem.library.api.node.ComponentNode;
import com.icfolson.aem.library.api.page.PageDecorator;
import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.citytechinc.aem.apps.ionic.api.errors.InvalidApplicationConfigurationException;
import com.citytechinc.aem.apps.ionic.api.models.application.root.ApplicationRoot;
import com.citytechinc.aem.apps.ionic.api.models.application.state.ApplicationState;
import com.citytechinc.aem.apps.ionic.api.resource.TypedResource;
import org.apache.sling.api.resource.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class DefaultApplicationRoot implements ApplicationRoot {

    private static final Logger LOG = LoggerFactory.getLogger(DefaultApplicationRoot.class);

    private static final Predicate<ComponentNode> COMPONENT_REQUIRES_ANGULAR_MODULES_PREDICATE = new Predicate<ComponentNode>() {
        public boolean apply(ComponentNode componentNode) {
            TypedResource typedResource = componentNode.getResource().adaptTo(TypedResource.class);

            return typedResource != null && !typedResource.getResourceType().getAsListInherited(ANGULAR_REQUIRED_MODULES_KEY, String.class).isEmpty();

        }
    };

    private static final Predicate<ComponentNode> COMPONENT_REQUIRES_PLUGINS_PREDICATE = new Predicate<ComponentNode>() {
        public boolean apply(ComponentNode componentNode) {
            TypedResource typedResource = componentNode.getResource().adaptTo(TypedResource.class);

            return typedResource != null && !typedResource.getResourceType().getAsListInherited(REQUIRED_CORDOVA_PLUGINS_KEY, String.class).isEmpty();

        }
    };

    private final PageDecorator rootPage;

    private List<ApplicationState> applicationStates;
    private Set<String> applicationModules;
    private Set<String> applicationPlugins;

    public DefaultApplicationRoot(PageDecorator rootPage) {
        this.rootPage = rootPage;
    }

    public String getInitialStateUrl() throws InvalidApplicationConfigurationException {
        Optional<String> initialStateOptional = rootPage.get("initialState", String.class);

        if (initialStateOptional.isPresent() && initialStateOptional.get().startsWith(rootPage.getPath())) {
            return initialStateOptional.get().substring(rootPage.getPath().length());
        }

        throw new InvalidApplicationConfigurationException("initialState is unconfigured for the Application Root");
    }

    public String getSanatizedControllerName() {
        //TODO: Tighten up
        return rootPage.getPath().replaceAll("[^A-Za-z0-9]", "");
    }

    public String getRelativePathToRoot() {
        return FrameworkContentExporterUtils.getRelativePathToRootLevel(rootPage.adaptTo(Resource.class));
    }

    public String getRelativePathToState(String absolutePathToState) {
        if (absolutePathToState != null && absolutePathToState.startsWith(getPath())) {
            return absolutePathToState.substring(getPath().length());
        }

        return absolutePathToState;
    }

    public List<ApplicationState> getApplicationStates() {
        if (applicationStates == null) {
            applicationStates = buildApplicationStates(rootPage.getChildren());
        }

        return applicationStates;
    }

    public String getApplicationName() {
        return rootPage.get("ionicApplicationName", "IonicApplication");
    }

    public Set<String> getRequiredAngularModules() {

        if (applicationModules != null) {
            return applicationModules;
        }

        applicationModules = Sets.newHashSet();

        for (ComponentNode currentChildNode : rootPage.adaptTo(ComponentNode.class).getParent().get().findDescendants(COMPONENT_REQUIRES_ANGULAR_MODULES_PREDICATE)) {
            TypedResource typedResource = currentChildNode.getResource().adaptTo(TypedResource.class);

            if (typedResource != null) {
                applicationModules.addAll(typedResource.getResourceType().getAsListInherited(ANGULAR_REQUIRED_MODULES_KEY, String.class));
            }
        }

        ConfigurationManager configurationManager = rootPage.getContentResource().getResourceResolver().adaptTo(ConfigurationManager.class);

        for(Iterator<Configuration> configurations = configurationManager.getConfigurations(rootPage.getContentResource()); configurations.hasNext();) {
            Configuration currentConfiguration = configurations.next();

            //TODO: Refactor so this adaptation isn't in the FOR loop
            //TODO: Determine if we need to make this come from an admin resource resolver
            ComponentManager componentManager = currentConfiguration.getContentResource().getResourceResolver().adaptTo(ComponentManager.class);

            applicationModules.addAll(Lists.newArrayList(
                    componentManager.getComponentOfResource(currentConfiguration.getContentResource()).getProperties().get(ApplicationRoot.ANGULAR_REQUIRED_MODULES_KEY, new String[]{})));

        }

        return applicationModules;

    }

    public Set<String> getRequiredPlugins() {

        if (applicationPlugins != null) {
            return applicationPlugins;
        }

        applicationPlugins = Sets.newHashSet();

        for (ComponentNode currentChildNode : rootPage.adaptTo(ComponentNode.class).getParent().get().findDescendants(COMPONENT_REQUIRES_PLUGINS_PREDICATE)) {
            TypedResource typedResource = currentChildNode.getResource().adaptTo(TypedResource.class);

            if (typedResource != null) {
                applicationPlugins.addAll(typedResource.getResourceType().getAsListInherited(REQUIRED_CORDOVA_PLUGINS_KEY, String.class));
            }
        }

        return applicationPlugins;

    }

    public String getPath() {
        return rootPage.getPath();
    }

    public String getApplicationDirectory() {
        String path = rootPage.getParent().getPath();
        return path.substring(rootPage.getParent().getPath().lastIndexOf("/") + 1);
    }

    private List<ApplicationState> buildApplicationStates(List<PageDecorator> pages) {

        List<ApplicationState> states = Lists.newArrayList();

        for (PageDecorator childPage : pages) {
            states.add(new DefaultApplicationState(childPage, rootPage));
            states.addAll(buildApplicationStates(childPage.getChildren()));
        }

        return states;

    }

}
