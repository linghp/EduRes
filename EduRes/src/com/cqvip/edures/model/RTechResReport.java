package com.cqvip.edures.model;

import com.cqvip.edures.base.BaseModel;

/**
 * 课题研究报告.. -题名 副题名 作者  授予学位  学位授予单位 导师姓名 授予年度 研究方向 中图分类号 关键词 文摘 基金项目 	-
 * 
 * @author luojiang
 * 
 */
public class RTechResReport extends BaseModel{
	
	
    	
	private String dc_title_null ;//关键词
	private String dc_date_issued;//发布时间
	public String getDc_title_null() {
		return dc_title_null;
	}
	public String getDc_date_issued() {
		return dc_date_issued;
	}
	
	
	
}
