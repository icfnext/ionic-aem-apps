package com.citytechinc.aem.apps.ionic.core.models.application.state.impl;

import com.icfolson.aem.library.api.page.PageDecorator;
import com.google.common.base.Optional;
import com.google.common.collect.Lists;
import com.citytechinc.aem.apps.ionic.api.models.application.root.ApplicationRoot;
import com.citytechinc.aem.apps.ionic.api.models.application.state.ApplicationState;
import com.citytechinc.aem.apps.ionic.api.resource.TypedResource;
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
        Optional<ApplicationState> parentState = getParentState();

        List<String> pathParts = Lists.newArrayList();

        while ( currentPage != null &&
                currentPage.getContentResource().isResourceType(ApplicationState.RESOURCE_TYPE) &&
                (
                        currentPage.getPath().equals(statePage.getPath()) ||
                        currentPage.get("isStructuralState", false) ||
                        (parentState.isPresent() && !parentState.get().getPath().equals(currentPage.getPath())))) {
            if (currentPage.get("isSlugState", false)) {
                pathParts.add(":" + currentPage.getName());
            }
            else {
                pathParts.add(currentPage.getName());
            }

            currentPage = currentPage.getParent();
        }

        url = "/" + StringUtils.join(Lists.reverse(pathParts), "/");

        return url;
    }

    public String getTemplate() {
        return rootPage.getName() + statePage.getPath().substring(rootPage.getPath().length()) + ".template.html";
    }

    public String getId() {
        if (stateId != null) {
            return stateId;
        }

        Optional<ApplicationState> parentStateOptional = getParentState();

        if (parentStateOptional.isPresent()) {
            stateId = parentStateOptional.get().getId() + "." + statePage.getName();
            return stateId;
        }

        stateId = statePage.getName();
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

    @Override
    public String getTitle() {
        return statePage.getTitle();
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

    @Override
    public Optional<ApplicationState> getDirectParentState() {
        return Optional.fromNullable(statePage.getParent().adaptTo(ApplicationState.class));
    }

    @Override
    public Optional<ApplicationState> getParentState() {
        Optional<String> parentStatePath = statePage.get("parentStatePath", String.class);

        if (parentStatePath.isPresent()) {
            return Optional.fromNullable(statePage.getPageManager().getPage(parentStatePath.get()).adaptTo(ApplicationState.class));
        }

        Optional<ApplicationState> directParentState = getDirectParentState();

        while (directParentState.isPresent() && directParentState.get().isStructuralState()) {
            directParentState = directParentState.get().getDirectParentState();
        }

        return directParentState;
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
