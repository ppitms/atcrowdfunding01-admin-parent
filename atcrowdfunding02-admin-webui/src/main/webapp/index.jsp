<%--
  Created by IntelliJ IDEA.
  User: itman
  Date: 2022/3/14
  Time: 18:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <base href="http://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}/">
    <script type="text/javascript" src="jquery/jquery-2.1.1.min.js"></script>
    <script type="text/javascript" src="layer/layer.js"></script>

    <script type="text/javascript">
        $(function () {
            $("#btn5").click(function () {
                layer.msg("Layer弹框")
            });

            $("#btn4").click(function () {
                var student = {
                    "name": "TOm",
                    "age": 12
                };

                //将json设置转化成json字符串
                var requesBody = JSON.stringify(student);

                $.ajax({
                    "url": "send/compose/object.json",
                    "type": "post",
                    "data": requesBody,
                    contentType: "application/json;charset=UTF-8",
                    "dataType": "json",
                    "success": function (response) {
                        alert(response);
                    },
                    "error": function (response) {
                        alert(response);
                    }
                })
            })

            $("#btn3").click(function () {
                var array = [5, 8, 12];
                // var obj={"name":"dfg","age":12};
                //将json设置转化成json字符串
                var requestBody = JSON.stringify(array);

                $.ajax({
                    "url": "send/array/three.html",
                    "type": "post",
                    "data": requestBody,
                    contentType: "application/json;charset=UTF-8",
                    "dataType": "text",
                    "success": function (response) {
                        alert(response);
                    },
                    "error": function (response) {
                        alert(response);
                    }
                })
            })

            $("#btn2").click(function () {
                $.ajax({
                    "url": "send/array/two.html",
                    "type": "post",
                    "data": {
                        "array[0]": 5,
                        "array[1]": 8,
                        "array[2]": 4
                    },
                    "dataType": "text",
                    "success": function (response) {
                        alert(response);
                    },
                    "error": function (response) {
                        alert(response);
                    }
                })
            })

            $("#btn1").click(function () {
                $.ajax({
                    "url": "send/array/one.html",
                    "type": "post",
                    "data": {
                        "array": [5, 8, 12]
                    },
                    "dataType": "text",
                    "success": function (response) {
                        alert(response);
                    },
                    "error": function (response) {
                        alert(response);
                    }
                })
            })
        })
    </script>
</head>
<body>
<a href="test/ssm.html">测试SSM整合环境</a><br/><br/>

<button id="btn1">Send [8,8,12] One</button>
<br/><br/>

<button id="btn2">Send [8,8,12] Two</button>
<br/><br/>

<button id="btn3">Send [8,8,12] Three</button>
<br/><br/>

<button id="btn4">Send Student Object</button>
<br/><br/>

<button id="btn5">点我弹框</button>
</body>
</html>
