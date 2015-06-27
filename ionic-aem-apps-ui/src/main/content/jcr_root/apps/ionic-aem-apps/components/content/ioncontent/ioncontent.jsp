<%@include file="/apps/ionic-aem-apps/components/global.jsp" %>
<c:choose>
    <c:when test="${!isAppMode}">
        <div class="ion-content">
            <h2>Ion-Content</h2>
            <cq:include path="content" resourceType="foundation/components/parsys" />
        </div>
    </c:when>
    <c:otherwise>
        <ion-content scroll="${!ioncontent.scrollDisabled}">
            <cq:include path="content" resourceType="foundation/components/parsys" />
        </ion-content>
    </c:otherwise>
</c:choose>

