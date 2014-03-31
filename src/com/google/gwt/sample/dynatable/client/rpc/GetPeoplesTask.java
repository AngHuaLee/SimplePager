package com.google.gwt.sample.dynatable.client.rpc;

import java.util.List;

import com.google.gwt.sample.dynatable.client.RpcCenter;
import com.google.gwt.sample.dynatable.client.event.GetPeoplesEvent;
import com.google.gwt.sample.dynatable.shared.vo.Person;
import com.google.gwt.sample.dynatable.shared.vo.ofy.OfyQueryParameter;
import com.google.gwt.sample.dynatable.shared.vo.ofy.OfyQueryResult;

import dontCare.gf.gwt.client.taskFlow.RpcTask;

public class GetPeoplesTask extends RpcTask<OfyQueryResult<List<Person>>> {
	private OfyQueryParameter<Person> para;

	public GetPeoplesTask(OfyQueryParameter<Person> para) {
		this.para = para;
	}

	@Override
	public void handleException(Throwable caught) {
		caught.printStackTrace();
	}

	@Override
	public void onCallback(OfyQueryResult<List<Person>> value) {
		RpcCenter.fireEvent(new GetPeoplesEvent(value));
	}

	@Override
	protected void specification() {
		RpcCenter.rpc.getPeoples(para, this);
	}
}
