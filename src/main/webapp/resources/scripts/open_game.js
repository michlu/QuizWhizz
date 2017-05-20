$(document).ready(function () {
    var gameId = $('#game-id').text();

    setInterval(function () { isGameStarted(); }, 1000);

    function isGameStarted() {
        $.get(window.location.origin + "/game/" + gameId + "/isGameStarted", function (data) {
            if (data.isStarted === true) {
                window.location.href = "/game/" + gameId + "/joinStarted";
            }
        });
    }
});