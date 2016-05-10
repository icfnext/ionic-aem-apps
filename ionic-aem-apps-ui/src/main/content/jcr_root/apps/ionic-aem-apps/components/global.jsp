<%@include file="/apps/aem-library/components/global.jsp"%>
<%@taglib prefix="ionic" uri="http://www.citytechinc.com/taglibs/ionic-aem-apps"%>
<c:set var="isAppMode" scope="request" value="${isPublish || isPreviewMode}"/>