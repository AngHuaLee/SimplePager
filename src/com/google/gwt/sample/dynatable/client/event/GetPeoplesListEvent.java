package com.google.gwt.sample.dynatable.client.event;

import java.util.List;

import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.sample.dynatable.shared.vo.Person;

public class GetPeoplesListEvent extends GwtEvent<GetPeoplesListHandler> {
	public static final Type<GetPeoplesListHandler> TYPE = new Type<GetPeoplesListHandler>();
	private List<Person> data;

	public GetPeoplesListEvent(List<Person> data) {
		this.data = data;
	}

	public List<Person> getData() {
		return data;
	}

	@Override
	public Type<GetPeoplesListHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(GetPeoplesListHandler handler) {
		handler.onGetPeoplesList(this);
	}
}
