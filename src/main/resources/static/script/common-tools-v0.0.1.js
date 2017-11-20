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
        var table = $( "<table class='ajax-data-table'></table>" );
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
        var globalPage;
        $.get( _url , _ajaxData , function( page ) {
            globalPage = page;
            // total pages
            var totalPages = globalPage.totalPages;
            // current page number 
            var number = globalPage.number;
            // total elements
            var totalElements = globalPage.totalElements;
            // size of per page
            var size = globalPage.size;
            var pageContainer = initNumberBtn();
            var data = globalPage.content;
            appendBodyData( data );
            table.append( thead );
            table.append( tbody );
            table.append( tfoot );
            $( _container ).append( table );
            $( _container ).append( pageContainer );
            updateNumberBtn( number , totalPages );
            $( _container ).find( "button" ).click( function( e ) {
                $( this ).inkReaction( e );
            } );
            $( "button[id='prevBtn'],button[id='nextBtn']" ).click( function( e ) {
                number = globalPage.number;
                if ($( this ).attr( "id" ).indexOf( "prev" ) > -1) {
                    _ajaxData.page = number - 1;
                    number--;
                } else {
                    _ajaxData.page = number + 1;
                    number++;
                }
                updateNumberBtn( number , globalPage.totalPages );
                ajaxNewData( _url , _ajaxData , size , tbody );
            } );
            $( "button[id*='pageBtn']" ).click( function( e ) {
                var number = $( this ).text();
                if ($( this ).hasClass( "md-flat-btn" ) || isNaN( number )) {
                    return false;
                }
                number -= 1;
                updateNumberBtn( number , globalPage.totalPages );
                _ajaxData.page = number;
                ajaxNewData( _url , _ajaxData , size , tbody );
            } );
        } );
        function initNumberBtn( pageContainer ) {
            var pageContainer = $( "<div class='page-container'></div>" );
            var ul = $( "<ul></ul>" );
            var prevBtn = $( "<li><button class='md-btn md-raised-btn prev-btn' id='prevBtn'>Prev</button></li>" )
            $( ul ).append( prevBtn );
            if (globalPage.totalPages >= 7) {
                for (var i = 1; i < 8; i++) {
                    var li = $( "<li></li>" );
                    var btn = $( "<button class='md-btn md-raised-btn' id='pageBtn" + i + "'>" + i + "</button>" );
                    $( li ).append( btn );
                    $( ul ).append( li );
                }
            } else {
                for (var i = 1; i < globalPage.totalPages + 1; i++) {
                    var li = $( "<li></li>" );
                    var btn = $( "<button class='md-btn md-raised-btn' id='pageBtn" + i + "'>" + i + "</button>" );
                    $( li ).append( btn );
                    $( ul ).append( li );
                }
            }
            var nextBtn = $( "<li><button class='md-btn md-raised-btn next-btn' id='nextBtn'>Next</button></li>" )
            $( ul ).append( nextBtn );
            $( pageContainer ).append( ul );
            return pageContainer;
        }

        function updateNumberBtn( number , totalPages ) {
            number = number + 1;
            $( "#pageBtn7" ).text( totalPages );
            $( "#prevBtn" ).prop( "disabled" , false );
            $( "#nextBtn" ).prop( "disabled" , false );
            if (number == 1) {
                $( "#prevBtn" ).prop( "disabled" , "disabled" );
                colorPageBtn( "#pageBtn1" );
            } else if (number == totalPages) {
                $( "#nextBtn" ).prop( "disabled" , "disabled" );
                colorPageBtn( "#pageBtn7" );
            }
            if (totalPages <= 7) {
                for (var i = 1; i <= totalPages; i++) {
                    $( "#pageBtn" + i ).text( i );
                    if (i == number) {
                        colorPageBtn( "#pageBtn" + number );
                    }
                }
                return false;
            }
            if (number <= 4) {
                for (var i = 2; i < 7; i++) {
                    if (i == 6) {
                        $( "#pageBtn" + i ).text( "..." );
                    } else {
                        $( "#pageBtn" + i ).text( i );
                    }
                    if (i == number) {
                        colorPageBtn( "#pageBtn" + number );
                    }
                }
            } else if (number > totalPages - 4) {
                var btnNum = 2;
                for (var i = totalPages - 5; i < totalPages; i++) {
                    if (btnNum == 2) {
                        $( "#pageBtn" + btnNum ).text( "..." );
                    } else {
                        $( "#pageBtn" + btnNum ).text( i );
                    }
                    if (i == number) {
                        colorPageBtn( "#pageBtn" + btnNum );
                    }
                    btnNum++;
                }
            } else {
                $( "#pageBtn2" ).text( "..." );
                $( "#pageBtn3" ).text( number - 1 );
                $( "#pageBtn4" ).text( number );
                $( "#pageBtn5" ).text( number + 1 );
                $( "#pageBtn6" ).text( "..." );
                colorPageBtn( "#pageBtn4" );
            }
        }

        function colorPageBtn( selector ) {
            $( selector ).parents( "ul" ).find( "button[class*='md-flat-btn']" ).removeClass( "md-flat-btn" ).addClass(
                    "md-raised-btn" )
            $( selector ).removeClass( "md-raised-btn" ).addClass( "md-flat-btn" );
        }

        function ajaxNewData( url , ajaxData , size , tbody ) {
            ajaxData.size = size;
            $.get( url , ajaxData , function( page ) {
                globalPage = page;
                var data = globalPage.content;
                $( tbody ).empty();
                appendBodyData( data );
            } );
        }

        function appendBodyData( data ) {
            $.each( data , function( index ) {
                var _obj = data[index];
                var tr = $( "<tr></tr>" );
                for (_prop in _columns) {
                    var td;
                    if (typeof (_columns[_prop].render) != "undefined") {
                        td = $( "<td>" + _columns[_prop].render( _obj[_columns[_prop].data] ) + "</td>" );
                    } else {
                        td = $( "<td>" + _obj[_columns[_prop].data] + "</td>" );
                    }
                    tr.append( td );
                }
                tbody.append( tr );
            } );
        }
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