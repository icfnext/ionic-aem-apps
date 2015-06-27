<%@ taglib prefix="ionic" uri="http://www.icfi.com/taglibs/ionic-aem-apps" %>
<%@page session="false" %><%
%><%@include file="/apps/ionic-aem-apps/components/global.jsp" %>

<ionic:suppressDecoration test="${isAppMode}">
    <cq:include path="ionnavview" resourceType="ionic-aem-apps/components/content/ionnavview" />
</ionic:suppressDecoration>



