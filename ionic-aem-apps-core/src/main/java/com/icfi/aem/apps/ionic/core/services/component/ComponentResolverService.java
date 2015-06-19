package com.icfi.aem.apps.ionic.core.services.component;

import com.citytechinc.aem.bedrock.api.node.ComponentNode;
import org.apache.sling.api.resource.Resource;

import java.util.List;

public interface ComponentResolverService {
    ComponentNode getComponentForResource(Resource resource);
    List<ComponentNode> getAllChildNodes(ComponentNode componentNode);
}
