$( document ).ready(function () {
    $('.slider').slick({
        infinite: true,
        slidesToShow: 3,
        slidesToScroll: 3
    });

    $("#productSelect").on("change", function () {
        let element = $(this).find('option:selected');
        let url = "/product/" + element.val();

        $.ajax({
            url: url,
            method: "GET",
            success: function (response) {
                let tariffSelect = $('#tariffSelect');
                clearOptions(tariffSelect);

                addOptionsToSelect(tariffSelect, response);
            }
        });
    });

    $("#send").on("click", function () {
        let form = $("#subscription-form");
        let url = form.attr("action");
        let array = form.serializeArray();

        let data = {};
        $.map(array, function(n, i){
            data[n['name']] = n['value'];
        });


        $.ajax({
            traditional: true,
            type: "POST",
            url: url,
            dataType: "json",
            contentType: 'application/json',
            data: JSON.stringify(data),
            success: function (response) {
                let alert = $("#success-alert");
                let alertBody = $("#success-alert .alert-body");

                showAlert(alert, alertBody, response.status);
            },
            error: function (response) {
                let jsonResponse = JSON.parse(response.responseText);
                let alert = $("#error-alert");
                let alertBody = $("#error-alert .alert-body");

                showAlert(alert, alertBody, jsonResponse.message);
            }
        });
    });
});
