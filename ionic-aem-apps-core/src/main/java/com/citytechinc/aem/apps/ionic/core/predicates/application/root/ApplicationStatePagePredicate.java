package com.citytechinc.aem.apps.ionic.core.predicates.application.root;

import com.icfolson.aem.library.api.page.PageDecorator;
import com.google.common.base.Predicate;
import com.citytechinc.aem.apps.ionic.api.models.application.state.ApplicationState;

public class ApplicationStatePagePredicate implements Predicate<PageDecorator> {

    @Override
    public boolean apply(PageDecorator pageDecorator) {
        return pageDecorator.getContentResource().isResourceType(ApplicationState.RESOURCE_TYPE);
    }

}
