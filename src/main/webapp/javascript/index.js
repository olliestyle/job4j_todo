"use strict"
$(document).ready(function () {
    $.ajax({
        type: 'GET',
        url: 'http://localhost:8080/job4j_todo/addTask',
        dataType: 'json'
    }).done(function (data) {
        for (var task of data) {
            $('#tasksTable thead:last').append('<tr>'
                + '<td>' + task.description + '</td>'
                + '<td>' + task.created + '</td>'
                + '<td>' + '<input type="checkbox" id="' + task.id + '" onchange="checkTask(' + task.id + ')">' + task.id + '</td>'
                + '</tr>')
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
            url: 'http://localhost:8080/job4j_todo/checkTask',
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
        console.log(description);
        $.ajax({
            type: 'POST',
            url: 'http://localhost:8080/job4j_todo/addTask',
            data: {description : description},
            dataType: 'json'
        }).done(function (data) {
            $('#tasksTable tr:last').after('<tr>'
                + '<td>' + data.description + '</td>'
                + '<td>' + data.created + '</td>'
                + '<td>' + '<input type="checkbox" id="' + data.id + '" onchange="checkTask(' + data.id + ')">' + data.id + '</td>'
                + '</tr>');
            document.getElementById("taskDesc").value = "";
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
                url: 'http://localhost:8080/job4j_todo/showAllTasks',
                dataType: 'json'
            }).done(function (data) {
                for (var task of data) {
                    $('#tasksTable thead:last').append('<tr>'
                        + '<td>' + task.description + '</td>'
                        + '<td>' + task.created + '</td>'
                        + '<td>' + '<input type="checkbox" disabled="true" checked="true" id="done' + data.id + '">' + task.id + '</td>'
                        + '</tr>')
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