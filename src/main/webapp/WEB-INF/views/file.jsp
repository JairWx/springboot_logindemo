<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<div id="uploader" class="wu-example">
	
    <!--用来存放文件信息-->
    <div id="thelist" class="uploader-list"></div>
    <div class="btns">
        <div id="picker">选择文件</div>
        <button id="ctlBtn" class="btn btn-default">开始上传</button>
    </div>
    文件列表：
	<input type="hidden" id="path"/>
    <div id="list" style="height:600px; overflow:scroll;"></div>
</div>
<!--引入CSS-->
<link rel="stylesheet" type="text/css" href="/css/webuploader.css">
<!--引入JS-->
<script type="text/javascript" src="/js/webuploader.js"></script>
<script>
$(document).on('click',"#file",function(){
	var url = $(this).data("url");
	
	loadList(url);
})
function loadList(url){
	var path = $("#path").val();
	$.post(url,{"path":path},function(res){
		var html ="";
		$.each(res.list,function(i,e){
			if(e.isDir){
				html += "<i class='fa fa-folder'></i><a id='file' data-url='list/"+res.path+e.name+"' href='javascript:void(0);'>"+e.name+"</a><br/>";
			}else{
				html += e.name+"<br/>";
			}
			
		});
		$("#list").html(html);
		$("#path").val(res.path);
	});
}
loadList("list/");
$list = $('#thelist');
$btn = $('#ctlBtn');
state = 'pending';
var path = $("#path").val();
var uploader = WebUploader.create({

    // swf文件路径
    swf: '/plugins/webupload/Uploader.swf',

    // 文件接收服务端。
    server: 'upload',
    formData:{
        'path' : path,
        'test' : path,
        'sss' : path,
        xxxx : 'xsxsxsxs',
    },
    // 选择文件的按钮。可选。
    // 内部根据当前运行是创建，可能是input元素，也可能是flash.
    pick: '#picker',

    // 不压缩image, 默认如果是jpeg，文件上传前会压缩一把再上传！
    resize: false
});
uploader.on( 'fileQueued', function( file ) {
    $list.append( '<div id="' + file.id + '" class="item">' +
        '<h4 class="info">' + file.name + '</h4>' +
        '<p class="state">等待上传...</p>' +
    '</div>' );
});
uploader.on( 'uploadProgress', function( file, percentage ) {
    var $li = $( '#'+file.id ),
        $percent = $li.find('.progress .progress-bar');

    // 避免重复创建
    if ( !$percent.length ) {
        $percent = $('<div class="progress progress-striped active">' +
          '<div class="progress-bar" role="progressbar" style="width: 0%">' +
          '</div>' +
        '</div>').appendTo( $li ).find('.progress-bar');
    }

    $li.find('p.state').text('上传中');

    $percent.css( 'width', percentage * 100 + '%' );
});

uploader.on( 'uploadSuccess', function( file ) {
    $( '#'+file.id ).find('p.state').text('已上传');
});

uploader.on( 'uploadBeforeSend', function( file,data ) {
	data.path = $("#path").val();
    //$( '#'+file.id ).find('p.state').text('已上传');
});

uploader.on( 'uploadError', function( file ) {
    $( '#'+file.id ).find('p.state').text('上传出错');
});

$btn.on( 'click', function() {
    if ( state === 'uploading' ) {
        uploader.stop();
    } else {
        uploader.upload();
    }
});
</script>