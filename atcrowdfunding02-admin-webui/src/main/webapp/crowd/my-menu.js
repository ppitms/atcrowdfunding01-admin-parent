//生成树形结构函数
function generateTree() {
    //1、生成树形结构的数据，由发送Ajax请求得来
    $.ajax({
        url:"menu/get/whole/tree.json",
        type:"post",
        dataType:"json",
        success:function (response) {
            var result=response.result;
            if(result=="SUCCESS"){
                //2、创建JSON对象用于存储对zTree所做的设置
                var setting={
                    "view": {
                        "addDiyDom": myAddDiyDom,
                        "addHoverDom":myAddHoverDom,
                        "removeHoverDom":myRemoveHoverDom
                    },
                    "data":{
                        "key":{
                            "url":"a"   //为了不跳转
                        }
                    }
                };
                //json数据
                var zNodes=response.data;
                //初始化数据结构
                $.fn.zTree.init($("#treeDemo"), setting, zNodes);
            }
            if(result=="FAILED"){
                layer.msg(response.message);
            }
        }
    });
}

//修改默认图标
function myAddDiyDom(treeId,treeNode) {
    console.log("treeId:"+treeId);
    console.log("treeNode:"+treeNode);

    var spanId=treeNode.tId+"_ico";
    $("#"+spanId).removeClass();
    $("#"+spanId).addClass(treeNode.icon);
}

//鼠标移入节点范围添加按钮
function myAddHoverDom(treeId,treeNode) {
    var btnGroupId=treeNode.tId+"_btnGrp";

    //准备各个按钮的HTML标签
    var addBtn="<a id='"+treeNode.id+"' class='btn btn-info dropdown-toggle btn-xs addBtn' style='margin-left:10px;padding-top:0px;' href='#' title='添加节点'>&nbsp;&nbsp;<i class='fa fa-fw fa-plus rbg '></i></a>";
    var removeBtn="<a id='"+treeNode.id+"' class='btn btn-info dropdown-toggle btn-xs removeBtn' style='margin-left:10px;padding-top:0px;' href='#' title='删除节点'>&nbsp;&nbsp;<i class='fa fa-fw fa-times rbg '></i></a>";
    var editBtn="<a id='"+treeNode.id+"' class='btn btn-info dropdown-toggle btn-xs editBtn' style='margin-left:10px;padding-top:0px;' href='#' title='修改节点'>&nbsp;&nbsp;<i class='fa fa-fw fa-edit rbg '></i></a>";

    //获取当前节点的级别数据
    var level=treeNode.level;
    //用于拼装数据
    var btnHTML="";
    //判断节点级别
    if(level==0){//0为根节点——添加
        btnHTML=addBtn;
    }
    if(level==1){//分支节点——添加、修改/添加、修改、删除
        // btnHTML=addBtn+" "+editBtn;
        // if(treeNode.children().length==0){
        //     btnHTML=btnHTML+" "+removeBtn;
        // }
        btnHTML=addBtn+" "+editBtn;
        var chilLen=treeNode.children.length;
        if(chilLen==0){
            btnHTML=btnHTML+" "+removeBtn;
        }
    }
    if(level==2){//叶子节点——修改删除-
        btnHTML=editBtn+" "+removeBtn;
    }

    var spanId=treeNode.tId+"_a";
    //判断是否添加按钮
    if($("#"+btnGroupId).length>0){
        return;
    }
    //超链接后加span元素
    $("#"+spanId).after("<span id='"+btnGroupId+"'>"+btnHTML+"</span>");
}

//鼠标移出节点范围删除按钮
function myRemoveHoverDom(treeId,treeNode) {
    var btnGroupId=treeNode.tId+"_btnGrp";
    //移除
    $("#"+btnGroupId).remove();
}