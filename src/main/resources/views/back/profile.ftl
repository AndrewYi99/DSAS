<!DOCTYPE html>
<html lang="en">

<#--引入统一头部-->
<#include "header.ftl">
<style>
    #headImg{
        width: 35px;
        height: 35px;
        border-radius: 50%;
        border: 3px solid #eee;
    }
    .profile-img{
        height: 150px;
        width: 150px;
    }
</style>
<body>
<div class="main-wrapper">
    <#--顶部状态栏-->
    <#include "header_bar.ftl">
    <#--侧边栏-->
    <#include "sidebar.ftl">
    <#--页面主体-->
    <div class="page-wrapper">
        <div class="content">
            <div class="row">
                <div class="col-sm-7 col-6">
                    <h4 class="page-title"><b style="color: lightskyblue">欢迎!</b>&nbsp;${currentAdmin.nickname}</h4>
                </div>

                <div class="col-sm-5 col-6 text-right m-b-30">
                    <a href="/admin/showEditProfile" class="btn btn-primary btn-rounded"><i class="fa fa-plus"></i>
                        编辑个人信息</a>
                </div>
            </div>
            <div class="card-box profile-header">
                <div class="row">
                    <div class="col-md-12">
                        <div class="profile-view">
                            <#--个人基本信息-->
                            <div class="profile-basic">
                                <div class="row">
                                    <div class="col-md-3">
                                        <div class="profile-info-left">
                                            <img src="${currentAdmin.avatar}" width="150px" height="150px">
                                            <h3 class="user-name m-t-0 mb-0">${currentAdmin.username}</h3>
                                            <div class="staff-id">注册时间:<br>
                                                ${currentAdmin.createTime?string("yyyy-MM-dd HH:MM:ss")}
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-md-9">
                                        <div class="card">
                                            <div class="card-body">
                                                <div class="chart-title">
                                                    <h5>操作记录</h5>
                                                </div>
                                                <div id="logs" style="width: 100%;height: 200px;display: block"></div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="profile-tabs" style="margin-top: 10px">
                <ul class="nav nav-tabs nav-tabs-bottom">
                    <li class="nav-item"><a class="nav-link active" href="#about-cont" data-toggle="tab">操作日志</a></li>
                </ul>

                <div class="tab-content">
                    <div class="tab-pane show active" id="about-cont">
                        <div class="row">
                            <div class="col-md-12">
                                <table class="layui-hide" id="table_log" lay-filter="table_log"></table>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<#--引入统一底部-->
<#include "footer.ftl">
<script>
    layui.use('table', function(){
        var table = layui.table;

        table.render({
            elem: '#table_log'
            ,url:'/admin/getAdminProfile'
            ,method:'POST'
            ,toolbar: true
            ,title: '管理员操作日志表'
            ,totalRow: true
            ,cols: [[
                {field:'id', title:'ID', width:80, fixed: 'left', unresize: true, sort: true, totalRowText: '合计行'}
                ,{field:'username', title:'用户名', width:120}
                ,{field:'ip', title:'Ip地址', width:150}
                ,{field:'type', title:'操作类型', width:100, sort: true}
                ,{field:'description', title:'操作描述', width:80}
                ,{field:'model', title:'操作模块', width:100, sort: true}
                ,{field:'operationTime', title:'操作时间'}
                ,{field:'result', title:'操作结果', width:100}
            ]]
            ,page: true
            ,response: {
                statusCode: 10000 //重新规定成功的状态码为 200，table 组件默认为 0
            },
            parseData:function (res){
                // console.log(res.status)
                // console.log(res.msg)
                // console.log(res.data.total)
                // console.log(res.data.list)
                return{
                    "code":res.status,
                    "data":res.data.list,
                    "count":res.data.total
                };
            }
        });
    });

</script>
<script>
    let userId = ${currentAdmin.id}
    let keys1 = new Array();
    let values1 = new Array();

    $(document).ready(function () {
        $.ajax({
            url:"/admin/logEchart",
            type: "POST",
            dataType: "json",
            data:{
                id:userId
            },
            success: function (res) {
                for (let key in res.data) {
                    keys1.push(key)
                    values1.push(res.data[key])
                }
                if (res.status == 10000) {
                    echart_logs(keys1, values1)
                }
            }
        })
    })

    function echart_logs(keys, values) {
        let myChart = echarts.init(document.getElementById('logs'));
        let option = {
            // Make gradient line here
            visualMap: [
                {
                    show: true,
                    type: 'continuous',
                    seriesIndex: 0,
                    min: 0,
                    max: 100,
                    orient: 'horizontal',
                    top:-10,
                    left:'center',
                    calculable:true,                        //是否显示拖拽用的手柄（手柄能拖拽调整选中范围）
                    realtime:true,
                    itemWidth:20,                           //图形的宽度，即长条的宽度。
                    itemHeight:100,
                    text:['High', 'Low'],
                }
            ],
            tooltip: {
                trigger: 'axis'
            },
            xAxis: [
                {
                    data: keys,
                    axisTick: {
                        alignWithLabel: true
                    },
                }
            ],
            yAxis: [
                {
                    type: 'value'
                },

            ],
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
                    type: 'line',
                    showSymbol: true,
                    data: values,
                    smooth: true
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