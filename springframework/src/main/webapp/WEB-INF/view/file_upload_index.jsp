<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>文件上传验证</title>
</head>
<body>
${abc }



<hr/>

${ddd }
    <form action="fileupload/doupload" enctype="multipart/form-data" method="post">
        <div>
            <input type="file" name="file1"/>
        </div>
        <div>
            <input type="file" name="file2"/>
        </div>
        <div>
            <input type="submit" value="上传"/>
        </div>
    </form>

</body>
</html>