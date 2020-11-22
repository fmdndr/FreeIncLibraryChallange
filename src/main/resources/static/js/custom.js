$(document).ready(function () {
  $("#bookSearchBox").on("keyup", function () {
    var value = $(this).val().toLowerCase();
    console.log(value);
    $("#filterCard").filter(function () {
      $(this).toggle($(this).text().toLowerCase().indexOf(value) > -1);
    });
  });
  $("#menu-toggle").click(function (e) {
    e.preventDefault();
    $("#wrapper").toggleClass("toggled");
  });
});
