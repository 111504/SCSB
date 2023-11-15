
let selectedFormCaseId;
// Formatting function for row details - modify as you need
function format_unreview(d) {
    // `d` is the original data object for the row
     return (
        '<dl>' +
        '<dt>發案人:</dt>' +
        '<dd>' +
        d.name +
         '</dd>' +
        '<dt>發案主題:</dt>' +
        '<dd>' +
        d.projectTitle +
         '</dd>' +
        '<dt>發案等級:</dt>' +
        '<dd>' +
        d.securityLevel +
        '</dd>' +
        '<dt>發案案由:</dt>' +
        '<dd>' +
        d.proposalTopic +
        `<dt></dt><dd><button  class="btn btn-primary" data-bs-target="#modal-1" data-bs-toggle="modal-1" onclick="openModal_seemore('${d.formCaseId}', '${d.formTemplateId}')">查看詳情</button></dd>` +
        '</dl>'
    );
}
function openModal_seemore(formId,formTemplateId) {
    var modal = document.getElementById('modal-seemore'); // 获取模态框元素
    //var formIdElement = modal.querySelector('#formId'); // 获取包含 formId 的元素
    var csrf_token = $("meta[name='_csrf']").attr("content");
    var csrf_header = $("meta[name='_csrf_header']").attr("content");
    // 设置 formId 的文本内容
   // formIdElement.innerText = formId;
    console.log("獲取表單 id=",formId,"的data內容")
    let datajson;
    // 打开模态框
       $.ajax({
          headers: {
                       "X-CSRF-Token": csrf_token

           },
           url: "http://localhost:8888/api/user/personal-formiojson?formCaseId=" + encodeURIComponent(formId),
           type: 'GET',
           dataType: 'json',
           async: true,//同步方法
           success: function (response) {
               console.log("Data sent and response received:", response);
               datajson = response;
               // 打开模态框
               var modalInstance = new bootstrap.Modal(modal);
               Formio.createForm(document.getElementById('formio_incodent_table'), 'http://localhost:8888/api/template?id=' + formTemplateId, {
                   readOnly: true,
               }).then(function (form) {
                   // Default the submission.
                   form.submission = datajson;
                   //console.log("datajson inner received:", typeof datajson);

               });
               modalInstance.show();
           },
           error: function (error) {
               console.error("Error:", error);
           }
       });
}

let currentLoginUser = document.getElementById('currentLoginUser').textContent;


