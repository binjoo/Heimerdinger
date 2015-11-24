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
<link rel="stylesheet" type="text/css" href="/assets/css/codemirror.css">
<script type="text/javascript" src="/assets/js/jquery.min.js?ver=1.9.1"></script>
<script type="text/javascript" src="/assets/js/bootstrap.min.js?ver=3.3.5"></script>
<script type="text/javascript" src="/assets/js/codemirror.js"></script>
<script type="text/javascript" src="/assets/js/markdown.js"></script>
<script type="text/javascript" src="/assets/js/base.js"></script>
</head>
<body>
<div id="wrap" class="side-open">
	<div class="row">
		<div class="col-md-3">
			<div class="side-header"></div>
			<div class="side-body" style="overflow: auto; height: 236px;">1</div>
		</div>
		<!-- /col-md-3 -->
		<div class="col-md-9">
			<div class="main-header">
				<h3 class="pull-left">Heimerdinger</h3>
				<div class="pull-right save-ctrl">
					<button type="button" class="btn btn-default">预览</button>
					<button type="button" class="btn btn-default">保存</button>
				</div>
			</div>
			<!-- /main-header -->
			<div class="main-editor row">
				<section class="entry-markdown col-md-6 col-xs-1">
					<header class="floatingheader">
						<small>Markdown</small>
					</header>
					<section class="entry-markdown-content">
						<textarea id="entry-markdown" style="display: none;"></textarea>
					</section>
				</section>
				<!-- /entry-markdown -->
				<section class="entry-preview col-md-6 col-xs-1">
					<header class="floatingheader">
						<small>Preview</small>
					</header>
					<section class="entry-preview-content">
					</section>
				</section>
				<!-- /entry-preview -->
			</div>
			<!-- /main-editor -->
		</div>
		<!-- /col-md-9 -->
	</div>
</div>
<!-- /wrap -->
</body>
</html>