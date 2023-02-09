<%--
  Created by IntelliJ IDEA.
  User: 122nl
  Date: 01/02/2023
  Time: 19:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Register Page</title>

    <style>
        body{
            background: radial-gradient(circle at top left, #F44336, #2196F3, #f5a02f);
            background-repeat: no-repeat;
            background-attachment: fixed;
            background-size: cover;
        }
    </style>


</head>
<body>

<%@ include file="navbar.jsp" %>

<section class="vh-100">
    <div class="container py-5 h-100">
        <div class="row d-flex justify-content-center align-items-center h-100">
            <div class="col col-xl-10">
                <div class="card" style="border-radius: 1rem;">
                    <div class="row g-0">
                        <div class="col-md-6 col-lg-5 d-none d-md-block">
                            <img src="./img/zoomClickLogo.png">

                        </div>
                        <div class="col-md-6 col-lg-7 d-flex align-items-center">
                            <div class="card-body p-4 p-lg-5 text-black">

                                <form action="registra" method="post" onsubmit="return validate(this)">

                                    <div class="d-flex align-items-center mb-3 pb-1">
                                        <i class="fas fa-cubes fa-2x me-3" style="color: #ff6219;"></i>
                                        <span class="h1 fw-bold mb-0">Registrazione</span>
                                    </div>

                                    <h5 class="fw-normal mb-3 pb-3" style="letter-spacing: 1px;">Registra un nuovo account!</h5>

                                    <div class="form-outline mb-4">
                                        <input type="text" name="nome" id="form2Example17" class="form-control form-control-lg" style="text-transform:capitalize;" required/>
                                        <label class="form-label" for="form2Example17">Nome</label>
                                    </div>

                                    <div class="form-outline mb-4">
                                        <input type="text" name="cognome" class="form-control form-control-lg" style="text-transform:capitalize;" required/>
                                        <label class="form-label" for="form2Example17">Cognome</label>
                                    </div>

                                    <div class="form-outline mb-4">
                                        <input type="email" name="email" class="form-control form-control-lg" required/>
                                        <label class="form-label" for="form2Example17">Email address</label>
                                    </div>

                                    <div class="form-outline mb-4">
                                        <input type="password" name="password" class="form-control form-control-lg" required/>
                                        <label class="form-label" for="form2Example17">Password</label>
                                    </div>

                                    <div class="form-outline mb-4">
                                        <input type="text" name="telefono" class="form-control form-control-lg" required/>
                                        <label class="form-label" for="form2Example17">Telefono</label>
                                    </div>

                                    <div class="pt-1 mb-4">
                                        <input type="submit" value="Registrati" class="btn btn-dark btn-lg btn-block">
                                    </div>
                                </form>

                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

</section>
<script type="text/javascript" src="js/validationForm.js"></script>
</body>
</html>
