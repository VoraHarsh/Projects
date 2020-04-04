<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.*" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title> Search Engine </title>
		
		<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css"
		integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
		
		
		<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.7.2/css/all.css">
		
		<style>
			
		 .input{
		  width: 500px;
		  border: 3px solid #fff;
		  background: transparent;
		  padding-left: 15px;
		  padding-bottom: 2px;
		   font-weight: bold;
		  border-radius: 50px;
		  outline: none;
		  font-size: 18px;
		  color: black;
		}
	::-webkit-input-placeholder { /* Chrome/Opera/Safari */
	  color: black;
	  
	}
		
	.close-btn{
			  position: absolute;
			  border-radius:20%;
			  border-color:transparent;
			  left: 550px;
			  outline: none;
			  text-transform: uppercase;
			  font-weight: bold;
			  justify-content: center;
			  background-color:white;
			  align-items: center;
			  transition: 0.4s;			 
			 cursor: pointer;
			}
			.input-group{
				position: relative;
				width: 100%;
				height: 41px;
				border: 1px solid black;
				border-radius: 20px;

			}
			.reg{
				background-color: black;
				margin-left: 200px;
				margin-right: 200px;
	
			}
				table {
				  font-family: arial, sans-serif;
				  border-collapse: collapse;
				  width: 100%;
				  background-color: white;
				}

				td, th {
				  border: 1px solid #dddddd;
				  text-align: center;
				  padding: 8px;
				}

				tr:nth-child(even) {
				  background-color: #dddddd;
				}
		</style>
		
		
		
	</head>
	
	<body>
	
		<div class="container-fluid" >
			<form action="result" method="post">
			
				<div class="row">
						<div class="col-sm-1" style="margin-left:10px">
							<a href="index.jsp"><img src="theaterarth.png" height="50px" width="100px"  style="margin-top:20px"></a>
						</div>
					
					<div class="col-sm-6" style="margin-left:20px">
						<div class="input-group" style="margin-top:20px">
							 <input type="text" class="input" name="search">
							<span class= "input-group-btn">
								<button input class="close-btn" type="submit" name="submit">
									<i class="fas fa-video fa-2x"></i>
								</button>
							</span>
						</div>
					</div>
				</div>
			</form>
		</div>
		<hr>
		<div class="reg">
	
		<form align="center" action="result.html" method="get">
			<table align="center">
			
				 <tr>
				    <th>Title</th>
				    <th>Genre</th>
				    <th>Release Date</th>
				  </tr>
			 
<%
            @SuppressWarnings("unchecked")
TreeMap<String, HashMap<String,String>> moviedata =(TreeMap<String, HashMap<String,String>>)request.getAttribute("movieData");
          
           	
			for(String id : moviedata.keySet())
            {
                %>  <tr>
				    <td>
				    <a href="<%= moviedata.get(id).get("movieLink") %>" target="_blank"><%= moviedata.get(id).get("title") %></a>
				    </td>
				    <td><%	
				    		if(moviedata.get(id).get("genre") == "")
				    		{%>
				    	<div>n/a</div>	
				    	<%	}
				    		else{%>
				   	<a href="<%= moviedata.get(id).get("genreLink") %>" target="_blank"><%= moviedata.get(id).get("genre") %></a>
				    		<%}
				    
				    %></td>
				    
				    <td><%
				    	if(moviedata.get(id).get("ReleaseDate") == "")
				    	{%>
				    <div>n/a</div>	
				    <% 	}
				    	else{%>
				   	<%= moviedata.get(id).get("ReleaseDate") %>
				    		<%}
				     %></td>
					</tr>				      
                <%
            }
     
        %>
				  
			</table>	
		</form>
		</div>





</body>
</html>