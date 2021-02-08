$( document ).ready(function () {
    let selected = $("#editTariffProductSelect").find("option:selected");

    let url = "/product/" + selected.val();

    $.ajax({
        url: url,
        method: "GET",
        success: function (response) {
            let tbody = $("#editTariffTables tbody");
            clearTbody(tbody);
            addTbody(tbody, response);
        }
    });
});