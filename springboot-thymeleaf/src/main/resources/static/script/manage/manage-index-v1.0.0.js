$( function() {
    var csrf = $( "#_csrf" ).val();
    var album;
    qiniuUploader( "uploadBtn" , "upload-container" , {
        'FileUploaded' : function( up , file , info ) {
            album = up.getOption( 'domain' ) + "/" + JSON.parse( info ).key;
            $( "#addTopic" ).click( addTopic )
        },
        'FilesAdded' : function( up , files ) {
            plupload.each( files , function( file ) {
                var image = new Image();
                var preloader = new mOxie.Image();
                preloader.onload = function() {
                    preloader.downsize( this.width , this.height );
                    image.setAttribute( "src" , preloader.getAsDataURL() );
                    $( "div.topic" ).css( "width" , "520px" );
                    $( "div.topic-info" ).css( "animation" , "preview-topic-anim 1s ease-in-out" );
                    $( '.preview-container' ).show( 1000 ).append( image );
                };
                preloader.load( file.getSource() );
                $( "#fileName" ).text( file.name );
            } );
        }
    } );
    function addTopic() {
        var title = $( "#title" ).val();
        var url = $( "#url" ).val();
        $.logInfo( "add new topic : title:{}, url:{}, album:{}" , title , url , album );
        $.post( "/manage/topic" , {
            "url" : url,
            "title" : title,
            "album" : album,
            "_csrf" : csrf
        } , function( data ) {
            $.alert( data.message );
            if (data.result == true) {
                $.confirm( {
                    title : "Tip",
                    content : data.message,
                    btnSure : {
                        content : "I know",
                        callback : function() {
                            location.reload();
                        }
                    },
                    btnCancel : {
                        content : "ok",
                        callback : function() {
                            location.reload();
                        }
                    }
                } )
            }
        } );
        return false;
    }
    /*<![CDATA[*/
    function qiniuUploader( browse_button , container , init ) {
        Qiniu.uploader( {
            runtimes : 'html5,flash,html4',
            browse_button : browse_button,
            uptoken_url : '/manage/upload/token',
            unique_names : false,
            save_key : false,
            domain : 'http://oqdpapa6l.bkt.clouddn.com',
            container : container,
            max_file_size : '100mb',
            flash_swf_url : '/script/qiniu/Moxie.swf',
            max_retries : 3,
            dragdrop : true,
            drop_element : container,
            chunk_size : '4mb',
            auto_start : true,
            init : {
                'FilesAdded' : init != null && init.FilesAdded != null ? init.FilesAdded : function( up , files ) {
                },
                'BeforeUpload' : init != null && init.BeforeUpload != null ? init.BeforeUpload : function( up , file ) {
                },
                'UploadProgress' : init != null && init.UploadProgress != null ? init.UploadProgress
                        : function( up , file ) {
                            var progressWidth = $( ".upload-progress" ).width();
                            $( ".upload-progress div" ).css( "width" ,
                                    new Number( file.percent * progressWidth / 100 ) >>> 0 );
                        },
                'FileUploaded' : init != null && init.FileUploaded != null ? init.FileUploaded : function( up , file ,
                        info ) {
                    fileDownUrl = up.getOption( 'domain' ) + "/" + JSON.parse( info ).key;
                },
                'Error' : init != null && init.Error != null ? init.Error : function( up , err , errTip ) {
                },
                'UploadComplete' : init != null && init.UploadComplete != null ? init.UploadComplete : function() {
                },
                'Key' : init != null && init.Key != null ? init.Key : function( up , file ) {
                    //返回值为保存文件名
                    // var fileName = file.name.substr( 0 , file.name.lastIndexOf( "." ) );
                    // var suffix = file.name.substr( fileName.length );
                    // return fileName + "-" + new Date().format( "yyyyMMddhhmmss" ) + suffix;
                    return file.name;
                }
            }
        } );
    }
    /*]]>*/
} )