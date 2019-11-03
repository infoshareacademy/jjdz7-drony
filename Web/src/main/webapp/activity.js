//(function () {
var maxUsersInput = document.querySelector('.maxUsers'),
    nameInput = document.querySelector('.name'),
    maxUsersLabel = document.querySelector('.maxUsersLabel'),
    durationLabel = document.querySelector('.durationLabel'),
    durationInput = document.querySelector('.duration'),
    btnChange = document.querySelector('button.change'),
    btnDelete = document.querySelector('button.delete'),
    errors = document.querySelector('#errors'),
    errorType = document.querySelector('#type-error'),
    assingInput = document.querySelector('#assignUserInput'),
    deleteUserInput = document.querySelector('#deleteUserInput'),
    assingUserButton = document.querySelector('#assignUser'),
    deleteUserButton = document.querySelector('#deleteUser'),
    dataId = document.querySelector('[data-id]').getAttribute('data-id'),
    activityType = document.querySelector('#activityType'),
    basicActitiyTypeValue = $('#activityType').attr('data-activitySelect'),
    maxUsersValue = JSON.parse(JSON.stringify(maxUsersInput.value)),
    durationValue = JSON.parse(JSON.stringify(durationInput.value)),
    nameValue = JSON.parse(JSON.stringify(nameInput.value));

$('#activityType').val($('#activityType').attr('data-activitySelect'));

function setInput(label, text, value, text2) {
    label.innerText = text + value + text2;
}

setInput(maxUsersLabel, "Max l. użytkowników: ", maxUsersInput.value, "");
setInput(durationLabel, "Czas trwania: ", durationInput.value * 15, " min");

maxUsersInput.addEventListener('input', function () {
    setInput(maxUsersLabel, "Max l. użytkowników: ", this.value, "")
}, false);

durationInput.addEventListener('input', function () {
    setInput(durationLabel, "Czas trwania: ", this.value * 15, " min")
}, false);

function checkChanges() {
    if (nameInput.value == nameValue
        && maxUsersInput.value == maxUsersValue
        && durationInput.value == durationValue
        && activityType.value == basicActitiyTypeValue) {
        return false;
    } else {
        return true;
    }
}

function checkActivityInputs() {
    if (nameInput.value.trim() == "") {
        $('#errors').removeClass("d-none");
        $('#type-error').text("Pole nazwa jest puste");
        return false;
    } else if (!checkChanges()) {
        $('#errors').removeClass("d-none");
        $('#type-error').text("Nic nie zmieniono");
        return false
    }
    return true;
}

function handleChangeButton() {
    $('#errors').addClass("d-none");
    if (checkActivityInputs()) {
        var trimedValue = (JSON.parse(JSON.stringify(nameInput.value))).trim();
        nameInput.value = trimedValue;
        $.ajax({
            type: 'PUT',
            url: '/activity?' + $.param({
                "id": dataId,
                "name": trimedValue,
                "maxusers": maxUsersInput.value,
                "duration": durationInput.value,
                "activitytype" : activityType.value
            })
        }).done(function () {
            window.location.href = "/activity?id=" + dataId;
        }).fail(function (msg) {
            $('#errors').removeClass("d-none");
            $('#type-error').text("Nie można zmienic danych użytkownika o id: " + dataId);
        })
    }
}

btnChange.addEventListener('click', function () {
    handleChangeButton();
}, false);

assingUserButton.addEventListener('click', function () {
    handleAddButton();
    console.log('click');
}, false);

$('#delete-activty').click(function () {
    $.ajax({
        type: 'DELETE',
        url: '/activity?' + $.param({
            "id": dataId,
        })
    }).done(function () {
        window.location.href = "/activities";
    }).fail(function (msg) {
        $('#errors').removeClass("d-none");
        $('#type-error').text("Nie można usunąć użytkownika o id: " + dataId);
    })
})

function handleAddButton() {
    $('#assign-errors').addClass('d-none');
    if (checkAssignInput()) {
        $.ajax({
            type: 'PUT',
            url: '/activity-assign?' + $.param({
                "id": dataId,
                "userid": assingInput.value
            })
        }).done(function () {
            window.location.href = "/activity?id=" + dataId;
        }).fail(function (msg) {
            $('#assign-errors').removeClass('d-none');
            $('#assign-error').text("Nie można dodać użytkownika o id: " + assingInput.value);
        })

    }
}

function checkAssignInput() {
    if (assingInput.value == "") {
        $('#assign-errors').removeClass('d-none');
        $('#assign-error').text("Nie podano id użytkownika do zapisania");
        return false;
    } else if (+assingInput.value <= 0) {
        $('#assign-errors').removeClass('d-none');
        $('#assign-error').text("Przekazana wartość id użytkownika musi być większa od zera");
        return false;
    }
    return true;
}

deleteUserButton.addEventListener('click', function () {
    handleDeleteButton();
}, false);

function handleDeleteButton() {
    $('#delete-errors').addClass('d-none');
    if (checkDeleteInput()) {
        $.ajax({
            type: 'PUT',
            url: '/activity-unassign?' + $.param({
                "id": dataId,
                "userid": deleteUserInput.value
            })
        }).done(function () {
            window.location.href = "/activity?id=" + dataId;
        }).fail(function (msg) {
            $('#delete-errors').removeClass('d-none');
            $('#delete-error').text("Nie można wypisać użytkownika o id: " + deleteUserInput.value);
        })
    }
}

function checkDeleteInput() {
    if (deleteUserInput.value == "") {
        $('#delete-errors').removeClass('d-none');
        $('#delete-error').text("Nie podano id użytkownika do usunięcia");
        return false;
    } else if (+deleteUserInput.value <= 0) {
        $('#delete-errors').removeClass('d-none');
        $('#delete-error').text("Przekazana wartość id użytkownika musi być większa od zera");
        return false;
    }
    return true;
}

//})();
