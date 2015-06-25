<%@page session="false" contentType="application/javascript; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/apps/ionic-aem-apps/components/global.jsp" %>
<bedrock:component className="com.icfi.aem.apps.ionic.core.components.page.applicationroot.ApplicationRootComponent" name="applicationRootComponent" />


( function ( angular ) {
    'use strict';

    angular.module(
        '${applicationRootComponent.applicationRoot.applicationName}',
        [<c:forEach var="currentRequiredModule" items="${applicationRootComponent.applicationRoot.requiredAngularModules}" varStatus="status">
                '${currentRequiredModule}' <c:if test="${!status.last}">,</c:if>
            </c:forEach>])
        .config( function ($stateProvider, $urlRouterProvider) {

            $stateProvider
            <c:forEach var="applicationState" items="${applicationRootComponent.applicationRoot.applicationStates}">
                <c:if test="${!applicationState.structuralState}">
                    .state( '${applicationState.id}', {
                        abstract: ${applicationState.abstract},
                        url: '${applicationState.url}',
                        templateUrl: '${applicationState.template}',
                        controller: 'LifecycleBroadcastCtrl'
                        } )
                </c:if>
            </c:forEach>
            ;

            $urlRouterProvider.otherwise( '${applicationRootComponent.applicationRoot.initialStateUrl}' );

        } );

} )( angular );
