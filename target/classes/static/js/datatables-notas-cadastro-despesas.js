// Call the dataTables jQuery plugin
$(document).ready(function() {
  $('#dataTable').DataTable({
	  "order": [[ 5, "desc" ]],
  	  "lengthMenu": [[10, 10, 25, 100], [10, 25, 50, 100]]
  });
});
