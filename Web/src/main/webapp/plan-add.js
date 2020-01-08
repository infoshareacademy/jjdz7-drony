// (function () {
var nameInput = document.querySelector('.name'),
    btnSave = document.querySelector('button.save'),
    errors = document.querySelector('#errors'),
    errorType = document.querySelector('#type-error'),
    activityType = document.querySelector('#activityType'),
    assignedActivities = document.querySelector('#assignedActivities'),
    availableActivities = document.querySelector('#availableActivities'),
    assignActivityInput = document.querySelector('#assignActivityInput'),
    assignActivityButton = document.querySelector('#assignActivity'),
    deleteActivityInput = document.querySelector('#deleteActivityInput'),
    deleteActivityButton = document.querySelector('#deleteActivity'),
    availableActivitiesMap = new Map(),
    assignedActivitiesMap = new Map();

if (availableActivities !== null) {
    for (var i = 0; i < availableActivities.children.length; i++) {
        availableActivitiesMap.set(
            +availableActivities.children[i].children[0].getAttribute('data-activityid'),
            availableActivities.children[i]
        )
    }
}

function checkActivityInputs() {
    if (nameInput.value.trim() == "") {
        $('#errors').removeClass("d-none");
        $('#type-error').text("Pole nazwa jest puste");
        return false;
    }
    return true;
}

function checkAssignInput() {
    if (assignActivityInput.value == "") {
        $('#assign-errors').removeClass('d-none');
        $('#assign-error').text("Nie podano id użytkownika do zapisania");
        return false;
    } else if (+assignActivityInput.value <= 0) {
        $('#assign-errors').removeClass('d-none');
        $('#assign-error').text("Przekazana wartość id użytkownika musi być większa od zera");
        return false;
    }
    return true;
}

function checkDeleteInput() {
    if (deleteActivityInput.value == "") {
        $('#delete-errors').removeClass('d-none');
        $('#delete-error').text("Nie podano id użytkownika do usunięcia");
        return false;
    } else if (+deleteActivityInput.value <= 0) {
        $('#delete-errors').removeClass('d-none');
        $('#delete-error').text("Przekazana wartość id użytkownika musi być większa od zera");
        return false;
    }
    return true;
}

assignActivityButton.addEventListener('click', function (evt) {
    if (checkAssignInput()) {
        var id = +assignActivityInput.value;
        if (availableActivitiesMap.get(id) !== undefined) {
            assignedActivitiesMap.set(id, availableActivitiesMap.get(id));
            availableActivitiesMap.delete(id);
            $('#assign-errors').addClass('d-none');
            reloadActivitiesLists();
        } else {
            $('#assign-errors').removeClass('d-none');
            $('#assign-error').text("Nie można przypisać zajęć o id: " + id);
        }
        assignActivityInput.value = "";
    }
}, false);

deleteActivityButton.addEventListener('click', function (evt) {
    if (checkDeleteInput()) {
        var id = +deleteActivityInput.value;
        if (assignedActivitiesMap.get(id) !== undefined) {
            availableActivitiesMap.set(id, assignedActivitiesMap.get(id));
            assignedActivitiesMap.delete(id);
            deleteActivityInput.value = "";
            $('#delete-errors').addClass('d-none');
            reloadActivitiesLists();
        }
    } else {
        $('#delete-errors').removeClass('d-none');
        $('#delete-error').text("Nie można wypisać zajęć o id: " + id);
    }
}, false);

function reloadActivitiesLists() {
    availableActivities.innerHTML = "";
    assignedActivities.innerHTML = "";
    assignedActivitiesMap = new Map([...assignedActivitiesMap.entries()].sort());
    availableActivitiesMap = new Map([...availableActivitiesMap.entries()].sort());
    Array.from(assignedActivitiesMap.values()).forEach(x => assignedActivities.innerHTML += x.outerHTML + "\n");
    Array.from(availableActivitiesMap.values()).forEach(x => availableActivities.innerHTML += x.outerHTML + "\n");
}

function handleSaveButton() {
    $('#errors').addClass("d-none");
    if (checkActivityInputs()) {
        var trimedValue = (JSON.parse(JSON.stringify(nameInput.value))).trim();
        nameInput.value = trimedValue;
        $.ajax({
            type: 'POST',
            url: '/plan?' + $.param({
                "name": trimedValue,
                "assignedactivities": Array.from(assignedActivitiesMap.keys()).join(',')
            })
        }).done(function () {
            window.location.href = "/plans";
        }).fail(function (msg) {
            $('#errors').removeClass("d-none");
            $('#type-error').text("Nie można doadać tego planu");
        })
    }
}

btnSave.addEventListener('click', function () {
    handleSaveButton();
}, false);
// })();