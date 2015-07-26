angular.module( 'icfi.aem.apps.ionic.lifecyclebroadcast.controller',
    [ 'ionic' ] )
    .controller( 'LifecycleBroadcastCtrl', function ( $scope, $log ) {

        var rebroadcast = function( e ) {
            if ( e.targetScope === $scope.$parent ) {
                $scope.$broadcast('$aem.' + e.name, e);
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
