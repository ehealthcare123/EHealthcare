<%@ taglib prefix="s" uri="/struts-tags"%>
<div class="sidenav">
    <h1>Sending receipts</h1>
    <s:form action="sendreceipt" method="post">
      Enter your receipts here:
      <s:textarea name="receipttext" cols="15" rows="9" />
      <s:submit value="End chat and send receipt" align="left" />
    </s:form>
</div>