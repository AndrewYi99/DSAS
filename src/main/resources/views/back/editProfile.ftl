<!DOCTYPE html>
<html lang="en">

<#include "header.ftl">

<body class="mini-sidebar">
    <div class="main-wrapper">
        <#include "header_bar.ftl">
        <#--左边侧边栏-->
        <#include "sidebar.ftl">
        <div class="page-wrapper">
            <div class="content">
                <div class="row">
                    <div class="col-sm-12">
                        <h4 class="page-title">编辑个人信息</h4>
                    </div>
                </div>
                <form>
                    <div class="card-box">
                        <h3 class="card-title">基本信息</h3>
                        <div class="row">
                            <div class="col-md-12">
                                <div class="profile-basic">
                                    <div class="row">
                                        <input hidden id="id" value="${currentAdmin.id}">
                                        <div class="col-md-6">
                                            <div class="form-group form-focus">
                                                <label class="focus-label">用户名</label>
                                                <input type="text" id="username" class="form-control floating" value="${currentAdmin.username}">
                                            </div>
                                        </div>
                                        <div class="col-md-6">
                                            <div class="form-group form-focus">
                                                <label class="focus-label">昵称</label>
                                                <input type="text" id="nickname" class="form-control floating" value="${currentAdmin.nickname}">
                                            </div>
                                        </div>
                                        <div class="col-md-6">
                                            <div class="form-group form-focus">
                                                <label class="focus-label">password</label>
                                                <input type="text" id="password" class="form-control floating" value="">
                                            </div>
                                        </div>
                                        <div class="col-md-6">
                                            <div class="form-group form-focus">
                                                <label class="focus-label">邮箱</label>
                                                <input type="text" id="email" class="form-control floating" value="${currentAdmin.email}">
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="text-center m-t-20">
                        <button class="btn btn-primary submit-btn" type="button" id="btnSubmit">更新</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
    <div class="sidebar-overlay" data-reff=""></div>
    <script src="/assets/js/jquery-3.2.1.min.js"></script>
	<script src="/assets/js/popper.min.js"></script>
    <script src="/assets/js/bootstrap.min.js"></script>
    <script src="/assets/js/jquery.slimscroll.js"></script>
    <script src="/assets/js/select2.min.js"></script>
    <script src="/assets/js/moment.min.js"></script>
    <script src="/assets/js/bootstrap-datetimepicker.min.js"></script>
    <script src="/assets/js/app.js"></script>
    <script>
        $("#btnSubmit").click(function () {
            // let username = $.trim($("#username").val());
            // let password = $.trim($("#password").val());
            // let nickname = $.trim($("#nickname").val());
            // let email = $.trim($("#email").val());
            let userData = {
                id:$.trim($("#id").val()),
                username:$.trim($("#username").val()),
                nickname:$.trim($("#nickname").val()),
                password:$.trim($("#password").val()),
                email:$.trim($("#email").val())
            }
            console.log(userData)
            //alert(username + password);
            $.ajax({
                url:"/admin/updateAdmin",
                dataType:"json",
                method: "POST",
                contentType:'application/json;charset=utf-8',
                data:JSON.stringify(userData),
                success:function (res) {
                    if (res.msg=="SUCCESS"){
                        alert("更新成功");
                        window.location.reload();
                    }
                }
            })
        });
    </script>
</body>

</html>