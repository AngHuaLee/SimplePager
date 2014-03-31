package com.google.gwt.sample.dynatable.shared.vo.ofy;

import com.google.gwt.user.client.rpc.IsSerializable;

public class OfySearchParameter implements IsSerializable {
	private String name;
	private OfySearchType type;
	private String startvalue;
	private String endvalue;
	
	public OfySearchParameter() {}

	public void greater(String name, String value) {
		this.name = name;
		startvalue = value;
		type = OfySearchType.greater;
	}
	public void greaterthan(String name, String value) {
		this.name = name;
		startvalue = value;
		type = OfySearchType.greaterthan;
	}
	public void less(String name, String value) {
		this.name = name;
		startvalue = value;
		type = OfySearchType.less;
	}
	public void lessthan(String name, String value) {
		this.name = name;
		startvalue = value;
		type = OfySearchType.lessthan;
	}
	public void in(String name, String value) {
		this.name = name;
		startvalue = value;
		type = OfySearchType.in;		
	}
	public void between(String name, String start, String end) {
		this.name = name;
		startvalue = start;
		endvalue = end;
		type = OfySearchType.between;
	}
	public void like(String name, String value) {
		this.name = name;
		startvalue = value;
		type = OfySearchType.like;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public OfySearchType getType() {
		return type;
	}

	public void setType(OfySearchType type) {
		this.type = type;
	}

	public String getStartvalue() {
		return startvalue;
	}

	public void setStartvalue(String startvalue) {
		this.startvalue = startvalue;
	}

	public String getEndvalue() {
		return endvalue;
	}

	public void setEndvalue(String endvalue) {
		this.endvalue = endvalue;
	}
}
