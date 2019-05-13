$(document).ready(function() {

    $(window).bind("beforeunload", function() {
        $("*").css("cursor", "progress");
    });

    $(window).bind("load"), function() {
        $("*").css("cursor", "pointer");
    }

});