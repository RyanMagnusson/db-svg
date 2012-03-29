/*
 * DB-SVG Copyright 2012 Derrick Bowen
 * SVG based database visualization tool. http://dbsvg.com
 * DB-SVG is licenced under the GNU General Public License as published by
 *   the Free Software Foundation, version 3. see <http://www.gnu.org/licenses/>.
 */

var dbi = $.getUrlVar('dbi');
var reqs;
var allFields;

$(function() {
	var title = $("#title");
	var url = $("#url");
	var driver = $("#driver");
	var username = $("#username");
	var password = $("#password");
	var allFields = $([]).add(title).add(url).add(driver).add(username).add(
			password);
	var reqs = $("#validateReqs");
	var pageTitle = $("#pageTitle");
	var pageFields = $([]).add(pageTitle);
	var pageReqs = $("#validatePageReqs");

	$("#editConnDialog").dialog({
		autoOpen : false,
		height : 400,
		width : 420,
		modal : false,
		buttons : {
			Ok : function() {
				var bValid = true;
				allFields.removeClass('ui-state-error');

				bValid = bValid && checkLength(title, "Title", reqs);
				bValid = bValid && checkLength(url, "URL", reqs);
				bValid = bValid && checkLength(driver, "Driver", reqs);
				bValid = bValid && checkLength(username, "Username", reqs);
				bValid = bValid && checkLength(password, "Password", reqs);

				if (bValid) {
					$.post("connections/save", {
						"dbi" : dbi,
						"title" : title.val(),
						"url" : url.val(),
						"driver" : driver.val(),
						"username" : username.val(),
						"password" : password.val()
					}, function(data) {
						if (data["val"] == "0") {
							alert("Unable to save connection.");
						} else {
							window.location.reload();
						}
					}, "json");
					$(this).dialog('close');
				}
			},
			Cancel : function() {
				$(this).dialog('close');
			}
		}

	});

	$("#addPageDialog").dialog({
		autoOpen : false,
		height : 400,
		width : 420,
		modal : false,
		buttons : {
			Ok : function() {
				var bValid = true;
				allFields.removeClass('ui-state-error');

				bValid = bValid && checkLength(pageTitle, "Title", pageReqs);

				if (bValid) {
					$.post("pages/add", {
						"dbi" : dbi,
						"title" : pageTitle.val(),
					}, function(data) {
						if (data["val"] == "0") {
							alert(data.message);
						} else {
							$.post("setup/pages", {
								"dbi" : dbi
							}, function(data) {
								$('#changer').html(data);
							});
						}
					}, "json");
					$(this).dialog('close');
				}
			},
			Cancel : function() {
				$(this).dialog('close');
			}
		}

	});

	$('#info').click(function() {
		$.post("setup/info", {
			"dbi" : dbi
		}, function(data) {
			$('#changer').html(data);
		});
		return false;
	});

	$('#pages').click(function() {
		$.post("setup/pages", {
			"dbi" : dbi
		}, function(data) {
			$('#changer').html(data);
		});
		return false;
	});
});

// displays edit connection dialog
function showEditConn() {
	$("#editConnDialog").dialog("open");
	return false;
}

// displays edit connection dialog
function showAddPage() {
	$("#addPageDialog").dialog("open");
	return false;
}

function selectPageForTable(page, table, input) {
	$.post("pages/setPageForTable", {
		"dbi" : dbi,
		"page" : page,
		"table" : table,
		"checked" : input.checked
	}, function(data) {
		if (data.val == 0) {
			alert(data.message);
		}
	}, "json");
}

function selectAllTablesForPage(page) {
	$(".page" + page).attr("checked", "checked");
	$.post("pages/selectAll", {
		"dbi" : dbi,
		"page" : page
	}, function(data) {
		if (data.val == 0) {
			alert(data.message);
		}
	}, "json");
}

function deselectAllTablesForPage(page) {
	$(".page" + page).attr("checked", "");
	$.post("pages/deselectAll", {
		"dbi" : dbi,
		"page" : page
	}, function(data) {
		if (data.val == 0) {
			alert(data.message);
		}
	}, "json");
}

function deletePage(page) {
	$.post("pages/remove", {
		"dbi" : dbi,
		"page" : page
	}, function(data) {
		if (data.val == 0) {
			alert(data.message);
		} else {
			$.post("setup/pages", {
				"dbi" : dbi
			}, function(data) {
				$('#changer').html(data);
			});
		}
	}, "json");
}

function renameTable(page) {
	var $inputlink = $("#ptitle-" + page);
	var $parent = $inputlink.parent();
	pageTitle = $inputlink.text();
	$parent.empty();
	$parent.append("<input id='pageTitleRenamer' value='" + pageTitle
			+ "'></input>");
	$("#pageTitleRenamer").blur(function() {
		renameTableSubmit(page, $parent);
	});
	$('#pageTitleRenamer').keypress(function(e) {
		if (e.which == 13) {
			$(this).blur();
		}
	});
	$("#pageTitleRenamer").focus();
}

function renameTableSubmit(page, $parent) {
	var newTitle = $("#pageTitleRenamer").val();
	$.post("pages/save", {
		"dbi" : dbi,
		"page" : page,
		"title" : newTitle
	}, function(data) {
		if (data.val == 0) {
			alert(data.message)
		}
	}, "json");
	$parent.empty();
	$parent.append('<a href="#" id="ptitle-' + page
			+ '" title="click to Rename" onclick="renameTable(\'' + page
			+ '\'); return false;">' + newTitle + '</a>');
}
