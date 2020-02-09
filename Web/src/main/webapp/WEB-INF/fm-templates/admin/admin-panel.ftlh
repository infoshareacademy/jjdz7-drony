<#import "../parts/macros/basicWithTables.ftlh" as basic/>
<@basic.includeContent scriptsSrc="" currentPosition="admin-panel" modal="" permissionLevel="ADMIN">
    <div class="row py-3">
        <div class="col-lg-6 col-md-8 mx-auto">
            Data Od
            <input id="datepickerFrom" value="${from}"/>
        </div>
        <div class="col-lg-6 col-md-8 mx-auto">
            Data do
            <input id="datepickerTo" value="${to}"/>
        </div>
    </div>
    <div class="row mt-3">
        <div class="col-3 mb-3 ml-auto mr-auto">
            <button class="btn btn-success text-white w-100 change">Zmień</button>
        </div>
    </div>
    <div class="row py-3">
        <div class="col-10 mr-auto ml-auto">
            <#list reports>
                <table id="example" class="table table-striped table-bordered" style="width:100%">
                    <thead>
                    <tr>
                        <th>email</th>
                        <th>view</th>
                        <th>action</th>
                        <th>timeOfAction</th>
                    </tr>
                    </thead>
                    <tbody>
                    <#items as report>
                        <tr>
                            <td>${report.email}</td>
                            <td>${report.view}</td>
                            <td>${report.action}</td>
                            <td>${report.timeOfAction}</td>
                        </tr>
                    </#items>
                    </tbody>
                    <tfoot>
                    <tr>
                        <th>email</th>
                        <th>view</th>
                        <th>action</th>
                        <th>timeOfAction</th>
                    </tr>
                    </tfoot>
                </table>
            </#list>
        </div>
    </div>
</@basic.includeContent>