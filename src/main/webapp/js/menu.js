var connToDelete;
$(function() {
    var title = $("#title"),
    url = $("#url"),
    driver = $("#driver"),
    username = $("#username"),
    password = $("#password"),
    allFields = $([]).add(title).add(url).add(driver).add(username).add(password),
    reqs = $("#validateReqs");

    $("#loadDialog").dialog({
        autoOpen: false,
        resizable: false,
        height:170,
        modal: true
    });
    $("#removeDialog").dialog({
        autoOpen: false,
        resizable: false,
        height:230,
        modal: true,
        buttons: {
            Ok: function() {
                $.post("connections/remove", { "id": connToDelete },
                    function(data){
                        if (data["val"] == "0") {
                            alert("Unable to delete connection.");
                        } else {
                            $("#conn"+connToDelete).remove();
                        }
                    }, "json");
                $(this).dialog('close');
            },
            Cancel: function() {
                $(this).dialog('close');
            }
        }
    });
    $("#addDialog").dialog({
        autoOpen: false,
        height: 400,
        width: 420,
        modal: false,
        buttons: {
            Ok: function() {
                var bValid = true;
                allFields.removeClass('ui-state-error');

                bValid = bValid && checkLength(title,"Title");
                bValid = bValid && checkLength(url,"URL");
                bValid = bValid && checkLength(driver,"Driver");
                bValid = bValid && checkLength(username,"Username");
                bValid = bValid && checkLength(password,"Password");

                if (bValid) {
                    $.post("connections/add", { "title": title.val(), "url": url.val(),
                        "driver": driver.val(), "username": username.val(), "password": password.val() },
                    function(data){
                        if (data["val"] == "0") {
                            alert("Unable to add connection.");
                        } else {
							var connId = data["val"];
							$(".frontmenuadd").remove();
				    		$(".frontmenulist").append(makeDBLink(connId, title.val()));
							$(".frontmenulist").append('<li class="frontmenuadd"><a href="#" onclick="showAdd();" >+ Add Connection</a></li>');
							resetFields();
                        }
                    }, "json");
                    $(this).dialog('close');
                }
            },
            Cancel: function() {
                $(this).dialog('close');
                resetFields();
            }
        }

    });
    $.getJSON("connections/list", function(data){
    	$(".frontmenuadd").remove();
    	$.each(data.connections, function(i,connection){
    		$(".frontmenulist").append(makeDBLink(connection.id, connection.title));
	    });
    	$(".frontmenulist").append('<li class="frontmenuadd"><a href="#" onclick="showAdd();" >+ Add Connection</a></li>');
    });
    // show validation requirements
    function updateReqs(t) {
        reqs.text(t).effect("highlight",{},1500);
    }
    // basic validation
    function checkLength(o,n) {
        if ( o.val().length < 1 ) {
            o.addClass('ui-state-error');
            updateReqs( n + " is required.");
            return false;
        } else {
            return true;
        }

    }
    function resetFields(){
        allFields.val('').removeClass('ui-state-error');
        title.val('My Database');
        url.val('jdbc:mysql://localhost:3306/mydb');
        driver.val('com.mysql.jdbc.Driver');
        reqs.text(' ');
    }
});
function makeDBLink(connId, title ){
	html = '<li id="conn' + connId + '"><a href="diagram?dbi=' + connId + '" onclick="showLoading();" >';
	html += title + '</a><span class="frontmenusetup"><a href="setup?dbi=' + connId;
	html += '" onclick="showLoading();" ><img src="images/document-properties.png\" alt=\"Setup\" border=0></a></span><span class="frontmenuremove"><a href="#Remove';
	html += connId + '" onclick=\"showRemove(' + connId + ',\'';
	html += title + '\');" ><img src="images/edit-delete.png" alt="Remove" border=0></a></span></li>';
	return html;
}

// displays loading dialog
function showLoading() {
    $("#loadDialog").dialog("open");
}
// displays add connection dialog
function showAdd() {
    $("#addDialog").dialog("open");
}
// displays add connection dialog
function showRemove(idToNix,titleToNix) {
    connToDelete = idToNix;
    $("#removeText").text(titleToNix);
    $("#removeDialog").dialog("open");
}