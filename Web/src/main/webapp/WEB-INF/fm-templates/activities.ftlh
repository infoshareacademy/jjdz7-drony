<#import "parts/macros/basic.ftlh" as baisc/>
<@baisc.includeContent scripts="../scripts.ftlh" currentPosition="activities" modal="">
    <div class="row">
        <div class="col-10 ml-auto mr-auto mt-5">
            <h2>Zajęcia</h2>
            <#list activities>
                <ul class="list-group w-100 ">
                    <#items as activity>
                    <li class="list-group-item"><a href="/activity?id=${activity.id}" class="stretched-link">Id: ${activity.id}, ${activity.name}</a></li>
                    </#items>
                </ul>
            </#list>
            <#if activities?size == 0>
                <p>Nie ma obecnie żadnych zajęć</p>
            </#if>
        </div>
    </div>
    <div class="row mt-3">
        <div class="col-3 ml-auto mr-auto mb-3">
            <a class="btn btn-success text-white w-100">Dodaj</a>
        </div>
    </div>
</@baisc.includeContent>