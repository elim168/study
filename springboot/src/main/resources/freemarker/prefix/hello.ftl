<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Hello Freemarker</title>
</head>
<body>
    Hello Freemarker!
    <br/>
    ${message}
    <ul>
        列表元素是：
        <#list list as item>
            <li>${item_index+1}=====${item}</li>
        </#list>
    </ul>
</body>
</html>