<#import "parts/macros/basic.ftlh" as basic/>
<@basic.includeContent scripts="../scripts.ftlh" currentPosition="upload" modal="">

    <div class="row">
        <span class="warning-message">${message}</span><br/><br/>
        <#if isPrintForm == true>
            <form action="/upload-file" method="post" enctype="multipart/form-data">
                <div class="form-group">
                    <label for="fileInput">Wybierz plik do załadowania:</label>
                    <input type="file" class="form-control-file" id="fileInput">
                </div>
                <button type="submit" class="btn btn-primary">Załaduj</button>
            </form>
        </#if>

    </div>
</@basic.includeContent>