package com.icfi.aem.apps.ionic.core.models.application.state.impl;

import com.citytechinc.aem.bedrock.api.page.PageDecorator;
import com.google.common.collect.Lists;
import com.icfi.aem.apps.ionic.api.models.application.root.ApplicationRoot;
import com.icfi.aem.apps.ionic.api.models.application.state.ApplicationState;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.resource.Resource;

import java.util.List;

public class DefaultApplicationState implements ApplicationState {

    private final PageDecorator statePage;
    private final PageDecorator rootPage;

    private String stateId;

    public DefaultApplicationState(PageDecorator statePage, PageDecorator rootPage) {
        this.statePage = statePage;
        this.rootPage = rootPage;
    }

    @Override
    public String getUrl() {
        return statePage.getPath();
    }

    @Override
    public String getTemplate() {
        return rootPage.getName() + statePage.getPath().substring(rootPage.getPath().length()) + ".template.html";
    }

    @Override
    public String getId() {
        if (stateId != null) {
            return stateId;
        }

        PageDecorator currentPage = statePage;

        List<String> parents = Lists.newArrayList();

        while(currentPage != null && !currentPage.getContentResource().isResourceType(ApplicationRoot.RESOURCE_TYPE)){
            parents.add(currentPage.getName());
            currentPage = currentPage.getParent();
        }

        stateId = StringUtils.join(Lists.reverse(parents), ".");
        return stateId;
    }

    @Override
    public Resource getContentResource() {
        return statePage.getContentResource();
    }
}
