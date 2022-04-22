<!DOCTYPE html>
<html lang="en">
<#include "header.ftl">

<body>
    <div class="main-wrapper">
        <#--顶部状态栏-->
        <#include "header_bar.ftl">
        <#--侧边栏-->
        <#include "sidebar.ftl">
        <div class="page-wrapper">
            <div class="content">
                <div class="row">
                    <div class="col-sm-12">
                        <h4 class="page-title">新增用户</h4>
                    </div>
                </div>
                <div class="row">
                    <div class="col-lg-12">
                        <div class="card-box">
                            <h4 class="card-title">基本信息</h4>
                            <form action="#">
                                <div class="form-group row">
                                    <label class="col-form-label col-md-2">用户名</label>
                                    <div class="col-md-10">
                                        <input type="text" class="form-control" name="username" id="username">
                                    </div>
                                </div>
                                <div class="form-group row">
                                    <label class="col-form-label col-md-2">Password</label>
                                    <div class="col-md-10">
                                        <input type="password" class="form-control" name="password" id="password">
                                    </div>
                                </div>
                                <div class="form-group row">
                                    <label class="col-form-label col-md-2">用户昵称</label>
                                    <div class="col-md-10">
                                        <input type="text" class="form-control" id="nickname" name="nickname">
                                    </div>
                                </div>
                                <div class="form-group row">
                                    <label class="col-form-label col-md-2">邮箱</label>
                                    <div class="col-md-10">
                                        <input type="text" class="form-control" id="email" name="email">
                                    </div>
                                </div>

                                <div class="form-group row">
                                    <label class="col-form-label col-md-2">头像</label>
                                    <div class="col-md-10">
                                        <input class="form-control" type="file">
                                    </div>
                                </div>
                                

                                <div class="form-group row">
                                    <label class="col-form-label col-md-2">权限</label>
                                    <div class="col-md-10">
                                        <select class="form-control">
                                            <option value="1" style="color: lightgreen">普通用户</option>
                                            <#if currentAdmin.role == 2>
                                            <option value="2" style="color: yellow">普通管理员</option>
                                            </#if>
                                            <#if currentAdmin.role == 3>
                                            <option value="3" style="color: red">超级管理员工</option>
                                            </#if>
                                        </select>
                                    </div>
                                </div>
                                <div class="text-center m-t-20">
                                    <button class="btn btn-primary submit-btn" type="button">Save</button>
                                </div>
                            </form>
                        </div>

                    </div>
                </div>
            </div>

        </div>
    </div>
    <#include "footer.ftl">
</body>

</html>