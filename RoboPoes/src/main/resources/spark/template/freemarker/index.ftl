<head>
	<title>RoboPoes</title>
	<link rel="stylesheet" type="text/css" href="/css/bootstrap.min.css">
	<link rel="stylesheet" type="text/css" href="/css/toggle-switch.css">
</head>

<div class="container" align="center">

<h1>ROBO POES</h1>

<br />

<div class="row">
	<video width ="480px" height="300px" poster="/img/poster.jpg"></video>
</div>

<br />

<div class="row">
	<button type="button" class="btn btn-primary" onClick='setDrivingMode("MANUAL");'>Manual</button>
	<button type="button" class="btn btn-primary" onClick='setDrivingMode("PATROL");'>Patrol</button>
	<button type="button" class="btn btn-primary" onClick='setDrivingMode("FOLLOW");'>Follow</button>
	<button type="button" class="btn btn-primary" onClick='setDrivingMode("CHARGE");'>Charge</button>
</div>
<br/>

<div class="row">
	<div class="col">
		<label class="switch">
		  <input type="checkbox">
		  <div class="slider round"></div>
		</label>
	</div>
	<div class="col">
		<strong>Toggle Video</strong>
	</div>
</div>

</div>

<script type="text/javascript" src="/js/jquery-3.2.1.min.js"></script>
<script>
	var webSocketControl = undefined;
	var webSocketData = undefined;
	
	$(function (){
		webSocketData = new WebSocket("ws://" + location.hostname + ":" + location.port + "/data/live");
		webSocketData.onopen = function() { console.log("Live data stream opened"); };
		webSocketData.onclose = function() { console.log("Live data stream closed"); };
		webSocketData.onmessage = function(msg) { updateData(msg.data); };
	});

	function onManualActivated() {
		if(webSocketControl === undefined){
			webSocketControl = new WebSocket("ws://" + location.hostname + ":" + location.port + "/control/manual");
			webSocketControl.onopen = function () { console.log("Manual control stream opened"); };
			webSocketControl.onclose = function () { console.log("Manual control stream closed"); };
			webSocketControl.onmessage = function (msg) { console.log("Message received: " + msg.data) };
		}
	}

	function setDrivingMode(mode){
		$.ajax({
		  url: "/api/driving/mode",
		  type: "POST",
		  contentType: "application/json",
		  data: JSON.stringify({"drivingMode" : mode}),
		  success: function(){
		  	console.log("success");
		  	if(mode === "MANUAL"){
		  		onManualActivated();
		  	}
		  },
		  error: function(){
		  	console.log("error");
		  }
		});
	}
	
	function updateData(jsonData) {
		console.log("Live data message received");
		console.log(jsonData);
	}
</script>