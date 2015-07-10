<%@include file="/apps/ionic-aem-apps/components/global.jsp" %>
<%-- To be overridden at the application level.  This hook is intended for the inclusion of application level
     configurations and the inclusion of an application run method.

     Example

     module( '${appName}' )
        .config( function( someProvider ) {
            ...
        } )
        .run( function( someService ) {
            ...
        } );
      --%>