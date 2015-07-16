package com.icfi.aem.apps.ionic.core.components.page.sidemenusapplicationstate;

import com.citytechinc.aem.bedrock.core.components.AbstractComponent;
import com.citytechinc.cq.component.annotations.Component;
import com.citytechinc.cq.component.annotations.DialogField;
import com.citytechinc.cq.component.annotations.Option;
import com.citytechinc.cq.component.annotations.widgets.DateTime;
import com.citytechinc.cq.component.annotations.widgets.Selection;

import java.util.List;

public class SideMenusApplicationState extends AbstractComponent {

    public static final String LEFT_MENU_SIDE = "left";
    public static final String RIGHT_MENU_SIDE = "right";

    public List<String> getMenuSides() {
        return getAsList("menuSides", String.class);
    }

    public boolean isUsesLeftSideMenu() {
        return getMenuSides().contains(LEFT_MENU_SIDE);
    }

    public boolean isUsesRightSideMenu() {
        return getMenuSides().contains(RIGHT_MENU_SIDE);
    }

}
