<#import "parts/macros/basic.ftlh" as baisc/>
<@baisc.includeContent scripts="../scripts.ftlh" currentPosition="search" modal="">
    <div class="row">
        <div class="col-10 ml-auto mr-auto mt-5">
            <h2>Szukaj</h2>
            <div class="btn-group" role="group" aria-label="Basic example">
                <button type="button" class="btn btn-secondary">Użytkownicy</button>
                <button type="button" class="btn btn-secondary">Zajęcia</button>
                <button type="button" class="btn btn-secondary">Plany</button>
            </div>
        </div>
        <div class="input-group mt-5 mb-3">
            <input type="text" class="form-control" placeholder="Searched text" aria-label="Searched text" aria-describedby="basic-addon2">
            <div class="input-group-append">
                <span class="btn input-group-text" id="basic-addon2">Szukaj</span>
            </div>
        </div>
    </div>
    <div class="row mt-3">
        <ul class="list-group w-100 mb-3">
            <li class="list-group-item"><a href="#" class="stretched-link">Go dadadsa</a></li>
            <li class="list-group-item"><a href="#" class="stretched-link">Go dadadsa</a></li>
            <li class="list-group-item"><a href="#" class="stretched-link">Go somewhere</a></li>
            <li class="list-group-item"><a href="#" class="stretched-link">Go somewhere</a></li>
            <li class="list-group-item"><a href="#" class="stretched-link">Go somewhere</a></li>
        </ul>
    </div>
</@baisc.includeContent>