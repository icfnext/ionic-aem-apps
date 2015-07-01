<%@page session="false" %><%
%><%@include file="/apps/ionic-aem-apps/components/global.jsp" %>
<c:choose>
    <c:when test="${!isAppMode}">
        <cq:include path="ion-content" resourceType="ionic-aem-apps/components/content/ioncontent" />
    </c:when>
    <c:otherwise>
        <ion-view view-title="<bedrock:property propertyName="pageTitle" />">
            <ionic:suppressDecoration>
                <cq:include path="ion-content" resourceType="ionic-aem-apps/components/content/ioncontent" />
            </ionic:suppressDecoration>
        </ion-view>
    </c:otherwise>
</c:choose>
