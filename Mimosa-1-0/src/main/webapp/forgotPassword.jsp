<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	
	<title>Forgot Password</title>
	<link rel="stylesheet" href="bootstrap-4.4.1-dist/css/bootstrap.css">
	
	<style>
		Html, body
		{
			background: linear-gradient(180deg,#401a4c 0,#120413);
			height:100%;
		}
		.fp-block 
		{
			display:table; 
			height:100%; 
			margin: 0 auto;
		}
		.fp-sec 
		{
			/*display:table-cell;*/
			vertical-align:middle;
		}
		#mainContent
		{
			background: #ffffff;
			width: 400px;
    		padding: 20px;
    		border-radius: 10px;
		}
		.logoContainer
		{
			text-align: center;
		}
		.formHeader
		{
			color: #ffffff;
		    font-size: 1.6rem;
		    line-height: 1em;
		    font-weight: 600;
		    width: 100%;
		    text-align: center;
		    margin: auto;
		    height: 12px;
		    padding: 10px 0 40px!important;
		}
		.formText
		{
			color: #ffffff;
		}
		label.error
		{
			font-size: smaller;
			color: red;
		}
		.foot
		{
			bottom: 4px;
			color: #ffffff;
			position: fixed;
			width: 100%;
		}
	</style>
	<script src="js/jquery-3.4.1.min.js" type="text/javascript"></script>
	<script src="js/jquery.validate.min.js" type="text/javascript"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
	
	<script src="https://cdn.jsdelivr.net/jquery.validation/1.16.0/additional-methods.min.js"></script>
	<script src="https://use.fontawesome.com/529ae77798.js"></script>
	<script src="bootstrap-4.4.1-dist/js/bootstrap.js" type="text/javascript"></script>
	
	<script type="text/javascript">
	$(document).ready(function() 
	{
        $("#formfp").validate(
        {
        	rules: 
        	{
                email: 
                {
                    required: true,
                    email: true
                }      
            },
            messages: 
            {
                email: 
                {
                    required: "Please enter email",
                    email: "Please enter a valid email address"
                }
            }
        });
 
    });
	</script>
</head>
<body>
	<section class="fp-block">
		<div class="fp-sec">
			<div class="logoContainer">
				<img src="images/Shopisight Logo (Extended) - Orange_White.png" alt="ShopisightLogo" style="width:200px"/>
			</div>
			<div class="formHeader">
					Reset Password
				</div>
				<div class="formText">
					<p>Please provide your email address to reset your password.</p>
				</div>
			<div id="mainContent">
      			<form action="fpServlet" method="post" id="formfp">
					<div class="form-group">
						<label for="emailId">Email ID</label>
						<input type="email" class="form-control" name="emailId" id="emailId" placeholder="" pattern="[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,}$" minlength="13" maxlength="45" required/>
					</div>
					<input type="submit" class="btn btn-primary btn-block" id="btnFp" value="Send Email"/>
					<br/>
					<h5>${message}</h5>
				</form>
      		</div>
      	</div>
	</section>
	<div class="foot" align="center">
		<small>&copy;2020 Devise Math Solutions. All Rights Reserved</small>
	</div>	
</body>
</html>