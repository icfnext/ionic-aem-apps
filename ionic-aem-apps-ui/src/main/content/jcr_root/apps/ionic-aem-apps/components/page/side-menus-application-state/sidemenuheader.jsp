<%@page session="false" %><%
%><%@include file="/apps/ionic-aem-apps/components/global.jsp" %>

<%-- TODO: Configurability of class? --%>
<ion-nav-bar class="bar-stable">
    <ion-nav-back-button>
    </ion-nav-back-button>

    <%-- TODO: Allow for left and right menus --%>
    <ion-nav-buttons side="left">
        <button class="button button-icon button-clear ion-navicon" menu-toggle="left">
        </button>
    </ion-nav-buttons>
</ion-nav-bar>