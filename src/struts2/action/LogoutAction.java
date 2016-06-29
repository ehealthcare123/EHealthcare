package struts2.action;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import struts2.chatmapping.SessionMapper;
import struts2.model.UserLoginData;

@Results({ @Result(name = "success", location = "/index.jsp")
	})
public class LogoutAction extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1507160479129689109L;

	@Action(value = "logout")
	public String execute() {
		UserLoginData uld = (UserLoginData) ActionContext.getContext().getSession().get("userlogindata");
		if(uld != null){
			SessionMapper.removeSession(uld.hashCode());
			ActionContext.getContext().getSession().remove("userlogindata");			
		}
		return SUCCESS;			
	}
}