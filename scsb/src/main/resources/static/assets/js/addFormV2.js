function sendToAPI1(submission) {
    // 向 API1 發送數據
}

function sendToAPI2(submission) {
    // 向 API2 發送數據
}

const myModal = new bootstrap.Modal(document.getElementById('confirmModal'));
let currentLoginUser= document.getElementById('currentLoginUser').textContent;

function onCancel() {
    // Reset wizard
    $('#smartwizard').smartWizard("reset");

    // Reset form
    document.getElementById("formiopage1").reset();
    document.getElementById("formiopage2").reset();
    document.getElementById("formiopage13").reset();
    document.getElementById("formiopage14").reset();
}

var forms = {};
// propolsal_data 表單中的要新增的內容
let proposalData= {

    emp_id: "-",
    // name: "-",
    // department: "-",
    // rank: "-",
    project_title: "-",
    propolsal_topic: "-",
    // issue_date: "-",
    // form_case_id: "-",
    priority_level: "-",
    security_level: "-",
    status: "-",
    // current_approver: "-",
    // arrive_date: "-",
    form_template_id: "1",
    data:""
};


function showConfirm() {
    alert('成功送出表單！');
    // 提取使用者的表單資訊
    let dataNeed=forms['formiopage2']._submission.data;
    console.log("dataNeed=",dataNeed);
    proposalData.data=JSON.stringify(dataNeed);
    proposalData.emp_id=currentLoginUser;
    console.log("In addFormV2 currentLoginUser=",currentLoginUser);
    var csrf_token = $("meta[name='_csrf']").attr("content");
    var csrf_header = $("meta[name='_csrf_header']").attr("content");

    $.ajax({

        headers: {
            "X-CSRF-Token": csrf_token,
        },

        url: "http://localhost:8888/api/saveProposalData",
        type: 'POST',
        data: JSON.stringify(proposalData),
        contentType: 'application/json',
        dataType: 'text',
        success: function(response) {
            console.log("Data sent and response received:", response);
        },
        error: function(error) {
            console.error("Error:", error);
        }
    });

    const name = $('#first-name').val() + ' ' + $('#last-name').val();
    const products = $('#sel-products').val();
    const shipping = $('#address').val() + ' ' + $('#state').val() + ' ' + $('#zip').val();
    let html = `
                  <div class="row">
                    <div class="col">
                      <h4 class="mb-3-">Customer Details</h4>
                      <hr class="my-2">
                      <div class="row g-3 align-items-center">
                        <div class="col-auto">
                          <label class="col-form-label">Name</label>
                        </div>
                        <div class="col-auto">
                          <span class="form-text-">${name}</span>
                        </div>
                      </div>
                    </div>
                    <div class="col">
                      <h4 class="mt-3-">Shipping</h4>
                      <hr class="my-2">
                      <div class="row g-3 align-items-center">
                        <div class="col-auto">
                          <span class="form-text-">${shipping}</span>
                        </div>
                      </div>
                    </div>
                  </div>
                  
        
                  <h4 class="mt-3">Products</h4>
                  <hr class="my-2">
                  <div class="row g-3 align-items-center">
                    <div class="col-auto">
                      <span class="form-text-">${products}</span>
                    </div>
                  </div>

                  `;
    $("#order-details").html(html);
    $('#smartwizard').smartWizard("fixHeight");

    window.location.reload();
}

