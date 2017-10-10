$(document).ready(function () {
  $('#history').DataTable({
    "language": {
      "emptyTable": "没有记录",
      "info": "显示 _START_ to _END_ of _TOTAL_ 记录",
      "infoEmpty": "显示 0 to 0 of 0 记录",
      "lengthMenu": "每页 _MENU_ 记录",
      "search": "搜索",
      "paginate": {
        "previous": "上一页",
        "next": "下一页"
      }
    }
  });
});
