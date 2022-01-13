"use strict"
$(document).ready(function () {
    $.ajax({
        type: 'GET',
        url: 'http://localhost:8080/job4j_todo/addTask.do',
        dataType: 'json'
    }).done(function (data) {
        if (data === 'Сессия не найдена') {
            if (document.getElementById('message') === null) {
                $('<a id="message" href="http://localhost:8080/job4j_todo/login.html" style="margin-left: 30%; text-align: center; display: inline">Войдите</a>'
                    + ' или ' + '<a href="http://localhost:8080/job4j_todo/reg.html">Зарегистрируйтесь</a>')
                    .insertAfter(".message");
            }
        } else {
            $('<p>Вы вошли как: ' + checkName() + ' </p>').insertAfter(".username");
            $('<form action="/job4j_todo/logout.do">' +
                '<input type="submit" class="btn btn-success" value="Выйти">' +
                '<input type="hidden" name="rand" value="' + Math.random() +'">' +
                '</form>')
                .insertAfter(".exit");
            for (var task of data) {
                $('#tasksTable thead:last').append('<tr>'
                    + '<td>' + task.description + '</td>'
                    + '<td>' + task.created + '</td>'
                    + '<td>' + task.user.name + '</td>'
                    + '<td>' + '<input type="checkbox" id="' + task.id + '" onchange="checkTask(' + task.id + ')"></td>'
                    + '</tr>')
            }
        }
    }).fail(function (err) {
        console.log(err);
    });
});
function checkTask(taskId) {
    $(function () {
        if (!document.getElementById('allTasksCheckBox').checked) {
            var rowToDelete = document.getElementById(taskId).parentNode.parentNode.rowIndex;
            document.getElementById('tasksTable').deleteRow(rowToDelete);
        } else {
            document.getElementById(taskId).setAttribute('disabled', 'true');
        }
        $.ajax({
            type: 'POST',
            url: 'http://localhost:8080/job4j_todo/checkTask.do',
            data: {taskId : taskId},
            dataType: 'json'
        }).done(function () {

        }).fail(function (err) {
            console.log(err);
        })
    });
}
function addTask() {
    $(function () {
        var description = document.getElementById("taskDesc").value;
        $.ajax({
            type: 'POST',
            url: 'http://localhost:8080/job4j_todo/addTask.do',
            data: {description : description},
            dataType: 'json'
        }).done(function (data) {
            if (data === 'Сессия не найдена') {
            } else {
                $('#tasksTable tr:last').after('<tr>'
                    + '<td>' + data.description + '</td>'
                    + '<td>' + data.created + '</td>'
                    + '<td>' + data.user.name + '</td>'
                    + '<td>' + '<input type="checkbox" id="' + data.id + '" onchange="checkTask(' + data.id + ')"></td>'
                    + '</tr>');
                document.getElementById("taskDesc").value = "";
            }
        }).fail(function (err) {
            console.log(err);
        })
    });
}
function getDoneTasks() {
    $(function () {
        if (document.getElementById('allTasksCheckBox').checked === true) {
            $.ajax({
                type: 'GET',
                url: 'http://localhost:8080/job4j_todo/showAllTasks.do',
                dataType: 'json'
            }).done(function (data) {
                if (data === 'Сессия не найдена') {
                } else {
                    for (var task of data) {
                        $('#tasksTable thead:last').append('<tr>'
                            + '<td>' + task.description + '</td>'
                            + '<td>' + task.created + '</td>'
                            + '<td>' + task.user.name + '</td>'
                            + '<td>' + '<input type="checkbox" disabled="true" checked="true" id="done' + data.id + '"></td>'
                            + '</tr>')
                    }
                }
            }).fail(function (err) {
                console.log(err);
            })
        } else {
            var checkedBoxes = $("input[type=checkbox]:checked", "#tasksTable");
            for (var i = 0; i < checkedBoxes.length; i++) {
                document.getElementById('tasksTable').deleteRow(checkedBoxes[i].parentNode.parentNode.rowIndex);
            }
        }
    });
}
function checkName() {
    var params = new URLSearchParams(window.location.search);
    const username = params.get('username');
    return username;
}
/*
function exitTodo() {
    $(function () {
        $.ajax({
            type: 'GET',
            url: 'http://localhost:8080/job4j_todo/logout.do',
            cache: false
        }).done(function () {
            console.log('done');
        }).fail(function (err) {
            console.log(err);
        })
    });
}
*/