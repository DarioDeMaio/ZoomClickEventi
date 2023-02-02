<%--
  Created by IntelliJ IDEA.
  User: 122nl
  Date: 01/02/2023
  Time: 18:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>

    <title>Login Page</title>

    <style>

        img{
            border-radius: 5%;
        }

        body {
            background: radial-gradient(circle at top left, #F44336, #2196F3, #f5a02f);
        }

        @media only screen and (max-width:991px) {
            img{
                width:350px;
                height:350px;
            }
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
                        <div class="col-md-6 col-lg-5 d-none d-md-block"><img src="img/images.jpeg"></div>
                        <div class="col-md-6 col-lg-7 d-flex align-items-center">
                            <div class="card-body p-4 p-lg-5 text-black">
                                <form action="login" method="post">

                                    <div class="d-flex align-items-center mb-3 pb-1">
                                        <i class="fas fa-cubes fa-2x me-3" style="color: #ff6219;"></i>
                                        <span class="h1 fw-bold mb-0">Login</span>
                                    </div>

                                    <h5 class="fw-normal mb-3 pb-3" style="letter-spacing: 1px;">Accedi al tuo account!</h5>

                                    <div class="form-outline mb-4">
                                        <input type="email" id="form2Example17" name="email" class="form-control form-control-lg" />
                                        <label class="form-label" for="form2Example17">Email address</label>
                                    </div>

                                    <div class="form-outline mb-4">
                                        <input type="password" id="form2Example27" name="password" class="form-control form-control-lg" />
                                        <label class="form-label" for="form2Example27">Password</label>
                                    </div>

                                    <div class="pt-1 mb-4">
                                        <input type="submit" value="Login" class="btn btn-dark btn-lg btn-block">
                                    </div>

                                    <p class="mb-5 pb-lg-2" style="color: #393f81;">Non hai un account?
                                        <a href="Registrazione.jsp" style="color: #393f81;">Registrati qui!</a></p>
                                </form>

                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>

</body>
</html>
