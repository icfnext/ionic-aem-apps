package com.icfi.aem.apps.ionic.core.services.component.impl;

import com.citytechinc.aem.bedrock.api.node.ComponentNode;
import com.google.common.collect.Lists;
import com.icfi.aem.apps.ionic.core.services.component.ComponentResolverService;
import org.apache.felix.scr.annotations.*;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

@Component(
        label = "Component Resolver Service",
        description = "Gets the underlying component node for a component.",
        immediate = true,
        metatype = true,
        enabled = true,
        inherit = false
)
@Service
public class ComponentResolverServiceImpl implements ComponentResolverService {

    private static final Logger LOG = LoggerFactory.getLogger(ComponentResolverServiceImpl.class);

    @Reference
    private ResourceResolverFactory resourceResolverFactory;

    private ResourceResolver adminResourceResolver;

    private List<ComponentNode> childNodeList;

    public ComponentNode getComponentForResource(Resource resource){
        Resource componentResource = adminResourceResolver.getResource(resource.getResourceType());

        if(componentResource == null){
            return null;
        }

        return componentResource.adaptTo(ComponentNode.class);
    }

    public List<ComponentNode> getAllChildNodes(ComponentNode componentNode){

        childNodeList = buildChildNodes(componentNode.getComponentNodes());

        return childNodeList;
    }

    private List<ComponentNode> buildChildNodes(List<ComponentNode> childNodes){

        List<ComponentNode> nodes = Lists.newArrayList();

        for(ComponentNode childNode : childNodes){
            nodes.add(childNode);
            nodes.addAll(childNode.getComponentNodes());
        }

        return nodes;
    }

    @Activate
    void activate(Map<String, Object> properties) throws LoginException {

        try{
            adminResourceResolver = resourceResolverFactory.getAdministrativeResourceResolver(null);
        }
        catch(LoginException loginException){
            LOG.error("Fatal exception while getting resource resovlver.",loginException);
            throw loginException;
        }
    }

    @Deactivate
    void deactivate() {
        adminResourceResolver.close();
    }
}
