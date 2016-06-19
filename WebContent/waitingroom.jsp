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
	 	<s:if test="%{#session.userlogindata.usertype == @struts2.model.UserType@DOCTOR}">
	 	
		<p>In your category <b><s:property value="yourcategory" /></b> are waiting <s:property value="waitingpatients" /> patients.</p>
		<s:if test="%{waitingpatients > 0}">
			<s:form action="chatdoctor" method="post">
		       <s:actionerror/>
			   <s:submit value="Serve next patient" align="left" />
		    </s:form>
	    </s:if>
    	<div class="clearer"><span></span></div>
    	</s:if>
    </div>
	<jsp:include page="sidenavigator.jsp" />
	<div class="clearer"><span></span></div>
  </div>
</div>
<jsp:include page="footer.jsp" />
</body>
</html>