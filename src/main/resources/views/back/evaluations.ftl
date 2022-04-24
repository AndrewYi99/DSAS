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
                <div class="col-12 col-md-6 col-lg-6 col-xl-6">
                    <div class="card">
                        <div class="card-body">
                            <div class="chart-title">
                                <h4>评论启用状态</h4>
                            </div>
                            <div id="eva_status" style="width: 100%;height: 200px;display: block"></div>
                        </div>
                    </div>
                </div>
                <div class="col-12 col-md-6 col-lg-6 col-xl-6">
                    <div class="card">
                        <div class="card-body">
                            <div class="chart-title">
                                <h5>评论分布统计</h5>
                            </div>
                            <div id="eva_category" style="width: 100%;height: 200px;display: block"></div>
                        </div>
                    </div>
                </div>
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
    <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del" style="color: white;">删除</a>
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
<script>
    let echartsEvaData = new Array();
    let keys1 = new Array();
    let values1 = new Array();
    let keys2 = new Array();
    let values2 = new Array();

    $(document).ready(function () {
        $.ajax({
            url:"/admin/EvaluationEcharts4",
            type: "POST",
            dataType: "json",
            success: function (res) {
                for (let key in res.data) {
                    echartsEvaData.push(
                        {
                            value:res.data[key],name:key
                        }
                    )
                }
                console.log(echartsEvaData)
                if (res.status == 10000) {
                    echart_Eva_status(echartsEvaData)
                }
            }
        })
        $.ajax({
            url: "/admin/EvalautionEcharts5",
            type: "POST",
            dataType: "json",
            success: function (res) {
                for (let key in res.data) {
                    keys1.push(key)
                    values1.push(res.data[key])
                }

                if (res.status == 10000) {
                    echart_Eva_total(keys1, values1)
                }

            }
        })
    })

    function echart_Eva_status(data) {
        let myChart = echarts.init(document.getElementById('eva_status'));
        let option = {
            tooltip: {
                trigger: 'item'
            },
            legend: {
                top: '0%',
                orient: 'vertical',
                x:'left',
                y:'center',
            },
            color:['#55ce63','#FF5722' ],
            series: [
                {
                    name: '评论状态',
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
    function echart_Eva_total(keys,values) {
        let myChart = echarts.init(document.getElementById('eva_category'));
        let option ={
            xAxis: {
                type: 'category',
                data: keys,
                axisLabel: { interval: 0, rotate: 30 },
                axisTick: {
                    alignWithLabel: true
                },
            },
            yAxis: {
                type: 'value'
            },
            grid: [
                {
                    left: '0%',
                    right: '0%',
                    bottom: '0%',
                    top: '10%',
                    containLabel: true
                }
            ],
            series: [
                {
                    name: '评论次数',
                    data: values,
                    type: 'line',
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