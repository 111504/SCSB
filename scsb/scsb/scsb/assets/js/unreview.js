// Formatting function for row details - modify as you need
function format(d) {
    // `d` is the original data object for the row
    return (
        '<dl>' +
        '<dt>發案人:</dt>' +
        '<dd>' +
        d.name +
        '</dd>' +
        '<dt>表單id:</dt>' +
        '<dd>' +
        d.form_id +
        '</dd>' +
        
        `<dt>Link:</dt><dd><a href="https://run.mocky.io/v3/${d.form_id}">https://run.mocky.io/v3/${d.form_id}</a></dd>` +  // 添加这一行
        `<dt>Link:</dt><dd><button onclick="window.open('https://run.mocky.io/v3/${d.form_id}' )" class="btn btn-primary" ;>查看詳情（另開分頁）</button></dd>` +  // 修改为按钮
        `<dt>Link:</dt><dd><button  class="btn btn-primary" data-bs-target="#modal-1" data-bs-toggle="modal" onclick="openModal('${d.form_id}')">查看詳情（Modal）</button></dd>` + 
        '</dl>'
    );
}
function openModal(formId) {
    var modal = document.getElementById('modal-1'); // 获取模态框元素
    var formIdElement = modal.querySelector('#formId'); // 获取包含 formId 的元素

    // 设置 formId 的文本内容
    formIdElement.innerText = formId;

    // 打开模态框
    var modal = new bootstrap.Modal(modal);
    modal.show();
}


let tablereviewlist = new DataTable('#tablereviwelist', {
    ajax: 'https://run.mocky.io/v3/1da02673-92c9-4726-9676-7f951f17b06d',
    columns: [
        {
            className: 'dt-control',
            orderable: false,
            data: null,
            defaultContent: ''
        },
        { data: 'form_case_number' },
        { data: 'name' },
        { data: 'department_rank' },
        { data: 'proposal_topic' },
        {
            data: 'timeliness_level',
            render: function (data, type) {

                if (type === 'display') {
                    let color = 'green';

                    switch (data) {
                        case 'high level':
                            color = 'red';
                            break;
                        case 'medium level':
                            color = 'orange';
                            break;
                    }
                    return `<span style="color:${color}">${data}</span>`;
                }

                return data;
            }
        },
        {
            data: 'arrival_date'
        },
        {
            data: null,
            defaultContent: '<div class="btn-group" role="group"><button class="btn btn-primary" type="button" style="background: rgb(4,205,48);" data-bs-target="#modal-1" data-bs-toggle="modal"><i class="fas fa-check"></i></button><button class="btn btn-primary" type="button" style="background: rgb(169,14,5);" data-bs-target="#modal-1" data-bs-toggle="modal"><i class="far fa-times-circle"></i></button><button class="btn btn-primary" type="button" data-bs-target="#modal-1" data-bs-toggle="modal"><i class="fas fa-search"></i></button></div>'
        }

    ],
    order: [[0, 'desc']],
    responsive: true,
    paging: true,
    rowCallback: function (row, data, index) {
        var api = this.api();
        var rowNum = api.row(row).index() + 1;
        $('td:eq(0)', row).html(rowNum); // 流水號放第一行
    },
    columnDefs: [
        { orderable: false, targets: 7 }
    ],
    dom: 'lBfrtip',
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
    ]
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
        row.child(format(row.data())).show();
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
tablereviewlist.on('click', 'tbody tr', function (e) {
    e.currentTarget.classList.toggle('selected');
});

document.querySelector('#button_unreview').addEventListener('click', function () {
    alert(tablereviewlist.rows('.selected').data().length + ' row(s) selected');
});