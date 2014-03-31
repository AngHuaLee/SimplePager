package com.google.gwt.sample.dynatable.client.rpc;

import com.google.gwt.sample.dynatable.client.RpcCenter;
import com.google.gwt.sample.dynatable.client.event.GetTokenEvent;
import com.google.gwt.sample.dynatable.shared.vo.channel.Notify;
import dontCare.gf.gwt.client.taskFlow.RpcTask;

public class GetTokenTask extends RpcTask<String> {
	@Override
	public void handleException(Throwable caught) {
		caught.printStackTrace();
	}

	@Override
	public void onCallback(String value) {
		RpcCenter.fireEvent(new GetTokenEvent(value));
	}

	@Override
	protected void specification() {
		RpcCenter.channelService.getToken(Notify.CHANNEL_NAME, this);
	}
}