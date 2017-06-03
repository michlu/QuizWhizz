$(document).ready(function () {
        $('#question-1').show();
    runCircleTimer();
    addListenerToCircleTimer();
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
    destroyTimer();
    runCircleTimer();
    runQuestionTimer();

    if(currentQuestion == 10) {
        clearInterval(intervalId);
        setTimeout(function() { sendAnswers(); }, timeForAnswerInMiliseconds)
    }
}
function runQuestionTimer() {
    var questionSecondsLeft = 15;
    $('#question-time-left').text(questionSecondsLeft);

    questionTimeIntervalId = setInterval(function () {
        $('#question-time-left').text(--questionSecondsLeft);
        if (questionSecondsLeft <= 0) {
            clearInterval(questionTimeIntervalId);
        }
    }, 1000);
}

function runCircleTimer() {
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

function destroyTimer() {
    $(".timer").TimeCircles().destroy();
}

function addListenerToCircleTimer() {
    $(".timer").TimeCircles().addListener(function(unit, amount, total){
        if(total == 0) {
            console.log("Czas minal");
//            $(".timer").TimeCircles().start();
        }
    });
}
