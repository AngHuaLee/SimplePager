package com.google.gwt.sample.dynatable.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.sample.dynatable.client.event.GetPeoplesEvent;
import com.google.gwt.sample.dynatable.client.event.GetPeoplesHandler;
import com.google.gwt.sample.dynatable.client.event.GetPeoplesListEvent;
import com.google.gwt.sample.dynatable.client.event.GetPeoplesListHandler;
import com.google.gwt.sample.dynatable.client.event.GetPeoplesListTestEvent;
import com.google.gwt.sample.dynatable.client.event.GetPeoplesListTestHandler;

import dontCare.gf.gae.gwtChannel.client.Channel;
import dontCare.gf.gae.gwtChannel.client.ChannelServiceAsync;

public class RpcCenter {
	public static final SchoolCalendarServiceAsync rpc = GWT.create(SchoolCalendarService.class);
	public static final ChannelServiceAsync channelService = Channel.getService("channel");
	
	private static final HandlerManager eventBus = new HandlerManager(null);

	public static void fireEvent(GwtEvent<? extends EventHandler> event) {
		eventBus.fireEvent(event);
	}
	
	public static HandlerRegistration addGetPeoplesHandler(GetPeoplesHandler h) {
		return eventBus.addHandler(GetPeoplesEvent.TYPE, h);
	}

	public static HandlerRegistration addGetPeoplesListHandler(GetPeoplesListHandler h) {
		return eventBus.addHandler(GetPeoplesListEvent.TYPE, h);
	}

	public static HandlerRegistration addGetPeoplesListTestHandler(GetPeoplesListTestHandler h) {
		return eventBus.addHandler(GetPeoplesListTestEvent.TYPE, h);
	}
}