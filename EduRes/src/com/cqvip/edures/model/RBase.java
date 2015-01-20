package com.cqvip.edures.model;

import java.util.Arrays;

public class RBase {
	private String title;
	private String nexttitle;
	private String abst;
	private Shortfile[] files;
	private String imgurl;
	
	public RBase(String title, String nexttitle, String abst, String imgurl) {
		super();
		this.title = title;
		this.nexttitle = nexttitle;
		this.abst = abst;
		this.imgurl = imgurl;
	}
	
	public RBase() {
		// TODO Auto-generated constructor stub
	}
	
	public RBase(String title, String nexttitle, String abst,
			Shortfile[] files, String imgurl) {
		super();
		this.title = title;
		this.nexttitle = nexttitle;
		this.abst = abst;
		this.files = files;
		this.imgurl = imgurl;
	}

	
	public Shortfile[] getFiles() {
		return files;
	}

	public void setFiles(Shortfile[] files) {
		this.files = files;
	}

	public String getTitle() {
		return title;
	}
	public String getNexttitle() {
		return nexttitle;
	}
	public String getAbst() {
		return abst;
	}
	public String getImgurl() {
		return imgurl;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public void setNexttitle(String nexttitle) {
		this.nexttitle = nexttitle;
	}
	public void setAbst(String abst) {
		this.abst = abst;
	}
	public void setImgurl(String imgurl) {
		this.imgurl = imgurl;
	}

	@Override
	public String toString() {
		return "RBase [title=" + title + ", nexttitle=" + nexttitle + ", abst="
				+ abst + ", files=" + Arrays.toString(files) + ", imgurl="
				+ imgurl + "]";
	}
	
	
}
