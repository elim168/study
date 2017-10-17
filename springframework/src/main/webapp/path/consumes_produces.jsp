<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>consumes and produces</title>
<script type="text/javascript"
    src="${pageContext.request.contextPath }/static/js/jquery-3.2.1.js"></script>
<script type="text/javascript">
    $(function() {
        $("#consumesBtn").click(function() {
            var contentType = $("#consumes").val();
            $.ajax({
                url: "${pageContext.request.contextPath}/path/test/consumes/text/plain",
                contentType: contentType,
                success: function(data) {
                    console.log("success:" + data);
                },
                error: function(data) {
                    console.log("error" + data);
                }
            })
        });
        
    });
</script>
</head>
<body>
    <div>
        consumes: <input type="text" id="consumes"/>
    </div>
    <input type="button" id="consumesBtn" value="consumes" />

</body>
</html>