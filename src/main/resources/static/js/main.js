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
                showSuccessAlert(response.message);
            },
            error: function (response) {
                let responseBody = JSON.parse(response.responseText);
                showErrorAlert(responseBody.errors);
            }
        });
    });

    $(".activate").on("click", function () {
        let url = "/admin/activate-user/" + $(this).attr("attr-user");

        $.ajax({
            url: url,
            method: "GET",
            success: function (response) {
                showSuccessAlert(response.message);

                setTimeout(function(){
                    location.reload();
                }, 1000);
            },
            error: function (response) {
                let responseBody = JSON.parse(response.responseText);
                showErrorAlert(responseBody.errors);
            }
        });
    });

    $("#deposit").on("click", function () {
        let form = $("#deposit-form");
        let url = form.attr("action");
        let array = form.serializeArray();

        let data = {};
        $.map(array, function(n, i){
            data[n['name']] = n['value'];
        });

        $.ajax({
            type: "POST",
            url: url,
            traditional: true,
            dataType: "json",
            contentType: 'application/json',
            data: JSON.stringify(data),
            success: function (response) {
                showSuccessAlert(response.message);
                setTimeout(function(){ history.back(); }, 2000);
            },
            error: function (response) {
                let responseBody = JSON.parse(response.responseText);
                showErrorAlert(responseBody.errors);
            }
        });
    })

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

});
