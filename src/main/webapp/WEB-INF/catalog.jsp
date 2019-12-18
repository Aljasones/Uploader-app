<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Upload files</title>
    <%@include file="bootstrap-css.jsp"%>
</head>
<body>
<div class="container">
    <div class="row">
        <div class="col">
            <br>
            <h1 align="center" style="color:Blue">Upload service</h1>
            <br>
            <form action="<%=request.getContextPath()%>" method="post" enctype="multipart/form-data" class="mt-3">
                <div class="custom-file">
                    <input type="file" id="file" name="file" class="custom-file-input" accept="text/plain" required>
                    <label class="custom-file-label" for="file">Choose file...</label>
                    <button type="submit" class="btn btn-primary mt-3">Upload</button>
                </div>
            </form>
            <br><br>
            <form action="<%=request.getContextPath()%>/search" method="get" enctype="multipart/form-data" class="mt-3">
                <div class="form-group">
                    <label for="phrase"><h6 align="left" style="color:Gray">Enter search phrase</h6></label>
                    <input type="text" id="phrase" name="phrase" class="form-control" required>
                    <button type="submit" class="btn btn-primary mt-3">Search</button>
                </div>
            </form>
        </div>
    </div>
</div>
<%@ include file="bootstrap-scripts.jsp"%>
</body>
</html>