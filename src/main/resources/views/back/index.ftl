<!DOCTYPE html>
<html lang="en">
<#include "header.ftl">
<style>
    #headImg {
        width: 35px;
        height: 35px;
        border-radius: 50%;
        border: 3px solid #eee;
    }

    #visited, #comment,#score,#Funnel {
        display: block;
        width: 100%;
        height: 300px;
    }

</style>
<body>
<div class="main-wrapper">
    <#--顶部状态栏-->
    <#include "header_bar.ftl">
    <#--侧边栏-->
    <#include "sidebar.ftl">
    <#--首页图标-->
    <div class="page-wrapper">
        <div class="content">
            <div class="row">
                <#--用户数量-->
                <div class="col-md-6 col-sm-6 col-lg-6 col-xl-3">
                    <div class="dash-widget">
                        <#---->
                        <span class="dash-widget-bg1"><i class="fa fa-user-o" aria-hidden="true"></i></span>
                        <div class="dash-widget-info text-right">
                            <h3>${responseIndexInfo.totalUsers}</h3>
                            <span class="widget-title1">用户数量 <i class="fa fa-check" aria-hidden="true"></i></span>
                        </div>
                    </div>
                </div>
                <#--总评价数-->
                <div class="col-md-6 col-sm-6 col-lg-6 col-xl-3">
                    <div class="dash-widget">
                        <span class="dash-widget-bg2"><i class="fa fa-comments"></i></span>
                        <div class="dash-widget-info text-right">
                            <h3>${responseIndexInfo.totalEvaluations}</h3>
                            <span class="widget-title2">总评价数量 <i class="fa fa-check" aria-hidden="true"></i></span>
                        </div>
                    </div>
                </div>
                <div class="col-md-6 col-sm-6 col-lg-6 col-xl-3">
                    <div class="dash-widget">
                        <span class="dash-widget-bg3"><i class="fa fa-coffee" aria-hidden="true"></i></span>
                        <div class="dash-widget-info text-right">
                            <h3>${responseIndexInfo.totalFoods}</h3>
                            <span class="widget-title3">菜品数量 <i class="fa fa-check" aria-hidden="true"></i></span>
                        </div>
                    </div>
                </div>
                <div class="col-md-6 col-sm-6 col-lg-6 col-xl-3">
                    <div class="dash-widget">
                        <span class="dash-widget-bg4"><i class="fa fa-heartbeat" aria-hidden="true"></i></span>
                        <div class="dash-widget-info text-right">
                            <h3>1</h3>
                            <span class="widget-title4">网站在线人数 <i class="fa fa-check" aria-hidden="true"></i></span>
                        </div>
                    </div>
                </div>
            </div>

            <#--图表信息-->
            <div class="row">
                <div class="col-12 col-md-6 col-lg-6 col-xl-6">
                    <div class="card">
                        <div class="card-body">
                            <div class="chart-title">
                                <h4>用户活跃统计</h4>
                                <#--<span class="float-right"><i class="fa fa-caret-up" aria-hidden="true"></i> 15% Higher than Last Month</span>-->
                            </div>
                            <div id="visited"></div>
                        </div>
                    </div>
                </div>
                <div class="col-12 col-md-6 col-lg-6 col-xl-6">
                    <div class="card">
                        <div class="card-body">
                            <div class="chart-title">
                                <h4>评价统计</h4>
                            </div>
                            <div id="comment"></div>
                        </div>
                    </div>
                </div>
                <div class="col-12 col-md-6 col-lg-6 col-xl-6">
                    <div class="card">
                        <div class="card-body">
                            <div class="chart-title">
                                <h4>菜品评分统计</h4>
                            </div>
                            <div id="score"></div>
                        </div>
                    </div>
                </div>
                <div class="col-12 col-md-6 col-lg-6 col-xl-6">
                    <div class="card">
                        <div class="card-body">
                            <div class="chart-title">
                                <h4>评论分布</h4>
                            </div>
                            <div id="Funnel"></div>
                        </div>
                    </div>
                </div>
            </div>
            <#--列表信息1-->
            <div class="row">

            </div>

        </div>
        <#--信息侧边栏-->
        <div class="notification-box">
            <div class="msg-sidebar notifications msg-noti">
                <div class="topnav-dropdown-header">
                    <span>Messages</span>
                </div>
                <div class="drop-scroll msg-list-scroll" id="msg_list">
                    <ul class="list-box">
                        <li>
                            <a href="chat.html">
                                <div class="list-item">
                                    <div class="list-left">
                                        <span class="avatar">R</span>
                                    </div>
                                    <div class="list-body">
                                        <span class="message-author">Richard Miles </span>
                                        <span class="message-time">12:28 AM</span>
                                        <div class="clearfix"></div>
                                        <span class="message-content">Lorem ipsum dolor sit amet, consectetur adipiscing</span>
                                    </div>
                                </div>
                            </a>
                        </li>
                    </ul>
                </div>
                <div class="topnav-dropdown-footer">
                    <a href="chat.html">See all messages</a>
                </div>
            </div>
        </div>
    </div>
</div>

