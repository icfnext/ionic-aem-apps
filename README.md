# Ionic AEM Apps

A library supporting the construction of Hybrid mobile applications using AEM Apps and the Ionic Framework.

## Overview

AEM Apps is a module of AEM which can be used to create authorable experiences which present as native mobile applications. 
These applications leverage the Cordova or PhoneGap framework and as such are colloquially referred to as Hybrid applications. 

## Content Structure

## Page Types

### Application Root 

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

#### Abstract States

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

## Adapters

## Bindings


