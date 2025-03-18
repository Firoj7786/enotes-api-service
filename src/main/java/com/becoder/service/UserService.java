package com.becoder.service;

import com.becoder.dto.ChangePasswordReq;
import com.becoder.util.CommonUtils;

public interface UserService {

	void changePassword(ChangePasswordReq changePass);
}
