
function checkPwdSize(input){
    var pwd=/^.{8,}$/;
    if(input.value.match(pwd))
        return true;
    return false;
}
function checkPwd(input){
    var pwd=/^(?=.*[A-Z])(?=.*[a-z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]+$/;
    if(input.value.match(pwd))
        return true;
    return false;
}

function checkEmail(input)
{
    var email=/^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/;
    if(input.value.match(email))
        return true;
    return false;
}

function checkPhonenumber(input) {
    var numbers = /^[0-9]+$/;
    if(input.value.match(numbers))
        return true;
    return false;
}

function checkPhonenumberSize(input) {
    var tenChar = /^.{10}$/;
    if(input.value.match(tenChar))
        return true;
    return false;
}

function validate(obj)
{
    var valid = true;

    //validiamo l'email
    var email=document.getElementsByName("email")[0];
    if(!checkEmail(email)){
        valid=false;
        alert('Campo EMAIL non corretto!');
        email.classList.add("error");
    }else
        email.classList.remove("error");

    //validiamo la password
    var pwd = document.getElementsByName("psw")[0];
    if(checkPwdSize(pwd))
    {
        if(!checkPwd(pwd)){
            valid=false;
            alert('Formato PASSWORD non corretto!');
            pwd.classList.add("error");
        }else
            pwd.classList.remove("error");
    }else{
        valid=false;
        alert('Lunghezza PASSWORD non corretto');
        pwd.classList.add("error");
    }

    //validiamo il numero di telefono
    var numbers = document.getElementsByName("numero")[0];
    if(checkPhonenumberSize(numbers))
    {
        if(!checkPhonenumber(numbers)){
            valid=false;
            alert('Formato TELEFONO non corretto!');
            numbers.classList.add("error");
        }else
            numbers.classList.remove("error");
    }else{
        valid=false;
        alert('Lunghezza TELEFONO non corretta');
        numbers.classList.add("error");
    }

    return valid
}


