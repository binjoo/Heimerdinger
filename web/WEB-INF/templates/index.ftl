<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Heimerdinger</title>
<link rel="stylesheet" type="text/css" href="/assets/css/normalize.min.css?ver=3.0.3">
<link rel="stylesheet" type="text/css" href="/assets/css/yue.css?ver=0.2">
<link rel="stylesheet" type="text/css" href="/assets/css/style.css">
<link rel="stylesheet" type="text/css" href="/assets/css/bootstrap.min.css?ver=3.3.5">
<script type="text/javascript" src="/assets/js/jquery.min.js?ver=1.9.1"></script>
<script type="text/javascript" src="/assets/js/bootstrap.min.js?ver=3.3.5"></script>
<script type="text/javascript" src="/assets/js/jspdf.js"></script>
<script type="text/javascript" src="/assets/js/jspdf.plugin.from_html.js"></script>
<script type="text/javascript" src="/assets/js/base.js"></script>
</head>
<body>
<div id="wrap">
	<header class="site-header">
		<div class="vertical">
			<h1 id="drtitle" class="ng-binding">${title?if_exists}</h1>
			<p class="subtitle" id="drsubtitle">${subtitle?if_exists}</p>
		</div>
	</header>
	<div id="editor"></div>
	<!-- /.site-header -->
	<div class="site-content yue">${content?if_exists}</div>
	<div class="action-bar hidden-xs ng-scope">
		<a href="javascript:pageObj.makepdf()" class="btn btn-default btn-sm">
			<span class="glyphicon glyphicon-download-alt"></span>
		</a>
	</div> 
</div>
<!-- /wrap -->
</body>
</html>