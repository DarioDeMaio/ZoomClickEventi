function checkCf(input){
	var cf=/^[a-zA-Z]{6}[0-9]{2}[a-zA-Z][0-9]{2}[a-zA-Z][0-9]{3}[a-zA-Z]$/;
	if(input.value.match(cf))
		return true;
	return false;
}

function checkPwd(input){
	var pwd=/^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,20}$/g;
	if(input.value.match(pwd))
		return true;
	return false;
}

function checkName(input){
	var name=/^[\w'\-,.][^0-9_!¡?÷?¿/\\+=@#$%ˆ&*(){}|~<>;:[\]]{2,}$/; 
	if(input.value.match(name))
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

function checkPhonenumber(input){
	var phone = /^([0-9]{10})+$/;
	if(input.value.match(phone))
		return true;
	return false;
}


function validate(obj)
{
	var valid = true;
	
	
	//validiamo il codice fiscale
	var cf = document.getElementsByName("cf")[0];
	if(!checkCf(cf)){
		valid=false;
		alert('Campo CODICE FISCALE non corretto!');
		cf.classList.add("error");
	}else
		cf.classList.remove("error");
	
	//validiamo il nome
	var name=document.getElementsByName("nome")[0];
	if(!checkName(name)){
		valid=false;
		alert('Campo NOME non corretto!');
		name.classList.add("error");
	}else
		name.classList.remove("error");
	
	//validiamo il cognome
	var cognome=document.getElementsByName("cognome")[0];
	if(!checkName(cognome)){
		valid=false;
		alert('Campo COGNOME non corretto!');
		cognome.classList.add("error");
	}else
		cognome.classList.remove("error");

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
	if(!checkPwd(pwd)){
		valid=false;
		alert('Campo PASSWORD non corretto!');
		pwd.classList.add("error");
	}else
		pwd.classList.remove("error");

	//validiamo il numero di telefono
	var numbers = document.getElementsByName("telefono");
	for(var i=0; i<numbers.length; i++)
	{
		if(!checkPhonenumber(numbers[i])){
			valid=false;
			alert('Campo TELEFONO non corretto!');
			numbers[i].classList.add("error");
		}else
			numbers[i].classList.remove("error");
	}
	
	return valid;
}


