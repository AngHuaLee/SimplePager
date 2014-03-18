package com.google.gwt.sample.dynatable.server;

import com.google.appengine.api.channel.ChannelService;
import com.google.appengine.api.channel.ChannelServiceFactory;
import com.google.gwt.sample.dynatable.shared.vo.channel.Notify;

import dontCare.gf.gae.gwtChannel.server.ChannelServiceServlet;

public class ChannelServiceImpl extends ChannelServiceServlet<Notify> {
	private static final long serialVersionUID = -3963421654964195692L;

	private static final ChannelService SERVICE = ChannelServiceFactory
			.getChannelService();
	public static final String TOKEN = SERVICE
			.createChannel(Notify.CHANNEL_NAME);
	@Override
	public String getToken(String name) {
		// TODO Auto-generated method stub
		return null;
	}

}
