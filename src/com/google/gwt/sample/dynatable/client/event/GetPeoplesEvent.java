package com.google.gwt.sample.dynatable.client.event;

import java.util.List;

import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.sample.dynatable.shared.vo.Person;
import com.google.gwt.sample.dynatable.shared.vo.ofy.OfyQueryResult;

public class GetPeoplesEvent extends GwtEvent<GetPeoplesHandler> {
	public static final Type<GetPeoplesHandler> TYPE = new Type<GetPeoplesHandler>();
	private OfyQueryResult<List<Person>> data;

	public GetPeoplesEvent(OfyQueryResult<List<Person>> data) {
		this.data = data;
	}

	public OfyQueryResult<List<Person>> getData() {
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
