pageObj = {};
pageObj.converter = null;
pageObj.editor = null;
pageObj.updatePreview = function(){
	var html = pageObj.converter.makeHtml(filterXSS(pageObj.editor.getValue()));
	$("div.rendered-markdown").html(html);
}
pageObj.updateWordCount = function(){
	if (pageObj.editor.getValue().length) {
		$("div.entry-word-count").html(pageObj.editor.getValue().match(/\S+/g).length + ' words');
	}
}
pageObj.syncScroll = function(e){
	var codeView = $(e.target),
		previewView = $('.entry-preview-content'),
		codeContent = $('.CodeMirror-sizer'),
		previewContent = $('.rendered-markdown'),
		codeHeight = codeContent.height() - codeView.height(),
		previewHeight = previewContent.height() - previewView.height(),
		ratio = previewHeight / codeHeight,
		previewPostition = codeView.scrollTop() * ratio;
		
	previewView.scrollTop(previewPostition);
}
pageObj.getData = function(){
	var data = {};
	data.title = $("input[name=title]").val();
	data.subtitle = $("input[name=subtitle]").val();
	data.password = $("input[name=password]").val();
	data.content = pageObj.editor.getValue();
	return data;
}
pageObj.save = function(){
	$.post("/admin?action=save",
		pageObj.getData(),
		function(data){
			$(".main-header .message").stop().html(data.message).removeClass().addClass("pull-left message " + data.result);
			$(".main-header .message").fadeIn(300).delay(3000).fadeOut(600);
		}
	);
}
pageObj.preview = function(){
	alert("preview");
}
$(document).ready(function() {
	pageObj.converter = new showdown.Converter();
	pageObj.editor = CodeMirror.fromTextArea(document.getElementById('entry-markdown'), {
		mode: 'markdown',
		tabMode: 'indent',
		lineWrapping: true
	});
	pageObj.editor.on("change", function() {
		pageObj.updatePreview();
	});
	//动态设置高度
	$(".side-body").css("height", ($(document).height() - 68) + "px");
	$(".entry-markdown-content, .entry-preview-content").css("height", ($(document).height() - 88) + "px");
	pageObj.editor.setSize('auto', ($(document).height() - 88) + "px");
	
	$('.CodeMirror-scroll').on('scroll', pageObj.syncScroll);
	pageObj.updatePreview();
});