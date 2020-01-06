<#import "parts/macros/basic.ftlh" as basic/>
<@basic.includeContent scriptsSrc="activity.js" currentPosition="none" modal="../activity-modal.ftlh">

    <#if users?size !=0 >
        <#assign minUsers = users?size>
        <#assign maxUsers = 30+users?size>
    <#else>
        <#assign minUsers = 1>
        <#assign maxUsers = 30>
    </#if>

    <div class="row">
        <div class="col-10 ml-auto mr-auto mt-5">
            <h2>Zajęcia</h2>
            <form>
                <div class="form-group row">
                    <label for="id" class="col-sm-2 col-form-label">ID:</label>
                    <div class="col-sm-10">
                        <input type="number" readonly class="form-control-plaintext" id="id" data-id="${activity.id}"
                               value="${activity.id}">
                    </div>
                </div>
                <div class="form-group row">
                    <label for="name" class="col-sm-2 col-form-label">Nazwa</label>
                    <div class="col-sm-10">
                        <input type="text" class="form-control name" id="name" value="${activity.name}"
                               placeholder="${activity.name}">
                    </div>
                </div>
                <div class="form-group row">
                    <label for="maxUsers" class="col-sm-6 col-form-label maxUsersLabel">Max l. użytkowników: x</label>
                    <div class="col-sm-6 col-lg-12">
                        <input type="range" class="custom-range maxUsers" min="${minUsers}" max="${maxUsers}"
                               value="${activity.maxUsers}"
                        id="maxUsers">
                    </div>
                </div>
                <div class="form-group row">
                    <label for="duration" class="col-sm-6 col-form-label durationLabel">Czas trwania: x</label>
                    <div class="col-sm-6 col-lg-12">
                        <input type="range" class="custom-range duration" min="1" max="10"
                        value="${activity.lengthInQuarters}" id="duration">
                    </div>
                </div>
                <div class="form-group">
                    <label for="activityType">Wybierz rodzaj zajęć</label>
                    <select class="form-control" data-activitySelect="${activity.activitiesType.number}"
                            id="activityType">
                        <option value="1">Wykład</option>
                        <option value="2">Ćwiczenia</option>
                        <option value="3">Warsztaty</option>
                    </select>
                </div>
            </form>
            <ul class="list-group w-80 mb-3 border border-danger d-none errors" id="errors">
                <li class="list-group-item type-error" id="type-error"></li>
            </ul>
        </div>
    </div>

    <div class="row mt-3">
        <div class="col-3 mb-3 ml-auto">
            <button class="btn btn-success text-white w-100 change">Zmień</button>
        </div>
        <div class="col-3 mb-3 mr-auto">
            <button class="btn btn-danger text-white w-100 delete" data-toggle="modal"
            data-target="#exampleModalCenter">usuń
            </button>
        </div>
    </div>

    <div class="row">
        <div class="col-10 ml-auto mr-auto mt-5" id="assigned-users">
            <h2>Użytykownicy przypisani do zajęć</h2>
            <input type="text" class="form-control" placeholder="Imię i nazwisko użytkownika"
                   aria-label="deleteUser" aria-describedby="basic-addon2" id="deleteUserInput">
            <#list users>
                <ul class="list-group w-100 mb-3 to-un-assign">
                    <#items as user>
                        <li class="list-group-item" data-userid="${user.id}">email: ${user.email} imię: ${user.name}, nazwisko: ${user.surname}</li>
                    </#items>
                </ul>
            </#list>
            <#if users?size == 0>
                <p>Nie ma obecnie żadnych zapisanych użytkowników</p>
            </#if>
        </div>
    </div>

    <div class="row">
        <div class="col-10 ml-auto mr-auto mt-5">
            <div class="input-group mb-3">
                <div class="input-group-append col-3">
                    <button class="btn w-100" id="deleteUser" disabled>Wypisz użytkownika</button>
                </div>
            </div>
        </div>
    </div>

    <ul class="list-group w-80 mb-3 border border-danger d-none col-10 mr-auto ml-auto errors" id="delete-errors">
        <li class="list-group-item type-error" id="delete-error"></li>
    </ul>

    <div class="row">
        <div class="col-10 ml-auto mr-auto">
            <div class="input-group mb-3">
                <div class="input-group-append col-3">
                    <button class="btn w-100" id="assignUser" disabled>Zapisz użytkownika</button>
                </div>
            </div>
        </div>
    </div>

    <ul class="list-group w-80 mb-3 border border-danger mr-auto ml-auto col-10 d-none errors" id="assign-errors">
        <li class="list-group-item type-error" id="assign-error"></li>
    </ul>

    <div class="row">
        <div class="col-10 ml-auto mr-auto mt-5">
            <h2>Użytkownicy, których można zapisać do zajęć</h2>
            <input type="text" class="form-control" placeholder="Imię i nazwisko użytkownika"
                   aria-label="assignUser" aria-describedby="basic-addon2" id="assignUserInput">
            <#list availableUsers>
                <ul class="list-group w-100 mb-3 to-assign">
                    <#items as user>
                        <li class="list-group-item" data-userid="${user.id}">email: ${user.email} imię: ${user.name}, nazwisko: ${user.surname}</li>
                    </#items>
                </ul>
            </#list>
            <#if availableUsers?size ==0>
                <p>Nie ma obecnie żadnych użytkowników, których można zapisać do zajęć</p>
            </#if>
        </div>
    </div>
</@basic.includeContent>
