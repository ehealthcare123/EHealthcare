package struts2.action;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;

import struts2.chatmapping.SessionMapper;
import struts2.model.UserLoginData;
import struts2.service.PatientSession;
import struts2.service.SendEmail;

@Results({ 	@Result(name = "success", location = "/index.jsp")})
public class SendReceiptAction extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4498264509300085094L;

	private String receipttext;

	
	@Action(value = "sendreceipt")
	public String execute() {
		SendEmail se = new SendEmail();
		UserLoginData uld = (UserLoginData) ActionContext.getContext().getSession().get("userlogindata");
		
		Integer patientID = PatientSession.getPatient(uld.hashCode());
		
		UserLoginData uldPatient = (UserLoginData) SessionMapper.getSession(patientID);
		
		se.send2(uldPatient.getUsername(), uld.getFullname(), uldPatient.getUsermail(), getReceipttext());
		
		return SUCCESS;
	}

	@RequiredStringValidator(message = "Please enter receipt text!")
	public String getReceipttext() {
		return receipttext;
	}


	public void setReceipttext(String receipttext) {
		this.receipttext = receipttext;
	}

}
