package com.google.gwt.sample.dynatable.shared.vo.ofy;

import com.google.gwt.user.client.rpc.IsSerializable;

public class OfyQueryResult<T> implements IsSerializable {
	private int count;
	private T result;

	public OfyQueryResult() {}
	
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}

	public T getResult() {
		return result;
	}
	public void setResult(T result) {
		this.result = result;
	}
}
