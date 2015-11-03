<%@page session="false" %>
<%@include file="/apps/ionic-aem-apps/components/global.jsp" %>
<bedrock:component className="com.icfi.aem.apps.ionic.api.models.application.root.ApplicationRoot" name="applicationRoot" />

<h2>${applicationRoot.applicationRoot.applicationName} Root</h2>