<%--
  Created by IntelliJ IDEA.
  User: michlu
  Date: 16.03.17
  Time: 11:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Quiz Whizz</title>

    <!-- Bootstrap -->
    <!-- Latest compiled and minified CSS -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">

</head>
<body data-spy="scroll" data-target="#navi">
<!-- Importowanie naglowka strony -->
<jsp:include page="fragment/navbar.jspf" />

<div class="row" style="padding-top: 100px;">
    <section class="col-md-offset-1 col-md-10 col-md-offset-1 col-sm-offset-1 col-sm-10">
        <!-- Panel 1-->
        <div class="panel panel-primary">
            <div class="panel-heading">
                <h3 class="panel-title">Dodaj TEMAT</h3>
            </div>
            <div class="panel-body">
                <form class="form-horizontal" method="post" action="subjectServlet">
                    <div class="form-group">
                        <label class="control-label col-sm-2" for="subject">Nazwa tematu:</label>
                        <div class="col-sm-10">
                            <input name="inputSubject" type="text" class="form-control" id="subject" placeholder="Podaj nagłówek tematu" required>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-2" for="description">Opis:</label>
                        <div class="col-sm-10">
                            <textarea name="inputDescription" class="form-control" rows="3" id="description" placeholder="Podaj opisu tematu" maxlength="400" required></textarea>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-sm-offset-2 col-sm-10">
                            <button type="submit" class="btn btn-danger">Zapisz</button>
                        </div>
                    </div>
                </form>
            </div>
            <div class="panel-footer">Stopka</div>
        </div>

        <!-- Panel 2-->
        <div class="panel panel-primary">
            <div class="panel-heading">
                <h3 class="panel-title">Dodaj PYTANIE</h3>
            </div>
            <div class="panel-body">
                <form class="form-horizontal" method="post" action="questionServlet">
                    <div class="form-group">
                        <label class="col-sm-2" for="question_subject">Wybierz temat pytania: </label>
                        <div class="col-sm-10">
                            <select name="inputQuestion_subject" class="form-control" id="question_subject" required>
                                <option>Java</option>
                                <option>Geografia</option>
                            </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-2" for="question">Pytanie:</label>
                        <div class="col-sm-10">
                            <textarea name="inputQuestion" class="form-control" rows="3" id="question" placeholder="Podaj treść pytania" required></textarea>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-2" for="answer_1">Odpowiedź (1):</label>
                        <div class="col-sm-10">
                            <textarea name="inputAnswer_1" class="form-control" rows="2" id="answer_1" placeholder="Podaj przykładową odpowiedź" required></textarea>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-2" for="answer_2">Odpowiedź (2):</label>
                        <div class="col-sm-10">
                            <textarea name="inputAnswer_2" class="form-control" rows="2" id="answer_2" placeholder="Podaj przykładową odpowiedź" required></textarea>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-2" for="answer_3">Odpowiedź (3):</label>
                        <div class="col-sm-10">
                            <textarea name="inputAnswer_3" class="form-control" rows="2" id="answer_3" placeholder="Podaj przykładową odpowiedź" required></textarea>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-2" for="answer_4">Odpowiedź (4):</label>
                        <div class="col-sm-10">
                            <textarea name="inputAnswer_4" class="form-control" rows="2" id="answer_4" placeholder="Podaj przykładową odpowiedź" required></textarea>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-2" for="answer_1">Poprawna:</label>
                        <div class="col-sm-offset-2 col-sm-10">
                            <div class="radio">
                                <label class="radio-inline"><input type="radio" name="answer_correct" value="correct_1" required> odpowiedź 1</label>
                                <label class="radio-inline"><input type="radio" name="answer_correct" value="correct_2" required> odpowiedź 2</label>
                                <label class="radio-inline"><input type="radio" name="answer_correct" value="correct_3" required> odpowiedź 3</label>
                                <label class="radio-inline"><input type="radio" name="answer_correct" value="correct_4" required> odpowiedź 4</label>
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-sm-offset-2 col-sm-10">
                            <button type="submit" class="btn btn-danger">Zapisz</button>
                        </div>
                    </div>
                </form>
            </div>
            <div class="panel-footer">Stopka</div>
        </div>

    </section>
</div>


<footer class="footer">
    <div class="container">
        <p class="text-muted">QuizWhizz - developed by Michał Nowiński and Karolina Prusaczyk</p>
    </div>
</footer>



<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<!-- Include all compiled plugins (below), or include individual files as needed -->
<!-- Latest compiled and minified JavaScript -->
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>
</body>
</html>