package com.cqvip.edures.model;

import com.cqvip.edures.base.BaseModel;

/**
 * 政策教育，国内动态，国际动态
 * @author luojiang
 *
 */
public class RUrltwoInfo extends BaseModel{
	
    private String nb_string_address;
	private String dc_title_null;
	public String getNb_string_address() {
		return nb_string_address;
	}
	public void setNb_string_address(String nb_string_address) {
		this.nb_string_address = nb_string_address;
	}
	public String getDc_title_null() {
		return dc_title_null;
	}
	public void setDc_title_null(String dc_title_null) {
		this.dc_title_null = dc_title_null;
	}
	
}
