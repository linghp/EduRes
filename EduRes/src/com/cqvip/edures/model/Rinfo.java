package com.cqvip.edures.model;

import org.json.JSONObject;

import android.text.TextUtils;

import com.cqvip.edures.base.BaseMessage;
import com.cqvip.edures.base.BaseModel;

public class Rinfo extends BaseModel{
	
	private String dc_title_null;//题名
	private String dc_description_abstract;//简介
	private String dc_contributor_author;//作者
	private String dc_subject_null;//关键词
	private String dc_date_issued;//发布时间
	private String zk_string_namecn;//中文刊名
	private String zk_string_year;//年
	private String zk_string_Vol;//卷
	private String zk_string_num;//期
	private String dc_publisher_null;//出版社
	private String zk_string_Organ;//机构
	private String zk_string_FirstWriter;//第一作者
	private String zk_string_FirstOrgan;//第一作者单位
	private String bs_string_References;//参考文献
	private String zk_string_sci;//收录数据库
	private String bs_string_Degree;//授予单位
	private String bs_string_Unit;//学位授予单位
	private String bs_string_TutorsName;//导师姓名
	private String bs_string_DegreeYear;//授予年度
	private String bs_string_StudyDirection;//研究方向
	
	private String bs_string_FundProject;//基金项目
	private String hy_string_meetingname;//会议名称
	private String hy_string_MeetingPlace;//会议地点
	private String hy_string_MeetingDate;//会议地点
	public String getHy_string_MeetingDate() {
		return hy_string_MeetingDate;
	}
	private String hy_string_HostOrganization;//主办方
	private String hy_string_PressOrganization;//出版单位
	private String hy_string_PressDate;//出版日期
	public String getHy_string_PressDate() {
		return hy_string_PressDate;
	}
	private String dc_string_CategoryNo;//中图分类号

	private String nb_string_sub;	//类型
	private String dc_type_null;	//类型
	private String nb_mlk_address;	//学校地址
	private String nb_mlk_code;	//邮政编码
	private String nb_mlk_telephone;	//联系电话
	private String nb_mlk_history;	//历史沿革
	private String nb_mlk_idea;	//办学理念
	private String nb_mlk_characteristic;	//办学特色
	private String nb_mlk_teaconstruct;	//师资建设
	private String nb_mlk_mottoandsong;	//校训校歌
	private String nb_mlk_fox;	//学校传真
	private String nb_mlk_introduction;	//学校简介
	private String nb_mlk_category;	//类别
	private String nb_zlk_other;	//备注
	private String nb_mlk_website;	//网址
	private String nb_xmk_researchdirect;	//研究与培训方向
	private String nb_xmk_contact;	//联系方式
	private String nb_source_null;	//来源
	private String dc_relation_uri;	//原文链接
	private String nb_string_address;	//原文链接
	
	
	public String getDc_type_null() {
		return dc_type_null;
	}
	public String getNb_string_address() {
		return nb_string_address;
	}
	public String getDc_relation_uri() {
		return dc_relation_uri;
	}
	public String getNb_mlk_characteristic() {
		return nb_mlk_characteristic;
	}
	public String getDc_title_null() {
		return dc_title_null;
	}
	public String getDc_description_abstract() {
		return dc_description_abstract;
	}
	public String getDc_contributor_author() {
		return dc_contributor_author;
	}
	public String getDc_subject_null() {
		return dc_subject_null;
	}
	public String getDc_date_issued() {
		return dc_date_issued;
	}
	public String getZk_string_namecn() {
		return zk_string_namecn;
	}
	public String getZk_string_year() {
		return zk_string_year;
	}
	public String getZk_string_Vol() {
		return zk_string_Vol;
	}
	public String getZk_string_num() {
		return zk_string_num;
	}
	public String getDc_publisher_null() {
		return dc_publisher_null;
	}
	public String getZk_string_Organ() {
		return zk_string_Organ;
	}
	public String getZk_string_FirstWriter() {
		return zk_string_FirstWriter;
	}
	public String getZk_string_FirstOrgan() {
		return zk_string_FirstOrgan;
	}
	public String getBs_string_References() {
		return bs_string_References;
	}
	public String getZk_string_sci() {
		return zk_string_sci;
	}
	public String getBs_string_Degree() {
		return bs_string_Degree;
	}
	public String getBs_string_Unit() {
		return bs_string_Unit;
	}
	public String getBs_string_TutorsName() {
		return bs_string_TutorsName;
	}
	public String getBs_string_DegreeYear() {
		return bs_string_DegreeYear;
	}
	public String getBs_string_StudyDirection() {
		return bs_string_StudyDirection;
	}
	public String getBs_string_FundProject() {
		return bs_string_FundProject;
	}
	public String getHy_string_meetingname() {
		return hy_string_meetingname;
	}
	public String getHy_string_MeetingPlace() {
		return hy_string_MeetingPlace;
	}
	public String getHy_string_HostOrganization() {
		return hy_string_HostOrganization;
	}
	public String getHy_string_PressOrganization() {
		return hy_string_PressOrganization;
	}
	public String getDc_string_CategoryNo() {
		return dc_string_CategoryNo;
	}
	public String getNb_string_sub() {
		return nb_string_sub;
	}
	public String getNb_mlk_address() {
		return nb_mlk_address;
	}
	public String getNb_mlk_code() {
		return nb_mlk_code;
	}
	public String getNb_mlk_telephone() {
		return nb_mlk_telephone;
	}
	public String getNb_mlk_history() {
		return nb_mlk_history;
	}
	public String getNb_mlk_idea() {
		return nb_mlk_idea;
	}
	public String getNb_mlk_teaconstruct() {
		return nb_mlk_teaconstruct;
	}
	public String getNb_mlk_mottoandsong() {
		return nb_mlk_mottoandsong;
	}
	public String getNb_mlk_fox() {
		return nb_mlk_fox;
	}
	public String getNb_mlk_introduction() {
		return nb_mlk_introduction;
	}
	public String getNb_mlk_category() {
		return nb_mlk_category;
	}
	public String getNb_zlk_other() {
		return nb_zlk_other;
	}
	public String getNb_mlk_website() {
		return nb_mlk_website;
	}
	public String getNb_xmk_researchdirect() {
		return nb_xmk_researchdirect;
	}
	public String getNb_xmk_contact() {
		return nb_xmk_contact;
	}
	public String getNb_source_null() {
		return nb_source_null;
	}

