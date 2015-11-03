<%@page session="false" %>
<%@include file="/apps/ionic-aem-apps/components/global.jsp" %>
<bedrock:component className="com.icfi.aem.apps.ionic.api.models.application.root.ApplicationRoot" name="applicationRoot" />

<c:forEach var="applicationState" items="${applicationRoot.applicationStates}">
    <script type="text/ng-template" id="${applicationState.template}">
        <ionic:suppressDecoration>
            <sling:include resource="${applicationState.contentResource}" addSelectors="template" />
        </ionic:suppressDecoration>
    </script>
</c:forEach>