let tablereviewlist = new DataTable('#tablereviwelist', {
    ajax: {
        url:'http://localhost:8888/api/user/pending-records?empId='+currentLoginUser,

    },

    columns: [
        {
            className: 'dt-control',
            orderable: false,
            data: null,
            defaultContent: ''
        },
        {
            className: 'operate-button',
            data: null,
            defaultContent: '<div class="btn-group" role="group"><button class="btn btn-primary" id="sign-button" type="button" style="background: rgb(4,205,48);" data-bs-target="#modal-sign" data-bs-toggle="modal" onclick="verifyOpenModel(this.id)"><i class="fas fa-check"></i></button><button class="btn btn-primary" id="rollback-button" type="button" style="background: rgb(169,14,5);" data-bs-target="#modal-rollback" data-bs-toggle="modal" onclick="verifyOpenModel(this.id)"><i class="far fa-times-circle"></i></div>'
        },
         
        { data: 'formCaseId' },
        { data: 'name' },
        {
            data: null,
        },
        { data: 'department' },
        { data: 'rank' },

        
        { data: 'projectTitle' },
        {
            data: 'priorityLevel',
            render: function (data, type) {

                if (type === 'display') {
                    let color = 'green';

                    switch (data) {
                        case '緊急':
                            color = 'red';
                            break;
                        case 'Medium':
                            color = 'orange';
                            break;
                    }
                    return `<span style="color:${color}">${data}</span>`;
                }

                return data;
            }
        },
        {
            data: 'arrivalDate',
            render: function (data, type) {
        if (type === 'display') {
            var date = moment(data).format('YYYY-MM-DD a hh:mm');
            return date;
        }

        return data; // 在其它情况下保持原样
    }
        },
        

    ],
    order: [[9, 'desc']],
    responsive: true,
    paging: true,
    rowCallback: function (row, data, index) {
        var api = this.api();
        var rowNum = api.row(row).index() + 1;
        $('td:eq(0)', row).html(rowNum); // 流水號放第一行
    },
    columnDefs: [
        { orderable: false, targets: 1 },
        {visible: false, targets: [ 2 ]},
                    {
                "targets": [ 4 ], // 指定你想操作的列的索引，假设这里是第一列
                "render": function ( data, type, row ) {
                    return '(' + row.department + ' ) ' + row.rank + '';
                }
            },
            { "targets": [ 5, 6 ], "visible": false } // 隐藏 department 和 rank 列
    ],
    dom: 'frtip',
    buttons: [{
        extend: 'colvis',
        text: '自定義表格',
        columns: 'th:nth-child(n+2):not(:last-child)',
        columnText: function (dt, idx, title) {
            return (idx) + ': ' + title;
        }

    },
    {
        text: '刷新',
        action: function (e, dt, node, config) {
            dt.ajax.reload();
        }
    }
    ],
    language:{
        "processing": "處理中...",
        "loadingRecords": "載入中...",
        "paginate": {
            "first": "第一頁",
            "previous": "上一頁",
            "next": "下一頁",
            "last": "最後一頁"
        },
        "emptyTable": "目前沒有資料",
        "datetime": {
            "previous": "上一頁",
            "next": "下一頁",
            "hours": "時",
            "minutes": "分",
            "seconds": "秒",
            "amPm": [
                "上午",
                "下午"
            ],
            "unknown": "未知",
            "weekdays": [
                "週日",
                "週一",
                "週二",
                "週三",
                "週四",
                "週五",
                "週六"
            ],
            "months": [
                "一月",
                "二月",
                "三月",
                "四月",
                "五月",
                "六月",
                "七月",
                "八月",
                "九月",
                "十月",
                "十一月",
                "十二月"
            ]
        },
        "searchBuilder": {
            "add": "新增條件",
            "condition": "條件",
            "deleteTitle": "刪除過濾條件",
            "button": {
                "_": "複合查詢 (%d)",
                "0": "複合查詢"
            },
            "clearAll": "清空",
            "conditions": {
                "array": {
                    "contains": "含有",
                    "equals": "等於",
                    "empty": "空值",
                    "not": "不等於",
                    "notEmpty": "非空值",
                    "without": "不含"
                },
                "date": {
                    "after": "大於",
                    "before": "小於",
                    "between": "在其中",
                    "empty": "為空",
                    "equals": "等於",
                    "not": "不為",
                    "notBetween": "不在其中",
                    "notEmpty": "不為空"
                },
                "number": {
                    "between": "在其中",
                    "empty": "為空",
                    "equals": "等於",
                    "gt": "大於",
                    "gte": "大於等於",
                    "lt": "小於",
                    "lte": "小於等於",
                    "not": "不為",
                    "notBetween": "不在其中",
                    "notEmpty": "不為空"
                },
                "string": {
                    "contains": "含有",
                    "empty": "為空",
                    "endsWith": "字尾為",
                    "equals": "等於",
                    "not": "不為",
                    "notEmpty": "不為空",
                    "startsWith": "字首為",
                    "notContains": "不含",
                    "notStartsWith": "開頭不是",
                    "notEndsWith": "結尾不是"
                }
            },
            "data": "欄位",
            "leftTitle": "群組條件",
            "logicAnd": "且",
            "logicOr": "或",
            "rightTitle": "取消群組",
            "title": {
                "_": "複合查詢 (%d)",
                "0": "複合查詢"
            },
            "value": "內容"
        },
        "editor": {
            "close": "關閉",
            "create": {
                "button": "新增",
                "title": "新增資料",
                "submit": "送出新增"
            },
            "remove": {
                "button": "刪除",
                "title": "刪除資料",
                "submit": "送出刪除",
                "confirm": {
                    "_": "您確定要刪除您所選取的 %d 筆資料嗎？",
                    "1": "您確定要刪除您所選取的 1 筆資料嗎？"
                }
            },
            "error": {
                "system": "系統發生錯誤(更多資訊)"
            },
            "edit": {
                "button": "修改",
                "title": "修改資料",
                "submit": "送出修改"
            },
            "multi": {
                "title": "多重值",
                "info": "您所選擇的多筆資料中，此欄位包含了不同的值。若您想要將它們都改為同一個值，可以在此輸入，要不然它們會保留各自原本的值。",
                "restore": "復原",
                "noMulti": "此輸入欄需單獨輸入，不容許多筆資料一起修改"
            }
        },
        "autoFill": {
            "cancel": "取消"
        },
        "buttons": {
            "copySuccess": {
                "_": "複製了 %d 筆資料",
                "1": "複製了 1 筆資料"
            },
            "copyTitle": "已經複製到剪貼簿",
            "excel": "Excel",
            "pdf": "PDF",
            "print": "列印",
            "copy": "複製",
            "colvis": "欄位顯示",
            "colvisRestore": "重置欄位顯示",
            "csv": "CSV",
            "pageLength": {
                "-1": "顯示全部",
                "_": "顯示 %d 筆"
            },
            "createState": "建立狀態",
            "removeAllStates": "移除所有狀態",
            "removeState": "移除",
            "renameState": "重新命名",
            "savedStates": "儲存狀態",
            "stateRestore": "狀態 %d",
            "updateState": "更新"
        },
        "searchPanes": {
            "collapse": {
                "_": "搜尋面版 (%d)",
                "0": "搜尋面版"
            },
            "emptyPanes": "沒搜尋面版",
            "loadMessage": "載入搜尋面版中...",
            "clearMessage": "清空",
            "count": "{total}",
            "countFiltered": "{shown} ({total})",
            "title": "過濾條件 - %d",
            "showMessage": "顯示全部",
            "collapseMessage": "摺疊全部"
        },
        "stateRestore": {
            "emptyError": "名稱不能空白。",
            "creationModal": {
                "button": "建立",
                "columns": {
                    "search": "欄位搜尋",
                    "visible": "欄位顯示"
                },
                "name": "名稱：",
                "order": "排序",
                "paging": "分頁",
                "scroller": "卷軸位置",
                "search": "搜尋",
                "searchBuilder": "複合查詢",
                "select": "選擇",
                "title": "建立新狀態",
                "toggleLabel": "包含："
            },
            "duplicateError": "此狀態名稱已經存在。",
            "emptyStates": "名稱不可空白。",
            "removeConfirm": "確定要移除 %s 嗎？",
            "removeError": "移除狀態失敗。",
            "removeJoiner": "和",
            "removeSubmit": "移除",
            "removeTitle": "移除狀態",
            "renameButton": "重新命名",
            "renameLabel": "%s 的新名稱：",
            "renameTitle": "重新命名狀態"
        },
        "select": {
            "columns": {
                "_": "選擇了 %d 欄資料",
                "1": "選擇了 1 欄資料"
            },
            "rows": {
                "1": "選擇了 1 筆資料",
                "_": "選擇了 %d 筆資料"
            },
            "cells": {
                "1": "選擇了 1 格資料",
                "_": "選擇了 %d 格資料"
            }
        },
        "zeroRecords": "沒有符合的資料",
        "aria": {
            "sortAscending": "：升冪排列",
            "sortDescending": "：降冪排列"
        },
        "info": "顯示第 _START_ 至 _END_ 筆結果，共 _TOTAL_ 筆",
        "infoEmpty": "顯示第 0 至 0 筆結果，共 0 筆",
        "infoFiltered": "(從 _MAX_ 筆結果中過濾)",
        "infoThousands": ",",
        "lengthMenu": "顯示 _MENU_ 筆結果",
        "search": "搜尋：",
        "searchPlaceholder": "請輸入關鍵字",
        "thousands": ","
    }
});

