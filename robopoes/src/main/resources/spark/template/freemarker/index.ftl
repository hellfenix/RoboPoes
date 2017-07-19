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
		<button id="manualBtn" type="button" class="btn btn-primary" onClick='setDrivingMode("MANUAL");'>Manual</button>
		<button id="patrolBtn" type="button" class="btn btn-primary" onClick='setDrivingMode("PATROL");'>Patrol</button>
		<button id="followBtn" type="button" class="btn btn-primary" onClick='setDrivingMode("FOLLOW");'>Follow</button>
		<button id="chargeBtn" type="button" class="btn btn-primary" onClick='setDrivingMode("CHARGE");'>Charge</button>
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
<script type="text/javascript">
	var webSocketControl = undefined;
	var webSocketData = undefined;
	
	$(function (){
		<#if drivingMode??>
			$("#${drivingMode}Btn").addClass("btn-danger");
		</#if>
		webSocketData = new WebSocket("ws://" + location.hostname + ":" + location.port + "/data/live");
		webSocketData.onopen = function() { console.log("Live data stream opened"); };
		webSocketData.onclose = function() { console.log("Live data stream closed"); };
		webSocketData.onmessage = function(msg) { updateData(msg.data); };
		
		<#if drivingMode == "manual">
			onManualActivated();
		</#if>
	});

	function onManualActivated() {
		if(webSocketControl === undefined){
			webSocketControl = new WebSocket("ws://" + location.hostname + ":" + location.port + "/control/manual");
			webSocketControl.onopen = function () { console.log("Manual control stream opened"); };
			webSocketControl.onclose = function () { console.log("Manual control stream closed"); };
			webSocketControl.onmessage = function (msg) { console.log("Message received: " + msg.data) };
			
			$(document).on('keydown', function(evt){
				switch(evt.which){
					case 37:
						webSocketControl.send("LEFT");
						evt.preventDefault();
						break;
					case 38:
						webSocketControl.send("FORWARD");
						evt.preventDefault();
						break;
					case 39:
						webSocketControl.send("RIGHT");
						evt.preventDefault();
						break;
					case 40:
						webSocketControl.send("BACKWARDS");
						evt.preventDefault();
						break;
				}
			});
		}
	}
	
	function onManualDeactivated() {
		if(webSocketControl !== undefined){
  			webSocketControl.close();
  			webSocketControl = undefined;
  			
  			$(document).unbind('keydown');
  		}	
	}

	function setDrivingMode(mode){
		$.ajax({
		  url: "/api/driving/mode",
		  type: "POST",
		  contentType: "application/json",
		  data: JSON.stringify({"drivingMode" : mode}),
		  success: function(){
		  	$(".btn-danger").removeClass("btn-danger");
		  	$("#" + mode.toLowerCase() + "Btn").addClass("btn-danger");
		  	
		  	if(mode === "MANUAL"){
		  		onManualActivated();
		  	} else {
		  		onManualDeactivated();
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