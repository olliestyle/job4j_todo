"use strict"

function tryRegister() {
    $(function () {
        var name = document.getElementById('nameId').value;
        var password = document.getElementById('passwordId').value;
        $.ajax({
            type: 'POST',
            url: 'http://localhost:8080/job4j_todo/reg.do',
            data: {name, password},
            dataType: 'json'
        }).done(function (data) {
            if (data === 'Пользователь с таким именем уже зарегистрирован') {
                if (document.getElementById('message') === null) {
                    $('<p style="color: red" id="message">' + data + '</p>').insertAfter(".message");
                }
            } else if (data === 'Пользователь добавлен') {
                window.location.href = "http://localhost:8080/job4j_todo/login.html";
            }
        }).fail(function (err) {
            console.log(err);
        })
    });
}