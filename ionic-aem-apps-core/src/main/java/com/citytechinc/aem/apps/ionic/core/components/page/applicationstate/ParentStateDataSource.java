package com.citytechinc.aem.apps.ionic.core.components.page.applicationstate;

import com.citytechinc.aem.apps.ionic.api.models.application.state.ApplicationState;
import com.google.common.base.Optional;
import com.google.common.collect.Lists;
import com.icfolson.aem.library.api.page.PageDecorator;
import com.icfolson.aem.library.api.page.PageManagerDecorator;
import com.icfolson.aem.library.api.request.ComponentServletRequest;
import com.icfolson.aem.library.core.servlets.datasource.AbstractOptionsDataSourceServlet;
import com.icfolson.aem.library.core.servlets.optionsprovider.Option;
import org.apache.felix.scr.annotations.sling.SlingServlet;

import java.util.List;


@SlingServlet(resourceTypes = ParentStateDataSource.RESOURCE_TYPE)
public class ParentStateDataSource extends AbstractOptionsDataSourceServlet {

    public static final String RESOURCE_TYPE = "ionic-aem-apps/components/page/application-state/parentstatedatasource";

    @Override
    protected List<Option> getOptions(ComponentServletRequest componentServletRequest) {
        List<Option> options = Lists.newArrayList();

        PageDecorator currentPage = componentServletRequest.getResourceResolver().adaptTo(PageManagerDecorator.class).getContainingPage(componentServletRequest.getSlingRequest().getRequestPathInfo().getSuffix());

        ApplicationState currentState = currentPage.adaptTo(ApplicationState.class);

        if (currentState != null) {
            Optional<ApplicationState> parentStateOptional = currentState.getDirectParentState();

            while(parentStateOptional.isPresent()) {
                ApplicationState parentState = parentStateOptional.get();

                if (!parentState.isStructuralState()) {
                    options.add(new Option(parentStateOptional.get().getPath(), parentStateOptional.get().getTitle()));
                }

                parentStateOptional = parentStateOptional.get().getDirectParentState();
            }
        }

        return options;
    }

}
