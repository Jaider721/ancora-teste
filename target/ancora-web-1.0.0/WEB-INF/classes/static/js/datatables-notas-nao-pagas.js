// Call the dataTables jQuery plugin
$(document).ready(function() {
  $('#dataTable').DataTable({
  	  "lengthMenu": [[50, 10, 25, 10], [50, 10, 25, 20]]
  });
});
