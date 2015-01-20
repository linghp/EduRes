package com.cqvip.edures.model;

import com.cqvip.edures.base.BaseModel;

/**
 * 会议论文.. 文献名称  年  作者中文名  作者单位 会议名称 会议日期 会议地点  主办方 出版单位 出版日期 学会名称 中图分类号 中文关键字 中文摘要 基金项目
 * 
 * @author luojiang
 * 
 */
public class RTechResConference extends BaseModel{
	
	private String dc_title_null;//题名
	private String dc_subject_null;//关键词
	private String hy_string_MeetingDate;//会议时间
	private String hy_string_MeetingPlace;//会议地点
	private String hy_string_meetingname;//会议名称
	private String hy_string_HostOrganization;//主办方
	private String dc_string_CategoryNo;//z中图分类号
	private String dc_description_abstract;//简介
	private String dc_date_issued;//发布时间
	public String getDc_title_null() {
		return dc_title_null;
	}
	public String getDc_subject_null() {
		return dc_subject_null;
	}
	public String getHy_string_MeetingDate() {
		return hy_string_MeetingDate;
	}
	public String getHy_string_MeetingPlace() {
		return hy_string_MeetingPlace;
	}
	public String getHy_string_meetingname() {
		return hy_string_meetingname;
	}
	public String getHy_string_HostOrganization() {
		return hy_string_HostOrganization;
	}
	public String getDc_string_CategoryNo() {
		return dc_string_CategoryNo;
	}
	public String getDc_description_abstract() {
		return dc_description_abstract;
	}
	public String getDc_date_issued() {
		return dc_date_issued;
	}
	
	

	
	
	
}
