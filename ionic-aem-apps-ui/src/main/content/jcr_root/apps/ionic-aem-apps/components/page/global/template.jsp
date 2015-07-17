<%@page session="false" %><%
%><%@include file="/apps/ionic-aem-apps/components/global.jsp" %>
<c:choose>
    <c:when test="${!isAppMode}">
        <div data-statename="${currentPage.name}">
            <cq:include path="ion-content" resourceType="ionic-aem-apps/components/content/ioncontent" />
        </div>
    </c:when>
    <c:otherwise>
        <ion-view data-statename="${currentPage.name}" view-title="<bedrock:property propertyName="pageTitle" />">
            <ionic:suppressDecoration>
                <cq:include path="ion-content" resourceType="ionic-aem-apps/components/content/ioncontent" />
            </ionic:suppressDecoration>
        </ion-view>
    </c:otherwise>
</c:choose>
