// (function () {
var btnSearchUsers = document.querySelector('#btnSearchUser'),
    btnSearchActivities = document.querySelector('#btnSearchActivities'),
    btnSearchPlans = document.querySelector('#btnSearchPlans'),
    searchInput = document.querySelector('#searchInput'),
    results = document.querySelector('#results'),
    URL = "/search-activity";


function setURL(txt) {
    removePressedStatus();
    URL = txt;
}

function removePressedStatus() {
    btnSearchUsers.classList.remove('btn-success');
    btnSearchUsers.classList.add('btn-secondary');
    btnSearchActivities.classList.remove('btn-success');
    btnSearchActivities.classList.add('btn-secondary');
    btnSearchPlans.classList.remove('btn-success');
    btnSearchPlans.classList.add('btn-secondary');
    var response;
}

btnSearchUsers.addEventListener('click', function () {
    setURL("/search-users");
    btnSearchUsers.classList.remove('btn-secondary');
    btnSearchUsers.classList.add('btn-success');
    console.log(URL);
}, false);

btnSearchActivities.addEventListener('click', function () {
    setURL("/search-activities");
    btnSearchActivities.classList.remove('btn-secondary');
    btnSearchActivities.classList.add('btn-success');
    console.log(URL);
}, false);

btnSearchPlans.addEventListener('click', function () {
    setURL("/search-plans");
    btnSearchPlans.classList.remove('btn-secondary');
    btnSearchPlans.classList.add('btn-success');
    console.log(URL);
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
    }).fail(function (msg) {
        console.log("fail")
    })
}
searchInput.addEventListener("keyup", handleInput, false);
// })();
