<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	
	<title>Settings</title>
	<link rel="stylesheet" href="bootstrap-4.4.1-dist/css/bootstrap.css">
	<link rel="stylesheet" href="fontawesome-free-5.13.0-web/css/all.css">
	
	<style>
	#logo
	{
		text-align:center;
	}
	#logo img
	{
		border-radius: 10px;
		width:40px;
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
		width: 400px;
    	padding: 20px;
    	border-radius: 10px;
		box-shadow: 2px 2px 10px 5px lightgrey;
	}
	.logoContainer
	{
		text-align: center;
	}
	.formHeader
	{
		font-size: 1.6rem;
		line-height: 1em;
		font-weight: 600;
		width: 100%;
		text-align: center;
		margin: auto;
		height: 12px;
		padding: 10px 0 40px!important;
	}
	label.error
	{
		font-size: smaller;
		color: red;
	}
	</style>
		
	<script src="js/jquery-3.4.1.min.js" type="text/javascript"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/malihu-custom-scrollbar-plugin/3.1.5/jquery.mCustomScrollbar.concat.min.js"></script>
	<script src="fontawesome-free-5.13.0-web/js/all.js" type="text/javascript"></script>
	<script src="bootstrap-4.4.1-dist/js/bootstrap.js" type="text/javascript"></script>
</head>
<body>
		<header>
			<nav class="navbar navbar-expand navbar-dark bg-dark">
				<div id="logo">
					<!-- <img src="images/Shopisight Logo (Stacked) - White_Orange Gradient.png" alt="ShopisightLogo"/>-->
					<img src="images/Shopisight Logo (App) - White_Orange Gradient.png" alt="ShopisightLogo"/>
	  			</div>
				<div class="collapse navbar-collapse justify-content-between" id="navbarSupportedContent">
	    			<ul class="navbar-nav">
	    				<li class="nav-item active">
	      					<a class="nav-link tag" href="#">
								<span class="icon">
									<i class="fas fa-rocket"></i>
								</span>
								<span>Growth&nbsp;—&nbsp;Trial
									<small class="icon" data-toggle="tooltip" data-placement="right" title="Continues on Free Forever after trial">
										<i class="fas fa-info-circle"></i>
									</small>
									<span class="sr-only">(current)</span>
								</span>
							</a>
	      				</li>
	      			</ul>
	      			<ul class="navbar-nav ml-auto">
	      				<li class="nav-item dropdown">
	        				<a class="nav-link dropdown-toggle" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
								<span class="icon"><i class="fas fa-user"></i></span>
								<span>Kiranmayi</span>&nbsp;&nbsp;
	        				</a>
	        				<div class="dropdown-menu dropdown-menu-right" aria-labelledby="navbarDropdown" style="margin-top: 12px;">
	          					<a class="dropdown-item" href="#">Change Password</a>
	          					<a class="dropdown-item" href="#">Manage stores</a>
	          					<div class="dropdown-divider"></div>
	          					<a class="dropdown-item" href="Welcome.jsp">Log out</a>
	        				</div>
						</li>
					</ul>
				</div>
			</nav>
		</header>
	<section class="fp-block">
		<div class="fp-sec">
			<br/>
			<div class="formHeader">
				Change Password
			</div>
			<div id="mainContent">
      			<form action="ChangePasswordServlet" method="post" id="formfp">
      			<div class="form-group">
						<label for="emailId">Email Id</label>
						<input type="email" class="form-control" name="emailId" id="emailId" placeholder="" pattern="[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,}$" minlength="13" maxlength="45" required/>
					</div>
					<div class="form-group">
						<label for="currentPassword">Current Password</label>
						<input type="password" class="form-control" name="currentPassword" id="currentPassword" placeholder="" minlength="5" maxlength="15" required/>
					</div>
					<div class="form-group">
						<label for="newPassword">New Password</label>
						<input type="password" class="form-control" name="newPassword" id="newPassword" placeholder="" minlength="5" maxlength="15" required/>
					</div>
					<div class="form-group">
						<label for="confirmNewPassword">Confirm New Password</label>
						<input type="password" class="form-control" name="confirmNewPassword" id="confirmNewPassword" placeholder="" minlength="5" maxlength="15" required/>
					</div>
					<input type="submit" class="btn btn-primary btn-block" id="btnFp" value="Update Password"/>
					<br/>
					<p>${message}</p>
				</form>
      		</div>
      	</div>
	</section>
</body>
</html>