package com.citytechinc.aem.apps.ionic.core.components.page.sidemenusapplicationstate;

import com.citytechinc.aem.bedrock.core.components.AbstractComponent
import org.apache.sling.api.resource.Resource
import org.apache.sling.models.annotations.Default
import org.apache.sling.models.annotations.Model;

import javax.inject.Inject;

@Model(adaptables = Resource)
public class SideMenusApplicationState extends AbstractComponent {

    public static final String RESOURCE_TYPE = "ionic-aem-apps/components/page/side-menus-application-state"

    public static final String LEFT_MENU_SIDE = "left"
    public static final String RIGHT_MENU_SIDE = "right"

    @Inject @Default(values = [])
    def List<String> menuSides

    public boolean isUsesLeftSideMenu() {
        menuSides.contains(LEFT_MENU_SIDE)
    }

    public boolean isUsesRightSideMenu() {
        menuSides.contains(RIGHT_MENU_SIDE)
    }

}
