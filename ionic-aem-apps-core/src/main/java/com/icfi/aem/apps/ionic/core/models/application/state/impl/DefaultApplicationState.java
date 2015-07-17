package com.icfi.aem.apps.ionic.core.models.application.state.impl;

import com.citytechinc.aem.bedrock.api.page.PageDecorator;
import com.google.common.base.Optional;
import com.google.common.collect.Lists;
import com.icfi.aem.apps.ionic.api.models.application.root.ApplicationRoot;
import com.icfi.aem.apps.ionic.api.models.application.state.ApplicationState;
import com.icfi.aem.apps.ionic.api.resource.TypedResource;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.resource.Resource;

import java.util.List;

public class DefaultApplicationState implements ApplicationState {

    private final PageDecorator statePage;
    private final PageDecorator rootPage;

    private String stateId;
    private String url;
    private Optional<String> angularController;

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

        while (currentPage != null &&
                currentPage.getContentResource().isResourceType(ApplicationState.RESOURCE_TYPE) &&
                (currentPage.getPath().equals(statePage.getPath()) || currentPage.get("isStructuralState", false))) {
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

    public String getPath() {
        return statePage.getPath();
    }

    public Resource getContentResource() {
        return statePage.getContentResource();
    }

    public String getAngularController() {
        return getAngularControllerOptional().orNull();
    }

    public boolean isHasAngularController() {
        return getAngularControllerOptional().isPresent();
    }

    public boolean isAbstract() {
        return statePage.get("isAbstractState", false);
    }

    public boolean isStructuralState() {
        return statePage.get("isStructuralState", false);
    }

    public ApplicationRoot getApplicationRoot() {
        return rootPage.adaptTo(ApplicationRoot.class);
    }

    protected Optional<String> getAngularControllerOptional() {

        if (angularController == null) {
            TypedResource typedResource = getContentResource().adaptTo(TypedResource.class);

            if (typedResource != null) {
                angularController = typedResource.getResourceType().getInherited(ANGULAR_CONTROLLER_KEY, String.class);
            }
        }

        return angularController;

    }
}
