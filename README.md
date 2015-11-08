# Ionic AEM Apps

A library supporting the construction of Hybrid mobile applications using AEM Apps and the Ionic Framework.

## Overview

AEM Apps is a module of AEM which can be used to create authorable experiences which present as native mobile applications. 
These applications leverage the Cordova or PhoneGap framework and as such are colloquially referred to as Hybrid applications.

To learn more about Cordova visit [https://cordova.apache.org/](https://cordova.apache.org/)
 
### Ionic

This library enforces the use of the Ionic Framework.  This framework wraps Cordova providing a suite of build and test 
tools.  Ionic is also build on Angular, extending many of its common development idioms bringing them in line with the 
expectations of a mobile application.  Numerous widgets, transition effects, and touch behaviors are provided by 
Ionic to make your Cordova application feel as close to a true native application as possible. 
 
To learn more about Ionic visit [http://ionicframework.com/](http://ionicframework.com/) 

## Content Structure

## Page Types

### Application Root 

The Application Root, implemented by the `sling:resourceType` `ionic-aem-apps/components/page/application-root`, 
is responsible for the initialization of the Angular application as a whole.  Sightly scripts are written to handle 
the production of the application module javascript based on the content structure under the root and hooks are provided 
for the addition of application initialization code germane to an application using this framework.  The Application Root 
is never rendered to the end user but *must* be the parent of all Application States. 

#### Application Level Configuration and Run Functionality

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

### Application State

A page created using the `sling:resourceType` `ionic-aem-apps/components/page/application-state` or any of its child 
types represent a State as defined by the [UI Router](https://github.com/angular-ui/ui-router/wiki).  When the application 
module is defined and configured the state pages which are direct or indirect children of the Application Root make up
the states defined through the `$stateProvider`.  

#### Abstract States

States as defined by the UI Router may be abstract.  Abstract states define a frame into which child states may be 
defined but are not visited directly.  

#### Slug States

#### Structural States

### Side Menus Application State

## Ionic Components

### Ion-Content

### Ion-Footer-Bar

### Ion-Nav-View

## Custom Component Development

### Required Angular Modules

### Required Cordova Plugins

## Content Sync 

### Splash Screen

## Building Native Applications

## Adapters

## Bindings