// Add event listener for opening and closing details
tablereviewlist.on('click', 'td.dt-control', function (e) {
    let tr = e.target.closest('tr');
    let row = tablereviewlist.row(tr);

    if (row.child.isShown()) {
        // This row is already open - close it
        row.child.hide();
    }
    else {
        // Open this row
        row.child(format_unreview(row.data())).show();
    }
});

document.querySelectorAll('a.toggle-vis').forEach((el) => {
    el.addEventListener('click', function (e) {
        e.preventDefault();

        let columnIdx = e.target.getAttribute('data-column');
        let column = tablereviewlist.column(columnIdx);

        // Toggle the visibility
        column.visible(!column.visible());
    });
});


tablereviewlist.on('click', 'td.operate-button', function (e) {
    let tr = e.target.closest('tr');
    let row = tablereviewlist.row(tr);
    selectedFormCaseId = row.data().formCaseId;

});

function verifyOpenModel(id){
    console.log(id);
    if(id=="sign-button")
        setTimeout(openModal_sign, 700);

    else if(id=="rollback-button")
        setTimeout(openModal_rollback, 700);
}

function signForm(){
    console.log("送簽表單 ", selectedFormCaseId);
    $.ajax({
        url: "http://localhost:8888/api/signForm?formCaseId=" + selectedFormCaseId,
        type: 'GET',
    });
    alert("簽核成功!");
    window.location.reload();
}

function rollbackForm(){
    console.log("退簽");
    console.log(selectedFormCaseId);
    $.ajax({
        url: "http://localhost:8888/api/rollbackForm?formCaseId=" + selectedFormCaseId,
        type: 'GET',
    });
    alert("退簽成功!");
    window.location.reload();
}

