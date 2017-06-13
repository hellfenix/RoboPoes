<head>
	<title>Robopoes Login!</title>
	<link rel="stylesheet" type="text/css" href="/css/bootstrap.min.css">
	<link rel="stylesheet" type="text/css" href="/css/login.css">
</head>

<div class="container">
    <div class="card card-container">
        <img id="profile-img" class="profile-img-card" src="/img/avatar.png" />
        <p id="profile-name" class="profile-name-card"></p>
        <form class="form-signin" method="post">
            <input type="text" name="username" class="form-control" placeholder="Username" required autofocus>
            <input type="password" name="password" class="form-control" placeholder="Password" required>
            <button class="btn btn-lg btn-primary btn-block btn-signin" type="submit">Sign in</button>
        </form><!-- /form -->
    </div><!-- /card-container -->
</div><!-- /container -->