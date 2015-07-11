# Ionic AEM Apps

A library supporting the construction of Hybrid mobile applications using AEM Apps and the Ionic Framework.

## Application Level Configuration and Run Functionality

A jsp hook, `angular-app-module-suffix.jsp` is provided off of the Application Root resource type.  This is an ideal 
place to code application level configurations and run mechanisms (ie, code which would normally be placed in the 
`.config` and `.run` calls off an Angular application's principal module).  To do so, first reference the module under 
configuration and call config and run methods as necessary.  

```
angular.module( 'applicationName' )
    .config( function( provider ) {
        ...
    } )
    .run( function( service ) {
        ...
    } );
```

This code will be included immediately after the generated application level state configuration.

## Adapters

