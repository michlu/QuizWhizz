$(document).ready(function () {
        $('#question-1').show();
    }
);

var timeForAnswerInMiliseconds = 15000;
var intervalId = setInterval(function() { showQuestion(); }, timeForAnswerInMiliseconds);
var submittedAnswer = '';

function submitSingleAnswer(answerId) {
    submittedAnswer += answerId + ',';
    showQuestion();
    clearInterval(intervalId);
    intervalId = setInterval(function() { showQuestion(); }, timeForAnswerInMiliseconds);
}

function sendAnswers() {
    var gameId = $('#game-id').text();
    window.location.href = "/game/" + gameId + "/submitAnswers/" + submittedAnswer.replace(/,\s*$/, "");
}

var currentQuestion = 1;
function showQuestion() {
    currentQuestion++;
    $('#question-' + (currentQuestion - 1)).hide();
    $('#question-' + currentQuestion).show();


    if(currentQuestion == 10) {
        clearInterval(intervalId);
        setTimeout(function() { sendAnswers(); }, timeForAnswerInMiliseconds)
    }
}
