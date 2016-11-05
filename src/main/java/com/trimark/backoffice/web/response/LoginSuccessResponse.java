package com.trimark.backoffice.web.response;

import com.trimark.backoffice.web.response.model.LoginSuccessModel;

public class LoginSuccessResponse extends BackofficeResponse<LoginSuccessModel> {
	
	private LoginSuccessModel output;
	
	public LoginSuccessResponse(LoginSuccessModel output) {
		setCode(0);
		this.output = output;
	}

	@Override
	public LoginSuccessModel getResponse() {
		return output;
	}
}
