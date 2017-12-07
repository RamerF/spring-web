$( function() {
    $( "#singinBtn" ).click( function() {
        var username = $( "input[name='username']" ).val();
        var password = $( "input[name='password']" ).val();
        var csrf = $( "#_csrf" ).val();
        $.post( "/login" , {
            "username" : username,
            "password" : password,
            "_csrf" : csrf
        } , function( data ) {
            if (data.result == true) {
                location.href = data.message;
            } else {
                $.alert( {
                    content : data.message,
                    timeout : 1
                } )
            }
        } );
        return false;
    } );
    $( "#signupBtn" ).click( function() {
        var username = $( "input[name='username']" ).val();
        var password = $( "input[name='password']" ).val();
        var csrf = $( "#_csrf" ).val();
        $.post( "/signup" , {
            "username" : username,
            "password" : password,
            "_csrf" : csrf
        } , function( data ) {
            if (data.result == true) {
                location.reload();
            } else {
                $.alert( {
                    content : data.message,
                    timeout : 1
                } )
            }
        } );
        return false;
    } );
} )