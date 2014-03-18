package com.google.gwt.sample.dynatable.client.event;

import java.util.List;

import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.sample.dynatable.shared.vo.Person;

public class GetPeoplesListTestEvent extends GwtEvent<GetPeoplesListTestHandler> {
	public static final Type<GetPeoplesListTestHandler> TYPE = new Type<GetPeoplesListTestHandler>();
	private List<Person> data;

	public GetPeoplesListTestEvent(List<Person> data) {
		this.data = data;
	}

	public List<Person> getData() {
		return data;
	}

	@Override
	public Type<GetPeoplesListTestHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(GetPeoplesListTestHandler handler) {
		handler.onGetPeoplesListTest(this);
	}
}
