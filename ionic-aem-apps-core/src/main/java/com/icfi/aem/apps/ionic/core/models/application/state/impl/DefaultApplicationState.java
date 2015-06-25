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
    private String url;

    public DefaultApplicationState(PageDecorator statePage, PageDecorator rootPage) {
        this.statePage = statePage;
        this.rootPage = rootPage;
    }

    public String getUrl() {
        if (url != null) {
            return url;
        }

        PageDecorator currentPage = statePage;

        List<String> parents = Lists.newArrayList();

        while (currentPage != null && !currentPage.getContentResource().isResourceType(ApplicationRoot.RESOURCE_TYPE) && (currentPage.equals(statePage) || currentPage.get("isStructuralState", false))) {
            if (currentPage.get("isSlugState", false)) {
                parents.add(":" + currentPage.getName());
            }
            else {
                parents.add(currentPage.getName());
            }
            currentPage = currentPage.getParent();
        }

        url = "/" + StringUtils.join(Lists.reverse(parents), "/");

        return url;
    }

    public String getTemplate() {
        return rootPage.getName() + statePage.getPath().substring(rootPage.getPath().length()) + ".template.html";
    }

    public String getId() {
        if (stateId != null) {
            return stateId;
        }

        PageDecorator currentPage = statePage;

        List<String> parents = Lists.newArrayList();

        while(currentPage != null && !currentPage.getContentResource().isResourceType(ApplicationRoot.RESOURCE_TYPE)) {
            if (!currentPage.get("isStructuralState", false)) {
                parents.add(currentPage.getName());
            }
            currentPage = currentPage.getParent();
        }

        stateId = StringUtils.join(Lists.reverse(parents), ".");
        return stateId;
    }

    public Resource getContentResource() {
        return statePage.getContentResource();
    }

    public boolean isAbstract() {
        return statePage.get("isAbstract", false);
    }

    public boolean isStructuralState() {
        return statePage.get("isStructuralState", false);
    }

}
