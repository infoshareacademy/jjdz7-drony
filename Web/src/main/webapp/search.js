(function () {
    var btnSearchUsers = document.querySelector('#btnSearchUser'),
        btnSearchActivities = document.querySelector('#btnSearchActivities'),
        btnSearchPlans = document.querySelector('#btnSearchPlans'),
        searchInput = document.querySelector('#searchInput'),
        results = document.querySelector('#results'),
        URL = "/search-activity",
        response;

    function setURL(txt) {
        removePressedStatus();
        results.innerHTML = "";
        searchInput.value = "";
        searchInput.focus();
        URL = txt;
    }

    function removePressedStatus() {
        btnSearchUsers.classList.remove('btn-success');
        btnSearchUsers.classList.add('btn-secondary');
        btnSearchActivities.classList.remove('btn-success');
        btnSearchActivities.classList.add('btn-secondary');
        btnSearchPlans.classList.remove('btn-success');
        btnSearchPlans.classList.add('btn-secondary');
    }

    btnSearchUsers.addEventListener('click', function () {
        setURL("/search-users");
        btnSearchUsers.classList.remove('btn-secondary');
        btnSearchUsers.classList.add('btn-success');
    }, false);

    btnSearchActivities.addEventListener('click', function () {
        setURL("/search-activities");
        btnSearchActivities.classList.remove('btn-secondary');
        btnSearchActivities.classList.add('btn-success');
    }, false);

    btnSearchPlans.addEventListener('click', function () {
        setURL("/search-plans");
        btnSearchPlans.classList.remove('btn-secondary');
        btnSearchPlans.classList.add('btn-success');
    }, false);

    btnSearchUsers.click();

    function handleInput() {
        $.ajax({
            type: 'GET',
            url: URL + "?" + $.param({
                "name": searchInput.value
            })
        }).done(function (resp) {
            response = resp;
            if (resp.length > 0) {
                results.innerHTML = prepareList(resp);
            } else {
                results.innerHTML = badRequest();
            }
        }).fail(function (msg) {
            results.innerHTML = badRequest();
        })
    }

    function prepareList(resp) {
        if (URL == "/search-users") {
            return prepareUsersList(resp);
        } else if (URL == "/search-activities") {
            return prepareActivitiesList(resp);
        } else if (URL == "/search-plans") {
            return preparePlansList(resp);
        }
    }

    function prepareUsersList(resp) {
        var str = "";
        for (var i = 0; i < resp.length; i++) {
            str += prepareUser(resp[i]);
        }
        return str;
    }

    function prepareUser(user) {
        return "<li class=\"list-group-item\">Id: " + user.id +
            ", imię: " + user.name + ", nazwisko: " + user.surname + "</li> "
    }

    function prepareActivitiesList(resp) {
        var str = "";
        for (var i = 0; i < resp.length; i++) {
            str += prepareActivity(resp[i]);
        }
        return str;
    }

    function prepareActivity(activity) {
        return "<li class=\"list-group-item\"><a href=\"/activity?id=" + activity.id + "\" class=\"stretched-link\">Id: " + activity.id +
            ", nazwa: " + activity.name + "</a></li> ";
    }

    function badRequest() {
        return "<li class=\"list-group-item\"> Nie znaleziono wyników dla podanego hasła</li> "
    }

    function preparePlan(plan) {
        return "<li class=\"list-group-item\"><a href=\"/plan?id=" + plan.id + "\" class=\"stretched-link\">Id: " + plan.id +
            ", nazwa: " + plan.name + "</a></li> ";
    }

    function preparePlansList(resp) {
        var str = "";
        for (var i = 0; i < resp.length; i++) {
            str += preparePlan(resp[i]);
        }
        return str;
    }

    searchInput.addEventListener("keyup", function (evt) {
        if (searchInput.value.length > 0) {
            handleInput();
        } else {
            results.innerHTML = "";
        }
    }, false);
})();