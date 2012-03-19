var dbi = $.getUrlVar('dbi');

$(function() {
	var title = $("#title"), 
	url = $("#url"), 
	driver = $("#driver"), 
	username = $("#username"), 
	password = $("#password"), 
	allFields = $([]).add(title).add(url).add(driver).add(username).add(password), reqs = $("#validateReqs");

	$("#editConnDialog").dialog({
		autoOpen : false,
		height : 400,
		width : 420,
		modal : false,
		buttons : {
			Ok : function() {
				var bValid = true;
				allFields.removeClass('ui-state-error');

				bValid = bValid && checkLength(title, "Title");
				bValid = bValid && checkLength(url, "URL");
				bValid = bValid && checkLength(driver, "Driver");
				bValid = bValid && checkLength(username, "Username");
				bValid = bValid && checkLength(password, "Password");

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
				resetFields();
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
			"dbi" : dbi,
			"refresh" : "true"
		}, function(data) {
			$('#changer').html(data);
		});
		return false;
	});
	// show validation requirements
	function updateReqs(t) {
		reqs.text(t).effect("highlight", {}, 1500);
	}
	// basic validation
	function checkLength(o, n) {
		if (o.val().length < 1) {
			o.addClass('ui-state-error');
			updateReqs(n + " is required.");
			return false;
		} else {
			return true;
		}

	}
});

// displays add connection dialog
function showEditConn() {
	$("#editConnDialog").dialog("open");
	return false;
}
