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
        <s:if test="%{#session.userlogindata != null && #session.userlogindata.usertype == @struts2.model.UserType@ADMIN}">
		    <h1>Do you want to create the missing tables?</h1>
		    <p>
		        <s:form action="createTables" method="post">
				   <s:submit value="CONFIRM" align="left" ></s:submit>
			    </s:form>
			</p>
			<p><a href="<s:url value="admin.jsp"/>">Back to menu.</a></p>
	    </s:if>
	    <s:else>
	    	<p>Permission denied!</p>
	    </s:else>
      <div class="clearer"><span></span></div>
    </div>
	<jsp:include page="sidenavigator.jsp" />
	<div class="clearer"><span></span></div>
  </div>
</div>
<div class="footer">&copy; 2016 <a href="#">E-Healthcare</a>. Valid <a href="http://jigsaw.w3.org/css-validator/check/referer">CSS</a> &amp; <a href="http://validator.w3.org/check?uri=referer">XHTML</a>. Template design by <a href="http://arcsin.se">Arcsin</a> </div>
</body>
</html>