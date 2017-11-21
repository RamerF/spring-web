$( function() {
    $( ".md-form input[name='username'],.md-form input[name='password']" ).blur( function() {
        var username = $( this ).val();
        if (username.trim() != "" && typeof username != "undefined") {
            $( this ).parent( "div" ).addClass( "filled" );
        } else {
            $( this ).parent( "div" ).removeClass( "filled" );
        }
        $( this ).parent( "div" ).removeClass( "focused" );
        return false;
    } );
    $( ".md-form input[name='username'],.md-form input[name='password']" ).focus( function() {
        $( this ).parent( "div" ).addClass( "focused" );
        return false;
    } );
    $( ".md-form input[type='reset']" ).click( function() {
        $( this ).parents( ".md-form" ).children( "div" ).removeClass( "filled" );
    } );
} )