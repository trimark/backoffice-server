package com.trimark.backoffice.web.response;

import com.trimark.backoffice.model.LoginSuccessModel;

public class LoginSuccessResponse extends BackofficeResponse<LoginSuccessModel> {
	
	private LoginSuccessModel model;
	
	public LoginSuccessResponse(LoginSuccessModel model) {
		setCode(0);
		this.model = model;
	}

	@Override
	public LoginSuccessModel getData() {
		return model;
	}
}