<#include "footer.ftl">
<script src="/assets/js/chart.js"></script>
<script src="/assets/js/echarts.js"></script>
<script type="text/javascript">
    let keys1 = new Array();
    let values1 = new Array();
    let echarts2Data = new Array();
    let echarts3Data = new Array();

    let keys2 = new Array();
    let values2 = new Array();

    $(document).ready(function () {
        $.ajax({
            url: "/admin/showChartsInfo",
            type: "POST",
            dataType: "json",
            success: function (res) {

                for (let key in res.data) {
                    keys1.push(key)
                    values1.push(res.data[key])
                }
                if (res.status == 10000) {
                    echart01(keys1, values1)
                }

            }
        })

        $.ajax({
            url:"/admin/EvalautionEcharts2",
            type: "POST",
            dataType: "json",
            success: function (res) {
                for (let key in res.data) {
                    echarts2Data.push(
                        {
                            value:res.data[key],name:key
                        }
                    )
                    //{value: 1048, name: 'Search Engine'},
                    // console.log(key)
                    // console.log(typeof key)
                    // console.log(res.data[key])
                    //console.log(res.data.data[key])
                }
                console.log(echarts2Data)
                // console.log(keys);
                // console.log(valus);
                if (res.status == 10000) {
                    echart02(echarts2Data)
                }
            }
        })

        $.ajax({
            url:"/admin/EvalautionEcharts3",
            type: "POST",
            dataType: "json",
            success: function (res) {
               // console.log(res.data)
                let len = res.data.length;
                //console.log(len)
                for (let i=0;i<len;i++){
                    echarts3Data.push([
                        res.data[i].foodName,res.data[i].avgScore.toFixed(2),res.data[i].totalCommentUser
                    ])
                    keys2.push(res.data[i].foodName)
                    values2.push({
                        value:res.data[i].totalCommentUser,name:res.data[i].foodName
                    })
                }
                console.log(echarts3Data)
                // console.log(keys);
                // console.log(valus);
                if (res.status == 10000) {
                    echart03(echarts3Data)
                    echart04(keys2,values2)
                }
            }
        })

    })

    function echart01(keys, values) {
        let myChart = echarts.init(document.getElementById('visited'));
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

    function echart02(data) {
        let myChart = echarts.init(document.getElementById('comment'));
        let option = {
            tooltip: {
                trigger: 'item'
            },
            legend: {
                orient: 'vertical',
                left: 'left'
            },
            grid: {
                left: '0%',
                right: '0%',
                bottom: '0%',
                top: '10%',
                containLabel: true
            },
            series: [
                {
                    name: 'Access From',
                    type: 'pie',
                    radius: '50%',
                    data: echarts2Data,
                    emphasis: {
                        itemStyle: {
                            shadowBlur: 10,
                            shadowOffsetX: 0,
                            shadowColor: 'rgba(0, 0, 0, 0.5)'
                        }
                    }
                }
            ]
        };
        myChart.setOption(option);
        $(window).resize(function () {
            myChart.resize();
        })
    }

    function echart03(data){
        let myChart = echarts.init(document.getElementById('score'));
        let option = {
            dataset: [
                {
                    dimensions: ['foodName', 'avgScore', 'totalCommentUser'],
                    source: data
                },
                {
                    transform: {
                        type: 'sort',
                        config: { dimension: 'avgScore', order: 'desc' }
                    }
                }
            ],
            xAxis: {
                type: 'category',
                axisLabel: { interval: 0, rotate: 30 },
                axisTick: {
                    alignWithLabel: true
                },
            },
            yAxis: {},
            series: {
                type: 'bar',
                encode: { x: 'foodName', y: 'avgScore' },
                datasetIndex: 1,
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
        };
        myChart.setOption(option);
        $(window).resize(function () {
            myChart.resize();
        })
    }

    function echart04(keys,values){
        let myChart = echarts.init(document.getElementById('Funnel'));
        let option = {
            tooltip: {
                trigger: 'item',
                formatter: '{a} <br/>{b} : {c}次'
            },grid: {
                left: '0%',
                right: '0%',
                bottom: '0%',
                top: '10%',
                containLabel: true
            },
            legend: {
                data: keys
            },
            series: [
                {
                    name: 'Funnel',
                    type: 'funnel',
                    left: '10%',
                    top: 50,
                    bottom: 0,
                    width: '80%',
                    min: 0,
                    max: 30,
                    minSize: '1%',
                    maxSize: '100%',
                    sort: 'descending',
                    gap: 2,
                    label: {
                        show: true,
                        position: 'inside'
                    },
                    labelLine: {
                        length: 10,
                        lineStyle: {
                            width: 1,
                            type: 'solid'
                        }
                    },
                    itemStyle: {
                        borderColor: '#fff',
                        borderWidth: 1
                    },
                    emphasis: {
                        label: {
                            fontSize: 20
                        }
                    },
                    data: values
                }
            ]
        };
        myChart.setOption(option);
        $(window).resize(function () {
            myChart.resize();
        })
    }

    /*日期格式化函数*/
    Date.prototype.format = function (fmt) {
        let o = {
            "M+": this.getMonth() + 1,                 //月份
            "d+": this.getDate(),                    //日
            "h+": this.getHours(),                   //小时
            "m+": this.getMinutes(),                 //分
            "s+": this.getSeconds(),                 //秒
            "q+": Math.floor((this.getMonth() + 3) / 3), //季度
            "S": this.getMilliseconds()             //毫秒
        };
        if (/(y+)/.test(fmt)) {
            fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
        }
        for (var k in o) {
            if (new RegExp("(" + k + ")").test(fmt)) {
                fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
            }
        }
        return fmt;
    }
</script>
</body>
</html>