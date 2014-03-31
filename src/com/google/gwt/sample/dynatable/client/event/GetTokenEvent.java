package com.google.gwt.sample.dynatable.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class GetTokenEvent extends GwtEvent<GetTokenHandler> {
	public static final Type<GetTokenHandler> TYPE = new Type<GetTokenHandler>();
	private String data;

	public GetTokenEvent(String value) {
		this.data = value;
	}

	public String getData() {
		return data;
	}

	@Override
	public Type<GetTokenHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(GetTokenHandler handler) {
		handler.onGetToken(this);
	}
}