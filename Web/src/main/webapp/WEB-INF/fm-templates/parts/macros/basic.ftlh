<#macro includeContent scriptsSrc currentPosition modal>
    <!doctype html>
    <html lang="pl">
    <#include "../header.ftlh">
    <body>
    <#import "nav.ftlh" as nav>
    <@nav.setNav current=currentPosition/>
    <div id="particles-js">
        <div class="container pr-5 pl-5 mt-5 mb-5">
            <#nested>
        </div>
    </div>
    <#if modal !="">
        <#include modal>
    </#if>
    <#import "scripts.ftlh" as scripts>
    <@scripts.setScripts script=scriptsSrc/>
    </body>
    </html>
</#macro>