<%@page session="false" %><%
%><%@include file="/apps/ionic-aem-apps/components/global.jsp" %>
<bedrock:component className="com.icfi.aem.apps.ionic.core.components.page.sidemenusapplicationstate.SideMenusApplicationState" name="sideMenusApplicationStateComponent" />
<c:choose>
    <c:when test="${!isAppMode}">
        <%-- TODO: Better Authoring experience --%>
        Side Menu Content
        <cq:include script="sidemenucontent.jsp"/>
        <c:if test="${sideMenusApplicationStateComponent.usesLeftSideMenu}">
            Left Side Menu
            <cq:include script="leftsidemenu.jsp"/>
        </c:if>
        <c:if test="${sideMenusApplicationStateComponent.usesRightSideMenu}">
            Right Side Menu
            <cq:include script="rightsidemenu.jsp"/>
        </c:if>
    </c:when>
    <c:otherwise>
        </ion-side-menus>
            <ion-side-menu-content>
                <cq:include script="sidemenuheader.jsp"/>
                <cq:include script="sidemenucontent.jsp"/>
            </ion-side-menu-content>
            <c:if test="${sideMenusApplicationStateComponent.usesLeftSideMenu}">
                <ion-side-menu side="left">
                    <cq:include script="leftsidemenu.jsp"/>
                </ion-side-menu>
            </c:if>
            <c:if test="${sideMenusApplicationStateComponent.usesRightSideMenu}">
                <ion-side-menu side="right">
                    <cq:include script="rightsidemenu.jsp"/>
                </ion-side-menu>
            </c:if>
        </ion-side-menus>
    </c:otherwise>
</c:choose>
