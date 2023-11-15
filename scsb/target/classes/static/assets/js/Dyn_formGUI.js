$(document).ready(function(){
    $('#formGUI').modal('hide');
});

$('#recheck_save_modal').on('click', function (event) {
    
    var formName = $('#formName_GUI').val();
    var signType = $('#signType_GUI').val();

    $('#modalFormName').text(formName);
    $('#modalSignType').text(signType);
    $('#testtest').text(GUI_Json);

    if (!formName || !signType || !GUI_Json) {
        alert('請填寫表單名稱 及 選擇表單是否需要簽核');
        $('#recheck_save').modal('hide');
        return;
    }else{
        saveDataToBackend(GUI_Json);
        $('#recheck_save').modal('show');
    }

    
});
