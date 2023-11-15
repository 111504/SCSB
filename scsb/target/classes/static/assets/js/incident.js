// Formatting function for row details - modify as you need

function format_incident(d) {
    // `d` is the original data object for the row
     return (
        '<dl>' +
        '<dt>發案人:</dt>' +
        '<dd>' +
        d.name +
        '</dd>' +
        '<dt>發案案由:</dt>' +
        '<dd>' +
        d.proposalTopic +
         '</dd>' +
        '<dt>目前簽核人:</dt>' +
        '<dd>' +
         d.currentApprover +
          '</dd>' +
        '<dt>送交簽核人時間:</dt>' +
        '<dd>' +
         moment(d.arrivalDate).format('YYYY-MM-DD a hh:mm') +
         '<dt>留言</dt>' +
         '<dd>' +
         d.message +
         '</dd>' +
        `<dt></dt><dd><button  class="btn btn-primary" data-bs-target="#modal-incident" data-bs-toggle="modal-incident" onclick="openModal1('${d.formCaseId}', '${d.formTemplateId}')">查看詳情</button></dd>` +
        '</dl>'
    );
}


function openModal1(formId, formTemplateId) {
    var modal = document.getElementById('modal-incident'); // 获取模态框元素
    // var formIdElement = modal.querySelector('#formId_in'); // 获取包含 formId 的元素
    // var formTemplateIdElement = modal.querySelector('#formTemplateId_in'); // 获取包含 formTemplateId 的元素

    // 设置 formId 和 formTemplateId 的文本内容
    // formIdElement.innerText = formId;
    // formTemplateIdElement.innerText = formTemplateId;
    console.log("獲取表單的data內容")
    let datajson;
    var csrf_token = $("meta[name='_csrf']").attr("content");
    var csrf_header = $("meta[name='_csrf_header']").attr("content");
    $.ajax({
           headers: {
                    "X-CSRF-Token": csrf_token
            },
            url: "http://localhost:8888/api/user/personal-formiojson?formCaseId=" + encodeURIComponent(formId),
            type: 'GET',
            dataType: 'json',
            async: false,//同步方法
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

    let img = document.createElement("img");
    let signatureReview = document.getElementById('signature_review');
    signatureReview.innerHTML='';
    // var img = document.createElement('IMG');

    // 打开模态框
    $.ajax({
        headers: {
            "X-CSRF-Token": csrf_token
        },
        url: "http://localhost:8888/api/user/getSignatureFile?formCaseId=" + encodeURIComponent(formId),
        type: 'GET',
        dataType: "text",
        success: function (response) {
            console.log("Data sent and response received:", response);

            var responseData = JSON.parse(response);
            img.src = responseData;
            console.log(img);
            signatureReview.appendChild(img);
        },
        error: function (error) {
            console.error("Error:", error);
        }
    });



}

let currentLoginUser1 = document.getElementById('currentLoginUser').textContent;

let table = new DataTable('#Individual_Project_Record', {
    ajax: 'http://localhost:8888/api/user/personal-records?empId='+ currentLoginUser1,
    columns: [
        {
            className: 'dt-control',
            orderable: false,
            data: null,
            defaultContent: ''
        },
//         {
//            data: null,
//            defaultContent: '<div class="btn-group" role="group"><button class="btn btn-primary" type="button" style="background: rgb(4,205,48);" data-bs-target="#modal-1" data-bs-toggle="modal"><i class="fas fa-check"></i></button><button class="btn btn-primary" type="button" style="background: rgb(169,14,5);" data-bs-target="#modal-1" data-bs-toggle="modal"><i class="far fa-times-circle"></i></button>'
//        },
        {
    data: 'status',
    render: function (data, type, row) {
        var statusText = '';
        var bgColor = '';

        switch (data) {
            case 0:
                statusText = '草稿';
                bgColor = 'lightblue'; // lightblue
                break;
            case 1:
                statusText = '送簽中';
                bgColor = 'lightgreen'; // lightgreen
                break;
            case 2:
                statusText = '完成';
                bgColor = 'lightgray'; // lightgray
                break;
            case 3:
                statusText = '已退件';
                bgColor = 'lightcoral'; // lightcoral
                break;
            default:
                statusText = '未知狀態';
                bgColor = 'white'; // white
        }

        if (type === 'display') {
            return '<div style="background-color: ' + bgColor + '">' + statusText + '</div>';
        }

        return data;
    }
},
        { data: 'projectTitle' },
        { data: 'department' },
      { data: 'currentApprover' },
      {
    data: 'issueDate',
    render: function (data, type) {
        if (type === 'display') {
            var date = moment(data).format('YYYY-MM-DD a hh:mm');
            return date;
        }

        return data; // 在其它情况下保持原样
    }
},
      {
    data: 'arrivalDate',
    render: function (data, type) {
        var arrivalDate = moment(data); // 将字符串日期转换为 moment.js 对象
        var now = moment(); // 获取当前系统时间的 moment.js 对象

        // 计算时间差（单位：小时）
        var diffHours = now.diff(arrivalDate, 'hours'); // 现在时间减去 arrivalDate，因 arrivalDate 小于当前时间

        if (type === 'display') {
            if (diffHours < 0) {
                // arrivalDate 已经过去，显示错误信息
                return `<span style="color:red">Error</span>`;
            } else {
                let color = 'green';
                if (diffHours > 24 && diffHours <= 72) {
                    color = 'orange';
                }else if (diffHours > 72 ) {
                    color = 'red';
                }

                if (diffHours < 24) {
                    // 在一天内显示小时
                    return `<span style="color:${color}">${diffHours} hours</span>`;
                } else {
                    // 超过一天显示天数
                    var diffDays = Math.floor(diffHours / 24); // 计算天数
                    return `<span style="color:${color}">${diffDays} days</span>`;
                }
            }
        }

        return data; // 在其它情况下保持原样
    }
}
,
        
     
      
    ],
    order: [[6, 'dsc']],
    responsive: true,
    paging: true,
    scrollX: true,
    columnDefs: [
       { orderable: false, targets: 1 }
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
table.on('click', 'td.dt-control', function (e) {
    let tr = e.target.closest('tr');
    let row = table.row(tr);
 
    if (row.child.isShown()) {
        // This row is already open - close it
        row.child.hide();
    }
    else {
        // Open this row
        row.child(format_incident(row.data())).show();
    }
});
document.querySelectorAll('a.toggle-vis').forEach((el) => {
    el.addEventListener('click', function (e) {
        e.preventDefault();
 
        let columnIdx = e.target.getAttribute('data-column');
        let column = table.column(columnIdx);
 
        // Toggle the visibility
        column.visible(!column.visible());
    });
});
// table.on('click', 'tbody tr', function (e) {
//     e.currentTarget.classList.toggle('selected');
// });