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
        const getPlayersUrl = window.location.origin + "/game/" + gameId + "/getNamesOfPlayers";

        $.get(getPlayersUrl, function (responseData) {

            var playerList = $('#players-container');
            playerList.empty();
            for (let player of responseData) {
                let playerListItem = $('<li class="list-inline-item label label-success player-name"></li>').text(player.name);
                playerList.append(playerListItem);
            };
        });
    }
});