<%@page session="false" %>
<%@include file="/apps/ionic-aem-apps/components/global.jsp" %>
<bedrock:component className="com.icfi.aem.apps.ionic.core.components.page.applicationroot.ApplicationRootComponent" name="applicationRootComponent" />

<h2>${applicationRootComponent.applicationRoot.applicationName} Root</h2>