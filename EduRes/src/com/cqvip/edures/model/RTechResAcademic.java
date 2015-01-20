package com.cqvip.edures.model;

import com.cqvip.edures.base.BaseModel;

/**
 * 学位论文.. -题名 副题名 作者  授予学位  学位授予单位 导师姓名 授予年度 研究方向 中图分类号 关键词 文摘 基金项目 	-
 * 
 * @author luojiang
 * 
 */
public class RTechResAcademic extends BaseModel{
	
	
    	
	private String dc_subject_null;//关键词
	private String dc_string_CategoryNo;//中图分类号
	private String dc_title_null;//题名
	private String dc_description_abstract;//简介
	private String dc_date_issued;//发布时间
	private String dc_contributor_author;//作者
	public String getDc_subject_null() {
		return dc_subject_null;
	}
	public String getDc_string_CategoryNo() {
		return dc_string_CategoryNo;
	}
	public String getDc_title_null() {
		return dc_title_null;
	}
	public String getDc_description_abstract() {
		return dc_description_abstract;
	}
	public String getDc_date_issued() {
		return dc_date_issued;
	}
	public String getDc_contributor_author() {
		return dc_contributor_author;
	}
	
	
	
}
