<!DOCTYPE html>
<html lang="en">

<#include "header.ftl">

<body>
<div class="main-wrapper">
    <#--顶部状态栏-->
    <#include "header_bar.ftl">
    <#--侧边栏-->
    <#include "sidebar.ftl">
    <#--页面主体内容-->
    <div class="page-wrapper">
        <div class="content">
            <#--表格信息-->
            <div class="row">
                <div class="col-md-12">
                    <div class="table-responsive">
                        <table class="layui-hide" id="eva_table" lay-filter="eva_table"></table>
                    </div>
                </div>
            </div>
        </div>
    </div>

</div>
<div class="sidebar-overlay" data-reff=""></div>
<#include "footer.ftl">
<script type="text/html" id="toolbarDemo">
    <div class="layui-btn-container">
        <button class="layui-btn layui-btn-sm" lay-event="getCheckData">获取选中行数据</button>
        <button class="layui-btn layui-btn-sm" lay-event="getCheckLength">获取选中数目</button>
        <button class="layui-btn layui-btn-sm" lay-event="isAll">验证是否全选</button>
    </div>
</script>
<script type="text/html" id="editBar">
    <a class="layui-btn layui-btn-xs" lay-event="edit" style="color: white;">编辑</a>
    <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del" style="color: white;">删除</a>
</script>


<script type="text/html" id="edit_form">
    <div class="layui-col-md10" style="margin-left: 35px;margin-top: 20px">
        <form class="layui-form layui-form-pane" lay-filter="edit_form" action="">
            <div class="layui-form-item">
                <label class="layui-form-label">用户名</label>
                <div class="layui-input-block">
                    <input type="text" name="user_name" required  lay-verify="required" placeholder="请输入新的用户名"
                           autocomplete="off" class="layui-input">
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">邮箱</label>
                <div class="layui-input-block">
                    <input type="text" name="mail_address" required  lay-verify="required" placeholder="请输入新的邮箱"
                           autocomplete="off" class="layui-input">
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">邮件类型</label>
                <div class="layui-input-block">
                    <select name="mail_type" lay-verify="required" lay-filter="mail_type">
                        <option value="">请选择</option>
                        <option value="注册邮件">注册邮件</option>
                        <option value="报名邮件">报名邮件</option>
                    </select>
                </div>
            </div>
            <div class="layui-form-item" style="margin-top: 20px">
                <div class="layui-input-block">
                    <button class="layui-btn" lay-submit lay-filter="formDemo">立即提交</button>
                    <button type="reset" class="layui-btn layui-btn-primary">重置</button>
                </div>
            </div>

        </form>

    </div>
</script>

