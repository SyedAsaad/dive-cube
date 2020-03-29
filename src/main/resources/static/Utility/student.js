/***********************************************************************************************************************************************************************************************************************************************************************************************************
 * File Validation
 **********************************************************************************************************************************************************************************************************************************************************************************************************/

$( '.validateFile' ).bind( 'change', function () {
    if ( $( this ).val() != '' ) {
        var fileName = $( this ).val();
        var fileExtension = fileName.split( '.' )[ fileName.split( '.' ).length - 1 ];
        var fileSize = this.files[ 0 ].size;

        if ( !( fileExtension === "jpg" || fileExtension === "jpeg" || fileExtension === "png" || fileExtension === "JPG" || fileExtension === "JPEG" ) ) {
            $( this ).val( '' );
            $( '#fileEr' ).html( "<span class='inputError'>File format is not supported</span>" ).show().fadeOut( messageTimeOut );

            $( this ).focus();
            // file size max 5MB allowed 5242880
        } else if ( fileSize > 1200000 ) {
            $( this ).val( '' );
            $( '#fileEr' ).html( "<span class='inputError'>File size is too large</span>" ).show().fadeOut( messageTimeOut );
            $( this ).focus();
            return false;
        } else {
            $( '#fileEr' ).html( "" );
            reader.readAsDataURL($('#fileUploadForm')[0].files[0]);
            return true;
        }
    }
} );


/***********************************************************************************************************************************************************************************************************************************************************************************************************
 * removeImg Validation
 **********************************************************************************************************************************************************************************************************************************************************************************************************/
$( '.removeImg' ).bind( 'click', function () {
    $( "#removeBtn" ).hide();
    $( "#imgSpn" ).attr( 'src', "" );
    $( ".b64" ).val( "" );
} );


var reader = new FileReader();
reader.onload = function(r_event) {
    $('#showImage').show()
    document.getElementById('imgSpn').setAttribute('src', r_event.target.result);
}
