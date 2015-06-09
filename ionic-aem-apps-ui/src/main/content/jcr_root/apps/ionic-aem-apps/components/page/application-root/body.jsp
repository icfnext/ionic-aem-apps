<%@page session="false" %>
<%@include file="/apps/ionic-aem-apps/components/global.jsp" %>
<bedrock:component className="com.citytechinc.things.beermunity.components.page.applicationroot.ApplicationRoot" name="applicationRoot" />

<body ng-app="${applicationRoot.applicationName}" ng-cloak>

    <c:choose>
        <c:when test="${isPublish || isPreviewMode}">
            <c:set var="isAppMode" scope="request" value="${true}"/>
            <ion-nav-view></ion-nav-view>

            <c:if test="${isPreviewMode}">
                <cq:include script="previewtemplates.jsp" />
            </c:if>

            <c:if test="${isPublish}">
                <script type="text/javascript" src="/cordova.js"></script>
            </c:if>

            <cq:includeClientLib js="ionic-1.0.0-beta.14"/>
            <script src="<c:out value='${currentPage.name}'/>.angular-app-module.js"></script>
        </c:when>
        <c:otherwise>
            <h2>${applicationRoot.applicationName} Root</h2>
        </c:otherwise>
    </c:choose>

    <cq:include script="js_clientlibs.jsp"/>

</body>