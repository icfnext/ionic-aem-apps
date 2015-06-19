package com.icfi.aem.apps.ionic.core.models.application.root.impl;

import com.adobe.cq.mobile.angular.data.util.FrameworkContentExporterUtils;
import com.citytechinc.aem.bedrock.api.node.ComponentNode;
import com.citytechinc.aem.bedrock.api.page.PageDecorator;
import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.icfi.aem.apps.ionic.api.models.application.root.ApplicationRoot;
import com.icfi.aem.apps.ionic.api.models.application.state.ApplicationState;
import com.icfi.aem.apps.ionic.core.models.application.state.impl.DefaultApplicationState;
import com.icfi.aem.apps.ionic.core.services.component.ComponentResolverService;
import org.apache.sling.api.resource.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class DefaultApplicationRoot implements ApplicationRoot {

    private static final Logger LOG = LoggerFactory.getLogger(DefaultApplicationRoot.class);

    private static final String ANGULAR_REQUIRED_MODULES_KEY = "requiredAngularModules";

    private final PageDecorator rootPage;
    private final ComponentResolverService resolverService;

    private List<ApplicationState> applicationStates;
    private List<String> applicationModules;


    public DefaultApplicationRoot(PageDecorator rootPage, ComponentResolverService resolverService) {
        this.resolverService = resolverService;
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

        if(applicationModules == null){
            applicationModules = new ArrayList<String>();
            buildAngularRequiredModules(rootPage.getChildren());
        }

        return Sets.newHashSet(applicationModules);
    }

    private List<ApplicationState> buildApplicationStates(List<PageDecorator> pages) {

        List<ApplicationState> states = Lists.newArrayList();

        for (PageDecorator childPage : pages) {
            states.add(new DefaultApplicationState(childPage, rootPage));
            states.addAll(buildApplicationStates(childPage.getChildren()));
        }

        return states;

    }

    private List<String> buildAngularRequiredModules(List<PageDecorator> pages){

        List<String> modules = Lists.newArrayList();

        for(PageDecorator childPage : pages){

            //For each page get all of the child components on the page of that have a property "requiredAngularModules"
            List<ComponentNode> pageComponentNodes = childPage.getComponentNode().get().findDescendants(getRequiredModulesPredicate);

            for(ComponentNode pageNode : pageComponentNodes){
                ComponentNode contentNode = resolverService.getComponentForResource(pageNode.getResource());

                if(contentNode == null){
                    continue;
                }

                List<String> pageModules = contentNode.getAsList(ANGULAR_REQUIRED_MODULES_KEY,String.class);

                for(String module : pageModules){
                    addModule(module);
                }

            }

            buildAngularRequiredModules(childPage.getChildren());
        }

        return modules;
    }

    private void addModule(String module){
        if(!applicationModules.contains(module)){
            applicationModules.add(module);
        }
    }

    Predicate<ComponentNode> getRequiredModulesPredicate = new Predicate<ComponentNode>(){

        @Override
        public boolean apply(ComponentNode componentNode) {

        ComponentNode componentDefinition = resolverService.getComponentForResource(componentNode.getResource());

        if(componentDefinition == null){
            return false;
        }

        List<String> requiredModulesProperty = componentDefinition.getAsList(ANGULAR_REQUIRED_MODULES_KEY,String.class);

        if(requiredModulesProperty.size() > 0){
            return true;
        }

        return false;
        }
    };

}
