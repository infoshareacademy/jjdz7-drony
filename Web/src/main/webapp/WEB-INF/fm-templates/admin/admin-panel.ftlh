<#import "../parts/macros/basic.ftlh" as basic/>
<@basic.includeContent scriptsSrc="" currentPosition="admin-panel" modal="" permissionLevel="ADMIN">
    <div class="row">
        <ul class="nav nav-tabs w-80 py-3" id="myTab" role="tablist">
            <li class="nav-item">
                <a class="nav-link active" id="reports-tab" data-toggle="tab" href="#reports" role="tab" aria-controls="reports" aria-selected="true">Raporty</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" id="configuration-tab" data-toggle="tab" href="#configuration" role="tab" aria-controls="configuration" aria-selected="false">Konfiguracja</a>
            </li>
        </ul>
        <div class="tab-content py-3" id="myTabContent">
            <div class="tab-pane fade show active" id="reports" role="tabpanel" aria-labelledby="reports-tab">
                <br>
                <br>
                <br>
                <br>
                <br>
                <#list reportsStatistics>
                <table class="table">
                    <thead class="thead-dark">
                    <tr>
                        <th scope="col">Email</th>
                        <th scope="col">Zakładka</th>
                        <th scope="col">Czynność</th>
                        <th scope="col">Data i godzina</th>
                    </tr>
                    </thead>
                        <#items as reportsStatistics>
                            <tr>
                                <td>${reportsStatistics.email}</td>
                                <td>${reportsStatistics.view}</td>
                                <td>${reportsStatistics.action}</td>
                                <td>${reportsStatistics.timeOfAction}</td>
                                </li>
                            </tr>
                        </#items>
                </table>
                </#list>
                <#if reportsStatistics?size == 0>
                    <p>Nie ma obecnie żadnych statystyk</p>
                </#if>
            </div>
            <div class="tab-pane fade" id="configuration" role="configuration" aria-labelledby="configuration-tab">Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam, eaque ipsa quae ab illo inventore veritatis et quasi architecto beatae vitae dicta sunt explicabo. Nemo enim ipsam voluptatem quia voluptas sit aspernatur aut odit aut fugit, sed quia consequuntur magni dolores eos qui ratione voluptatem sequi nesciunt. Neque porro quisquam est, qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit, sed quia non numquam eius modi tempora incidunt ut labore et dolore magnam aliquam quaerat voluptatem. Ut enim ad minima veniam, quis nostrum exercitationem ullam corporis suscipit laboriosam, nisi ut aliquid ex ea commodi consequatur? Quis autem vel eum iure reprehenderit qui in ea voluptate velit esse quam nihil molestiae consequatur, vel illum qui dolorem eum fugiat quo voluptas nulla pariatur?</div>
        </div>
    </div>
</@basic.includeContent>