<%@ taglib prefix="s" uri="/struts-tags"%>
<div class="navigation"> 
<a href="index.jsp">Home</a>
<s:if test="%{#session.userlogindata != null}">
<a href="profile.jsp">My Profile</a> 
<s:if test="%{#session.userlogindata.usertype == @struts2.model.UserType@DOCTOR}">
<a href="<s:url action="waitingroom"/>">Look for patients</a> 
</s:if>
<s:if test="%{#session.userlogindata.usertype == @struts2.model.UserType@ADMIN}">
<a href="<s:url value="admin.jsp"/>">Admin Operations</a>
</s:if>
<s:if test="%{#session.userlogindata.usertype == @struts2.model.UserType@PATIENT}">
<a href="<s:url action="prechoosedoc"/>">Search Doctor</a>
</s:if>
</s:if>
<div class="clearer"><span></span></div>
</div>