<%@page session="false" %>
<%@include file="/apps/ionic-aem-apps/components/global.jsp" %>
<bedrock:component className="com.citytechinc.things.beermunity.components.page.applicationroot.ApplicationRoot" name="applicationRoot" />

<c:forEach var="applicationState" items="${applicationRoot.applicationStates}">
    <script type="text/ng-template" id="${applicationState.template}">
        <sling:include resource="${applicationState.contentResource}" addSelectors="template" />
    </script>
</c:forEach>