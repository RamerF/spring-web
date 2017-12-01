(function() {

    $.logAssert = function( condition , errorMessage ) {
        console.assert( condition , errorMessage );
    }
    $.logClear = function() {
        console.clear();
    }
    $.logCount = function( label ) {
        console.count( label );
    }
    $.logDebug = function( paras ) {
        console.debug( _appendLogContent( arguments ) );
    }
    $.logError = function( paras ) {
        console.error( _appendLogContent( arguments ) );
    }

    $.logInfo = function( paras ) {
        console.info( _appendLogContent( arguments ) );
    }

    $.log = function( paras ) {
        console.log( _appendLogContent( arguments ) );
    }

    $.logTable = function( tabularData , properties ) {
        console.table( tabularData , properties );
    }

    $.logTrace = function( paras ) {
        console.trace( _appendLogContent( arguments ) );
    }

    $.logWarn = function( paras ) {
        console.warn( _appendLogContent( arguments ) )
    }

    $.logGroup = function( paras ) {
        console.group( _appendLogContent( arguments ) );
    }
    $.logGroupCollapsed = function( paras ) {
        console.groupCollapsed( _appendLogContent( arguments ) );
    }
    $.logGroupEnd = function() {
        console.groupEnd();
    }

    function _appendLogContent( arguments ) {
        if (typeof arguments == "undefined") {
            return;
        }
        var _logContent = arguments[0];
        for (i = 1; i < arguments.length; i++) {
            _logContent = _logContent.replace( /{}/ , arguments[i] );
        }
        return _logContent;
    }
})( jQuery )