<%@page session="false" %>
<%@include file="/apps/ionic-aem-apps/components/global.jsp" %>
<bedrock:component className="com.icfi.aem.apps.ionic.core.components.page.applicationroot.ApplicationRootComponent" name="applicationRootComponent" />

<body ng-app="${applicationRootComponent.applicationRoot.applicationName}" <c:if test="${isAppMode}">ng-cloak</c:if>>

    <c:choose>
        <c:when test="${isAppMode}">
            <ion-nav-view></ion-nav-view>

            <c:if test="${isPreviewMode}">
                <cq:include script="previewtemplates.jsp" />
            </c:if>

            <c:if test="${isPublish}">
                <script type="text/javascript" src="/cordova.js"></script>
            </c:if>

            <cq:includeClientLib js="ionic-1.0.0"/>
            <cq:include script="js_application_clientlibs.jsp"/>
            <script src="<c:out value='${currentPage.name}'/>.angular-app-module.js"></script>
        </c:when>
        <c:otherwise>
            <cq:include script="root_author.jsp"/>
        </c:otherwise>
    </c:choose>

    <cq:include script="js_clientlibs.jsp"/>

</body>