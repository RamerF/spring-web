(function() {
    $.confirm = function( opts ) {
        var paras = $.extend( {
            title : "Tip",
            content : "some message .",
            width : "300px",
            height : "200px",
            opacity : .5,
            btnSure : {
                content : "Sure",
                callback : function() {
                }
            },
            btnCancle : {
                content : "Cancel",
                callback : function() {
                    clearAlert();
                }
            }
        } , opts || {} );
        function clearAlert() {
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
        var mask = $( "<div class='alert-mask'></div>" );
        var main = $( "<div class='alert-main'></div>" );
        var main_title = $( "<div class='alert-title'>" + _title + "</div>" )
        var main_content = $( "<div class='alert-content'>" + _content + "</div>" );
        var btnSureNode = $( "<button>" + _btnSure.content + "</button>" );
        var btnCancelNode = $( "<button>" + _btnCancel.content + "</button>" );
        var main_btn = $( "<div class='alert-btn'></div>" );
        $( main_btn ).append( btnSureNode );
        $( main_btn ).append( btnCancelNode );
        $( "body" ).append( mask );
        $( main ).append( main_title );
        $( main ).append( main_content );
        $( main ).append( main_btn );
        $( "body" ).append( main );
        $( btnSureNode ).click( function() {
            _btnSure.callback();
            clearAlert();
        } );
        $( btnCancelNode ).click( function() {
            _btnCancel.callback();
            clearAlert();
        } );
        $( mask ).on( "click" , function() {
            clearAlert();
        } );
        return false;
    }
})( jQuery )