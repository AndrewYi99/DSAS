<!DOCTYPE html>
<html lang="en">

<#include "header.ftl">

<body class="mini-sidebar">
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
                                <h5>热门菜品评论分布统计</h5>
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
    <div class="layui-inline">
        <button class="layui-btn layui-btn-sm layui-btn-danger" lay-event="delAllSelect">删除选中</button>
        <button class="layui-btn layui-btn-sm layui-btn-warm" lay-event="stopAllSelect">批量禁用</button>
        <button class="layui-btn layui-btn-sm layui-btn-success" lay-event="startAllSelect">批量启用</button>
    </div>
    <div class="layui-inline">
        <div class="layui-inline">
            <input class="layui-input" type="text" placeholder="评论目标/内容" autocomplete="off" name="keyword" id="keyword">
        </div>
        <button class="layui-btn" lay-submit="" lay-filer="searchEva" id="searchEva" data-type="reload">
            <i class="layui-icon">&#xe615;</i>
        </button>
        <button type="reset" class="layui-btn layui-btn-primary" id="resetBtn" lay-event="reset">重置</button>
    </div>
</script>
<#--<script type="text/html" id="toolbarDemo">-->
<#--    <div class="layui-btn-container">-->
<#--        <button class="layui-btn layui-btn-sm" lay-event="getCheckData">获取选中行数据</button>-->
<#--        <button class="layui-btn layui-btn-sm" lay-event="getCheckLength">获取选中数目</button>-->
<#--        <button class="layui-btn layui-btn-sm" lay-event="isAll">验证是否全选</button>-->
<#--    </div>-->
<#--</script>-->
<script type="text/html" id="editBar">
    <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del" style="color: white;">删除</a>
</script>


