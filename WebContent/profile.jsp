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
      <s:if test="%{#session.userlogindata != null}">
      <h1>Edit your profile :</h1>
        <s:form action="profile" method="post">
	       <s:actionerror/><s:actionmessage/>
		   <s:password  name="password" value="%{#session.userlogindata.userpassword}" label="Password" size="20" />
		   <s:password  name="password2" value="%{#session.userlogindata.userpassword}" label="Reenter Password" size="20" />
		   <s:textfield name="firstname" value="%{#session.userlogindata.firstname}" label="First Name" size="20" />
		   <s:textfield name="surname" value="%{#session.userlogindata.surname}" label="Surname" size="20" />
		   <s:textfield name="usermail" value="%{#session.userlogindata.usermail}" label="Your E-Mail Address" size="20"  />
		   <s:submit value="Change profile" align="left" />
	    </s:form>
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
<jsp:include page="footer.jsp" />
</body>
</html>