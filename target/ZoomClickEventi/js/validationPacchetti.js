
function checkTitolo(input){
    var titolo=/^[a-zA-Z\s]+([-][a-zA-Z\s]+)*$/;
    if(input.value.match(titolo))
        return true;
    return false;
}
function checkEventi(input){
    var eventi=/^[a-zA-Z0-9\s]+([,][a-zA-Z0-9\s]+)*$/;
    if(input.value.match(eventi))
        return true;
    return false;
}

function checkPrezzo(input){
    var prezzo=/^[0-9]+$/;
    if(input.value.match(prezzo))
        return true;
    return false;
}

function validate(obj)
{
    var valid = true;

    //validiamo il titolo
    var titolo=document.getElementsByName("titolo")[0];
    if(!checkTitolo(titolo)){
        valid=false;
        alert('Formato TITOLO non corretto');
        titolo.classList.add("error");
    }else
        titolo.classList.remove("error");

    //validiamo gli eventi consigliati
    var eventi=document.getElementsByName("eventi")[0];
    if(!checkEventi(eventi)){
        valid=false;
        alert('Formato EVENTI CONSIGLIATI non corretto');
        eventi.classList.add("error");
    }else
        eventi.classList.remove("error");

    //validiamo il prezzo
    var prezzoPacchetto=document.getElementsByName("prezzoPacchetto")[0];
    if(!checkPrezzo(prezzoPacchetto)){
        valid=false;
        alert('Formato PREZZO non corretto');
        prezzoPacchetto.classList.add("error");
    }else
        prezzoPacchetto.classList.remove("error");

    return valid;
}


