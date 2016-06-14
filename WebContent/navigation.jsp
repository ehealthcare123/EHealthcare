<%@ taglib prefix="s" uri="/struts-tags"%>
<div class="navigation"> 
<a href="index.jsp">Home</a>
<s:if test="%{#session.userlogindata != null}">
<a href="#">My Profile</a> 
<s:if test="%{#session.userlogindata.usertype == @struts2.model.UserType@PATIENT || #session.userlogindata.usertype == @struts2.model.UserType@DOCTOR }">
<a href="#">Waiting Room</a> 
</s:if>
<s:if test="%{#session.userlogindata.usertype == @struts2.model.UserType@ADMIN}">
<a href="#">Admin Operations</a> 
</s:if>
<s:if test="%{#session.userlogindata.usertype == @struts2.model.UserType@PATIENT}">
<a href="#">Search Doctor</a>
</s:if>
</s:if>
<div class="clearer"><span></span></div>
</div>