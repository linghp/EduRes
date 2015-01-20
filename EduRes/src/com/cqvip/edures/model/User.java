package com.cqvip.edures.model;

import java.util.ArrayList;
import java.util.List;

import com.cqvip.edures.base.BaseModel;

/**
 * 用户类
 * @author luojiang
 *
 */
public class User extends BaseModel{
	

    	
	private String user_name="";	
	private String phone;	
	private String email;	
	private String real_name;	
	private String shool;	
	private String qq;	
	private String mobile;	
	
	// single instance for login
		static private User user = null;
		// default is no login
		private boolean isLogin = false;
		
		
		public static User getUser() {
			return user;
		}
		public boolean isLogin() {
			return isLogin;
		}
		static public User getInstance () {
			if (User.user == null) {
				User.user = new User();
			}
			return User.user;
		}
	public User(){
		
	}	

	public String getUser_name() {
		return user_name;
	}

	public String getPhone() {
		return phone;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public void setReal_name(String real_name) {
		this.real_name = real_name;
	}
	public void setShool(String shool) {
		this.shool = shool;
	}
	public void setQq(String qq) {
		this.qq = qq;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public static void setUser(User user) {
		User.user = user;
	}
	public void setLogin(boolean isLogin) {
		this.isLogin = isLogin;
	}
	public String getEmail() {
		return email;
	}

	public String getReal_name() {
		return real_name;
	}

	public String getShool() {
		return shool;
	}

	public String getQq() {
		return qq;
	}

	public String getMobile() {
		return mobile;
	}
	
public ArrayList<String> getList(){
	ArrayList<String> arrayList=new ArrayList<String>();
	arrayList.add(user_name);
	arrayList.add(phone);
	arrayList.add(email);
	arrayList.add(real_name);
	arrayList.add(shool);
	arrayList.add(qq);
	arrayList.add(mobile);
	return arrayList;
}
	@Override
	public String toString() {
		return "User [user_name=" + user_name + ", phone=" + phone + ", email="
				+ email + ", real_name=" + real_name + ", shool=" + shool
				+ ", qq=" + qq + ", mobile=" + mobile + "]";
	}
	
}
