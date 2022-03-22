<%--
  Created by IntelliJ IDEA.
  User: itman
  Date: 2022/3/15
  Time: 22:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="zh-CN">
<%@include file="/WEB-INF/include-head.jsp" %>
<%--引入pagination的css--%>
<link href="css/pagination.css" rel="stylesheet"/>
<%--引入基于jquery的paginationjs--%>
<script type="text/javascript" src="jquery/jquery.pagination.js"></script>
<link href="ztree/zTreeStyle.css" rel="stylesheet"/>
<script type="text/javascript" src="ztree/jquery.ztree.all-3.5.min.js"></script>
<script type="text/javascript" src="crowd/my-role.js"></script>
<script type="text/javascript">
    $(function () {
        //1、为分页操作初始化数据
        window.pageNum = 1;
        window.pageSize = 5;
        window.keyword = "";

        //分页导航条
        generatePage();

        //单击查询按钮
        $("#searchBtn").click(function () {
            //获取关键字
            window.keyword = $("#keywordInput").val();
            //调用分页函数导航条
            generatePage();
        });

        //点击新增按钮弹出模态框
        $("#showAddModalBtn").click(function () {
            $("#addModal").modal("show");
        });

        //点击保存
        $("#saveRoleBtn").click(function () {
            //获取用户在文本框中的角色名称
            var roleName = $.trim($("#addModal [name=roleName]").val());
            //
            $.ajax({
                url: "role/save.json",
                type: "post",
                dataType: "json",
                data: {"name": roleName},
                success: function (response) {
                    var result = response.result;
                    if (result == "SUCCESS") {
                        layer.msg("操作成功！");
                        //清空数据（模态框）
                        $("#addModal [name=roleName]").val("");
                        //关闭模态框
                        $("#addModal").modal("hide");
                        //分页导航到最后一页
                        window.pageNum=999999;
                        generatePage();
                    }
                    if (result == "FAILED") {
                        layer.msg("操作失败！"+response.message);
                    }
                },
                error: function (response) {
                    layer.msg("操作失败！"+response.message);
                }
            });

        });
        //点击铅笔图标打开模态框
        // $(".pencilBtn").click(function () {
        //     alert("aaaaaa");
        // })
        $("#rolePageBody").on("click",".pencilBtn",function () {
            //打开模态框
            $("#editModal").modal("show");
            var roleName=$(this).parent().parent().prev().text()
            //当前角色Id
            window.roleId=$(this).parent().attr("id");
            $("#editModal [name=roleName]").val(roleName);
            // $("#inputSuccess4").val(roleName);
        });

        //按下更新按钮
        $("#updateRoleBtn").click(function () {
            var roleName=$("#editModal [name=roleName]").val();
            $.ajax({
                url:"role/update.json",
                data:{
                    id:window.roleId,
                    name:roleName
                },
                type:"post",
                dataType:"json",
                success: function (response) {
                    var result = response.result;
                    if (result == "SUCCESS") {
                        layer.msg("操作成功！");
                        //关闭模态框
                        $("#editModal").modal("hide");
                        //重新加载分页数据
                        generatePage();
                    }
                    if (result == "FAILED") {
                        layer.msg("操作失败！"+response.message);
                    }
                },
                error: function (response) {
                    layer.msg("操作失败！"+response.message);
                }
            })
        })
        // var roleArray=["dff","f3fdf","的官方答复"];
        // showConfirmModal(roleArray)
        //点击图标打开删除单个的模态框
        $("#rolePageBody").on("click",".removeBtn",function () {
            var roleName=$(this).parent().parent().prev().text();
            var roleArray=[{
                roleId:$(this).parent().attr("id"),
                roleName:roleName
            }];
            //调用函数打开模态框
            showConfirmModal(roleArray);
        });

        //点击确认按钮执行删除
        $("#removeRoleBtn").click(function () {
            //转化成json字符串
            var requestBody=JSON.stringify(window.roleIdArray);
            $.ajax({
                url:"role/remove/by/role/id/array.json",
                type:"post",
                data:requestBody,
                contentType:"application/json;charset=UTF-8",
                dataType:"json",
                success: function (response) {
                    var result = response.result;
                    if (result == "SUCCESS") {
                        layer.msg("操作成功！");
                        //清空数据（模态框）
                        $("#confirmModal [name=roleName]").val("");
                        //关闭模态框
                        $("#confirmModal").modal("hide");
                        generatePage();
                    }
                    if (result == "FAILED") {
                        layer.msg("操作失败！"+response.message);
                    }
                },
                error: function (response) {
                    layer.msg("操作失败！"+response.message);
                }
            });
        });
        //给总checkbox绑定单机函数
        $("#summaryBox").click(function () {
            //获取自身状态
            var currentStatus=this.checked;
            //依据当前多选框设置其他单选框
            $(".itemBox").prop("checked",currentStatus);
        });
        //给单个checkbox绑定单机函数
        $("#rolePageBody").on("click",".itemBox",function () {
            var checkedBoxCount=$(".itemBox:checked").length;
            var totalBoxCount=$(".itemBox").length;
            $("#summaryBox").prop("checked",checkedBoxCount==totalBoxCount);
        });

        //给批量删除的按钮绑定
        $("#batchRemoveBtn").click(function () {
            var roleArray=[];
            //遍历当前选中框
            $(".itemBox:checked").each(function () {
                var roleId=this.id;
                var roleName=$(this).parent().next().text();
                roleArray.push({
                    roleId:roleId,
                    roleName:roleName
                });
            });
            if(roleArray.length==0){
                layer.msg("请勾选要删除的数据！");
                return;
            }
            //调用函数打开模态框
            showConfirmModal(roleArray);
        });

        //给单个checkbox绑定单机函数
        $("#rolePageBody").on("click",".checkBtn",function () {
            //当前角色存入全局变量
            window.roleId=$(this).parent().prop("id");
            //打开模态框
            $("#assignModal").modal("show");
            //装载树形结构数据
            fillAutoTree();
        });

        //给模态框的确认单击按钮响应函数
        $("#assignBtn").click(function () {
            //收集选中的信息
            var authIdArray=[];
            var zTreeObj=$.fn.zTree.getZTreeObj("authTreeDemo");
            var checkedNodes=zTreeObj.getCheckedNodes();
            for(var i=0;i<checkedNodes.length;i++){
                authIdArray.push(checkedNodes[i].id);
            }
            var requestBody={
                authIdArray:authIdArray,
                id:[window.roleId]
            };
            var requestBody =JSON.stringify(requestBody);
            //发送请求执行
            $.ajax({
                url:"assign/do/role/assign/auth.json",
                type:"post",
                dataType:"json",
                contentType:"application/json;charset=UTF-8",
                data:requestBody,
                success:function (response) {
                    var result=response.result;
                    if(result=="SUCCESS"){
                        layer.msg("操作成功！");
                        //关闭模态框
                        $("#assignModal").modal("hide");
                        generatePage();
                    }
                    if(result=="FAILED"){
                        layer.msg("操作失败！"+response.message)
                    }
                },
                error:function (response) {
                    layer.msg(response.status+" "+response.statusText);
                }
            });

        })
    })
