<%@include file="/apps/bedrock/components/global.jsp"%>
<%@taglib prefix="ionic" uri="http://www.icfi.com/taglibs/ionic-aem-apps"%>
<c:set var="isAppMode" scope="request" value="${isPublish || isPreviewMode}"/>