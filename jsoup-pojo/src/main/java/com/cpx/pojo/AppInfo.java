package com.cpx.pojo;

import java.sql.Date;

public class AppInfo {

	
	private String id;
	private String name;
	private String type;
	private String typeId;
	private String appSize;
	private Date updateTime;
	private String appSystem;
	private String appIcon;
	private String appApkPath;
	private String appIOSPath;
	private int status;
	private int templateStatus;
	private int view_count;
	private Date create_time;
	private Date update_time;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getTypeId() {
		return typeId;
	}
	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}
	public String getAppSize() {
		return appSize;
	}
	public void setAppSize(String appSize) {
		this.appSize = appSize;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public String getAppSystem() {
		return appSystem;
	}
	public void setAppSystem(String appSystem) {
		this.appSystem = appSystem;
	}
	public String getAppIcon() {
		return appIcon;
	}
	public void setAppIcon(String appIcon) {
		this.appIcon = appIcon;
	}
	public String getAppApkPath() {
		return appApkPath;
	}
	public void setAppApkPath(String appApkPath) {
		this.appApkPath = appApkPath;
	}
	public String getAppIOSPath() {
		return appIOSPath;
	}
	public void setAppIOSPath(String appIOSPath) {
		this.appIOSPath = appIOSPath;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getTemplateStatus() {
		return templateStatus;
	}
	public void setTemplateStatus(int templateStatus) {
		this.templateStatus = templateStatus;
	}
	public int getView_count() {
		return view_count;
	}
	public void setView_count(int view_count) {
		this.view_count = view_count;
	}
	public Date getCreate_time() {
		return create_time;
	}
	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}
	public Date getUpdate_time() {
		return update_time;
	}
	public void setUpdate_time(Date update_time) {
		this.update_time = update_time;
	}
	public AppInfo(String id, String name, String type) {
		super();
		this.id = id;
		this.name = name;
		this.type = type;
	}
	
	public AppInfo() {
		super();
	}
	
	
	
}
