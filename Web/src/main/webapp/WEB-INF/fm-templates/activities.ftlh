<#import "parts/macros/basic.ftlh" as baisc/>
<@baisc.includeContent scripts="../scripts-activities.ftlh" currentPosition="activities" modal="">
    <div class="row">
        <div class="col-10 ml-auto mr-auto mt-5">
            <h2>Zajęcia</h2>
            <div class="form-group">
                <label for="activityType">Wybierz rodzaj zajęć</label>
                <select class="form-control" data-activitySelect="${type}"
                        id="activityType">
                    <option value="1">Wykład</option>
                    <option value="2">Ćwiczenia</option>
                    <option value="3">Warsztaty</option>
                    <option value="4">Wszystkie</option>
                </select>
            </div>
            <#list activities>
                <ul class="list-group w-100 ">
                    <#items as activity>
                        <li class="list-group-item"><a href="/activity?id=${activity.id}" class="stretched-link">Id: ${activity.id},
                                nazwa: ${activity.name} - ${activity.activitiesType.polishName}</a></li>
                    </#items>
                </ul>
            </#list>
            <#if activities?size == 0>
                <p>Nie ma obecnie żadnych zajęć podanego typu</p>
            </#if>
        </div>
    </div>
    <div class="row mt-3">
        <div class="col-3 ml-auto mr-auto mb-3">
            <a class="btn btn-success text-white w-100" href="/activity-add">Dodaj</a>
        </div>
    </div>
</@baisc.includeContent>