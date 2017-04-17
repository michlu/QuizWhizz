/**
 * Created by michlu on 12.04.2017.
 */
function checkPass()
{
    var pass1 = document.getElementById('inputPassword1');
    var pass2 = document.getElementById('inputPassword2');

    var message = document.getElementById('confirmMessage');

    var goodColor = "#66cc66";
    var badColor = "#ff6666";

    if(pass1.value == pass2.value){
        pass2.style.backgroundColor = goodColor;
        message.style.color = goodColor;
        message.innerHTML = "Hasło poprawne!"
    }else{
        pass2.style.backgroundColor = badColor;
        message.style.color = badColor;
        message.innerHTML = "Hasło nie poprwne!"
    }
}