</script>
<body>

<%@include file="/WEB-INF/include-nav.jsp" %>
<div class="container-fluid">
    <div class="row">
        <%@include file="/WEB-INF/include-sidebar.jsp" %>
        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h3 class="panel-title"><i class="glyphicon glyphicon-th"></i> 数据列表</h3>
                </div>
                <div class="panel-body">
                    <form class="form-inline" role="form" style="float:left;">
                        <div class="form-group has-feedback">
                            <div class="input-group">
                                <div class="input-group-addon">查询条件</div>
                                <input id="keywordInput" class="form-control has-success" type="text"
                                       placeholder="请输入查询条件">
                            </div>
                        </div>
                        <button id="searchBtn" type="button" class="btn btn-warning"><i
                                class="glyphicon glyphicon-search"></i> 查询
                        </button>
                    </form>
                    <button id="batchRemoveBtn" type="button" class="btn btn-danger" style="float:right;margin-left:10px;"><i
                            class=" glyphicon glyphicon-remove"></i> 删除
                    </button>
                    <button id="showAddModalBtn" type="button" class="btn btn-primary" style="float:right;">
                        <i class="glyphicon glyphicon-plus"></i> 新增
                    </button>
                    <br>
                    <hr style="clear:both;">
                    <div class="table-responsive">
                        <table class="table  table-bordered">
                            <thead>
                            <tr>
                                <th width="30">#</th>
                                <th width="30"><input id="summaryBox" type="checkbox"></th>
                                <th>名称</th>
                                <th width="100">操作</th>
                            </tr>
                            </thead>
                            <tbody id="rolePageBody">
                            <%--ajax请求--%>
                            </tbody>
                            <tfoot>
                            <tr>
                                <td colspan="6" align="center">
                                    <div id="Pagination" class="pagination"><!-- 这里显示分页 --></div>
                                </td>
                            </tr>

                            </tfoot>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<%@ include file="/WEB-INF/modal-role-add.jsp" %>
<%@ include file="/WEB-INF/modal-role-edit.jsp" %>
<%@ include file="/WEB-INF/modal-role-confirm.jsp" %>
<%@ include file="/WEB-INF/modal-role-assign-auth.jsp" %>
</body>
</html>
