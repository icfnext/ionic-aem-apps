<%@page session="false" %><%
%><%@include file="/apps/ionic-aem-apps/components/global.jsp" %>
<c:choose>
    <c:when test="${!isPublish && !isPreviewMode}">
        <cq:include path="content-par" resourceType="foundation/components/parsys" />
    </c:when>
    <c:otherwise>
        <ion-view view-title="<bedrock:property propertyName="pageTitle" />">
            <cq:include path="content-par" resourceType="foundation/components/parsys" />
        </ion-view>
    </c:otherwise>
</c:choose>
