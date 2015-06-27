<%@page session="false" %><%
%><%@include file="/apps/ionic-aem-apps/components/global.jsp" %>
<c:choose>
    <c:when test="${!isAppMode}">
        <%-- TODO: Better Authoring experience --%>
        Side Menu Content
        <cq:include script="sidemenucontent.jsp"/>
        Left Side Menu
        <cq:include script="leftsidemenu.jsp"/>
    </c:when>
    <c:otherwise>
        <ion-side-menus>
            <ion-side-menu-content>
                <cq:include script="sidemenuheader.jsp"/>
                <cq:include script="sidemenucontent.jsp"/>
            </ion-side-menu-content>
            <%-- TODO: Allow for left and right side menus --%>
            <ion-side-menu side="left">
                <cq:include script="leftsidemenu.jsp"/>
            </ion-side-menu>
        </ion-side-menus>
    </c:otherwise>
</c:choose>