<script>
    layui.use('table', function(){
        var table = layui.table;

        table.render({
            elem: '#eva_table',
            url:'/admin/evaluationData',
            method:'post',
            parseData:function (res){
                // console.log(res.status)
                // console.log(res.msg)
                // console.log(res.data.total)
                // console.log(res.data.list)
                return{
                    "code":status,
                    "data":res.data.list,
                    "count":res.data.total
                };
            }
            ,toolbar: '#toolbarDemo'
            ,title: '用户数据表',
           cols: [[
                {type: 'checkbox', fixed: 'left'}
                ,{field:'evaluationId',title:'id', width:80, fixed: 'left', unresize: true, sort: true, totalRowText: '合计'}
                ,{field:'userId', title:'用户id', width:120,sort: true, edit: 'text'}
                ,{field:'userName', title:'用户名称', width:120,sort: true, edit: 'text'}
                ,{field:'foodId', title:'菜品id', width:120, sort: true,edit: 'text'}
                ,{field:'foodName', title:'菜品名称', width:120, sort: true,edit: 'text'}
                ,{field:'evaluationCategory', title:'评论分类', width:150, edit: 'text',templet: function(res){
                    if (res.evaluationCategory==1){
                        return '<b class="text-success">菜品建议</b>'
                    }else if (res.evaluationCategory == 2){
                        return '<b class="text-danger">菜品推荐</b>'
                    }else if (res.evaluationCategory == 3){
                        return '<b class="text-danger">其他建议</b>'
                    }
                }}
                ,{field:'content',sort: true, title:'具体内容', width:120}
               ,{field:'state',sort: true, title:'状态', width:100,templet: function(res){
                       if (res.state=="1"){
                           return '<a class="layui-btn layui-btn-xs layui-btn-normal" lay-event="disable" style="color: white;">'+"启用中"+'</a>'
                       }else if (res.state == "0"){
                           return '<a class="layui-btn layui-btn-xs layui-btn-danger" lay-event="enable" style="color: white;">'+"停用中"+'</a>'
                       }

                   }}
               ,{field:'likes',sort: true, title:'评分', width:120}
                ,{field:'createTime',sort: true, title:'加入时间', width:120}
                ,{field:'updateTime',sort: true, title:'修改时间', width:120}
                ,{fixed: 'right', title:'操作', toolbar: '#editBar', width:120}
            ]]
            ,page: true
        });

        //工具栏事件
        table.on('toolbar(eva_table)', function(obj){
            var checkStatus = table.checkStatus(obj.config.id);
            switch(obj.event){
                case 'getCheckData':
                    var data = checkStatus.data;
                    layer.alert(JSON.stringify(data));
                    break;
                case 'getCheckLength':
                    var data = checkStatus.data;
                    layer.msg('选中了：'+ data.length + ' 个');
                    break;
                case 'isAll':
                    layer.msg(checkStatus.isAll ? '全选': '未全选')
                    break;
            };
        });

        //监听工具条
        table.on('tool(eva_table)', function(obj){
            var data = obj.data;
            if(obj.event === 'detail'){
                layer.msg('ID：'+ data.id + ' 的查看操作');
            } else if(obj.event === 'del'){
                layer.confirm('真的删除行么', function(index){
                    console.log(data)
                    //确认按钮后发送ajax请求,评论编号
                    $.get("/admin/deleteEvaluation/"+data.evaluationId, {}, function (json) {
                        if(json.code=="10000"){
                            //删除成功刷新表格
                            table.reload('eva_table');
                            //提示操作成功
                            layui.layer.msg('数据操作成功,用户列表已刷新');
                            //关闭对话框
                            layui.layer.close(index);
                        }else{
                            //处理失败,提示错误信息
                            layui.layer.msg(json.msg);
                        }
                    }, "json");
                    obj.del();
                    layer.close(index);
                });
            } else if(obj.event === 'edit'){
                layer.open({
                    title:'修改评论信息',
                    type:1,
                    area:['420px','330px'],
                    content:$('#edit_form').html(),
                })
                //form.render()//更新渲染表单
                // form.val('edit_form',{
                //     //填充表单
                //     user_name:obj.data.meeting_register_id,
                //     mail_address:obj.data.mail_address
                // })
            }else if (obj.event == 'disable'){
                layer.confirm('真停用吗', function(index){
                    console.log(data)
                    //确认按钮后发送ajax请求,传入评论编号
                    $.get("/admin/changeEvaluation/"+data.evaluationId, {}, function (json) {
                        if(json.code=="10000"){
                            //删除成功刷新表格
                            //提示操作成功
                            layui.layer.msg('数据操作成功,用户列表已刷新');
                            //关闭对话框
                            layui.layer.close(index);
                        }else{
                            //处理失败,提示错误信息
                            layui.layer.msg(json.msg);
                        }
                        table.reload('eva_table');
                    }, "json");
                    layer.close(index);

                });

            }else if(obj.event == 'enable') {
                layer.confirm('真启用吗', function (index) {
                    console.log(data)
                    //确认按钮后发送ajax请求,包含图书编号
                    $.get("/admin/changeEvaluation/" + data.evaluationId, {}, function (json) {
                        if (json.code == "10000") {
                            //删除成功刷新表格
                            //提示操作成功
                            layui.layer.msg('数据操作成功,用户列表已刷新');
                            //关闭对话框
                            layui.layer.close(index);
                        } else {
                            //处理失败,提示错误信息
                            layui.layer.msg(json.msg);
                        }
                        table.reload('eva_table');
                    }, "json");
                    layer.close(index);

                });

            }

        });

    });
    function myrefresh(){
        window.location.reload();
    }
    //setTimeout('myrefresh()',1000); //指定1秒刷新一次
</script>
</body>
</html>