$(document).ready(function () {
    var smartWizard = $('#smartwizard').smartWizard({
        selected: 0,
        theme: 'round',
        justified: true,
        autoAdjustHeight: false,
        backButtonSupport: true,
        enableUrlHash: true,
        transition: {
            animation: 'none',
            speed: '400',
            easing: '',
            prefixCss: '',
            fwdShowCss: '',
            fwdHideCss: '',
            bckShowCss: '',
            bckHideCss: '',
        },
        toolbar: {
            position: 'both',
            showNextButton: true,
            showPreviousButton: true,
            extraHtml: `<button id="finishButton" class="btn btn-success disabled" onclick="showConfirm()">送出</button>
                <button class="btn btn-secondary" onclick="onCancel()">取消</button>`
        },
        anchor: {
            enableNavigation: true,
            enableNavigationAlways: false,
            enableDoneState: true,
            markPreviousStepsAsDone: true,
            unDoneOnBackNavigation: true,
            enableDoneStateNavigation: true
        },
        keyboard: {
            keyNavigation: true,
            keyLeft: [37],
            keyRight: [39]
        },
        lang: {
            next: '下一步',
            previous: '返回'
        },
        disabledSteps: [],
        errorSteps: [],
        warningSteps: [],
        hiddenSteps: [2,3],
        getContent: null,
    });
    $("#smartwizard").on("showStep", function (e, anchorObject, stepIndex, stepDirection, stepPosition) {
        //alert("You are on step " + stepIndex + " now");
        var finishButtons = document.querySelectorAll('#finishButton');

        //
        for (var i = 0; i < finishButtons.length; i++) {
            if (stepIndex == 1) {
                finishButtons[i].classList.remove('disabled');
            } else {
                finishButtons[i].classList.add('disabled');
            }
        }
    });



    function createFormioInstance(targetId, url) {
        Formio.createForm(document.getElementById(targetId), url, {readOnly: false}).then(function(form) {
            forms[targetId] = form;
        });
    }

    createFormioInstance('formiopage1', {
        "display": "form",
        "settings": {
            "pdf": {
                "id": "1ec0f8ee-6685-5d98-a847-26f67b67d6f0",
                "src": "https://files.form.io/pdf/5692b91fd1028f01000407e3/file/1ec0f8ee-6685-5d98-a847-26f67b67d6f0"
            }
        },
        "components": [
            {
                "label": "請選擇表單",
                "widget": "choicesjs",
                "tableView": true,
                "dataSrc": "url",
                "data": {
                    "url": "http://localhost:8888/api/template/getList",
                    "headers": [
                        {
                            "key": "",
                            "value": ""
                        }
                    ]
                },
                "validate": {
                    "required": true
                },
                "key": "select",
                "type": "select",
                "input": true,
                "disableLimit": false,
                "noRefreshOnScroll": false,
                "onChange": function (newValue, oldValue) {
                    console.log('用戶選擇了：', newValue.data.select.value);
                    page2url = 'http://localhost:8888/api/template?id=' + newValue.data.select.value;

                    // 回傳資料proposalData 中新增 form_template_id
                    proposalData.form_template_id = newValue.data.select.value.toString();

                    createFormioInstance('formiopage2', page2url);
                }
            }
        ]
    }, {
        readOnly: false
    });

    createFormioInstance('formiopage3', {
        "components": [
            {
                "label": "Data Grid",
                "reorder": true,
                "addAnotherPosition": "bottom",
                "layoutFixed": false,
                "enableRowGroups": false,
                "initEmpty": true,
                "tabindex": "1",
                "hideLabel": true,
                "tableView": true,
                "defaultValue": [
                    {
                        "select": "",
                        "checkbox": false,
                        "checkbox1": false
                    }
                ],
                "key": "dataGrid",
                "type": "datagrid",
                "input": true,
                "components": [
                    {
                        "label": "（部門）姓名",
                        "widget": "choicesjs",
                        "tableView": true,
                        "multiple": true,
                        "dataSrc": "url",
                        "data": {
                            "url": "https://run.mocky.io/v3/4e5d8c65-55b4-4e22-85e2-50e2ed293181",
                            "headers": [
                                {
                                    "key": "",
                                    "value": ""
                                }
                            ]
                        },
                        "dataType": "auto",
                        "idPath": "",
                        "valueProperty": "position",
                        "template": "<span>{{ item.position }} {{ item.name }}</span>",
                        "clearOnRefresh": true,
                        "clearOnHide": false,
                        "key": "select",
                        "type": "select",
                        "selectValues": "data",
                        "disableLimit": false,
                        "noRefreshOnScroll": false,
                        "input": true
                    },
                    {
                        "legend": "調整項",
                        "key": "Set",
                        "type": "fieldset",
                        "label": "Field Set",
                        "input": false,
                        "tableView": false,
                        "components": [
                            {
                                "label": "閱覽通知",
                                "tableView": false,
                                "key": "checkbox",
                                "type": "checkbox",
                                "input": true,
                                "defaultValue": false
                            },
                            {
                                "label": "審核意見通知",
                                "tableView": false,
                                "key": "checkbox1",
                                "type": "checkbox",
                                "input": true,
                                "defaultValue": false
                            }
                        ]
                    }
                ]
            }
        ]
    }, {
        readOnly: false
    });

    createFormioInstance('formiopage4', 'https://run.mocky.io/v3/13c7ae6f-a773-465d-8978-d10cdf08324b');



});

