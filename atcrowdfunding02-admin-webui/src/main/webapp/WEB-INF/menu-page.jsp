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
<link href="ztree/zTreeStyle.css" rel="stylesheet"/>
<script type="text/javascript" src="ztree/jquery.ztree.all-3.5.min.js"></script>
<script type="text/javascript" src="crowd/my-menu.js"></script>
<script type="text/javascript">
    $(function () {

        //调用初始化树形结构的函数
        generateTree();
        //添加按钮绑定单及相应函数
        $("#treeDemo").on("click",".addBtn",function () {
            //当前节点就等于新节点的PID
            window.pid=this.id;
            //打开模态框
            $("#menuAddModal").modal("show");

            return false;
        });
        //给添加模态框的保存按钮添加响应函数
        $("#menuSaveBtn").click(function () {
            //收集数据
            var name=$.trim($("#menuAddModal [name=name]").val());
            var url=$.trim($("#menuAddModal [name=url]").val());
            var icon=$.trim($("#menuAddModal [name=icon]:checked").val());
            //发送ajax请求
            $.ajax({
                url:"menu/save.json",
                type:"post",
                dataType:"json",
                data:{
                    pid:window.pid,
                    name:name,
                    url:url,
                    icon:icon
                },
                success:function (response) {

                    var result=response.result;
                    if(result=="SUCCESS"){
                        layer.msg("操作成功！");
                    }
                    if(result=="FAILED"){
                        layer.msg("操作失败！"+response.message);
                    }
                    //关闭模态框
                    $("#menuAddModal").modal("hide");
                    //调用树形结构（用于刷新）
                    generateTree();
                    //清空表单（用模态框的重置）
                    $("#menuResetBtn").click();
                },
                error:function (response) {
                    layer.msg(response.status+" "+response.statusText)
                }
            });
        })
        //修改按钮绑定单及相应函数
        $("#treeDemo").on("click",".editBtn",function () {
            //当前节点保存到全局变量
            window.id=this.id;
            //打开模态框
            $("#menuEditModal").modal("show");
            //获取zTreeObj对象
            var zTreeObj=$.fn.zTree.getZTreeObj("treeDemo");
            //属性名
            var key="id";
            //节点属性值
            var value=window.id;
            //根据id属性查询节点对象
            var currentNode=zTreeObj.getNodeByParam(key,value);
            //回显表单数据
            $("#menuEditModal [name=name]").val(currentNode.name);
            $("#menuEditModal [name=url]").val(currentNode.url);
            $("#menuEditModal [name=icon]").val([currentNode.icon]);

            return false;
        });

        //给添加模态框的更新按钮添加响应函数
        $("#menuEditBtn").click(function () {
            //收集数据
            //收集数据
            var name=$.trim($("#menuEditModal [name=name]").val());
            var url=$.trim($("#menuEditModal [name=url]").val());
            var icon=$.trim($("#menuEditModal [name=icon]:checked").val());

            //发送ajax请求
            $.ajax({
                url:"menu/update.json",
                type:"post",
                dataType:"json",
                data:{
                    id:window.id,
                    name:name,
                    url:url,
                    icon:icon
                },
                success:function (response) {

                    var result=response.result;
                    if(result=="SUCCESS"){
                        layer.msg("操作成功！");
                    }
                    if(result=="FAILED"){
                        layer.msg("操作失败！"+response.message);
                    }
                    //关闭模态框
                    $("#menuEditModal").modal("hide");
                    //调用树形结构（用于刷新）
                    generateTree();
                },
                error:function (response) {
                    layer.msg(response.status+" "+response.statusText)
                }
            });
        })

        //修改按钮绑定单及相应函数
        $("#treeDemo").on("click",".removeBtn",function () {
            //打开确认删除模态框
            $("#menuConfirmModal").modal("show");
            //当前节点保存到全局变量
            window.id=this.id;
            //获取zTreeObj对象
            var zTreeObj=$.fn.zTree.getZTreeObj("treeDemo");
            //属性名
            var key="id";
            //节点属性值
            var value=window.id;
            //根据id属性查询节点对象
            var currentNode=zTreeObj.getNodeByParam(key,value);
            $("#removeNodeSpan").html("  【<i class='"+currentNode.icon+"'></i> "+currentNode.name+"  】");

            return false;
        })
        //给确认模态框的确认按钮绑定单击函数
        $("#confirmBtn").click(function () {
            //发送ajax请求
            $.ajax({
                url:"menu/remove.json",
                type:"post",
                dataType:"json",
                data:{
                    id:window.id,
                },
                success:function (response) {

                    var result=response.result;
                    if(result=="SUCCESS"){
                        layer.msg("操作成功！");
                    }
                    if(result=="FAILED"){
                        layer.msg("操作失败！"+response.message);
                    }
                    //关闭模态框
                    $("#menuConfirmModal").modal("hide");
                    //调用树形结构（用于刷新）
                    generateTree();
                },
                error:function (response) {
                    layer.msg(response.status+" "+response.statusText)
                }
            });
        })
    });
</script>
<body>


<%@include file="/WEB-INF/include-nav.jsp" %>
<div class="container-fluid">
    <div class="row">
        <%@include file="/WEB-INF/include-sidebar.jsp" %>
        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">

            <div class="panel panel-default">
                <div class="panel-heading"><i class="glyphicon glyphicon-th-list"></i> 权限菜单列表
                    <div style="float:right;cursor:pointer;" data-toggle="modal" data-target="#myModal"><i
                            class="glyphicon glyphicon-question-sign"></i></div>
                </div>
                <div class="panel-body">
                    <%--ul标签是zTree动态生成的节点所依附的静态节点--%>
                    <ul id="treeDemo" class="ztree"></ul>
                </div>
            </div>
        </div>
    </div>
</div>

<%@ include file="/WEB-INF/modal-menu-add.jsp"%>
<%@ include file="/WEB-INF/modal-menu-confirm.jsp"%>
<%@ include file="/WEB-INF/modal-menu-edit.jsp"%>
</body>
</html>
