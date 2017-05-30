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

