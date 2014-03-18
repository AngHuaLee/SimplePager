package com.google.gwt.sample.dynatable.client.rpc;

import java.util.List;

import com.google.gwt.sample.dynatable.client.RpcCenter;
import com.google.gwt.sample.dynatable.client.event.GetPeoplesListEvent;
import com.google.gwt.sample.dynatable.shared.vo.Person;

import dontCare.gf.gwt.client.taskFlow.RpcTask;

public class GetPeoplesListTask extends RpcTask<List<Person>> {
	private int startIndex;
	private int visibleRange;

	public GetPeoplesListTask(int startIndex, int visibleRange) {
		this.startIndex = startIndex;
		this.visibleRange = visibleRange;
	}

	@Override
	public void handleException(Throwable caught) {
		caught.printStackTrace();
	}

	@Override
	public void onCallback(List<Person> value) {
		RpcCenter.fireEvent(new GetPeoplesListEvent(value));
	}

	@Override
	protected void specification() {
		RpcCenter.rpc.getPeoplesList(startIndex, visibleRange, this);
	}
}
