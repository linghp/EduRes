package com.cqvip.edures.base;

import com.cqvip.edures.model.User;

/**
 * 验证是否已经登陆
 * @author luojiang
 *
 */
public class BaseAuth {
	/**
	 * 判断是否登录，true表示登录，false表示未登陆
	 * @return
	 */
	static public boolean isLogin () {
		User user = User.getInstance();
		if (user.isLogin() == true) {
			return true;
		}
		return false;
	}
	/**
	 * 设置登录表示
	 * @param status
	 */
	static public void setLogin (Boolean status) {
		User user = User.getInstance();
		user.setLogin(status);
	}
	
	static public void setUser (User mc) {
		User user = User.getInstance();
		if(mc!=null){
		user.setUser_name(mc.getUser_name());
		user.setEmail(mc.getEmail());
		user.setMobile(mc.getMobile());
		user.setPhone(mc.getPhone());
		user.setQq(mc.getQq());
		user.setReal_name(mc.getReal_name());
		user.setShool(mc.getShool());
		}
	}
	
	static public User getUser () {
		return User.getInstance();
	}

}
