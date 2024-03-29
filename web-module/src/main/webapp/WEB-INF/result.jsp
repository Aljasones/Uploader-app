<%@ page import="java.util.List" %>
<%@ page import="java.nio.file.Path" %>
<%@ page import="java.io.File" %>
<%@ page import="ru.itpark.model.Task" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>RFC-Searcher</title>
    <%@include file="bootstrap-css.jsp" %>
</head>
<body>
<div class="container">
    <h6 align="right"><a href="/">BACK</a></h6>

    <h1 align="center" style="color:Blue">Upload service</h1>
    <br>

    <br>
    <table class="table table-striped">
        <thead>
        <tr>
            <th scope="col">Searching phrase</th>
            <th scope="col">Status</th>
        </tr>
        </thead>
        <tbody>
        <% if (request.getAttribute("tasks") != null) {%>
        <%List<Task> taskList = (List<Task>) request.getAttribute("tasks");%>
        <% for (Task tasks : taskList) { %>
        <tr>

            <td><%=tasks.getPhrase() %>
            </td>
            <td><%=tasks.getStatus() %>
            </td>
        </tr>
        <% } %>
        </tbody>
    </table>
    <% } else response.getWriter().write("Sorry, task list is empty!");%>

</div>
<div class="container">
    <div class="row">
        <div class="col">




            <div class="card">
                <div class="card-body">
                    <h5 class="card-title">
                        <% if (request.getAttribute("itemsMap") != null) {%>
                        <%List<File> resultFiles = (List<File>) request.getAttribute("itemsMap");%>
                        <% for (File file : resultFiles) { %>

                    </h5>
                    <p class="card-text">
                    <h5 class="card-title"><%=file.getName()%>
                    </h5>


                    <div>

                        <form action="/result" method="get" enctype="multipart/form-data" class="mt-3">
                            <button type="submit" name="download" value="<%=file.getName()%>" class="btn btn-primary mt-3">Download</button>
                        </form>
                    </div>

                    <% } %>
                    <% } %>

                </div>
            </div>
            <form action="/result" method="get" enctype="multipart/form-data" class="mt-3">
                <button type="submit" name="clearButton" value="clearButton" class="btn btn-primary mt-3">CLEAR</button>
            </form>

        </div>
    </div>

</div>
</div>
<%@include file="bootstrap-scripts.jsp" %>

</body>
</html>
