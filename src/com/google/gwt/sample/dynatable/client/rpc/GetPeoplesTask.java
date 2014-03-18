package com.google.gwt.sample.dynatable.client.rpc;

import com.google.gwt.sample.dynatable.client.RpcCenter;
import com.google.gwt.sample.dynatable.client.event.GetPeoplesEvent;
import com.google.gwt.sample.dynatable.shared.vo.Person;

import dontCare.gf.gwt.client.taskFlow.RpcTask;

public class GetPeoplesTask extends RpcTask<Person[]> {
	private int startIndex;
	private int visibleRange;

	public GetPeoplesTask(int startIndex, int visibleRange) {
		this.startIndex = startIndex;
		this.visibleRange = visibleRange;
	}

	@Override
	public void handleException(Throwable caught) {
		caught.printStackTrace();
	}

	@Override
	public void onCallback(Person[] value) {
		RpcCenter.fireEvent(new GetPeoplesEvent(value));
	}

	@Override
	protected void specification() {
		RpcCenter.rpc.getPeoples(startIndex, visibleRange, this);
	}
}
