<%@page session="false" contentType="application/javascript; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/apps/ionic-aem-apps/components/global.jsp" %>
<bedrock:component className="com.citytechinc.things.beermunity.components.page.applicationroot.ApplicationRoot" name="applicationRoot" />


( function ( angular ) {
    'use strict';

    angular.module(
        '${applicationRoot.applicationName}',
        [<c:forEach var="currentRequiredModule" items="${applicationRoot.requiredModules}" varStatus="status">
                '${currentRequiredModule}' <c:if test="${!status.last}">,</c:if>
            </c:forEach>])
        .config( function ($stateProvider, $urlRouterProvider) {

            $stateProvider
            <c:forEach var="applicationState" items="${applicationRoot.applicationStates}">
                .state( '${applicationState.id}', {
                    url: '${applicationState.url}',
                    templateUrl: '${applicationState.template}'
                    } )
            </c:forEach>
            ;

            $urlRouterProvider.otherwise( '${applicationRoot.initialStateUrl}' );

        } );

} )( angular );
