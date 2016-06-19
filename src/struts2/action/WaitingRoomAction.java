package struts2.action;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import struts2.model.UserLoginData;
import struts2.service.CategoryMapper;
import struts2.service.WaitingQueueCategory;

@Results({ 	@Result(name = "success", location = "/chat.jsp"),
			@Result(name = "input", location = "/waitingroom.jsp") })
public class WaitingRoomAction extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4498264509300085094L;

	private Integer waitingpatients;
	private String yourcategory;

	public WaitingRoomAction() {
		yourcategory = (String) ActionContext.getContext().getSession().get("doctortype");
		setWaitingpatients(WaitingQueueCategory.getWaitingQueueSize(yourcategory));
	}

	public String getYourcategory() {
		return yourcategory;
	}

	public void setYourcategory(String yourcategory) {
		this.yourcategory = yourcategory;
	}

	public Integer getWaitingpatients() {
		return waitingpatients;
	}
	
	public void setWaitingpatients(Integer waitingpatients) {
		this.waitingpatients = waitingpatients;
	}
	
	@Action(value = "chatdoctor")
	public String execute() {
		UserLoginData uld = (UserLoginData) ActionContext.getContext().getSession().get("userlogindata");
		CategoryMapper.addCategory(uld.hashCode(), yourcategory);
		return SUCCESS;
	}

	@Action(value = "waitingroom")
	public String display() {
		return INPUT;
	}


}
