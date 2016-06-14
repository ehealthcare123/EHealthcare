<%@ taglib prefix="s" uri="/struts-tags"%>
<div class="sidenav">
    <s:if test="%{#session.userlogindata == null}">
    <h1>Login</h1>
    <s:form action="login" method="post">
       <s:actionerror/>
	   <s:textfield name="loginname" label="Name" size="20" />
	   <s:password name="password" label="Password" size="20" />
	   <s:submit value="Submit" align="left" />
    </s:form>
	<p><a href="<s:url value="register.jsp"/>">Not registered yet?</a></p>
    </s:if>
    <s:else>
       <p>Logged in as <s:property value="#session.userlogindata.username" /></p>
       <p>Your role is <s:property value="#session.userlogindata.usertype" /></p>
       <p><a href="<s:url action="logout"/>">Logout</a></p>
    </s:else>
</div>