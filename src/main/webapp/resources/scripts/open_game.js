$(document).ready(function () {
    var gameId = $('#game-id').text();

    setInterval(function () {
        isGameStarted();
        getGamePlayers();
    }, 1000);

    function isGameStarted() {
        const isStartedUrl = window.location.origin + "/game/" + gameId + "/isStarted";
        const joinStartedUrl = "/game/" + gameId + "/joinStarted";

        $.get(isStartedUrl, function (responseData) {

            if (responseData.isStarted === true) {
                window.location.href = joinStartedUrl;
            }
        });
    }

    function getGamePlayers() {
        const getPlayersUrl = window.location.origin + "/game/" + gameId + "/getPlayers";

        $.get(getPlayersUrl, function (responseData) {

            var playersDiv = $('#players-container');
            playersDiv.empty();
            for (let player of responseData) {
                let playerDiv = $('<div></div>').text(player.name);
                playersDiv.append(playerDiv);
            };
        });
    }
});