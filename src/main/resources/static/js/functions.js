function clearOptions(select) {
    select.find('option').remove();
}

function addOptionsToSelect(select, response) {
    $.each(response, function (index, value) {
        select.append('<option value="'+ value.id +'">' + value.name + '</option>');
    });
}

function showSuccessAlert(message) {
    let alert = $("#success-alert");
    let body = alert.find(".alert-body");

    body.empty();
    body.append("<p>" + message + "</p>");

    alert.fadeIn();

    setTimeout(function(){
        alert.fadeOut();
    }, 4000);
}

function showErrorAlert(messages) {
    let alert = $("#error-alert");
    let body = alert.find(".alert-body");

    body.empty();
    for (let message of messages) {
        body.append("<p>" + message + "</p>");
    }

    alert.fadeIn();

    setTimeout(function(){
        alert.fadeOut();
    }, 4000);
}