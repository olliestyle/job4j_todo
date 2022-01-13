"use strict"

function tryLogin() {
    $(function () {
        var name = document.getElementById('nameId').value;
        var password = document.getElementById('passwordId').value;
        $.ajax({
            type: 'POST',
            url: 'http://localhost:8080/job4j_todo/log.do',
            data: {name, password},
            dataType: 'json'
        }).done(function (data) {
            if (data === 'Не верное имя или пароль') {
                if (document.getElementById('message') === null) {
                    $('<p style="color: red" id="message">' + data + '</p>').insertAfter(".message");
                }
            } else {
                window.location.href = "http://localhost:8080/job4j_todo?username=" + data;
            }
        }).fail(function (err) {
            console.log(err);
        })
    });
}