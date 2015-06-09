<%@page session="false" %>
<%@include file="/apps/ionic-aem-apps/components/global.jsp" %>

<cq:includeClientLib js="apps.beermunity-data-entry.all"/>

<c:if test="${isPreviewMode}">
    <cq:include script="js_previewlibs.jsp"/>
</c:if>
