package com.citytechinc.aem.apps.ionic.core.predicates.contentsync.root;

import com.citytechinc.aem.bedrock.api.page.PageDecorator;
import com.google.common.base.Predicate;
import com.citytechinc.aem.apps.ionic.api.models.contentsync.root.ContentSyncRoot;

public class ContentSyncRootPagePredicate implements Predicate<PageDecorator> {

    @Override
    public boolean apply(PageDecorator pageDecorator) {
        return pageDecorator.getContentResource().isResourceType(ContentSyncRoot.RESOURCE_TYPE);
    }
}
