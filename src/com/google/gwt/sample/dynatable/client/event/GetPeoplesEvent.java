package com.google.gwt.sample.dynatable.client.event;

import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.sample.dynatable.shared.vo.Person;

public class GetPeoplesEvent extends GwtEvent<GetPeoplesHandler> {
	public static final Type<GetPeoplesHandler> TYPE = new Type<GetPeoplesHandler>();
	private Person[] data;

	public GetPeoplesEvent(Person[] data) {
		this.data = data;
	}

	public Person[] getData() {
		return data;
	}

	@Override
	public Type<GetPeoplesHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(GetPeoplesHandler handler) {
		handler.onGetPeoples(this);
	}
}
