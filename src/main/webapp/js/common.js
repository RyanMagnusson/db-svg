/*
 * DB-SVG Copyright 2012 Derrick Bowen
 * SVG based database visualization tool. http://dbsvg.com
 * DB-SVG is licenced under the GNU General Public License as published by
 *   the Free Software Foundation, version 3. see <http://www.gnu.org/licenses/>.
 */

$.extend({
	getUrlVar : function(name, paramString) {
		paramString = typeof paramString !== 'undefined' ? paramString
				: window.location.href;

		name = name.replace(/[\[]/, "\\\[").replace(/[\]]/, "\\\]");
		var regexS = "[\\?&]" + name + "=([^&#]*)";
		var regex = new RegExp(regexS);
		var results = regex.exec(paramString);
		if (results == null)
			return "";
		else
			return decodeURIComponent(results[1]);
	}

});

$(document).ready(function() {
	$("#loadDialog").dialog({
		autoOpen : false,
		resizable : false,
		height : 170,
		modal : true
	});
});

// displays loading dialog
function showLoading() {
	$("#loadDialog").dialog("open");
}

// displays loading dialog
function hideLoading() {
	$("#loadDialog").dialog("close");
}

// show validation requirements
function updateReqs(t, reqs) {
	reqs.text(t).effect("highlight", {}, 1500);
}
// basic validation
function checkLength(o, n, reqs) {
	if (o.val().length < 1) {
		o.addClass('ui-state-error');
		updateReqs(n + " is required.", reqs);
		return false;
	} else {
		return true;
	}
}

if (typeof String.prototype.startsWith != 'function') {
	String.prototype.startsWith = function(str) {
		return this.slice(0, str.length) == str;
	};
}
if (typeof String.prototype.endsWith != 'function') {
	String.prototype.endsWith = function(str) {
		return this.slice(-str.length) == str;
	};
}