
function checkTipoEvento(input){
    var tipo=/^[a-zA-Z0-9\s]+$/;
    if(input.value.match(tipo))
        return true;
    return false;
}
function checkFesteggiato(input){
    var festeggiato=/^[a-zA-Z\s]+$/;
    if(input.value.match(festeggiato))
        return true;
    return false;
}

function checkCitta(input){
    var citta=/^[a-zA-Z0-9\s]+$/;
    if(input.value.match(citta))
        return true;
    return false;
}

function validate(obj)
{
    var valid = true;

    //validiamo il tipo di evento
    var tipoEvento=document.getElementsByName("tipoEvento")[0];
    if(!checkTipoEvento(tipoEvento)){
        valid=false;
        alert('Formato TIPO EVENTO non corretto');
        tipoEvento.classList.add("error");
    }else
        tipoEvento.classList.remove("error");

    //validiamo il nome del festeggiato
    var festeggiato=document.getElementsByName("festeggiato")[0];
    if(!checkFesteggiato(festeggiato)){
        valid=false;
        alert('Formato FESTEGGIATO non corretto');
        festeggiato.classList.add("error");
    }else
        festeggiato.classList.remove("error");

    //validiamo la città
    var citta=document.getElementsByName("citta")[0];
    if(!checkFesteggiato(citta)){
        valid=false;
        alert('Formato CITTA non corretto');
        citta.classList.add("error");
    }else
        citta.classList.remove("error");

    return valid;
}


