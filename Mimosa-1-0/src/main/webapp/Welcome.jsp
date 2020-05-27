<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% 
	if(session!=null)
	{
		//out.println("Session: "+session);
		if(session.getAttribute("emailId")!=null)
		{	
			//out.println("Email Id: "+session.getAttribute("emailId"));
			response.sendRedirect("overview.html");
		}
	}
	else
	{
		out.println("He He He");
	}
%>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	
	<title>Welcome</title>
	<link rel="stylesheet" href="bootstrap-4.4.1-dist/css/bootstrap.css">
	
	<style>
		.login-block 
		{
			background: #efefef;
			width: 100%;
			height: 100%;
			padding: 35px 50px 50px 50px;
		}
		.container 
		{
			background: #fff;
			border-radius: 10px;
			box-shadow: 10px 10px 0px rgba(0, 0, 0, 0.1);
		}
		.row
		{
			height: inherit;
		}
		.login-sec 
		{
			padding: 0px 60px 0px 60px;
			position: relative;
		}
		#logo
		{
			text-align:center;
		}
		#logo img
		{
			border-radius: 10px;
			margin-top: 10px;
			margin-bottom: 10px;
			width:200px;
		}
		#formRegister .form-group 
		{
		    margin-bottom: 5px;
		}
		#formRegister label
		{
			margin-bottom: 0px !important;
		}
		.form-check
		{
			padding :10px;
			text-align: center;
		}
		label.error
		{
			font-size: smaller;
			color: red;
		}
		#loginFailure
		{
			font-size: smaller;
			color: red;
		}
		#email_status
		{
			color: red;
			font-size: smaller;
		}
		.input-group-addon
		{
			width: 50px;
    		height: 38px;
    		text-align: center;
    		padding: 10px;
    		background: #efefef;
		}
		.btn
		{
			background-color: #f76738;
			border: none;
    		color: #ffffff;
			font-size: 20px;
			font-variant: all-petite-caps;
			font-weight: 500;
		}
		#btnRegister
		{
		    margin-top: 15px;
		}
		.carousel-sec 
		{
			border-radius: 0 10px 10px 0;
			padding: 0;
			text-align: end;
		}
		.carousel-inner 
		{
			border-radius: 0 10px 10px 0;
			height: 100%;
		}
		.carousel-item
		{
			
		}
		.carousel-item .gb
		{
			border-radius: 0 10px 10px 0;
		}
		.gb
		{
			border-radius: 0 10px 10px 0;
		}
		.foot
		{
			bottom: 4px;
			position: fixed;
			width: 100%;
		}
	</style>
	<script src="js/jquery-3.4.1.min.js" type="text/javascript"></script>
	<script src="js/jquery.validate.min.js" type="text/javascript"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
	
	<script src="https://cdn.jsdelivr.net/jquery.validation/1.16.0/additional-methods.min.js"></script>
	<script src="fontawesome-free-5.13.0-web/js/all.min.js"></script>
	<!-- <script src="https://use.fontawesome.com/529ae77798.js"></script> -->
	<script src="bootstrap-4.4.1-dist/js/bootstrap.js" type="text/javascript"></script>
	<script type="text/javascript">
		function fnLoginUser() 
		{
			if($("#formLogin").valid())
			{
				var lemailId = $("#lemailId").val();
				var lpassword = $("#lpassword").val();
				$("#formLogin").submit();
			}
		}
		function fnValidateRegistrationForm()
		{
			$("#formRegister").validate(
			{
				rules:
				{
					storeName:
					{
						required: true
					},
					emailId:
					{
						required: true,
						email: true,
						maxlength: 45
					},
					phoneNo:
					{
						maxlength: 15
					},
					password:
					{
						required: true,
						rangelength: [5,15]
					}
				}
			});
		}
		function fnRegisterUser()
		{
			fnValidateRegistrationForm();
			if($("#formRegister").valid())
			{
				var storeName = $("#storeName").val();
				var password = $("#password").val();
				var emailId = $("#emailId").val();
				var phoneNo = $("#phoneNo").val();
				
				
				$.post(
				{
					url : "RegistrationServlet",
					data : 
					{
						storeName : storeName,
						password : password,
						emailId : emailId,
						phoneNo : phoneNo
					},
					success : function(data) 
					{
						$("#registrationResponse").html($(data).find("Message").text());
					},
					error : function(data) 
					{
						$("#registrationResponse").html("Oops! Something went wrong. Please try again.");
					}
				});
			}
		}
		$(document).ready(function() 
		{
			$("#show_hide_password a").on('click', function(event) 
			{
		        event.preventDefault();
		        if($('#show_hide_password input').attr("type") == "text")
		        {
		            $('#show_hide_password input').attr('type', 'password');
		            $('#show_hide_password i').addClass( "fa-eye-slash" );
		            $('#show_hide_password i').removeClass( "fa-eye" );
		        }
		        else if($('#show_hide_password input').attr("type") == "password")
		        {
		            $('#show_hide_password input').attr('type', 'text');
		            $('#show_hide_password i').removeClass( "fa-eye-slash" );
		            $('#show_hide_password i').addClass( "fa-eye" );
		        }
		    });
		});
		function fnCheckEmailId()
		{
			var emailId = $("#emailId").val();
			if(!emailId=="")
			{
				$.ajax(
				{
					type: 'post',
					url: 'CheckEmailIdServlet',
					data: 
					{
						emailId : emailId,
					},
					success: function (data) 
					{
						$("#email_status").html($(data).find("Message").text());
					}
				});
			}
		}
	</script>
