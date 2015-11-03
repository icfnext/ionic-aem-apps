<%@page session="false" contentType="application/javascript; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/apps/ionic-aem-apps/components/global.jsp" %>
<bedrock:component className="com.icfi.aem.apps.ionic.api.models.application.root.ApplicationRoot" name="applicationRoot" />


( function ( angular ) {
    'use strict';

    angular.module(
        '${applicationRoot.applicationName}',
        [<c:forEach var="currentRequiredModule" items="${applicationRoot.requiredAngularModules}" varStatus="status">
                '${currentRequiredModule}' <c:if test="${!status.last}">,</c:if>
            </c:forEach>])
        .config( function ($stateProvider, $urlRouterProvider) {

            $stateProvider
            <c:forEach var="applicationState" items="${applicationRoot.applicationStates}">
                <c:if test="${!applicationState.structuralState}">
                    .state( '${applicationState.id}', {
                        abstract: ${applicationState.abstract},
                        url: '${applicationState.url}',
                        <c:choose>
                            <c:when test="${isPreviewMode}">
                                templateUrl: '${applicationState.path}.template.html'
                            </c:when>
                            <c:otherwise>
                                templateUrl: '${applicationState.template}'
                            </c:otherwise>
                        </c:choose>
                        <c:if test="${applicationState.hasAngularController}">
                            ,controller: '${applicationState.angularController}'
                        </c:if>

                        } )
                </c:if>
            </c:forEach>
            ;

            $urlRouterProvider.otherwise( '${applicationRoot.initialStateUrl}' );

        } );

    <cq:include script="angular-app-module-suffix.jsp"/>
} )( angular );
