package com.icfi.aem.apps.ionic.core.predicates.application.root;

import com.citytechinc.aem.bedrock.api.page.PageDecorator;
import com.google.common.base.Predicate;
import com.icfi.aem.apps.ionic.api.models.application.root.ApplicationRoot;

public class ApplicationRootPagePredicate implements Predicate<PageDecorator> {

    @Override
    public boolean apply(PageDecorator pageDecorator) {
        return pageDecorator.getContentResource().isResourceType(ApplicationRoot.RESOURCE_TYPE);
    }

}
