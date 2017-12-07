$( function() {
    $( ".md-form input[type='text']" ).blur( function() {
        var username = $( this ).val();
        if (username.trim() != "" && typeof username != "undefined") {
            $( this ).parent( "div" ).addClass( "filled" );
        } else {
            $( this ).parent( "div" ).removeClass( "filled" );
        }
        $( this ).parent( "div" ).removeClass( "focused" );
        return false;
    } );
    $( ".md-form input[type='text']" ).focus( function() {
        $( this ).parent( "div" ).addClass( "focused" );
        return false;
    } );
    $( ".md-form input[type='reset']" ).click( function() {
        $( this ).parents( ".md-form" ).children( "div" ).removeClass( "filled" );
    } );
} )