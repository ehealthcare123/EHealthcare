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
	 		<div id="waitingElements">Please wait a few moments.</div>
		    <div id="chatControls" style="display:none">
		        <input id="message" placeholder="Type your message">
		        <button id="send">Send</button>
		    </div>
			<div id="chat" style="display:none"> <!-- Built by JS --> </div>
    		<div class="clearer"><span></span></div>
    </div>
	<jsp:include page="sidenavigator.jsp" />
	<div class="clearer"><span></span></div>
  </div>
</div>
<jsp:include page="footer.jsp" />
<span id="sessionID" style="visibility:hidden"><s:property value="#session.strutssessionid" /></span>
<script src="js/chatService.js"></script>
</body>
</html>