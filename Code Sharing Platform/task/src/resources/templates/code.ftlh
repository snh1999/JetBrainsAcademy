<html>
<head>
    <title>Code</title>
    <link rel="stylesheet"
           href="//cdn.jsdelivr.net/gh/highlightjs/cdn-release@10.2.1/build/styles/default.min.css">
    <script src="//cdn.jsdelivr.net/gh/highlightjs/cdn-release@10.2.1/build/highlight.min.js"></script>
    <script>hljs.initHighlightingOnLoad();</script>
</head>
<body>
    <span id= "load_date">${code.getDate()}</span>"
    <#if code.isTimeSet()>
      <span id="time_restriction"> The code will be available for ${code.getTime()} seconds </span>
    </#if>
    <#if code.isCountSet()>
        <span id="views_restriction"> ${code.getViews()} more views allowed </span>
    </#if>
    <pre id="code_snippet"> <code> ${code.getCode()} </code> </pre>
</body>
</html>