	public static  RBase formNexttitle(String response) throws Exception {
		JSONObject json = new JSONObject(response);
		BaseMessage base = new BaseMessage();
		String result = json.getString("dcvalue");
		Rinfo res = (Rinfo) base.getRinfoResult(result,"Rinfo");
		String title = res.getDc_title_null();
		
		String t1 = "作者：";
		String t2 = "关键词：";
		String t3 = "中文刊名：";
		String t4 = "年/期：";
		String t5 = "卷：";
		String t6 = "分类号：";
		String t7 = "机构：";
		String t8 = "第一作者：";
		String t9 = "第一作者单位：";
		String t10 = "收录数据库：";
		String t11 = "授予单位：";
		String t12 = "学位授予单位";
		String t13 = "导师姓名";
		String t14 = "授予年度";
		String t15 = "研究方向";
		String t16 = "中图分类号";
		String t17 = "项目基金";
		String t18 = "会议名称：";
		String t19 = "会议时间：";
		String t20 = "会议地点：";
		String t21 = "主办方：";
		String t22 = "出版单位：";
		String t23 = "出版日期：";
		String t24 = "参考资料：";
		String t25 = "原文地址：";
		String t26 = "类型：";
		String t27 = "学校地址：";
		String t28 = "邮编：";
		String t29 = "联系电话：";
		String t30 = "历史沿革：";
		String t31 = "办学理念：";
		String t32 = "办学特色：";
		String t33 = "师资建设：";
		String t34 = "校训校歌：";
		String t35 = "学校传真：";
		String t36 = "类别：";
		String t37 = "备注：";
		String t38 = "网站：";
		String tt1 =formfield(res.getDc_contributor_author(), t1);
		String tt2 =formfield(res.getDc_subject_null(), t2);
		String tt3 =formfield(res.getZk_string_namecn(), t3);
		String tt4 =formfield(addfield(res.getZk_string_year(),res.getZk_string_num()), t4);
		String tt5 =formfield(res.getZk_string_Vol(), t5);
		String tt6 =formfield(res.getDc_string_CategoryNo(), t6);
		String tt7 =formfield(res.getZk_string_Organ(), t7);
		String tt8 =formfield(res.getZk_string_FirstWriter(), t8);
		String tt9 =formfield(res.getZk_string_FirstOrgan(), t9);
		String tt10 =formfield(res.getZk_string_sci(), t10);
		String tt11 =formfield(res.getBs_string_Degree(), t11);
		String tt12 =formfield(res.getBs_string_Unit(), t12);
		String tt13 =formfield(res.getBs_string_TutorsName(), t13);
		String tt14 =formfield(res.getBs_string_DegreeYear(), t14);
		String tt15 =formfield(res.getBs_string_StudyDirection(), t15);
		String tt16 =formfield(res.getDc_string_CategoryNo(), t16);
		String tt17=formfield(res.getBs_string_FundProject(), t17);
		
		String tt18 =formfield(res.getHy_string_meetingname(), t18);
		String tt19 =formfield(res.getHy_string_MeetingDate(), t19);
		String tt20 =formfield(res.getHy_string_MeetingPlace(), t20);
		String tt21 =formfield(res.getHy_string_HostOrganization(), t21);
		String tt22 =formfield(res.getHy_string_PressOrganization(), t22);
		String tt23=formfield(res.getHy_string_PressDate(), t23);
		String tt24 =formfield(res.getBs_string_References(), t24);
		String tt25 =formfield(res.getNb_string_address(), t25);
		String tt26 =formfield(res.getNb_string_sub(), t26);
		
		String tt27 =formfield(res.getNb_mlk_address(), t27);
		String tt28 =formfield(res.getNb_mlk_code(), t28);
		String tt29 =formfield(res.getNb_mlk_telephone(), t29);
		String tt30 =   formfield(res.getNb_mlk_history(), t30);
		
		String tt31 =formfield(res.getNb_mlk_idea(), t31);
		String tt32 =formfield(res.getNb_mlk_characteristic(), t32);
		
		String tt33 =formfield(res.getNb_mlk_teaconstruct(), t33);
		String tt34 =formfield(res.getNb_mlk_mottoandsong(), t34);
		String tt35 =formfield(res.getNb_mlk_fox(), t35);
		String tt36 =formfield(res.getNb_string_sub(), t36);
		String tt37 =formfield(res.getNb_zlk_other(), t37);
		String tt38 =formfield(res.getNb_mlk_website(), t38);
		
		String nexttitle = null;
		StringBuilder builder = new StringBuilder();
		builder.append(tt1);
		builder.append(tt2);
		builder.append(tt3);
		builder.append(tt4);
		builder.append(tt5);
		builder.append(tt6);
		builder.append(tt7);
		builder.append(tt8);
		builder.append(tt9);
		builder.append(tt10);
		builder.append(tt11);
		builder.append(tt12);
		builder.append(tt13);
		builder.append(tt14);
		builder.append(tt15);
		builder.append(tt16);
		builder.append(tt17);
		builder.append(tt18);
		builder.append(tt19);
		builder.append(tt20);
		builder.append(tt21);
		builder.append(tt22);
		builder.append(tt23);
		builder.append(tt24);
		builder.append(tt25);
		builder.append(tt26);
		builder.append(tt27);
		builder.append(tt28);
		builder.append(tt29);
		builder.append(tt30);
		builder.append(tt31);
		builder.append(tt32);
		builder.append(tt33);
		builder.append(tt34);
		builder.append(tt35);
		builder.append(tt36);
		builder.append(tt37);
		builder.append(tt38);
		nexttitle = builder.toString();
		String abst = res.getDc_description_abstract();
		 return new RBase(title,nexttitle,abst,null);
	}

private static String addfield(String zk_string_year, String zk_string_num) {
   String str = "";
	if(!TextUtils.isEmpty(zk_string_year)&&!TextUtils.isEmpty(zk_string_num)){
	   str = zk_string_year+","+zk_string_num;
   }
	return str;
}

private static String formfield(String res, String t1) {
	String tt1;
	if(TextUtils.isEmpty(res)){
		tt1 = "";
	}else{
		tt1 = t1+res+"\n";
	}
	return tt1;
}
}
