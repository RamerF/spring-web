$( function() {
    console.log( "Inherit in Thymeleaf." );
    $.ajaxTable( {
        url : "/users",
        ajaxData : {
            page : 0,
            size : 3
        },
        container : ".ajax-table-container",
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
            data : "username",
            editable : true
        }, {
            data : "password",
            editable : true
        }, {
            data : "createTime",
            render : function( data ) {
                var createTime = new Date( data );
                var date = createTime.getUTCDate() + 1;
                var month = createTime.getUTCMonth() + 1;
                var year = createTime.getUTCFullYear();
                return date + "/" + month + "/" + year;
                // return data;
            }
        }, {
            data : "operator"
        } ],
    } );
    var csrf = $( "#_csrf" ).val();
    $( ".btnUpdate" ).click( function() {
        var thisNode = $( this );
        var uid = $( thisNode ).parents( "tr" ).find( "input[uid]" ).attr( "uid" );
        if (typeof (uid) == "undefined") {
            uid = $( thisNode ).parents( "tr" ).find( "button[uid]" ).attr( "uid" );
        }
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
                            "password" : password,
                            "_csrf" : csrf
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
        if (typeof (uid) == "undefined") {
            uid = $( thisNode ).parents( "tr" ).find( "button[uid]" ).attr( "uid" );
        }
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
                        type : "PUT",
                        data : {
                            "_csrf" : csrf
                        },
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
    $( "#addBtn" ).click( function() {
        $( ".user-form" ).slideToggle();
    } );
    $( "#newUser" ).click( function() {
        var username = $( ".md-form input[name='username']" ).val();
        var password = $( ".md-form input[name='password']" ).val();
        console.debug( username )
        console.debug( password )
        $.post( "/user/add" , {
            "username" : username,
            "password" : password
        } , function( data ) {
            $.alert( {
                content : data.message,
                timeout : 2,
                callback : function() {
                    if (data.result == true) {
                        $( ".user-form" ).slideToggle();
                    }
                }
            } )
        } );
        return false;
    } );

} )