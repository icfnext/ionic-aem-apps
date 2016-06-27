/*
 * This file exists to fix a problem in the Touch UI authoring interface manifest in
 * http://localhost:4502/libs/cq/gui/components/authoring/editors/clientlibs/core.js
 *
 * During page loading - on the `cq-page-info-loaded` event, this script checks to see if a CQ object exists
 * in the content frame's global namespace.  If one does it further checks to see if CQ.shared.User.data is
 * undefined.  It does not, however, make intermediary checks for the properties between CQ and data.  The Content
 * Sync code we are including creates an otherwise not-present CQ object and as such we end up passing the check for the
 * existence of the CQ object but fail on the check for the existence of CQ.shared.User.data, essentially breaking
 * the touch interface when we preview the application.
 *
 * This hack forces something that is not undefined to be there
 */

window.CQ = window.CQ || {};
CQ.shared = CQ.shared || {};
CQ.shared.User = CQ.shared.User || {};
CQ.shared.User.data = CQ.shared.User.data || {};
