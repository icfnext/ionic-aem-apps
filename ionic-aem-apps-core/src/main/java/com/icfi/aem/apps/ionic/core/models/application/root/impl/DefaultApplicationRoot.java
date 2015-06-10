package com.icfi.aem.apps.ionic.core.models.application.root.impl;

import com.adobe.cq.mobile.angular.data.util.FrameworkContentExporterUtils;
import com.citytechinc.aem.bedrock.api.page.PageDecorator;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.icfi.aem.apps.ionic.api.models.application.root.ApplicationRoot;
import com.icfi.aem.apps.ionic.api.models.application.state.ApplicationState;
import com.icfi.aem.apps.ionic.core.models.application.state.impl.DefaultApplicationState;
import org.apache.sling.api.resource.Resource;

import java.util.List;
import java.util.Set;

public class DefaultApplicationRoot implements ApplicationRoot {

    private final PageDecorator rootPage;

    private List<ApplicationState> applicationStates;

    public DefaultApplicationRoot(PageDecorator rootPage) {
        this.rootPage = rootPage;
    }

    @Override
    public String getInitialStateUrl() {
        return getApplicationStates().get(0).getUrl();
    }

    @Override
    public String getSanatizedControllerName() {
        //TODO: Tighten up
        return rootPage.getPath().replaceAll("[^A-Za-z0-9]", "");
    }

    @Override
    public String getRelativePathToRoot() {
        return FrameworkContentExporterUtils.getRelativePathToRootLevel(rootPage.adaptTo(Resource.class));
    }

    @Override
    public List<ApplicationState> getApplicationStates() {
        if (applicationStates == null) {
            applicationStates = buildApplicationStates(rootPage.getChildren());
        }

        return applicationStates;
    }

    @Override
    public String getApplicationName() {
        return rootPage.get("ionicApplicationName", "IonicApplication");
    }

    @Override
    public Set<String> getRequiredAngularModules() {
        return Sets.newHashSet(rootPage.getAsList("requiredAngularModules", String.class));
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