<script>
    layui.use('table', function(){
        var table = layui.table;

        let evaTable =  table.render({
            elem: '#eva_table',
            url:'/admin/evaluationData',
            method:'post',
            cellMinWidth: 120,//指定最小宽度，自适应
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
                ,{field:'userId', title:'用户id', sort: true}
                ,{field:'userName', title:'用户名称',sort: true}
                ,{field:'foodName', title:'评论目标',sort: true,templet: function(res){
                    if (res.foodName == "非菜品"){
                        return '<b class="text-danger">非菜品</b>'
                    }else {
                        return res.foodName;
                    }
                   }}
                ,{field:'evaluationCategory', title:'评论分类',sort: true, templet: function(res){
                    if (res.evaluationCategory==1){
                        return '<b class="text-success">菜品评价</b>'
                    }else if (res.evaluationCategory == 2){
                        return '<b class="text-danger">菜品推荐</b>'
                    }else if (res.evaluationCategory == 3){
                        return '<b class="text-danger">其他建议</b>'
                    }
                }}
                ,{field:'content',sort: true, title:'具体内容'}
               ,{field:'state',sort: true, title:'状态', templet: function(res){
                       if (res.state=="1"){
                           return '<a class="layui-btn layui-btn-xs layui-btn-normal" lay-event="disable" style="color: white;">'+"启用中"+'</a>'
                       }else if (res.state == "0"){
                           return '<a class="layui-btn layui-btn-xs layui-btn-danger" lay-event="enable" style="color: white;">'+"停用中"+'</a>'
                       }

                   }}
               ,{field:'likes',sort: true, title:'评分'}
                ,{field:'createTime',sort: true, title:'加入时间'}
                ,{field:'updateTime',sort: true, title:'修改时间'}
                ,{fixed: 'right', title:'操作', toolbar: '#editBar'}
            ]]
            ,page: true
        });

        //工具栏事件
        table.on('toolbar(eva_table)', function(obj){
            var checkStatus = table.checkStatus(obj.config.id);
            //用于存储待删除的评论
            let arrForDel = [];
            //用于提示不能删除的评论
            let CantDel = [];
            let arrForChange = [];
            //存储不能修改状态的评论
            let CantChange = [];
            switch(obj.event){
                case 'reset':
                    window.parent.location.reload();
                    break;
                //批量删除
                case 'delAllSelect':
                    layer.confirm("确定删除选中的评论吗？",{icon:3,title:'询问'},function (index) {
                        layer.close(index);
                        if (checkStatus.data.length !=0){
                            //遍历选中的数据数组，获取其中的id值，存到一个指定数组中
                            for (let i = 0;i<checkStatus.data.length;i++){
                                arrForDel.push(checkStatus.data[i].evaluationId)
                                if (checkStatus.data[i].state ==1){
                                    CantDel.push(checkStatus.data[i].foodName)
                                }
                            }
                            if (CantDel.length !=0){
                                layer.alert("评论："+CantDel+"处于启用状态,不能删除",{icon:5,title:'提示'})
                                CantDel = [];
                                return false;
                            }
                            //ajax是不能向后台发送数组数据的，所以需要对数据进行转换，可以转换成字符串
                            let idsString = arrForDel.join(",");
                            //待删除的id数组
                            console.log(idsString);
                            //return false;
                            $.ajax({
                                url:"/admin/changEvaluationsByIds",
                                method:"POST",
                                dataType:"JSON",
                                data:{"ids":idsString,"type":"del"},
                                success:function (res) {
                                    arrForDel = [];
                                    console.log(res)
                                    if (res.msg=="SUCCESS"){
                                        console.log(res.data)
                                        layer.alert("成功删除"+res.data+"条数据",{icon:6,title:'提示'})
                                        //window.parent.location.reload();
                                        evaTable.reload({url:'/admin/evaluationData'})
                                    }else {
                                        layer.alert(res.msg,{icon:5,title:'提示'})
                                    }
                                }
                            })
                        }else {
                            layer.alert("请选中需要删除的数据",{icon:0,title:'提示'})
                        }
                    });
                    break;
                //批量禁用
                case 'stopAllSelect':
                    layer.confirm("确定禁用选中的评论吗？",{icon:3,title:'询问'},function (index) {
                        layer.close(index);
                        if (checkStatus.data.length !=0){
                            //遍历选中的数据数组，获取其中的id值，存到一个指定数组中
                            for (let i = 0;i<checkStatus.data.length;i++){
                                arrForChange.push(checkStatus.data[i].evaluationId)
                                if (checkStatus.data[i].state ==0){
                                    //已经为禁用状态不能再进行禁用操作
                                    CantChange.push(checkStatus.data[i].foodName)
                                }
                            }
                            if (CantChange.length !=0){
                                layer.alert("评论："+CantChange+"已经禁用,不能再进行禁用操作",{icon:5,title:'提示'})
                                CantChange = [];
                                return false;
                            }
                            //ajax是不能向后台发送数组数据的，所以需要对数据进行转换，可以转换成字符串
                            let idsString = arrForChange.join(",");
                            //待禁用的id数组
                            console.log(idsString);
                            //return false;
                            $.ajax({
                                url:"/admin/changEvaluationsByIds",
                                method:"POST",
                                dataType:"JSON",
                                data:{"ids":idsString,"type":"stop"},
                                success:function (res) {
                                    arrForChange = [];
                                    console.log(res)
                                    if (res.msg=="SUCCESS"){
                                        console.log(res.data)
                                        layer.alert("成功禁用"+res.data+"条数据",{icon:6,title:'提示'},function () {
                                            window.parent.location.reload();
                                        })
                                        //evaTable.reload({url:'/admin/evaluationData'})
                                    }else {
                                        layer.alert(res.msg,{icon:5,title:'提示'})
                                    }
                                }
                            })
                        }else {
                            layer.alert("请选中需要禁用的评论",{icon:0,title:'提示'})
                        }
                    });
                    break;
                //批量启用
                case 'startAllSelect':
                    layer.confirm("确定启用选中的评论吗？",{icon:3,title:'询问'},function (index) {
                        layer.close(index);
                        if (checkStatus.data.length !=0){
                            //遍历选中的数据数组，获取其中的id值，存到一个指定数组中
                            for (let i = 0;i<checkStatus.data.length;i++){
                                arrForChange.push(checkStatus.data[i].evaluationId)
                                if (checkStatus.data[i].state ==1){
                                    //已经为启用状态不能再进行启用操作
                                    CantChange.push(checkStatus.data[i].foodName)
                                }
                            }
                            if (CantChange.length !=0){
                                layer.alert("评论："+CantChange+"已经启用,不能再进行启用操作",{icon:5,title:'提示'})
                                CantChange = [];
                                return false;
                            }
                            //ajax是不能向后台发送数组数据的，所以需要对数据进行转换，可以转换成字符串
                            let idsString = arrForChange.join(",");
                            //待启用的id数组
                            console.log(idsString);
                            //return false;
                            $.ajax({
                                url:"/admin/changEvaluationsByIds",
                                method:"POST",
                                dataType:"JSON",
                                data:{"ids":idsString,"type":"start"},
                                success:function (res) {
                                    console.log(res)
                                    if (res.msg=="SUCCESS"){
                                        console.log(res.data)
                                        layer.alert("成功启用"+res.data+"条数据",{icon:6,title:'提示'},function () {
                                            window.parent.location.reload();
                                        })
                                        //evaTable.reload({url:'/admin/evaluationData'})
                                    }else {
                                        layer.alert(res.msg,{icon:5,title:'提示'})
                                    }
                                }
                            })
                        }else {
                            layer.alert("请选中需要启用的评论",{icon:0,title:'提示'})
                        }
                    });
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
        //搜索功能
        $('#searchEva').click(function () {
            var keyword = $('#keyword').val();
            console.log(keyword)
            if($('#keyword').val()==""){
                layer.msg('查询内容不能为空');
                return false;
            }
            table.reload('eva_table', {
                url: '/admin/evaluationData',
                methods:"post"
                ,request: {
                    pageName: 'page'
                    ,limitName: 'limit'
                }
                ,where: {
                    keyword: keyword
                }
                ,page: {
                    curr: 1
                }
            });
            return false;
        })
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