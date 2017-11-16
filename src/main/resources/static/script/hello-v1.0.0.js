$( function() {
    console.log( "Inherit in Thymeleaf." );
    $( ".btnUpdate" ).click( function() {
        var uid = $( this ).parents( "tr" ).find( "input[uid]" ).attr( "uid" );
        var username = $( this ).parents( "tr" ).find( "input[class='username']" ).val();
        var password = $( this ).parents( "tr" ).find( "input[class='password']" ).val();
        console.debug( "userId = " + uid );
        console.debug( "username = " + username );
        console.debug( "password = " + password );
        $.confirm( {
            title : "Tip",
            content : "You are modifying information of " + username + " ?",
            btnSure : {
                content : "YES",
                callback : function() {
                    console.debug( "更新用户信息" );
                    $.ajax( {
                        url : "/user/update/" + uid,
                        type : "PUT",
                        data : {
                            "id" : uid,
                            "username" : username,
                            "password" : password
                        }
                    } ).done( function( data ) {
                        alert( data.message )
                    } )
                }
            },
        } );
        return false;
    } );
    $( ".btnDelete" ).click( function() {
        var uid = $( this ).parents( "tr" ).find( "input[uid]" ).attr( "uid" );
        var username = $( this ).parents( "tr" ).find( "input[class='username']" ).val();
        console.log( "userId = " + uid );
        $.confirm( {
            title : "标题",
            content : "You are deleting information of " + username + " ?",
            btnSure : {
                content : "YES",
                callback : function() {
                    console.debug( "delete user info" );
                    $.ajax( {
                        url : "/user/delete/" + uid,
                        type : "DELETE",
                    } ).done( function( data ) {
                        alert( data.message )
                    } );
                }
            }
        } );
        return false;
    } )
} )