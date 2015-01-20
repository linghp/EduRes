package com.cqvip.edures.model;

import com.cqvip.edures.base.BaseModel;

/**
 * 教育指南
 * 
 * @author luojiang
 * 
 */
public class RNavthreeInfo extends BaseModel{

	private String dc_relation_uri;
	private String dc_title_null;
	private String dc_description_abstract;

	public String getDc_relation_uri() {
		return dc_relation_uri;
	}

	public void setDc_relation_uri(String dc_relation_uri) {
		this.dc_relation_uri = dc_relation_uri;
	}

	public String getDc_title_null() {
		return dc_title_null;
	}

	public void setDc_title_null(String dc_title_null) {
		this.dc_title_null = dc_title_null;
	}

	public String getDc_description_abstract() {
		return dc_description_abstract;
	}

	public void setDc_description_abstract(String dc_description_abstract) {
		this.dc_description_abstract = dc_description_abstract;
	}

}
