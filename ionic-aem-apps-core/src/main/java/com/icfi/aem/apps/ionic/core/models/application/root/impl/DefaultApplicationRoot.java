package com.icfi.aem.apps.ionic.core.models.application.root.impl;

import com.adobe.cq.mobile.angular.data.util.FrameworkContentExporterUtils;
import com.citytechinc.aem.bedrock.api.node.ComponentNode;
import com.citytechinc.aem.bedrock.api.page.PageDecorator;
import com.citytechinc.aem.bedrock.core.node.predicates.ComponentNodePropertyExistsPredicate;
import com.google.common.base.Predicate;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.icfi.aem.apps.ionic.api.models.application.root.ApplicationRoot;
import com.icfi.aem.apps.ionic.api.models.application.state.ApplicationState;
import com.icfi.aem.apps.ionic.api.resource.TypedResource;
import com.icfi.aem.apps.ionic.core.models.application.state.impl.DefaultApplicationState;
import org.apache.sling.api.resource.Resource;

import java.util.List;
import java.util.Set;

public class DefaultApplicationRoot implements ApplicationRoot {

    private static final String ANGULAR_REQUIRED_MODULES_KEY = "requiredAngularModules";
    private static final Predicate<ComponentNode> SLING_RESOURCE_TYPE_EXISTS_PREDICATE = new ComponentNodePropertyExistsPredicate("sling:resourceType");

    private final PageDecorator rootPage;

    private List<ApplicationState> applicationStates;
    private Set<String> applicationModules;


    public DefaultApplicationRoot(PageDecorator rootPage) {
        this.rootPage = rootPage;
    }

    public String getInitialStateUrl() {
        return getApplicationStates().get(0).getUrl();
    }

    public String getSanatizedControllerName() {
        //TODO: Tighten up
        return rootPage.getPath().replaceAll("[^A-Za-z0-9]", "");
    }

    public String getRelativePathToRoot() {
        return FrameworkContentExporterUtils.getRelativePathToRootLevel(rootPage.adaptTo(Resource.class));
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

        for (ComponentNode currentChildNode : rootPage.adaptTo(ComponentNode.class).getParent().get().findDescendants(SLING_RESOURCE_TYPE_EXISTS_PREDICATE)) {
            TypedResource typedResource = currentChildNode.getResource().adaptTo(TypedResource.class);

            if (typedResource != null) {
                applicationModules.addAll(typedResource.getResourceType().getAsList(ANGULAR_REQUIRED_MODULES_KEY, String.class));
            }
        }

        return applicationModules;

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
