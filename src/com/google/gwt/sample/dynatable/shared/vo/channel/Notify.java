package com.google.gwt.sample.dynatable.shared.vo.channel;

import dontCare.gf.gae.gwtChannel.client.Message;

public class Notify implements Message {
	private static final long serialVersionUID = -3648409995139709959L;

	public static final String CHANNEL_NAME = "notify";

	private int code;

	Notify() {
	}

	@SuppressWarnings("unused")
	private Notify(int code) {
		this.code = code;
	}

	public int getCode() {
		return code;
	}
}
