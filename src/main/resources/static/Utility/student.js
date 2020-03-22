function postStudentData(event) {
    // var studentDto = GenerateJsonArray('formSubmit');
    // console.log(studentDto);
    // $('#studentData').val(JSON.stringify(studentDto));
    // console.log($('#studentData').val());
    var form = $('#formSubmit')[0];

    var data = new FormData(form);


    $("#submit-btn").prop("disabled", true);

    $.ajax({
        type: "POST",
        enctype: 'multipart/form-data',
        url: "/student/save",
        data: data,
        processData: false, //prevent jQuery from automatically transforming the data into a query string
        contentType: false,
        cache: false,
        timeout: 600000,
        success: function (data) {

            // $("#result").text(data);
            console.log("SUCCESS : ", data);
            //         clearInputFields('formSubmit')
            $("#submit-btn").prop("disabled", false);

        },
        error: function (e) {

            // $("#result").text(e.responseText);
            console.log("ERROR : ", e);
            $("#submit-btn").prop("disabled", false);

        }
    });
    

}




/* Form Data to JsonArray */
function GenerateJsonArray ( form_id ) {
    var formDetails = document.getElementById( form_id );
    var elementlenght = formDetails.elements.length - 1;
    var keyy;
    var valuee;
    var obj = {};
    for ( var i = 0; i <= elementlenght; i++ ) {
        if ( formDetails.elements[ i ].name) {
            keyy = formDetails.elements[ i ].name;
            valuee = formDetails.elements[ i ].value;
            obj[ keyy ] = valuee;
        }
        // if ( formDetails.elements[ i ].type == "checkbox" && formDetails.elements[ i ].checked ) {
        //     keyy = formDetails.elements[ i ].name;
        //     valuee = formDetails.elements[ i ].value;
        //     studentDto[ keyy ] = valuee;
        // }
        // if ( formDetails.elements[ i ].type == "radio" && formDetails.elements[ i ].checked == true ) {
        //     keyy = formDetails.elements[ i ].name;
        //     valuee = formDetails.elements[ i ].value;
        //     studentDto[ keyy ] = valuee;
        // }
    }
    return obj;
}

function clearInputFields(form_id){
    var formDetails = document.getElementById( form_id );
    var elementlenght = formDetails.elements.length - 1;
    for (i = 0; i < formDetails.length; i++)
    {
        field_type = formDetails[i].type.toLowerCase();
        switch (field_type)
        {
            case "text":
            case "password":
            case "textarea":
            case "file":
            case "hidden":
                formDetails[i].value = "";
                break;
            case "radio":
            case "checkbox":
                if (formDetails[i].checked)
                {
                    formDetails[i].checked = false;
                }
                break;
            case "select-one":
            case "select-multi":
                formDetails[i].selectedIndex = -1;
                break;
            default:
                break;
        }
    }
}