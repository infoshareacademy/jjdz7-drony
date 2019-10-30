(function () {
    var maxUsers = document.querySelector('.maxUsers'),
        maxUsersLabel = document.querySelector('.maxUsersLabel'),
        durationLabel = document.querySelector('.durationLabel'),
        duration = document.querySelector('.duration');

    function setInput(label, text, value, text2) {
        label.innerText = text + value + text2;
    }

    setInput(maxUsersLabel, "Max l. użytkowników: ", maxUsers.value, "");
    setInput(durationLabel, "Czas trwania: ", duration.value * 15, " min");

    maxUsers.addEventListener('input', function () {
        setInput(maxUsersLabel, "Max l. użytkowników: ", this.value, "")
    }, false);

    duration.addEventListener('input', function () {
        setInput(durationLabel, "Czas trwania: ", this.value * 15, " min")
    }, false);
})();