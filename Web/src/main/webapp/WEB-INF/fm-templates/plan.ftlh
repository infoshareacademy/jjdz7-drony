<#import "parts/macros/basic.ftlh" as baisc/>
<@baisc.includeContent scripts="../scripts-plan.ftlh" currentPosition="none" modal="../plan-modal.ftlh">
    <div class="row">
        <div class="col-10 ml-auto mr-auto mt-5">
            <h2>Plan</h2>
            <form>
                <div class="form-group row">
                    <label for="id" class="col-sm-2 col-form-label">ID:</label>
                    <div class="col-sm-10">
                        <input type="number" readonly class="form-control-plaintext" id="id" data-id="${plan.id}"
                               value="${plan.id}">
                    </div>
                </div>
                <div class="form-group row">
                    <label for="name" class="col-sm-2 col-form-label">Nazwa</label>
                    <div class="col-sm-10">
                        <input type="text" class="form-control name" id="name" value="${plan.name}"
                               placeholder="${plan.name}">
                    </div>
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
        <div class="col-10 ml-auto mr-auto mt-5" id="assigned-activities">
            <h2>Zajęcia przypisane do planu</h2>
            <#list activities>
                <ul class="list-group w-100 mb-3 ">
                    <#items as activity>
                        <li class="list-group-item"><a href="/activity?id=${activity.id}"
                                                       class="stretched-link">Id: ${activity.id},
                                nazwa: ${activity.name} - ${activity.activitiesType.polishName}</a></li>
                    </#items>
                </ul>
            </#list>
            <#if activities?size ==0>
                <p>Nie ma obecnie żadnych użytkowników, których można zapisać do zajęć</p>
            </#if>
        </div>
    </div>

    <div class="row">
        <div class="col-10 ml-auto mr-auto mt-5">
            <div class="input-group mb-3">
                <input type="number" class="form-control" placeholder="Id zajęć do wypisania"
                       aria-label="unassignActivity" aria-describedby="basic-addon2" id="unassignActivityInput">
                <div class="input-group-append col-3">
                    <button class="btn w-100" id="unassignActivity">Wypisz zajęcia</button>
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
                <input type="number" class="form-control" placeholder="Id zajęć do zapisania"
                       aria-label="assignActivity" aria-describedby="basic-addon2" id="assignActivityInput">
                <div class="input-group-append col-3">
                    <button class="btn w-100" id="assignActivity">Dodaj zajęcia</button>
                </div>
            </div>
        </div>
    </div>

    <ul class="list-group w-80 mb-3 border border-danger mr-auto ml-auto col-10 d-none errors" id="assign-errors">
        <li class="list-group-item type-error" id="assign-error"></li>
    </ul>

    <div class="row">
        <div class="col-10 ml-auto mr-auto mt-5">
            <h2>Zajęcia, które można przypisać do planu</h2>
            <#list avaiableActivities>
                <ul class="list-group w-100 mb-3 ">
                    <#items as activity>
                        <li class="list-group-item"><a href="/activity?id=${activity.id}"
                                                       class="stretched-link">Id: ${activity.id},
                                nazwa: ${activity.name} - ${activity.activitiesType.polishName}</a></li>
                    </#items>
                </ul>
            </#list>
            <#if avaiableActivities?size == 0>
                <p>Nie ma obecnie żadnych przypisanych zajęć</p>
            </#if>
        </div>
    </div>
</@baisc.includeContent>
