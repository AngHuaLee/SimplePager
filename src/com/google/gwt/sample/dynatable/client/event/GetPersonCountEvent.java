package com.google.gwt.sample.dynatable.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class GetPersonCountEvent extends GwtEvent<GetPersonCountHandler> {
	public static final Type<GetPersonCountHandler> TYPE = new Type<GetPersonCountHandler>();
	private Integer data;

	public GetPersonCountEvent(Integer data) {
		this.data = data;
	}

	public Integer getData() {
		return data;
	}

	@Override
	public Type<GetPersonCountHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(GetPersonCountHandler handler) {
		handler.onGetPersonCount(this);
	}
}
