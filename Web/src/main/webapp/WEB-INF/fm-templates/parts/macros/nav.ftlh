<#macro setNav current>
    <a class="banner nav-link nav-item" href="/index"> Korpodrony</a>
    <nav class="navbar navbar-expand-lg navbar-light bg-primary">
        <a class="navbar-brand col-1"></a>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNav"
                aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarNav">
            <ul class="navbar-nav col-10">
                <#if current=="users">
                    <li class="nav-item active">
                        <a class="nav-link">Użytkownicy</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link text-white" href="/activities">Zajęcia</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link text-white" href="/plans">Plany</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link text-white ml-auto" href="/search">Szukaj</a>
                    </li>
                <#elseif current=="activities">
                    <li class="nav-item">
                        <a class="nav-link text-white" href="/users">Użytkownicy</a>
                    </li>
                    <li class="nav-item active">
                        <a class="nav-link">Zajęcia</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link text-white" href="/plans">Plany</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link text-white ml-auto" href="/search">Szukaj</a>
                    </li>
                <#elseif current=="plans">
                    <li class="nav-item">
                        <a class="nav-link text-white" href="/users">Użytkownicy</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link text-white" href="/activities">Zajęcia</a>
                    </li>
                    <li class="nav-item active">
                        <a class="nav-link">Plany</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link text-white ml-auto" href="/search">Szukaj</a>
                    </li>
                <#elseif current=="search">
                    <li class="nav-item">
                        <a class="nav-link text-white" href="/users">Użytkownicy</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link text-white" href="/activities">Zajęcia</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link text-white" href="/plans">Plany</a>
                    </li>
                    <li class="nav-item  active">
                        <a class="nav-link ml-auto">Szukaj</a>
                    </li>
                <#else>
                    <li class="nav-item">
                        <a class="nav-link text-white" href="/users">Użytkownicy</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link text-white" href="/activities">Zajęcia</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link text-white" href="/plans">Plany</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link text-white ml-auto" href="/search">Szukaj</a>
                    </li>
                </#if>
            </ul>
        </div>
    </nav>
</#macro>