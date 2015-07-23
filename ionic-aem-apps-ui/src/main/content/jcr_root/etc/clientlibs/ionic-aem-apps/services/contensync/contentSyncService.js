angular.module( 'icfi.aem.apps.ionic.contentsync.service', [] )
    .service( 'ContentSyncService', function ( $q ) {

        this.isContentPackageUpdateAvailable = function(contentPackageName) {

            var deferred = $q.defer();

            if (!checkContentUpdate()) {
                deferred.reject(new Error('Content sync not configured.'));
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
        }
    });
