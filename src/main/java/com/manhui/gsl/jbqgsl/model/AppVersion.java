package com.manhui.gsl.jbqgsl.model;

import lombok.Data;

@Data
public class AppVersion {

	private String id;			//id
	
	private String version;		//版本号
	
	private String is_force;		//是否强制更新("0":不强制,"1":强制)
	
	private String url;			//下载地址
	
	private String file;			//文件回显
	
	private String channal;		//对应类别(1:IOS,2:安卓)
	
	private String create_time;	//创建时间
	
	private String update_time;	//修改时间
	
	private String introduce;	//更新内容说明
}
