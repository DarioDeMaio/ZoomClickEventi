
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
	var pwd = document.getElementsByName("password")[0];
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
	var numbers = document.getElementsByName("telefono")[0];
	if(checkPhonenumberSize(numbers))
	{
		//numbers.classList.remove("error");
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

	return valid;
}


