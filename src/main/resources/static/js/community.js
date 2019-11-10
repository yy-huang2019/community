function comment2target(targetId,type,content) {
    if(!content){                                    //客户端校验
        alert("不能回复空内容喔~~~");
        return;
    }

    $.ajax({
        type: "post",
        url: "/comment",
        contentType: 'application/json',
        data: JSON.stringify({
            "parent_id": targetId,
            "content": content,
            "type": type
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



//提交回复
function post() {
    var question_id = $("#question_id").val();
    var content = $("#comment_content").val();
    comment2target(question_id,1,content);
}


function comment(e) {
    var comment_id = e.getAttribute("data-id");
    var content = $("#input-"+comment_id).val();
    comment2target(comment_id,2,content);
}




//展开二级回复
function collapseComments(e) {
    var id = e.getAttribute("data-id");    //得到该级评论按钮data的值
    var comment = $("#comment-"+id);       //得到该下拉页框的id

    var attribute = e.getAttribute("data-collapse");       //得到data-collapse的值
    if(attribute != null){
        comment.removeClass("in");             ////js通过增加removeClass()方法直接将该class属性值的in删除掉
        e.removeAttribute("data-collapse");       //移除data-collapse
        e.classList.remove("active");
    }else {
        var subCommentContainer = $("#comment-"+id);
        if(subCommentContainer.children().length != 1){     //获取subCommentContainer的子元素的长度
            //添加in用来展开二级评论
            comment.addClass("in");                //js通过增加addClass()方法直接将该class属性值加上去以此将该二级评论页框展示出来
            e.setAttribute("data-collapse","in");  //通过设置attribute值用来表示点击过的按钮
            e.classList.add("active");
        }else {
            $.getJSON( "/comment/"+id, function( data ) {
                console.log(data);

                //通过循环的方式直接将question.html页面中的二级评论页面通过json的方式将其添加
                $.each( data.data.reverse(), function(index,comment) {            //reverse是将该数据反转(从后往前)显示

                    var mediaLeftElement = $("<div/>",{
                        "class":"media-left"
                    }).append($("<img/>",{
                        "class":"media-object img-rounded",
                        "src":comment.user.avatar_url
                    }));

                    var mediaBodyElement = $("<div/>",{
                        "class":"media-body"
                    }).append($("<h5/>",{
                        "class":"media-heading",
                        "style":"margin-top: 12px",
                        "text":comment.user.name    //不能用getName()
                    })).append($("<div/>",{
                        "text":comment.content
                    })).append($("<div/>",{
                        "style":"margin-top: 10px",
                        "class":"comment"
                    }).append($("<span/>",{
                        "text":moment(comment.gmt_create).format('YYYY-MM-DD HH:mm'),
                        "class":"pull-right"
                    })));

                    debugger;

                    var mediaElement = $("<div/>",{
                        "class":"media"
                    }).append(mediaLeftElement).append(mediaBodyElement);

                    var commentElement = $("<div/>",{
                        "class":"col-lg-12 col-md-12 col-sm-9 col-xs-12 comments"
                    }).append(mediaElement);

                    subCommentContainer.prepend(commentElement);
                });

                //添加in用来展开二级评论
                comment.addClass("in");                //js通过增加addClass()方法直接将该class属性值加上去以此将该二级评论页框展示出来
                e.setAttribute("data-collapse","in");  //通过设置attribute值用来表示点击过的按钮
                e.classList.add("active");
            });
        }
    }
}

//添加标签
function selectTag(e) {
    var value = e.getAttribute("data-tag");    //得到该标签data的值
    var previous = $("#tag").val();
    //如果tag框内的值没有需要添加的值时将该添加的标签的值添加进去
    if(previous.indexOf(value) == -1){
        if(previous){
            $("#tag").val(previous+','+value);
        }else {
            $("#tag").val(value);
        }
    }

}

//通过触发标签栏将标签选择页显示出来
function showSelectTag() {
    $("#selectTags").show();
}