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
      <h1>Add a new medical category to system:</h1>
        <s:form action="addcategory" method="post">
	       <s:actionerror/>
		   <s:textfield name="category" label="Category" size="20" />
		   <s:submit value="Submit" align="left" />
	    </s:form>
	    <p><a href="<s:url value="admin.jsp"/>">Back to menu.</a></p>
	    <p><s:actionerror/></p>
	    </s:if>
      <div class="clearer"><span></span></div>
    </div>
	<jsp:include page="sidenavigator.jsp" />
	<div class="clearer"><span></span></div>
  </div>
</div>
<jsp:include page="footer.jsp" />
</body>
</html>