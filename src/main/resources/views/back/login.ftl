<!DOCTYPE html>
<html lang="en">

<#--<head>-->
<#--    <meta charset="utf-8">-->
<#--    <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0">-->
<#--    <link rel="icon" type="image/png" sizes="64x64" href="/images/favicon.png"/>-->
<#--    <title>高校食堂用餐评价系统后台</title>-->
<#--    <link rel="stylesheet" type="text/css" href="/assets/css/bootstrap.min.css">-->
<#--    <link rel="stylesheet" type="text/css" href="/assets/css/font-awesome.min.css">-->
<#--    <link rel="stylesheet" type="text/css" href="/assets/css/style.css">-->
<#--    <!--[if lt IE 9]>-->
<#--    <script src="/assets/js/html5shiv.min.js"></script>-->
<#--    <script src="/assets/js/respond.min.js"></script>-->
<#--    <script src="http://cdn.itlaoqi.com./resources/jquery.3.3.1.min.js"></script>-->
<#--    <script src="http://cdn.itlaoqi.com./resources/bootstrap4/js/bootstrap.min.js"></script>-->
<#--    <![endif]&ndash;&gt;-->
<#--</head>-->
<#--统一头部-->
<#include "header.ftl">
<style>
    #headImg{
        width: 35px;
        height: 35px;
        border-radius: 50%;
        border: 3px solid #eee;
    }
</style>
<body>
<div class="main-wrapper account-wrapper">
    <div class="account-page">
        <div class="account-center">
            <div class="account-box">
                <form id="frmLogin" class="form-signin">
                    <div class="account-logo">
                        <a href="index.html"><img src="/assets/img/logo.png" alt=""></a>
                    </div>
                    <div style="text-align: center">
                        <h5>高校食堂用餐评价系统后台</h5>
                    </div>
                    <div class="form-group">
                        <label>用户名</label>
                        <input id="username" type="text" autofocus="" class="form-control">
                    </div>
                    <div class="form-group">
                        <label>密码</label>
                        <input id="password" type="password" class="form-control">
                    </div>

                    <div class=" form-group input-group mt-4 ">
                        <div class="col-5 p-0">
                            <input type="text" id="verifyCode" name="vc" class="form-control p-4" placeholder="验证码">
                        </div>
                        <div class="col-4 p-0 pl-2 pt-0">
                            <img id="imgVerifyCode" src="/verify_code"
                                 style="width: 120px;height:50px;cursor: pointer">
                        </div>

                    </div>


                    <div class="form-group text-center">
                        <button id="btnSubmit" type="submit" class="btn btn-primary account-btn">Login</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
<#--引入统一js-->
<#include "footer.ftl">
<script>

    function reloadVerifyCode() {
        $("#imgVerifyCode").attr("src", "/verify_code?ts=" + new Date().getTime());
    }

    $("#imgVerifyCode").click(function () {
        reloadVerifyCode();
    });

    $("#btnSubmit").click(function () {
        let username = $.trim($("#username").val());
        let password = $.trim($("#password").val());
        let verifyCode = $.trim($("#verifyCode").val());
        //alert(username + password);
        $.ajax({
            url: "/admin/login",
            type: "post",
            dataType: "json",
            data: {
                "username": username,
                "password": password,
                "verifyCode":verifyCode
            },
            success: function (data) {
                console.info(data);
                if (data.status == "10000") {
                    window.location = "/admin/showIndex?ts=" + new Date().getTime();
                }else{
                    alert(data.msg)
                    reloadVerifyCode();
                }
            }
        });
        return false;
    });
</script>
</body>

</html>