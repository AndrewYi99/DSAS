﻿<!DOCTYPE html>
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
                <div class="col-12 col-md-6 col-lg-6 col-xl-6">
                    <div class="card">
                        <div class="card-body">
                            <div class="chart-title">
                                <h4>菜品启用状态</h4>
                            </div>
                            <div id="food_status" style="width: 100%;height: 200px;display: block"></div>
                        </div>
                    </div>
                </div>
                <div class="col-12 col-md-6 col-lg-6 col-xl-6">
                    <div class="card">
                        <div class="card-body">
                            <div class="chart-title">
                                <h5>菜品评分分布</h5>
                            </div>
                            <div id="food_likes" style="width: 100%;height: 200px;display: block"></div>
                        </div>
                    </div>
                </div>
                <div class="col-md-12">
                    <div class="table-responsive">
                        <table class="layui-hide" id="food_table" lay-filter="food_table"></table>
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
                <label class="layui-form-label">菜品id</label>
                <div class="layui-input-block">
                    <input type="text" name="food_id" id="food_id"  value="" required disabled  autocomplete="off" class="layui-input">
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">菜品名</label>
                <div class="layui-input-block">
                    <input type="text" name="food_name" required  lay-verify="required" placeholder="请输入新的菜品名"
                           autocomplete="off" class="layui-input">
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">菜品描述</label>
                <div class="layui-input-block">
                    <input type="text" name="food_description" required  lay-verify="required" placeholder="请输入新的描述内容"
                           autocomplete="off" class="layui-input">
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">推荐菜品</label>
                <div class="layui-input-block">
                    <select name="food_isRecommend" lay-verify="required" lay-filter="food_isRecommend">
                        <option value="">请选择</option>
                        <option value="1">是</option>
                        <option value="0">否</option>
                    </select>
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">今日菜品</label>
                <div class="layui-input-block">
                    <select name="food_isTodayFood" lay-verify="required" lay-filter="food_isTodayFood">
                        <option value="">请选择</option>
                        <option value="1">是</option>
                        <option value="0">否</option>
                    </select>
                </div>
            </div>
            <div class="layui-form-item" style="margin-top: 20px">
                <div class="layui-input-block">
                    <button class="layui-btn" lay-submit lay-filter="submitForm">立即提交</button>
                    <button type="reset" class="layui-btn layui-btn-primary">重置</button>
                </div>
            </div>

        </form>

    </div>
</script>

