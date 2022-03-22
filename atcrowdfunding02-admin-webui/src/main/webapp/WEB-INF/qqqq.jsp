<%--
  Created by IntelliJ IDEA.
  User: itman
  Date: 2022/3/17
  Time: 16:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

<head>
    <title>Title</title>
    <link rel="stylesheet" href="/css/pagination.css" />
    <script type="text/javascript" src="lib/jquery.min.js"></script>
    <script type="text/javascript" src="/jquery/jquery.pagination.js"></script>

    <script type="text/javascript">
        $(function () {
            initPagination();
        });

        function initPagination() {
            //获取总记录数
            var totalRecord =${requestScope.pageInfo.total};
            //json对象存储属性
            var propertiess = {
                num_edge_entries: "3",
                num_display_entries: "5", //主体页数
                callback: pageSelectCallback,
                items_per_page:"${requestScope.pageInfo.pageSize}", //每页显示1项
                current_page:"${requestScope.pageInfo.pageNum-1}",
                prev_text: "上一页",
                next_text: "下一页"
            }
            //生成页码导航条
            ${"#Pagination"}.pagination(totalRecord, propertiess);
        }

        function pageSelectCallback(pageIndex, jQuery) {
            var pageNum = pageIndex + 1;
            window.location.href = "admin/get/page.html?pageNum=" + pageNum;
            return false;
        }
    </script>

</head>

<body>
<tr>
    <td colspan="6" align="center">
        <div id="Pagination" class="pagination"><!-- 这里显示分页 --></div>

    </td>
</tr>
</body>
</html>
