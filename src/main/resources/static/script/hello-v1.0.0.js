$( function() {
    console.log( "Inherit in Thymeleaf." );
    $.ajaxTable( {
        url : "/users",
        ajaxData : {
            page : 0
        },
        container : ".ajax-table-container",
        size : 3,
        thead : [ {
            data : "No.",
            width : "60px"
        }, {
            data : "Username"
        }, {
            data : "Password"
        }, {
            data : "Since"
        }, {
            data : "Operate"
        } ],
        columns : [ {
            data : "id"
        }, {
            data : "username"
        }, {
            data : "password"
        }, {
            data : "createTime",
            render : function( data ) {
                var createTime = new Date( data );
                var date = createTime.getUTCDate();
                var month = createTime.getUTCMonth() + 1;
                var year = createTime.getUTCFullYear();
                return date + "/" + month + "/" + year;
                // return data;
            }
        }, {
            data : "operator"
        } ]
    } );
    $( ".btnUpdate" ).click( function() {
        var thisNode = $( this );
        var uid = $( thisNode ).parents( "tr" ).find( "input[uid]" ).attr( "uid" );
        var username = $( thisNode ).parents( "tr" ).find( "input[class='username']" ).val();
        var password = $( thisNode ).parents( "tr" ).find( "input[class='password']" ).val();
        console.debug( "userId = " + uid );
        console.debug( "username = " + username );
        console.debug( "password = " + password );
        $.confirm( {
            title : "Tip",
            content : "You are modifying information of " + username + " ?",
            btnSure : {
                content : "YES",
                callback : function() {
                    console.debug( "update user info" );
                    $.ajax( {
                        url : "/user/update/" + uid,
                        type : "PUT",
                        data : {
                            "id" : uid,
                            "username" : username,
                            "password" : password
                        }
                    } ).done( function( data ) {
                        $.alert( {
                            content : data.message,
                            timeout : 1
                        } )
                    } )
                }
            },
        } );
        return false;
    } );
    $( ".btnDelete" ).click( function() {
        var thisNode = $( this );
        var uid = $( thisNode ).parents( "tr" ).find( "input[uid]" ).attr( "uid" );
        var username = $( thisNode ).parents( "tr" ).find( "input[class='username']" ).val();
        console.log( "userId = " + uid );
        $.confirm( {
            title : "WARN !",
            content : "You are deleting information of " + username + " ?",
            btnSure : {
                content : "YES",
                callback : function() {
                    console.debug( "delete user info" );
                    $.ajax( {
                        url : "/user/delete/" + uid,
                        type : "DELETE",
                    } ).done( function( data ) {
                        $.alert( {
                            content : data.message,
                            timeout : 1,
                            callback : function() {
                                if (data.result == true) {
                                    $( thisNode ).parents( "tr" ).fadeOut( 'normal' , function() {
                                        $( this ).remove();
                                    } );
                                }
                            }
                        } )
                    } );
                }
            }
        } );
        return false;
    } );

    $( "button[class*='md-']" ).click( function( e ) {
        $( this ).inkReaction( e );
    } );
} )