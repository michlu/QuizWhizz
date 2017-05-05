/**
 * Created by karol on 05.05.2017.
 */

var vm = this;

$.ajax({
    url: '/game/open' + requestData,
    type: 'GET',
    success: function (response) {
        var game = response.data;
        console.log(game);
    }
});

document.getElementById("test").setAttribute("Skrypt dzia")= "Skrypt dziala";