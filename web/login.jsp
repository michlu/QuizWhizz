<%--
  Created by IntelliJ IDEA.
  User: michlu
  Date: 14.03.17
  Time: 18:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Quiz Whizz</title>

    <!-- Bootstrap -->
    <!-- Latest compiled and minified CSS -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">

    <script src="https://cdnjs.cloudflare.com/ajax/libs/1000hz-bootstrap-validator/0.11.5/validator.min.js">
    </script>

</head>
<body data-spy="scroll" data-target="#navi">
<nav class = "navbar navbar-inverse navbar-fixed-top">
    <div class="container">
        <a href="#" class="navbar-brand">Quiz Whizz</a>



        <button class="navbar-toggle" data-toggle="collapse" data-target=".navCollapse">
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
        </button>
    </div>
</nav>

<div class="row" style="padding-top: 100px;">
    <section class="col-md-offset-1 col-md-10 col-md-offset-1 col-sm-offset-1 col-sm-10">
        <!-- Panel 1-->
        <div class="panel-heading"><h1 class="display-3">Logowanie</h1></div>

        <div class="panel-body">

            <form role="form"  method="POST" action="<%= response.encodeURL("/j_security_check") %>">

                <div class="form-group">
                    <label class="control-label" for="inputLogin">Login</label>
                    <input name="j_username" class="form-control" data-error="Please enter login field." id="inputLogin" placeholder="Podaj login"  type="text" required />
                    <div class="help-block with-errors"></div>
                </div>

                <div class="form-group">
                    <label for="inputPassword" class="control-label">Hasło</label>
                    <div class="form-group">
                        <input name="j_password" type="password" data-minlength="5" class="form-control" id="inputPassword" data-error="must enter minimum of 5 characters" placeholder="Podaj hasło" required>
                        <div class="help-block with-errors"></div>
                    </div>
                </div>

                <div class="form-group">
                    <button class="btn btn-primary" type="submit">
                        Zaloguj
                    </button>
                    <a href="${pageContext.request.contextPath}/register.jsp" class="btn btn-success" role="button">Przejdz do rejestracji</a>
                </div>
            </form>
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
