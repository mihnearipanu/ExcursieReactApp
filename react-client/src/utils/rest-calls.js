import {URL} from "./consts";

function status(response) {
    console.log("Response status " + response.status);
    if (response.status >= 200 && response.status < 300) {
        return Promise.resolve(response)
    } else {
        return Promise.reject(new Error(response.statusText))
    }
}

function json(response){
    return response.json();
}

export function getExcursii() {
    var headers = new Headers();
    headers.append("Accept", "application/json");
    var init = { method: "GET",
        headers: headers,
        mode: "cors"
    };
    var request = new Request(URL, init);

    console.log("Inainte de fetch pt " + URL);

    return fetch(request)
        .then(status)
        .then(json)
        .then(data => {
            console.log('Request succeeded with JSON response', data)
            return data;
        }).catch(error => {
            console.log("Request failed", error);
            return error;
        })
}

export function deleteExcursie(id){
    console.log('inainte de fetch delete')
    var myHeaders = new Headers();
    myHeaders.append("Accept", "application/json");

    var antet = { method: 'DELETE',
        headers: myHeaders,
        mode: 'cors'};

    var deleteUrl = URL + '/' + id;

    return fetch(deleteUrl, antet)
        .then(status)
        .then(response => {
            console.log('Delete status ' + response.status);
            return response.text();
        }).catch(e => {
            console.log('error ' + e);
            return Promise.reject(e);
        });
}

export function adaugareExcursie(excursie){
    console.log('inainte de fetch post'+JSON.stringify(excursie));

    var myHeaders = new Headers();
    myHeaders.append("Accept", "application/json");
    myHeaders.append("Content-Type","application/json");

    var antet = { method: 'POST',
        headers: myHeaders,
        mode: 'cors',
        body:JSON.stringify(excursie)};

    return fetch(URL,antet)
        .then(status)
        .then(response=>{
            return response.text();
        }).catch(error=>{
            console.log('Request failed', error);
            return Promise.reject(error);
        }); //;
}