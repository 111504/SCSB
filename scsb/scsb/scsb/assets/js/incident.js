// Formatting function for row details - modify as you need
function format(d) {
    // `d` is the original data object for the row
    return (
        '<dl>' +
        '<dt>Full name:</dt>' +
        '<dd>' +
        d.name +
        '</dd>' +
        '<dt>Extension number:</dt>' +
        '<dd>' +
        d.formId +
        `<dt>Link:</dt><dd><a href="https://run.mocky.io/v3/${d.form_id}">https://run.mocky.io/v3/${d.form_id}</a></dd>` +  // 添加这一行
        `<dt>Link:</dt><dd><button onclick="window.open('https://run.mocky.io/v3/${d.form_id}' )" class="btn btn-primary" ;>查看詳情（另開分頁）</button></dd>` +  // 修改为按钮
        `<dt>Link:</dt><dd><button  class="btn btn-primary" data-bs-target="#modal-1" data-bs-toggle="modal" onclick="openModal('${d.form_id}')">查看詳情（Modal）</button></dd>` + 
        '</dl>'
    );
}
 
let table = new DataTable('#Individual_Project_Record', {
    ajax: 'https://run.mocky.io/v3/4e5d8c65-55b4-4e22-85e2-50e2ed293181',
    columns: [
        {
            className: 'dt-control',
            orderable: false,
            data: null,
            defaultContent: ''
        },
        { data: 'status' },
        { data: 'projectTitle' },
        { data: 'position' },
      { data: 'currentApprover' },
      {
            data: 'issueDate'
        },
       {
            data: 'stayDuration',
            render: function (data, type) {
                var number = DataTable.render
                    .number(',', '.', 1)
                    .display(data);
 
                if (type === 'display') {
                    let color = 'green';
                    if (number > 8.0) {
                        color = 'red';
                    }
                    else if (number >= 4.0 && number <= 8.0) {
        color = 'orange';
    }
 
                    return `<span style="color:${color}">${number}</span>`;
                }
 
                return number;
            }
        },
        
      {
            data: null,
            defaultContent: '<div class="btn-group" role="group"><button class="btn btn-primary" type="button" style="background: rgb(4,205,48);" data-bs-target="#modal-1" data-bs-toggle="modal"><i class="fas fa-check"></i></button><button class="btn btn-primary" type="button" style="background: rgb(169,14,5);" data-bs-target="#modal-1" data-bs-toggle="modal"><i class="far fa-times-circle"></i></button><button class="btn btn-primary" type="button" data-bs-target="#modal-1" data-bs-toggle="modal"><i class="fas fa-search"></i></button></div>'
        }
      
    ],
    order: [[1, 'asc']],
   responsive: true,
    paging: true,
    scrollX: true,
  columnDefs: [
       { orderable: false, targets: -1 }
     ]
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
        row.child(format(row.data())).show();
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
table.on('click', 'tbody tr', function (e) {
    e.currentTarget.classList.toggle('selected');
});