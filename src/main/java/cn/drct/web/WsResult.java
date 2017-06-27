package cn.drct.web;



public class WsResult {
	private String status;// 状态值：success:成功;failed:失败
	private String msg="";// 失败的提示信息

	public WsResult() {
		status = Constant.Status.SUCCESS;
	}

	public WsResult(String status) {
		this.status = status;
	}

	public WsResult(String status, String errmsg) {
		this.status = status;
		this.msg = errmsg;
	}
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMsg() {
		return null == msg ? "" : msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	@Override
	public String toString() {
		return "{\"status\":\""+status+"\",\"msg\":\""+msg+"\"}";
	}
	
}
