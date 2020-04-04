<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
	<style>
			body, html {
			  height: 100%;
			  margin: 0;
			  padding: 0;
			}			
			.bg {
			  /* The image used */
			  background-image: url("theatereditpng.png");

			  /* Full height */
			  height: 100%; 

			  /* Center and scale the image nicely */
			  background-position: center;
			  background-repeat: no-repeat;
			  background-size: cover;
			}
			

			.search_box {
			 position: absolute;
			 top: 60%;
			 left: 50%;
			 transform: translate(-50%, -50%);
			 background: #ffffff;
			 height: 40px;
			 border-radius: 40px;
			 padding: 10px;
			}

			.search_box:hover > .input_box {
			 width: 240px;
			 padding: 0 6px;
			}

			.search_box:hover > .btn {
			 background: #ff0000;
			 color: black;
			}

			.btn {
			 color:  black;
			 float: right;
			 width: 40px;
			 height: 40px;
			 border-radius: 50%;
			 background: #ff0000;
			 display: flex;
			 justify-content: center;
			 align-items: center;
			 transition: 0.4s;
			 
			 cursor: pointer;
			}

			.input_box {
			 border: none;
			 background: none;
			 outline: none;
			 float: left;
			 padding: 0;
			 color: black;
			 font-size: 16px;
			 transition: 0.4s;
			 line-height: 40px;
			 width: 0px;
			 font-weight: bold;
			}﻿
			
		</style>
		<title>Arth Search</title>
		
		<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.7.2/css/all.css">
	</head>
	<body onload="ld()">
		<script>
			function ld()
			{	
				document.search_box.search.focus();
			}
			function validate(){
				var search=document.search_box.search.value;
				if (search == "") {
					alert("Please Enter the Search");
					document.search_box.search.value.focus();
					return false;
				}
				if (search.length<7) {
					alert("Please Enter valid search");
					document.search_box.search.value.focus();
					return false;
				}
			}
			
		</script>
		<div class="bg"></div>
	
	<form name="search_box" action="result" method="post">
		
		<div class="search_box">
			<input class="input_box" type="text" name="search" placeholder="Semantic Search">
			<button class="btn" type="submit" value="Semantic Search" name='submit' onclick="return validate();">
				<i class="fas fa-video"></i>
			</button>
		</div>
	</form>
	</body>
</html>