<script>
    layui.use('table', function(){
        var table = layui.table;
        var form = layui.form;
        table.render({
            elem: '#food_table',
            url:'/admin/foodData',
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
                ,{field:'id',title:'id', width:80, fixed: 'left', unresize: true, sort: true, totalRowText: '合计'}
                ,{field:'foodName', title:'菜品名称', width:120,sort: true}
                ,{field:'foodDescription', title:'菜品描述', width:120, sort: true}
                ,{field:'isRecommend', title:'是否为推荐菜品', width:150, templet: function(res){
                    if (res.isRecommend==1){
                        return '<b class="text-success">是</b>'
                    }else if (res.isRecommend == 0){
                        return '<b class="text-danger">否</b>'
                    }
                }}
               ,{field:'status',sort: true, title:'状态', width:100,templet: function(res){
                       if (res.status==1){
                           return '<a class="layui-btn layui-btn-xs layui-btn-normal" lay-event="disable" style="color: white;">'+"启用中"+'</a>'
                       }else if (res.status == 0){
                           return '<a class="layui-btn layui-btn-xs layui-btn-danger" lay-event="enable" style="color: white;">'+"停用中"+'</a>'
                       }

                   }}
               ,{field:'isTodayFood',sort: true, title:'是否为今日菜品', width:120, templet: function(res){
                       if (res.isTodayFood==1){
                           return '<b class="text-success">是</b>'
                       }else if (res.isTodayFood == 0){
                           return '<b class="text-danger">否</b>'
                       }
                   }}
                ,{field:'createTime',sort: true, title:'加入时间', width:120}
                ,{field:'updateTime',sort: true, title:'修改时间', width:120}
                ,{fixed: 'right', title:'操作', toolbar: '#editBar', width:120}
            ]]
            ,page: true
        });

        //工具栏事件
        table.on('toolbar(food_table)', function(obj){
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
        table.on('tool(food_table)', function(obj){
            var data = obj.data;
            if(obj.event === 'detail'){
                layer.msg('ID：'+ data.id + ' 的查看操作');
            } else if(obj.event === 'del'){
                layer.confirm('真的删除行么', function(index){
                    console.log(data)
                    //确认按钮后发送ajax请求,评论编号
                    $.get("/admin/deleteFood/"+data.id, {}, function (json) {
                        if(json.code=="10000"){
                            //删除成功刷新表格
                            table.reload('food_table');
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
                    title:'修改菜品信息',
                    type:1,
                    area:['550px','500px'],
                    content:$('#edit_form').html(),
                })
                form.render()//更新渲染表单
                form.val('edit_form',{
                    //填充表单
                    food_id:data.id,
                    food_name:data.foodName,
                    food_description:data.foodDescription
                })
            }else if (obj.event == 'disable'){
                layer.confirm('真停用吗', function(index){
                    console.log(data)
                    //确认按钮后发送ajax请求,传入评论编号
                    $.get("/admin/changeFood/"+data.id,function (json) {
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
                        table.reload('food_table');
                    }, "json");
                    layer.close(index);

                });
            }else if(obj.event == 'enable') {
                layer.confirm('真启用吗', function (index) {
                    console.log(data)
                    //确认按钮后发送ajax请求,包含图书编号
                    $.get("/admin/changeFood/" + data.id,function (json) {
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
                        table.reload('food_table');
                    }, "json");
                    layer.close(index);
                });
            }
            //提交编辑更新
            form.on('submit(submitForm)',function (data){
                let foodData = {
                    id:data.field.food_id,
                    foodName:data.field.food_name,
                    foodDescription:data.field.food_description,
                    isRecommend:data.field.food_isRecommend,
                    isTodayFood:data.field.food_isTodayFood
                }
                //console.log(data.field)
                //alert("提交更新")
                //return false;
                $.ajax({
                    url:"/admin/foodUpdate",
                    dataType:"json",
                    method: "POST",
                    contentType:'application/json;charset=utf-8',
                    data:JSON.stringify(foodData),
                    success:function (res) {
                        if (res.status==1000){
                            layui.layer.alert(res.msg)
                        }
                    }
                })
            });
        });

    });
    function myrefresh(){
        window.location.reload();
    }
    //setTimeout('myrefresh()',1000); //指定1秒刷新一次
</script>
<script>
    let echartsFoodData = new Array();
    let keys1 = new Array();
    let values1 = new Array();
    let keys2 = new Array();
    let values2 = new Array();
    let echartsFoodData02 = new Array();

    $(document).ready(function () {
        $.ajax({
            url:"/admin/foodStatus",
            type: "POST",
            dataType: "json",
            success: function (res) {
                for (let key in res.data) {
                    echartsFoodData.push(
                        {
                            value:res.data[key],name:key
                        }
                    )
                }
                console.log(echartsFoodData)
                if (res.status == 10000) {
                    echart_food_status(echartsFoodData)
                }
            }
        })
        $.ajax({
            url: "/admin/EvalautionEcharts3",
            type: "POST",
            dataType: "json",
            success: function (res) {
                let len = res.data.length;
                for (let i=0;i<len;i++){
                    keys2.push(res.data[i].foodName)
                    values2.push(res.data[i].avgScore.toFixed(2))
                }
                if (res.status == 10000) {
                    echart_food_likes(keys2, values2)
                }

            }
        })
    })

    function echart_food_status(data) {
        let myChart = echarts.init(document.getElementById('food_status'));
        let option = {
            tooltip: {
                trigger: 'item'
            },
            legend: {
                top: '5%',
                left: 'center'
            },
            series: [
                {
                    name: 'Access From',
                    type: 'pie',
                    radius: ['40%', '70%'],
                    avoidLabelOverlap: false,
                    itemStyle: {
                        borderRadius: 10,
                        borderColor: '#fff',
                        borderWidth: 2
                    },
                    label: {
                        show: true,
                        position: 'center'
                    },
                    emphasis: {
                        label: {
                            show: true,
                            fontSize: '20',
                            fontWeight: 'bold'
                        }
                    },
                    labelLine: {
                        show: false
                    },
                    data: data
                }
            ]
        };
        myChart.setOption(option);
        $(window).resize(function () {
            myChart.resize();
        })
    }
    function echart_food_likes(keys,values) {
        let myChart = echarts.init(document.getElementById('food_likes'));
        let option = {
            tooltip: {
                trigger: 'axis',
                axisPointer: {
                    type: 'shadow'
                }
            },
            grid: {
                left: '0%',
                right: '0%',
                bottom: '0%',
                containLabel: true
            },
            xAxis: [
                {
                    type: 'category',
                    data: keys,
                    axisTick: {
                        alignWithLabel: true
                    },axisLabel: {
                        interval: 0, rotate: 35,fontSize:10
                    }

                }
            ],
            yAxis: [
                {
                    type: 'value'
                }
            ],
            series: [
                {
                    name: 'Direct',
                    type: 'bar',
                    barWidth: '60%',
                    data: values,
                    itemStyle: {
                        normal: {
                            label: {
                                show: true, //开启显示
                                position: 'top', //在上方显示
                                textStyle: { //数值样式
                                    color: 'black',
                                    fontSize: 10
                                }
                            }
                        }
                    },
                }
            ]
        };
        myChart.setOption(option);
        $(window).resize(function () {
            myChart.resize();
        })
    }
</script>
</body>
</html>