
function checkPrice(input){
    var prezzo=/^[0-9]+$/;
    if(input.value.match(prezzo))
        return true;
    return false;
}

function validate(obj)
{
    var valid = true;

    //validiamo il prezzo
    var priceDj=document.getElementsByName("prezzoDj")[0];
    var priceFot=document.getElementsByName("prezzoFotografo")[0];
    var priceSpe=document.getElementsByName("prezzoSpeaker")[0];

    if(!checkPrice(priceDj)){
        valid=false;
        alert('Formato PREZZO DJ non corretto/i');
        priceDj.classList.add("error");
    }else
        priceDj.classList.remove("error");

    if(!checkPrice(priceFot)){
        valid=false;
        alert('Formato PREZZO FOTOGRAFO non corretto/i');
        priceFot.classList.add("error");
    }else
        priceFot.classList.remove("error");

    if(!checkPrice(priceSpe)){
        valid=false;
        alert('Formato PREZZO SPEAKER non corretto/i');
        priceSpe.classList.add("error");
    }else
        priceSpe.classList.remove("error");

    return valid;
}


