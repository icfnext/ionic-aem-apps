<%@include file="/apps/ionic-aem-apps/components/global.jsp" %>
<c:if test="${!isPublish && !isPreviewMode}">
    <div class="author-instructions ion-nav-view">Ion Nav View insertion point</div>
</c:if>
<ion-nav-view></ion-nav-view>