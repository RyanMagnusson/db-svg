/*
 * DB-SVG Copyright 2012 Derrick Bowen
 * SVG based database visualization tool. http://dbsvg.com
 * DB-SVG is licenced under the GNU General Public License as published by
 *   the Free Software Foundation, version 3. see <http://www.gnu.org/licenses/>.
 */

$(function() {
	$("#saveDialog").dialog({
		autoOpen : false,
		resizable : true,
		height : 260,
		modal : true,
		buttons : {
			Ok : function() {
				$(this).dialog('close');
			},
			Cancel : function() {
				$(this).dialog('close');
			}
		}
	});
});

function isWebKit() {
	return RegExp(" AppleWebKit/").test(navigator.userAgent);
}
function f_scrollLeft() {
	return f_filterResults(window.pageXOffset ? window.pageXOffset : 0,
			document.documentElement ? document.documentElement.scrollLeft : 0,
			document.body ? document.body.scrollLeft : 0);
}
function f_scrollTop() {
	return f_filterResults(window.pageYOffset ? window.pageYOffset : 0,
			document.documentElement ? document.documentElement.scrollTop : 0,
			document.body ? document.body.scrollTop : 0);
}
function f_filterResults(n_win, n_docel, n_body) {
	var n_result = n_win ? n_win : 0;
	if (n_docel && (!n_result || (n_result > n_docel)))
		n_result = n_docel;
	return n_body && (!n_result || (n_result > n_body)) ? n_body : n_result;
}
function showSave(message) {
    $("#saveText").text(message);
    $("#saveDialog").dialog("open");
}