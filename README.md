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

The Ionic AEM Apps library translates a page tree created via standard AEM authoring tools into a State tree.  In the 
context of this document the word "State" refers to application states as defined by the 
[Angular UI Router](https://github.com/angular-ui/ui-router).   

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
defined but are not visited directly.  An example of usage would be the establishment of an abstract state to house a 
common header bar and/or footer bar.  For child states to be injected into the framing provided by an abstract state, 
the abstract state must contain an instance of the `ionic-aem-apps/components/content/ionnavview` component. 
See the documentation for the Angular UI Router for more details on the usage of abstract states.

#### Slug States

Slug states may be leveraged in instances where the path to a state needs to provide information concerning the 
resource needs of the state.  Using the Angular UI Router a path to a state may be defined using any number of 
slugs.  For example, in the path `/root/speakers/:speakerId`, `:speakerId` is a slug which will be replaced in a true 
path by an actual speaker ID.  A request made to `/root/speakers/194898939` will resolve to the state identified 
by the path `/root/speakers/:speakerId`.  After this, the actual speaker ID can be extracted by controllers using the 
`$stateParams` service.  See the documentation for the Angular UI Router for more details on the usage of slug states.

#### Structural States

Structural States are states which exist solely to provide structure to the URL of the state and do not affect the 
states rendering.  These might be used in cases where content needs to be aranged to allow both for slug states and 
standard states in a reasonable content structure.  Structural States would never be presented to an end user and 
do not exist as actual application states in the Angular application.

### Side Menus Application State

The Side Menus Application State implemented by `sling:resourceType` `ionic-aem-apps/components/page/side-menus-application-state` 
represents the usage of the [Ionic Side Menu](http://ionicframework.com/docs/api/directive/ionSideMenus/) directive.  
Left and/or Right side menus can be enabled via the page properties dialog.  The content of the side menus is defined 
by components pulled into an open paragraph system allowing for full flexibility of the content. 

While not required this state is intended to be used as an Abstract State with the content of child states injected 
into the main content area.

## Ionic Components

The following components are provided by the Ionic AEM Apps library and are intended to be used as-is or extended 
as necessary.

### Ion-Content

Represents an instance of the `ion-content` directive and should be used for holding the principal content of an 
application state.  

* Component Group: `.hidden`
* Authorability
    * Disable Scrolling: Boolean indicating whether the content area allows scrolling

### Ion-Footer-Bar

Represents an instance of the `ion-footer-bar` directive.  The footer bar is intended to be baked into 
page components extending from Application State and allows full flexibility of its content by exposing an 
open paragraph system.

* Component Group: `.hidden`
* Authorability 
    * Always Show: By default, the footer bar will not show if no content has been placed into its paragraph system.  Turning this on will cause the footer bar to always be presented.

### Ion-Nav-View



## Custom Component Development

### Required Angular Modules

### Required Cordova Plugins

## Content Sync 

### Splash Screen

## Building Native Applications

## Adapters

## Bindings


