package com.google.gwt.sample.dynatable.client.rpc;

import java.util.List;
import java.util.Map;

import com.google.gwt.sample.dynatable.client.RpcCenter;
import com.google.gwt.sample.dynatable.client.event.GetPeoplesListTestEvent;
import com.google.gwt.sample.dynatable.shared.vo.Person;

import dontCare.gf.gwt.client.taskFlow.RpcTask;

public class GetPeoplesListTestTask extends RpcTask<List<Person>> {
	private int startIndex;
	private int visibleRange;
	private List<String> sort;
	private Map<String, String> search;

	public GetPeoplesListTestTask(int startIndex, int visibleRange, List<String> sort, Map<String, String> search) {
		this.startIndex = startIndex;
		this.visibleRange = visibleRange;
		this.sort = sort;
		this.search = search;
	}

	@Override
	public void handleException(Throwable caught) {
		caught.printStackTrace();
	}

	@Override
	public void onCallback(List<Person> value) {
		RpcCenter.fireEvent(new GetPeoplesListTestEvent(value));
	}

	@Override
	protected void specification() {
		RpcCenter.rpc.getPeoplesListTest(startIndex, visibleRange, sort, search, this);
	}
}
