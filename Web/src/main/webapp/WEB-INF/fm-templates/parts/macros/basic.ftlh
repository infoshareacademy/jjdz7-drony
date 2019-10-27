<#macro includeContent>
<!doctype html>
<html lang="pl">
<#include "../header.ftlh">
<body>
<#include "../nav.ftlh">
<div id="particles-js">
    <div class="container pr-5 pl-5 mt-5 mb-5">
        <#nested>
    </div>
</div>
<#include "../scripts.ftlh">
</body>
</html>
</#macro>