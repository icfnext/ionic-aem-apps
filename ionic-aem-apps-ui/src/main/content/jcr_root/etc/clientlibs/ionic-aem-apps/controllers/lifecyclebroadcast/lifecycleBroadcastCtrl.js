angular.module( 'icfi.aem.apps.ionic.lifecyclebroadcast.controller',
    [ 'ionic' ] )
    .controller( 'LifecycleBroadcastCtrl', function ( $scope, $log ) {

        var rebroadcast = function( e, eventData ) {
            if ( e.targetScope == e.currentScope && eventData.stateId ) {
                /*
                 * TODO: This still is not as clean as I would like it to be.  While initial tests indicate it no longer over broadcasts it still requires a controller to be in the mix which I am not a fan of
                 */
                $scope.$broadcast( '$aem.' + e.name, e );
            }
        };

        [
            "$ionicView.loaded",
            "$ionicView.beforeEnter",
            "$ionicView.enter",
            "$ionicView.leave",
            "$ionicView.beforeLeave",
            "$ionicView.afterEnter",
            "$ionicView.afterLeave",
            "$ionicView.unloaded"
        ].forEach( function( currentEventName ) {
            $scope.$on( currentEventName, rebroadcast );
        } );

    } );
