(function () {
    var nameInput = document.querySelector('.name'),
        btnChange = document.querySelector('button.change'),
        btnDelete = document.querySelector('button.delete'),
        errors = document.querySelector('#errors'),
        errorType = document.querySelector('#type-error'),
        assingInput = document.querySelector('#assignActivityInput'),
        deletePlanInput = document.querySelector('#unassignActivityInput'),
        assingPlanButton = document.querySelector('#assignActivity'),
        deletePlanButton = document.querySelector('#unassignActivity'),
        dataId = document.querySelector('[data-id]').getAttribute('data-id'),
        nameValue = JSON.parse(JSON.stringify(nameInput.value));

    function checkChanges() {
        if (nameInput.value == nameValue) {
            return false;
        } else {
            return true;
        }
    }

    function checkPlanInputs() {
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
        if (checkPlanInputs()) {
            var trimedValue = (JSON.parse(JSON.stringify(nameInput.value))).trim();
            nameInput.value = trimedValue;
            $.ajax({
                type: 'PUT',
                url: '/plan?' + $.param({
                    "id": dataId,
                    "name": trimedValue
                })
            }).done(function () {
                window.location.href = "/plan?id=" + dataId;
            }).fail(function (msg) {
                $('#errors').removeClass("d-none");
                $('#type-error').text("Nie można zmienic danych planu o id: " + dataId);
            })
        }
    }

    btnChange.addEventListener('click', function () {
        handleChangeButton();
    }, false);

    assingPlanButton.addEventListener('click', function () {
        handleAddButton();
    }, false);

    $('#delete-plan').click(function () {
        $.ajax({
            type: 'DELETE',
            url: '/plan?' + $.param({
                "id": dataId,
            })
        }).done(function () {
            window.location.href = "/plans";
        }).fail(function (msg) {
            $('#errors').removeClass("d-none");
            $('#type-error').text("Nie można usunąć planu o id: " + dataId);
        })
    })

    function handleAddButton() {
        $('#assign-errors').addClass('d-none');
        if (checkAssignInput()) {
            $.ajax({
                type: 'PUT',
                url: '/plan-assign?' + $.param({
                    "id": dataId,
                    "activityid": assingInput.value
                })
            }).done(function () {
                window.location.href = "/plan?id=" + dataId;
            }).fail(function (msg) {
                $('#assign-errors').removeClass('d-none');
                $('#assign-error').text("Nie można dodać zajęć o id: " + assingInput.value);
            })

        }
    }

    function checkAssignInput() {
        if (assingInput.value == "") {
            $('#assign-errors').removeClass('d-none');
            $('#assign-error').text("Nie podano id zajęć do zapisania");
            return false;
        } else if (+assingInput.value <= 0) {
            $('#assign-errors').removeClass('d-none');
            $('#assign-error').text("Przekazana wartość id użytkownika musi być większa od zera");
            return false;
        }
        return true;
    }

    deletePlanButton.addEventListener('click', function () {
        handleDeleteButton();
    }, false);

    function handleDeleteButton() {
        $('#delete-errors').addClass('d-none');
        if (checkDeleteInput()) {
            $.ajax({
                type: 'PUT',
                url: '/plan-unassign?' + $.param({
                    "id": dataId,
                    "activityid": deletePlanInput.value
                })
            }).done(function () {
                window.location.href = "/plan?id=" + dataId;
            }).fail(function (msg) {
                $('#delete-errors').removeClass('d-none');
                $('#delete-error').text("Nie można wypisać zajęć o id: " + deletePlanInput.value);
            })
        }
    }

    function checkDeleteInput() {
        if (deletePlanInput.value == "") {
            $('#delete-errors').removeClass('d-none');
            $('#delete-error').text("Nie podano id zajęć do wypisania");
            return false;
        } else if (+deletePlanInput.value <= 0) {
            $('#delete-errors').removeClass('d-none');
            $('#delete-error').text("Przekazana wartość id zajęć musi być większa od zera");
            return false;
        }
        return true;
    }

})();