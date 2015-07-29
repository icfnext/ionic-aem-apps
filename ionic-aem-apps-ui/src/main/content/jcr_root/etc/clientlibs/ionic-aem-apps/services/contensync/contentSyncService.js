angular.module( 'icfi.aem.apps.ionic.contentsync.service', [] )
    .service( 'ContentSyncService', function ( $q ) {

        this.initializeApplication = function(config){

            var deferred = $q.defer();

            config = config || {};

            config.additionalFiles = config.additionalFiles || [];

            if (!checkContentInitialize()) {
                deferred.reject(new Error('Content init not configured.'));
                return deferred.promise;
            }

            var contentInitializer = CQ.mobile.contentInit(config);

            contentInitializer.initializeApplication(function(error,newLocation) {

                if (error) {
                    deferred.reject(error);
                    return;
                }

                // Truthy newLocation indicates initialization was successful
                // undefined `newLocation` indicates the app has already been initialized
                deferred.resolve(newLocation);
            });

            return deferred.promise;
        };

        this.isContentPackageUpdateAvailable = function(contentPackageName) {

            var deferred = $q.defer();

            if (!checkContentUpdate()) {
                deferred.reject(new Error('Content sync not configured.'));
                return deferred.promise;
            }

            var contentUpdater = CQ.mobile.contentUpdate();

            contentUpdater.isContentPackageUpdateAvailable(contentPackageName,
                function callback(error, isUpdateAvailable) {
                    if (error) {
                        deferred.reject(error);
                    }

                    if (isUpdateAvailable) {
                        deferred.resolve(true);

                    } else {
                        deferred.resolve(false);
                    }
                });

            return deferred.promise;
        };

        this.updateContentPackage = function(contentPackageName){

            var deferred = $q.defer();

            if (!checkContentUpdate()) {
                deferred.reject(new Error('Content sync not configured.'));
                return deferred.promise;
            }

            var contentUpdater = CQ.mobile.contentUpdate();

            contentUpdater.updateContentPackageByName(contentPackageName,
                function callback(error, pathToContent) {
                    if (error) {
                        deferred.reject('Content package update error: ' + error);
                    }

                    deferred.resolve(true);
                });

            return deferred.promise;
        };

        var checkContentUpdate = function(){
            return CQ && CQ.mobile && CQ.mobile.contentUpdate;
        };

        var checkContentInitialize = function(){
            return CQ && CQ.mobile && CQ.mobile.contentInit;
        };
    });
