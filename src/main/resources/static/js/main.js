$( document ).ready(function () {
    $('.slider').slick({
        infinite: true,
        slidesToShow: 3,
        slidesToScroll: 3
    });

    $("#send").on("click", function () {
        let form = $("#subscription-form");
        let url = form.attr("action");
        let array = form.serializeArray();

        let data = {};
        $.map(array, function(n, i){
            data[n['name']] = n['value'];
        });


        let selectedOptions = $("#tariffAllSelect").val();
        data["tariffs"] = [];
        $(selectedOptions).each( function (index, value) {
            data.tariffs[index] = {"id": value};
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

    $("#editTariffProductSelect").on("change", function () {
        let element = $(this).find('option:selected');
        let url = "/product/" + element.val();

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

    $("#addNewTariffOption").on("click", function () {
        let createTariffForm = $("#createTariffForm");
        let option_placeholder = $("#optionLabel").text();
        createTariffForm.append(
            '<div class="mb-3">' +
            '<input class="tariffOption form-control border-info text-info tariff-option" placeholder="'+ option_placeholder +'" type="text" value="">' +
            '</div>'
        );
    });

    $("#addNewTariffOptionTable").on("click", function () {
        let editTariffOptionForm = $("#editTariffOptionTable");
        let option_placeholder = $("#optionLabel").text();
        let create_button = $("#createLabel").text();
        editTariffOptionForm.append(
            '<tr>\n' +
            '<td colspan="2"><input class="tariffOption form-control border-info text-info tariff-option" placeholder="'+ option_placeholder +'" type="text" th:value=""></td>\n' +
            '<td><button class="createTariffOption btn border-success text-success" type="button">'+ create_button +'</button></td>\n' +
            '</tr>'
        );
    });

    $("#tariffCreate").on("click", function () {
        let form = $("#createTariffForm");
        let url = form.attr("action");
        let array = form.serializeArray();

        let data = {};
        $.map(array, function(n, i){
            data[n['name']] = n['value'];
        });

        data["tariffOption"] = [];
        $(".tariff-option").each( function (index, value) {
            data.tariffOption[index] = {"option": $(value).val()};
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
            },
            error: function (response) {
                let responseBody = JSON.parse(response.responseText);
                showErrorAlert(responseBody.errors);
            }
        });
    });

    $("#tariffUpdate").on("click", function () {
        let form = $("#createTariffForm");
        let url = form.attr("action");
        let array = form.serializeArray();
        let id = $("#tariffId").val();
        url = url + id;

        let data = {};
        $.map(array, function(n, i){
            data[n['name']] = n['value'];
        });

        $.ajax({
            type: "PUT",
            url: url,
            traditional: true,
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

    $(document).on("click", ".createTariffOption", function () {
        let tr = $(this).parent().parent();
        let input = tr.find("input");
        let id = $("#tariffId").val();
        let url = "/admin/tariff/option/" + id;

        $.ajax({
            url: url,
            method: "POST",
            dataType: "json",
            contentType: 'application/json',
            data: JSON.stringify({"option": input.val()}),
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

    $(".editTariffOption").on("click", function () {
        let tr = $(this).parent().parent();
        let input = tr.find("input");
        let id = $(this).attr("attr-id");
        let url = "/admin/tariff/option/" + id;

        $.ajax({
            url: url,
            method: "PUT",
            dataType: "json",
            contentType: 'application/json',
            data: JSON.stringify({"option": input.val()}),
            success: function (response) {
                showSuccessAlert(response.message);
            },
            error: function (response) {
                let responseBody = JSON.parse(response.responseText);
                showErrorAlert(responseBody.errors);
            }
        });
    });

    $(".deleteTariffOption").on("click", function () {
        let id = $(this).attr("attr-id");
        let url = "/admin/tariff/option/" + id;

        $.ajax({
            url: url,
            method: "DELETE",
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

});
