package com.google.gwt.sample.dynatable.client.rpc;

import com.google.gwt.sample.dynatable.client.RpcCenter;
import com.google.gwt.sample.dynatable.client.event.GetPersonCountEvent;

import dontCare.gf.gwt.client.taskFlow.RpcTask;

public class GetPersonCountTask extends RpcTask<Integer> {
	public GetPersonCountTask() {
	}

	@Override
	public void handleException(Throwable caught) {
		caught.printStackTrace();
	}

	@Override
	public void onCallback(Integer value) {
		RpcCenter.fireEvent(new GetPersonCountEvent(value));
	}

	@Override
	protected void specification() {
		RpcCenter.rpc.getPersonCount(this);
	}
}
