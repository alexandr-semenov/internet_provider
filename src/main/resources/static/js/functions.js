function clearOptions(select) {
    select.find('option').remove();
}

function addOptionsToSelect(select, response) {
    $.each(response, function (index, value) {
        select.append('<option value="'+ value.id +'">' + value.name + '</option>');
    });
}

function showAlert(alert, body, message) {
    body.empty();
    body.append(message);

    alert.fadeIn();
    setTimeout(function(){
        alert.fadeOut();
    }, 4000);
}