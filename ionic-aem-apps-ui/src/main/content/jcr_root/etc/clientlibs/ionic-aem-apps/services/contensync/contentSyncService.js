/** Service wrapping the content functionality from AEM Apps. */

var citytechinc = citytechinc || {};
citytechinc.services = citytechinc.services || {};
citytechinc.services.contentsync = (function(){
    var _args = {};
    var _applicationRootUrl = '';
    var _packageName = '';
    var _applicationId = '';
    var _closeSplashScreen = false;

    return {

        /**
         * Initializes the content sync function
         * @param Args
         * [0] - path to application root.
         * [1] - package name - matches to the directory above the application root.
         * [2] - application id - used by adobe content sync to identify an application.
         * [3] - closeSplashScreen - true closes it before the redirect, false leaves the closing to application root
         */
        init: function (Args) {
            _args = Args;
            _applicationRootUrl = _args[0];
            _packageName = _args[1];
            _applicationId = _args[2];
            _closeSplashScreen = _args[3];
        },

        /**
         * Syncs the application and redirects to the application root
         */
        sync: function () {

            /** Initialize the application.  Does this need to be done everytime?  */
            var contentInitializer = CQ.mobile.contentInit({
                id: _applicationId
            });

            contentInitializer.initializeApplication(function (error, localContentPath) {
                if (error) {
                    return console.error("ContentSync error. - InitializeApplication: " + error);
                }

                console.log("ContentSync success. Returned path: [" + localContentPath + "]");

                var contentUpdater = CQ.mobile.contentUpdate({
                    id: _applicationId
                });

                /** Initialize doesn't do a pull, so the pull needs to happen here. */
                contentUpdater.isContentPackageUpdateAvailable(_packageName,
                    function callback(error, isUpdateAvailable) {
                        if (error) {
                            console.error("ContentUpdate error. - ContentPackageUpdateAvailable: " + error);
                            return window.location.href = localContentPath + _applicationRootUrl;
                        }

                        if (isUpdateAvailable) {
                            contentUpdater.updateContentPackageByName(_packageName,
                                function callback(error, pathToContent) {
                                    if (error) {
                                        return console.error("ContentUpdate error. - updateContentPackageByName: " + error);
                                    }

                                    if (_closeSplashScreen) {
                                        navigator.splashscreen.hide();
                                    }

                                    console.log("ContentSync success. Returned pathToContent: [" + pathToContent + "]");
                                    return window.location.href = localContentPath + _applicationRootUrl;
                                });
                        }
                        else{
                            if (_closeSplashScreen) {
                                navigator.splashscreen.hide();
                            }
                            return window.location.href = localContentPath + _applicationRootUrl;
                        }
                    });
            });
        }
    }

}());

