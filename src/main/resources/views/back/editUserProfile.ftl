<!DOCTYPE html>
<html lang="en">

<#include "header.ftl">

<body>
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
                <form method="post">
                    <div class="card-box">
                        <h3 class="card-title">基本信息</h3>
                        <div class="row">
                            <div class="col-md-12">
                                <div class="profile-img-wrap">
                                    <img class="inline-block" src="${currentEditUser.avatar}" alt="user">
                                    <div class="fileupload btn">
                                        <span class="btn-text">修改</span>
                                        <input class="upload" type="file">
                                    </div>
                                </div>
                                <div class="profile-basic">
                                    <div class="row">
                                        <div class="col-md-6">
                                            <div class="form-group form-focus">
                                                <label class="focus-label">用户名</label>
                                                <input id="username" type="text" class="form-control floating" value="${currentEditUser.username}">
                                            </div>
                                        </div>
                                        <div class="col-md-6">
                                            <div class="form-group form-focus">
                                                <label class="focus-label">昵称</label>
                                                <input id="nickname" type="text" class="form-control floating" value="${currentEditUser.nickname}">
                                            </div>
                                        </div>
                                        <div class="col-md-6">
                                            <div class="form-group form-focus">
                                                <label class="focus-label">password</label>
                                                <input id="password" type="text" class="form-control floating" value="设置新密码">
                                            </div>
                                        </div>
                                        <div class="col-md-6">
                                            <div class="form-group form-focus">
                                                <label class="focus-label">邮箱</label>
                                                <input id="email" type="text" class="form-control floating" value="${currentEditUser.email}">
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>


                    <div class="text-center m-t-20">
                        <button class="btn btn-primary submit-btn" type="button" id="editCurrentUserProfile">Save</button>
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

        $("#editCurrentUserProfile").click(function(){
            let id = ${currentEditUser.id};
            let username = $.trim($("#username").val());
            let password = $.trim($("#password").val());
            let email = $.trim($("#email").val());
            let nickname = $.trim($("#nickname").val());
            let data = {
                "id":id,
                "username":username,
                "password":password,
                "email":email,
                "nickname":nickname
            };
            $.ajax({
                url:"/admin/update",
                method:"POST",
                dataType:"json",
                contentType:'application/json;charset=utf-8',
                data:JSON.stringify(data),
                success:function (data){
                    console.log(data)
                    if (data.msg == "SUCCESS"){
                        alert("更新成功,2s后跳转");
                        setTimeout(toUsers,2000)
                    }else {
                        alert(data.msg)
                    }
                }

            })

        });
        function toUsers(){
            window.location.href = "/admin/showUsers";
        }

    </script>
</body>


</html>