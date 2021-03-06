/**
 * Skrypty wywolujace metodę sprawdzajaca, czy gra się skonczyla,
 * w celu uzyskania wynikow; odpowiedzialna za przekierowanie na stronę z wynikami,
 * kiedy sa one dostępne.
 * @author Karolina Prusaczyk
 */


$(document).ready(function () {
    var gameId = $('#game-id').text();

    setInterval(function () {
        isGameClosed();
    }, 2000);

    function isGameClosed() {
        const isClosedUrl = window.location.origin + "/game/" + gameId + "/isClosed";
        const checkScoresUrl = "/game/" + gameId + "/checkScores";

        $.get(isClosedUrl, function (responseData) {
            console.log("Checking if the game is closed");
            if (responseData.isClosed === true) {
                window.location.href = checkScoresUrl;
            }
        });
    }
});