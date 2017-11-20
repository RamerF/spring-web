(function() {
    $.confirm = function( opts ) {
        var paras = $.extend( {
            title : "Tip",
            content : "some message .",
            width : "300px",
            height : "200px",
            opacity : .5,
            btnSure : {
                content : "YES",
                callback : function() {
                }
            },
            btnCancle : {
                content : "NO",
                callback : function() {
                    clearConfirm();
                }
            }
        } , opts || {} );
        function clearConfirm() {
            $( main ).remove();
            $( mask ).remove();
        }
        var _title = paras.title;
        var _content = paras.content;
        var _width = paras.width;
        var _height = paras.height;
        var _opacity = paras.opacity;
        var _stylePrefix = paras.style;
        var _btnSure = paras.btnSure;
        var _btnCancel = paras.btnCancle;
        var mask = $( "<div class='common-mask'></div>" );
        var main = $( "<div class='confirm-main'></div>" );
        var contentContainer = $( "<div class='md-dialog'></div>" );
        var main_title = $( "<div class='confirm-title'>" + _title + "</div>" )
        var main_content = $( "<div class='confirm-content'>" + _content + "</div>" );
        $( contentContainer ).append( main_title );
        $( contentContainer ).append( main_content );
        var btnSureNode = $( "<button class='md md-btn md-flat-btn'>" + _btnSure.content + "</button>" );
        var btnCancelNode = $( "<button class='md md-btn md-flat-btn'>" + _btnCancel.content + "</button>" );
        var main_btn = $( "<div class='confirm-btn md-dialog-btn'></div>" );
        $( main_btn ).append( btnSureNode );
        $( main_btn ).append( btnCancelNode );
        $( "body" ).append( mask );
        $( main ).append( contentContainer );
        $( main ).append( main_btn );
        $( "body" ).append( main );
        $( btnSureNode ).click( function() {
            _btnSure.callback();
            clearConfirm();
        } );
        $( btnCancelNode ).click( function() {
            _btnCancel.callback();
            clearConfirm();
        } );
        $( mask ).on( "click" , function() {
            clearConfirm();
        } );
        return false;
    }
    $.alert = function( opts ) {
        var paras = $.extend( {
            content : "some message .",
            timeout : 2,
            callback : function() {
            }
        } , opts || {} );
        var _content = paras.content;
        var _timeout = paras.timeout;
        function clearAlert() {
            $( main ).remove();
            $( mask ).remove();
        }
        var mask = $( "<div class='common-mask alert-mask'></div>" );
        $( "body" ).append( mask );
        var main = $( "<div class='alert-main'><p>" + _content + "</p></div>" );
        $( "body" ).append( main );
        if (typeof (_timeout) != "number") {
            console.error( "wrong param: timeout." );
            return;
        }
        if (_timeout != 0) {
            setTimeout( function() {
                clearAlert();
            } , _timeout * 1000 );
            paras.callback();
        } else {
            $( mask ).on( "click" , function() {
                clearAlert();
            } );
            paras.callback();
        }
    }
    $.ajaxTable = function( opts ) {
        var paras = $.extend( {
            url : null,
            ajaxData : {},
            container : "ajax-table-container",
            columns : {},
            thead : {},
            callback : function() {
            }
        } , opts || {} );
        var _url = paras.url;
        var _ajaxData = paras.ajaxData;
        var _container = paras.container;
        var _columns = paras.columns;
        var _thead = paras.thead;
        if (_url == null) {
            console.error( "url cannot be null" );
            return false;
        }
        var addBtn = $( "<button id='_addBtn' class='md md-btn md-raised-btn'>NEW</button>" );
        $( _container ).append( addBtn );
        // $( addBtn ).on( "click" , function() {
        // var addContainer = $( "<div class='add-container'></div>" );
        // var usernameNode = $( "<input type='text' name='username'/>" );
        // var passwordNode = $( "<input type='text' name='password'/>" );
        // var addNode = $( "<button id='_doAdd' class='md md-16'>ADD</button>" );
        // $( addContainer ).append( usernameNode ).append( passwordNode ).append( addNode );
        // $( this ).after( addContainer );
        // $( addNode ).on( "click" , function() {
        //     var username = $( this ).prev( "input[name='username']" ).val();
        //     var password = $( this ).prev( "input[name='password']" ).val();
        //     console.debug( username + "><" + password );
        //     $.post( "/user" , {
        //         "username" : username,
        //         "password" : password
        //     } , function() {
        //         $.alert( {
        //             content : "Success to add user."
        //         } );
        //     } );
        // } );
        // } );
        var table = $( "<table></table>" );
        var thead = $( "<thead></thead>" );
        var tbody = $( "<tbody></tbody>" );
        var tfoot = $( "<tfoot></tfoot>" );
        var thr = $( "<tr></tr>" );
        for (_th in _thead) {
            var th;
            if (typeof (_thead[_th].width) != "undefined") {
                th = $( "<th width='" + _thead[_th].width + "'>" + _thead[_th].data + "</th>" );
            } else {
                th = $( "<th>" + _thead[_th].data + "</th>" );
            }
            thr.append( th );
        }
        thead.append( thr );
        $.get( _url , _ajaxData , function( data ) {
            $.each( data , function( index ) {
                var _obj = data[index];
                var tr = $( "<tr></tr>" );
                for (_prop in _columns) {
                    var td;
                    console.debug( _prop + " : " + _obj[_columns[_prop].data] );
                    if (typeof (_columns[_prop].render) != "undefined") {
                        td = $( "<td>" + _columns[_prop].render( _obj[_columns[_prop].data] ) + "</td>" );
                    } else {
                        td = $( "<td>" + _obj[_columns[_prop].data] + "</td>" );
                    }
                    tr.append( td );
                }
                tbody.append( tr );
            } );
            table.append( thead );
            table.append( tbody );
            table.append( tfoot );
            $( _container ).append( table );
        } );
    }
    /**
     * add ink reaction for html cell.
     * @param e
     */
    $.fn.inkReaction = function( e ) {
        var thisNode = $( this );
        var width = $( thisNode ).outerWidth();
        var height = $( thisNode ).outerHeight();
        var top = $( thisNode ).offset().top;
        var left = $( thisNode ).offset().left;

        var inkReactionContainer = $( '<div></div>' );
        $( inkReactionContainer ).addClass( "ink-reaction-container" );
        $( inkReactionContainer ).css( "width" , width + 'px' );
        $( inkReactionContainer ).css( "left" , left + 'px' );
        $( inkReactionContainer ).css( "top" , top + 'px' );
        $( inkReactionContainer ).css( "height" , height + 'px' );
        $( thisNode ).append( inkReactionContainer );
        var radius = (width > height ? width : height) * 2;
        var inkReaction = $( '<div></div>' );
        $( inkReaction ).addClass( "ink-reaction" );
        $( inkReaction ).css( "width" , radius + 'px' );
        $( inkReaction ).css( "height" , radius + 'px' );
        $( inkReaction ).css( "left" , ((e.pageX - left) - radius / 2) + 'px' );
        $( inkReaction ).css( "top" , ((e.pageY - top) - radius / 2) + 'px' );
        inkReactionContainer.append( inkReaction );
        $( inkReaction ).on( 'animationend' , function() {
            inkReactionContainer.remove();
        } );
    }
})( jQuery )