function openModal_sign() {
    //var modal = document.getElementById('sign-button');
    // var formIdElement = modal.querySelector("#formId");
    // formIdElement.innerText = formId;
    Formio.createForm(
        document.getElementById("formio_sign"),
        {
            display: "form",
            components: [
                {
                    label: "請在此處簽名",
                    tableView: false,
                    key: "signature",
                    type: "signature",
                    input: true,
                },

                {
                    label: "儲存簽名",
                    action: "custom",
                    key: "submit",
                    type: "button",
                    custom: (form) => {
                        // 把 form.data 所產生的 {signature: '...', submit: true} ，儲存到 formData
                        let formData = form.data;
                        // 提取 signature 屬性的值，使form.data的值不再產生 submit: true，而只剩下 {signature: ''}
                        let signatureData = {
                            signature:
                            formData.signature,
                        };
                        // signatureData =1;
                        //把 {signature: ''} 字串化為 {"signature": ""}
                        let jsonData =
                            JSON.stringify(
                                signatureData
                            );

                        let signatureValue = formData.signature;

                        console.log(jsonData);
                        console.log(signatureData);

                        var csrf_token = $("meta[name='_csrf']").attr("content");
                        var csrf_header = $("meta[name='_csrf_header']").attr("content");

                        $.ajax({
                            type: "POST",
                            url: "http://localhost:8888/api/user/signature?formCaseId=" + encodeURIComponent(selectedFormCaseId),
                            headers: {
                                "X-CSRF-Token": csrf_token,
                            },
                            dataType: "json",
                            contentType: 'application/json',
                            data: JSON.stringify(signatureValue),
                            success: function (response) {
                                console.log("后端返回的数据:", response);
                            },
                            error: function (jqXHR, textStatus, errorThrown) {
                                console.error("發生錯誤:", jqXHR.responseText);
                            },
                        });
                           alert("儲存簽名")
                    },
                },

                // //// 顯示簽名圖檔 ////
                // {
                //     label: "顯示簽名圖檔",
                //     action: "custom",
                //     key: "submit",
                //     type: "button",
                //     custom: (form) => {
                //         fetch("/api/signatureJson", {
                //             method: "GET",
                //             headers: {
                //                 Accept: "application/json",
                //             },
                //         })
                //             .then((response) =>
                //                 response.json()
                //             )
                //             .then((data) => {
                //                 //   console.log(data);      // print allstate value
                //                 console.log(
                //                     data["signature"]
                //                 );
                //                 // 確保 data 中有 signature 屬性
                //                 if (data.signature) {
                //                     let imgData =
                //                         data.signature;
                //
                //                     let img =
                //                         document.createElement(
                //                             "img"
                //                         );
                //                     img.src = imgData;
                //
                //                     let divElement =
                //                         document.getElementById(
                //                             "png"
                //                         );
                //                     divElement.appendChild(
                //                         img
                //                     );
                //                     // console.log(data);
                //                 }
                //             })
                //             .catch((err) =>
                //                 console.error(
                //                     "Error:",
                //                     err
                //                 )
                //             );
                //     },
                // },
            ],
        }
    ).then(function (form) {
        form.on("submit", function (submission) {
            console.log(submission);
        });
    });
    // open modal
    // var modal = new bootstrap.Modal(modal);
    // modal.show();
}

function openModal_rollback(formId, formTemplateId) {

    // var modal = document.getElementById('modal-rollback');

    Formio.createForm(document.getElementById('formio_rollback'), {
        components: [
            {
                type: 'textfield',
                label: '標題',
                placeholder: '請輸入標題...',
                validate: {
                    required: true
                },
                key: 'title',
                input: true,
                inputType: 'text'
            },
            {
                type: 'textarea',
                label: '內文',
                wysiwyg: true,
                validate: {
                    required: true
                },
                key: 'content',
                input: true,
                inputType: 'text'
            }
        ]
    }).then(function (form) {
    var csrf_token = $("meta[name='_csrf']").attr("content");
    var csrf_header = $("meta[name='_csrf_header']").attr("content");
              document
                  .getElementById("messageGet")
                  .addEventListener("click", function () {
                      let jsonData = JSON.stringify(form.data);
                      console.log("留言=", form.data);
                      $.ajax({
                          headers: {
                             "X-CSRF-Token": csrf_token,
                          },
                          type: "POST",
                          url: "http://localhost:8888/api/user/postMessage?formCaseId="+encodeURIComponent(selectedFormCaseId),
                          contentType: 'application/json',
                          dataType: "text",
                          data: jsonData,
                          success: function (response) {
                              // 響應成功
                              console.log(response);
                          },
                          error: function (error) {
                              // 錯誤
                              console.error("錯誤:", error);
                          }
                      });
                  });
    });
    // 打开模态框
    // var modal = new bootstrap.Modal(modal);
    // modal.show();
}

// tablereviewlist.on('click', 'tbody tr', function (e) {
//     e.currentTarget.classList.toggle('selected');
// });