</head>
<body>
	<section class="login-block">
		<div class="container">
			<div class="row">
				<div class="col-12 col-md-12 col-lg-5 col-xl-5 login-sec">
					<div id="logo">
						<img src="images/Shopisight Logo (Extended) - Orange_White.png" alt="ShopisightLogo"/>
					</div>
					<ul class="nav nav-tabs nav-justified" role="tablist">
   						<li class="nav-item">
      						<a class="nav-link active" data-toggle="tab" href="#login">Login</a>
					    </li>
					    <li class="nav-item">
      						<a class="nav-link" data-toggle="tab" href="#register">Register</a>
    					</li>
  					</ul>
  					<div class="tab-content">
    					<div id="login" class="tab-pane active"><br>
      						<form action="LoginServlet" class="login-form" method="post" id="formLogin">
								<div class="form-group">
									<label for="lemailId">Email ID</label>
									<input type="email" class="form-control" name="lemailId" id="lemailId" placeholder="" pattern="[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,}$" minlength="13" maxlength="45" required/>
								</div>
								<div class="form-group">
									<label for="lpassword">Password</label>
									<div class="input-group" id="show_hide_password">
										<input type="password" class="form-control" name="lpassword" id="lpassword" placeholder="" minlength="5" maxlength="15" required/>
										<div class="input-group-addon">
											<a href=""><i class="fa fa-eye-slash" aria-hidden="true"></i></a>
										</div>
									</div>
									 <label id="lpassword-error" class="error" for="lpassword"></label>
								</div>
								<input type="button" class="btn btn-block" id="btnLogin" value="Login" onclick="fnLoginUser()"/>
								<br/>
								<div id="loginFailure">
									<% 
										if(session!=null)
										{
											if(session.getAttribute("loginFailure")!=null)
											{	
												out.println(session.getAttribute("loginFailure"));	
												session.removeAttribute("loginFailure");
											}
										}

									%>
								</div>
								<div class="form-check">
									<span><a href="forgotPassword.jsp">Forgot Password?</a></span>
								</div>
							</form>
      					</div>
					    <div id="register" class="tab-pane fade"><br>
					    	<form class="login-form" method="post" id="formRegister">
								<div class="form-group">
									<label for="storeName">Store Name</label>
									<input type="text" class="form-control" name="storeName" id="storeName" placeholder=""  minlength="5" maxlength="20" required/>
								</div>
								<div class="form-group">
									<label for="emailId">Email ID</label>
									<input type="email" class="form-control" name="emailId" id="emailId" placeholder="" minlength="13" maxlength="45" pattern="[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,}$" onchange="fnCheckEmailId()" required/>
									<span id="email_status"></span>
								</div>
								<div class="form-group">
									<label for="phoneNo">Phone Number <small>(optional)</small></label>
									<input type="tel" class="form-control" name="phoneNo" id="phoneNo" placeholder="" pattern="[0-9]{10}" minlength="10" maxlength="15"/>
								</div>
								<div class="form-group">
									<label for="password">Password</label>
									<input type="password" class="form-control" name="password" id="password" placeholder=""  minlength="5" maxlength="15" required/>
								</div>
								<input type="button" class="btn btn-block" id="btnRegister" value="Register" onclick="fnRegisterUser()"/>
								<div class="form-check">
									<span><small>By registering you agree to our <a href="#">Terms of Service</a> and <a href="#">Privacy Policy</a></small></span>
								</div>
							</form>
							<div id="registrationResponse" align="center"></div>
					    </div>
					</div>
				</div>
				<div class="d-none d-md-none d-lg-block col-lg-7 d-xl-block col-xl-7 carousel-sec" style="margin:auto">
					<div id="carouselIndicators" class="carousel slide" data-ride="carousel">
						<ul class="carousel-indicators">
							<li data-target="#carouselIndicators" data-slide-to="0" class="active"></li>
							<li data-target="#carouselIndicators" data-slide-to="1"></li>
							<li data-target="#carouselIndicators" data-slide-to="2"></li>
							<li data-target="#carouselIndicators" data-slide-to="3"></li>
							<li data-target="#carouselIndicators" data-slide-to="4"></li>
							<li data-target="#carouselIndicators" data-slide-to="5"></li>
						</ul>
						<div class="carousel-inner">
							<div class="carousel-item active">
								<img class="gb" src="images/animation.gif" alt="First slide">
							</div>
							<div class="carousel-item">
								<img class="gb" src="images/Main Page Final 2animation.gif" alt="First slide">
							</div>
							<div class="carousel-item">
								<img class="gb" src="images/Customer 3 animation.gif" alt="Second slide">
							</div>
							<div class="carousel-item">
								<img class="gb" src="images/Product animation.gif" alt="Third slide">
							</div>
							<div class="carousel-item">
								<img class="gb" src="images/Inventory Page Final animation.gif" alt="Fourth slide">
							</div>
							<div class="carousel-item">
								<img class="gb" src="images/Marketing animation.gif" alt="Fifth slide">
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</section>
	<div class="foot" align="center">
		<small>&copy;2020 Devise Math Solutions. All Rights Reserved</small>
	</div>	
</body>
</html>