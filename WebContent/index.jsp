<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
   pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" 
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>E-Healthcare</title>
<meta http-equiv="content-type" content="text/html; charset=iso-8859-1"/>
<link rel="stylesheet" type="text/css" href="css/default.css" media="screen"/>
</head>
<body>
<div class="container">
  <div class="header">
    <div class="title">
      <h1>E-Healthcare Web Application</h1>
    </div>
    <jsp:include page="navigation.jsp" />
  </div>
  <div class="main">
    <div class="content">
      <h1>Welcome to E-Healthcare</h1>
      <div class="descr">Jun 11, 2016 by SWP Team 9</div>
      <p>E-Healthcare is a web application in which patients are able to chat with a doctor.</p>
      <cite>Maybe one of the best applications in the world! -Dr. Morph Ium</cite>
      <div class="clearer"><span></span></div>
    </div>
	<jsp:include page="sidenavigator.jsp" />
	<div class="clearer"><span></span></div>
  </div>
</div>
<jsp:include page="footer.jsp" />
</body>
</html>