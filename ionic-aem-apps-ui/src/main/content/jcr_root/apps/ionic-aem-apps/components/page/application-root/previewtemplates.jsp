<%@page session="false" %>
<%@include file="/apps/ionic-aem-apps/components/global.jsp" %>
<bedrock:component className="com.icfi.aem.apps.ionic.core.components.page.applicationroot.ApplicationRootComponent" name="applicationRootComponent" />

<c:forEach var="applicationState" items="${applicationRootComponent.applicationRoot.applicationStates}">
    <script type="text/ng-template" id="${applicationState.template}">
        <sling:include resource="${applicationState.contentResource}" addSelectors="template" />
    </script>
</c:forEach>