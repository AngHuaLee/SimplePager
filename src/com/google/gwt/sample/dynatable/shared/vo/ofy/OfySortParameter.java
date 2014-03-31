package com.google.gwt.sample.dynatable.shared.vo.ofy;

import com.google.gwt.user.client.rpc.IsSerializable;

public class OfySortParameter implements IsSerializable {
	private String name;
	private OfySortType type;

	public OfySortParameter() {
		this.name = "";
		type = OfySortType.ascending;
	}

	public OfySortParameter(String name) {
		this.name = name;
		type = OfySortType.ascending;
	}

	public OfySortParameter(String name, OfySortType type) {
		this.name = name;
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isDescending() {
		return type == OfySortType.descending;
	}

	public void setSortType(OfySortType type) {
		this.type = type;
	}

	public OfySortType getSortType() {
		return type;
	}
}