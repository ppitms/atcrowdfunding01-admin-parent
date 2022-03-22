//封装auth模态框树形结构数据
function fillAutoTree(){
    //发送ajax请求查询auth数据
    var ajaxReturn=$.ajax({
        url:"assgin/get/all/auth.json",
        type:"post",
        dataType:"json",
        async:false
    });
    if(ajaxReturn.status!=200){
        layer.msg("操作失败!"+ajaxReturn.status+" "+ajaxReturn.statusText);
        return ;
    }
    //成功则获取数据
    var authList=ajaxReturn.responseJSON.data;

    var setting={
        data:{
            simpleData:{
                //开启简单JSON功能
                enable:true,
                //用此属性关联父节点
                pIdKey:"categoryId"
            },
            key:{
                //属性名用title属性
                name:"title"
            }
        },
        check:{
            enable:true
        }
    };
    //生成树形结构
    $.fn.zTree.init($("#authTreeDemo"),setting,authList);
    //zTreeOjc对象方法把节点展开
    var zTreeObj=$.fn.zTree.getZTreeObj("authTreeDemo");
    //展开节点
    zTreeObj.expandAll(true);
    //查询已分配的Auth的int组成的数组
    var ajaxReturn=$.ajax({
        url:"assign/get/assigned/auth/id/by/role/id.json",
        type:"post",
        dataType:"json",
        data:{
            roleId:window.roleId
        },
        async:false
    });
    if(ajaxReturn.status!=200){
        layer.msg("操作失败!"+ajaxReturn.status+" "+ajaxReturn.statusText);
        return ;
    }
    var authIdArray=ajaxReturn.responseJSON.data;
    //根据数据勾选以选的节点
    //1 遍历数组
    for(var i=0;i<authIdArray.length;i++){
        var authId=authIdArray[i];
        //2 根据id查询树形结构的节点名
        var treeNode=zTreeObj.getNodeByParam("id",authId);
        //3 设置勾选（哪个节点节点，要节点勾选，不联动）
        zTreeObj.checkNode(treeNode,true,false)
    }
}

//确认模态框
function showConfirmModal(roleArray) {
    //清除旧数据
    $("#roleNameDiv").empty();
    //打开模态框
    $("#confirmModal").modal("show");
    //全局变量存放角色id
    window.roleIdArray=[];

    //遍历roleArray数组
    for(var i=0;i<roleArray.length;i++){
        var role=roleArray[i];
        var roleName=role.roleName;
        $("#roleNameDiv").append(roleName+"<br/>");

        var roleId=role.roleId;

        window.roleIdArray.push(roleId);
    }
}

//生成分页
function generatePage() {
    //重置全选
    $("#summaryBox").prop("checked",false);
    //1、获取分页数据
    var pageInfo = getPageInfoRemote();

    //2、填充表格
    fillTableBody(pageInfo);
}

//远程拿到数据
function getPageInfoRemote() {
    var ajaxResult = $.ajax({
        "url": "role/get/page/info.json",
        "type": "post",
        "data": {
            pageNum: window.pageNum,
            pageSize: window.pageSize,
            keyword: window.keyword
        },
        "dataType": "json",
        "async": false
    });
    console.log(ajaxResult);
    //判断响应状态码是否200
    var statusCode=ajaxResult.status;
    if(statusCode!=200){
        layer.msg("服务器端程序调用失败！响应状态码："+statusCode+" 说明信息="+ajaxResult.statusText);
        return null;
    }
    //成功后
    var resultEntity=ajaxResult.responseJSON;

    //判断是否成功
    var result=resultEntity.result;
    if(result=="FAILED"){
        layer.msg(resultEntity.message);
        return null;
    }
    //成功后获取
    var pageInfo=resultEntity.data;

    return pageInfo;
}

//填充表格
function fillTableBody(pageInfo) {
    //清除旧数据和导航条（没有搜索结果时）
    $("#rolePageBody").empty();
    $("#Pagination").empty();

    //判断pageINfo是否有效
    if(pageInfo==null||pageInfo==undefined||pageInfo.list==null||pageInfo.list.length==0){
        $("#rolePageBody").append("<tr><td colspan='4'>抱歉！没有查到您要的数据</td></tr>");
        return;
    }
    console.log(pageInfo);

    //填充pageInfo
    for(var i=0;i<pageInfo.list.length;i++){
        var role=pageInfo.list[i];
        var roleId=role.id;
        var roleName=role.name;

        var numberTd="<td>"+(i+1)+"</td>";
        var checkboxTd="<td><input id='"+roleId+"' class='itemBox' type='checkbox'></td>";
        var roleNameTd="<td>"+roleName+"</td>";
        var checkBtn=" <button id='"+roleId+"' type='button' class='btn btn-success btn-xs'><i class='glyphicon glyphicon-check checkBtn'></i></button>";
        var pencilBtn=" <button id='"+roleId+"' type='button' class='btn btn-primary btn-xs'><i class='glyphicon glyphicon-pencil pencilBtn'></i></button>";
        var removeBtn="<button id='"+roleId+"' type='button' class='btn btn-danger btn-xs'><i class='glyphicon glyphicon-remove removeBtn'></i></button></td>";
        var buttonTd="<td>"+checkBtn+" "+pencilBtn+" "+removeBtn+"</td>";
        var tr="<tr>"+numberTd+checkboxTd+roleNameTd+buttonTd+"</tr>";
        $("#rolePageBody").append(tr);
    }
    generateNavigator(pageInfo);
}

//分页导航栏
function generateNavigator(pageInfo) {
    //获取总记录数
    var totalRecord=pageInfo.total;
    //相关属性
    var properties={
        num_edge_entries:3,
        num_display_entries:5,
        callback:paginationCallBack,
        items_per_page:pageInfo.pageSize,
        current_page:pageInfo.pageNum-1,
        prev_text: "上一页",                                 //在对应上一页操作的按钮上的文本
        next_text: "下一页"
    }

    //生成导航条
    $("#Pagination").pagination(totalRecord,properties);
}

//翻页时的回调函数
function paginationCallBack(pageIndex,jQuery) {
    window.pageNum=pageIndex+1;

    generatePage();

    return false;

}