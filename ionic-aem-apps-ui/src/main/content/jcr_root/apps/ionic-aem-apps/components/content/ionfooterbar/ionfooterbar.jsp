<%@include file="/apps/ionic-aem-apps/components/global.jsp" %>
<c:choose>
    <c:when test="${!isAppMode}">
        <div class="ion-footer-bar" data-pagename="${ionfooterbar.pageName}">
            <h2>Ion-Footer-Bar</h2>
            <cq:include path="footer-content" resourceType="foundation/components/parsys" />
        </div>
    </c:when>
    <c:otherwise>
        <c:if test="${ionfooterbar.hasContent || ionfooterbar.alwaysShow}">
            <ion-footer-bar data-pagename="${ionfooterbar.pageName}">
                <cq:include path="footer-content" resourceType="foundation/components/parsys" />
            </ion-footer-bar>
        </c:if>
    </c:otherwise>
</c:choose>

