<%@include file="/apps/ionic-aem-apps/components/global.jsp" %>
<c:choose>
    <c:when test="${!isAppMode}">
        <div class="ion-content" data-pagename="${ioncontent.pageName}">
            <h2>Ion-Content</h2>
            <cq:include path="state-content" resourceType="foundation/components/parsys" />
        </div>
    </c:when>
    <c:otherwise>
        <ion-content scroll="${!ioncontent.scrollDisabled}" data-pagename="${ioncontent.pageName}">
            <cq:include path="state-content" resourceType="foundation/components/parsys" />
        </ion-content>
    </c:otherwise>
</c:choose>

