<#import "../parts/macros/basic.ftlh" as basic/>
<@basic.includeContent scriptsSrc="../superAdmin/users.js" currentPosition="users" modal=""permissionLevel="ADMIN">
    <div class="row">
        <div class="col-10 ml-auto mr-auto mt-5 mb-5">
            <h2>Użytkownicy</h2>
            <#list users>
                <ul class="list-group w-100 ">
                    <#items as user>
                        <li class="list-group-item" href="/user?id=${user.id}" class="stretched-link">
                            <div class="row">
                                <div class="col-8">
                                    email: ${user.email}, imię: ${user.name}, nazwisko: ${user.surname}
                                </div>
                                <div class="col-4">
                                    <select class="custom-select" data-userId="${user.id}" id="userType"
                                            value="${user.permissionLevel.value}">
                                        <#if user.permissionLevel.value==3>
                                            <option value="3" selected>SUPER ADMIN</option>
                                            <option value="0">ADMIN</option>
                                            <option value="1">USER</option>
                                        <#elseif user.permissionLevel.value==0>
                                            <option value="3">SUPER ADMIN</option>
                                            <option value="0" selected>ADMIN</option>
                                            <option value="1">USER</option>
                                        <#else>
                                            <option value="3">SUPER ADMIN</option>
                                            <option value="0">ADMIN</option>
                                            <option value="1" selected>USER</option>
                                        </#if>
                                    </select>
                                </div>
                            </div>
                        </li>
                    </#items>
                </ul>
            </#list>
            <#if users?size == 0>
                <p>Nie ma obecnie żadnych użytkowników</p>
            </#if>
        </div>
    </div>
</@basic.includeContent>