<#import "parts/macros/basic.ftlh" as baisc/>
<@baisc.includeContent scripts="../scripts.ftlh">
    <div class="row">
        <div class="col-10 ml-auto mr-auto mt-5">
            <h2>Użytkowicy</h2>
            <#list users>
            <ul class="list-group w-100 ">
                <#items as user>
                    <li class="list-group-item"><a href="/user?id=${user.id}" class="stretched-link">Id: ${user.id}, imię: ${user.name}, nazwisko: ${user.surname}</a></li>
                </#items>
                </ul>
            </#list>
        </div>
    </div>
    <div class="row mt-3">
        <div class="col-3 ml-auto mr-auto mb-3">
            <a class="btn btn-success text-white w-100">Dodaj</a>
        </div>
    </div>
</@baisc.includeContent>