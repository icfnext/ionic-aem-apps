<%@page session="false" %><%
%><%@include file="/apps/ionic-aem-apps/components/global.jsp" %>
<bedrock:component className="com.icfi.aem.apps.ionic.core.components.page.sidemenusapplicationstate.SideMenusApplicationState" name="sideMenusApplicationStateComponent" />

<%-- TODO: Configurability of class? --%>
<ion-nav-bar class="bar-stable">
    <ion-nav-back-button>
    </ion-nav-back-button>

    <%-- TODO: Allow for left and right menus --%>
    <c:if test="${sideMenusApplicationStateComponent.usesLeftSideMenu}">
        <ion-nav-buttons side="left">
            <button class="button button-icon button-clear ion-navicon" menu-toggle="left">
            </button>
        </ion-nav-buttons>
    </c:if>
    <c:if test="${sideMenusApplicationStateComponent.usesRightSideMenu}">
        <ion-nav-buttons side="right">
            <button class="button button-icon button-clear ion-navicon" menu-toggle="right">
            </button>
        </ion-nav-buttons>
    </c:if>
</ion-nav-bar>