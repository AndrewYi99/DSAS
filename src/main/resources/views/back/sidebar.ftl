<link rel="stylesheet" type="text/css" href="/assets/css/bootstrap.min.css">
<style>
    a:hover, a:active, a:focus {
        text-decoration: none;
        color: #009ce7;
        outline: none;
    }

</style>
<div class="sidebar" id="sidebar">
    <div class="sidebar-inner slimscroll">
        <div id="sidebar-menu" class="sidebar-menu">
            <ul>
                <#--点击时添加该样式：class="active"-->
                <li class="menu-title">菜单列表</li>
                <li>
                    <a href="/admin/showIndex"><i class="fa fa-dashboard"></i> <span>控制面板</span></a>
                </li>
                <li>
                    <a href="/admin/showUsers"><i class="fa fa-user-md"></i> <span>用户列表</span></a>
                </li>
                <li>
                    <a href="/admin/showFood"><i class="fa fa-coffee"></i> <span>菜品列表</span></a>
                </li>
                <li>
                    <a href="/admin/showEvaluations"><i class="fa fa-comments"></i> <span>评论列表</span></a>
                </li>


            </ul>
        </div>
    </div>
</div>
<script src="/assets/js/jquery-3.2.1.min.js"></script>
<script src="/assets/js/bootstrap.min.js"></script>
<script>
    // $("#sidebar-menu ul li").click(function () {
    //     alert("test")
    // })
    // let element = document.querySelector("#sidebar-menu > ul>li");
    // element.addEventListener('click',function () {
    //     alert("test")
    // })
    //
    // $(function() {
    //     $("#sidebar-menu ul li").bind("click", function() {
    //         console.log(this)
    //         alert("test")
    //         $(this).addClass
    //         $(this).siblings('li').removeClass('active'); //删除其他兄弟元素的类名
    //         $(this).addClass('active');
    //     });
    // })
</script>