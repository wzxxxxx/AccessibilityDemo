package com.test;

import android.graphics.Bitmap;

public class Notify {
	
	public long time;
	public String title;
	public String message;
	public String pkName;
	public String tikerText;
	public int id;
	public Bitmap icon;
	
	
	public Notify() {
		super();
	}
	
	public void setTime(long time) {
		this.time=time;
	}
	
	public long getTime() {
		return time;
	}
	
	public void setTitle(String title) {
		this.title=title;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setMessage(String message) {
		this.message=message;
	}
	
	public String getMessage() {
		return message;
	}
	
	public void setPkName(String pkName){
		this.pkName=pkName;
	}
	
	public String getPkName() {
		return pkName;
	}
	
	public void setTikerText(String tikerText) {
		this.tikerText=tikerText;
	}
	
	public String getTikerText() {
		return tikerText;
	}
	
	public void setID(int id) {
		this.id=id;
	}
	
	public int getID() {
		return id;
	}
	
	public void setIcon(Bitmap icon) {
		this.icon=icon;
	}
	
	public Bitmap getIcon() {
		return icon;
	}
	
}
