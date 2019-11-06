function post() {
    var question_id = $("#question_id").val();
    var content = $("#comment_id").val();
    if(!content){                                    //客户端校验
        alert("不能回复空内容喔~~~");
        return;
    }

    $.ajax({
        type: "post",
        url: "/comment",
        contentType: 'application/json',
        data: JSON.stringify({
            "parent_id": question_id,
            "content": content,
            "type": 1
        }),
        success: function (response) {
            if (response.code == 200) {
                window.location.reload();    //加载页面
                $("#comment_section").hide();

            } else {
                //2003表示用户未登录
                if (response.code == 2003) {
                    var isAccept = confirm(response.message);          //confirm是js的window的一个弹框
                    //如果点了确定,则表示要登录
                    if (isAccept) {
                        //window的直接打开一个url
                        window.open("https://github.com/login/oauth/authorize?client_id=da503055424f1fa44435&redirect_uri=http://localhost:8888/callback&scope=user&state=1");
                        window.localStorage.setItem("closable" , true);           //在web页面通过localStorage存值
                    }
                } else {
                    alert(response.message);
                }
            }
            console.log(response);
        },
        dataType: "json"
    });
}