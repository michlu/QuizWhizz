/**
 * Skrypty zarzadzajace wyswietlaniem kolejych pytan oraz czasu dla rozpoczetej gry,
 * odpowiedzialne za wyslanie odpowiedzi gracza w odpowiednim czasie
 * oraz przekierowanie na odpowiednia stronę.
 * @author Karolina Prusaczyk
 */

$(document).ready(function () {
        $('#question-1').show();
        runQuestionTimer();

        var gameSecondsLeft = 150;
        var gameTimeIntervalId = setInterval(function() {
            $('#game-time-left').text(--gameSecondsLeft);
            if (gameSecondsLeft <= 0)
            {
                clearInterval(gameTimeIntervalId);
            }
        }, 1000);
    }
);

var timeForAnswerInMiliseconds = 15000;
var intervalId = setInterval(function() { showQuestion(); }, timeForAnswerInMiliseconds);
var submittedAnswer = '';
var currentQuestion = 1;
var questionTimeIntervalId = 0;

function submitSingleAnswer(answerId) {
    submittedAnswer += answerId + ',';

    if(currentQuestion == 10) {
        sendAnswers();
    }
    showQuestion();
    clearInterval(intervalId);
    intervalId = setInterval(function() { showQuestion(); }, timeForAnswerInMiliseconds);
}

function sendAnswers() {
    var gameId = $('#game-id').text();
    window.location.href = "/game/" + gameId + "/submitAnswers/" + submittedAnswer.replace(/,\s*$/, "");
}

function showQuestion() {
    currentQuestion++;
    $('#question-' + (currentQuestion - 1)).hide();
    $('#question-' + currentQuestion).show();

    clearInterval(questionTimeIntervalId);
    destroyQuestionTimer();
    runQuestionTimer();

    if(currentQuestion == 10) {
        clearInterval(intervalId);
        setTimeout(function() { sendAnswers(); }, timeForAnswerInMiliseconds)
    }
}

function runQuestionTimer() {
    $(".timer").TimeCircles(
        {
            time: {
                Days: {show: false},
                Hours: {show: false},
                Minutes: {show: false},
                Seconds: {
                    color: "#FFCC00",
                    text: ""
                }
            },
            refresh_interval: 1,
            fg_width: 0.09,
            circle_bg_color: "#D7DBDD",
            animation: "tick",
            count_past_zero: false
        });
}

function destroyQuestionTimer() {
    $(".timer").TimeCircles().destroy();
}

