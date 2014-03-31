package com.google.gwt.sample.dynatable.server;

import java.lang.reflect.Method;

import com.google.appengine.api.channel.ChannelMessage;
import com.google.appengine.api.channel.ChannelService;
import com.google.appengine.api.channel.ChannelServiceFactory;
import com.google.gwt.user.client.rpc.SerializationException;
import com.google.gwt.user.server.rpc.RPC;
import com.google.gwt.user.server.rpc.SerializationPolicy;
import com.google.gwt.sample.dynatable.shared.vo.channel.Notify;

import dontCare.gf.gae.gwtChannel.client.GetMessageService;
import dontCare.gf.gae.gwtChannel.client.Message;
import dontCare.gf.gae.gwtChannel.server.ChannelServiceServlet;

public class ChannelServiceImpl extends ChannelServiceServlet<Notify> {
	private static final long serialVersionUID = -3963421654964195692L;

	private static final ChannelService SERVICE = ChannelServiceFactory
			.getChannelService();
	public static final String TOKEN = SERVICE
			.createChannel(Notify.CHANNEL_NAME);
	@Override
	public String getToken(String name) {
		return TOKEN;
	}

	public static void send(Notify message) {
		try {
			Method serviceMethod = GetMessageService.class.getMethod("getMessage", Message.class);

			// Yes, the SerializationPolicy is a hack
			String serialized = RPC.encodeResponseForSuccess(serviceMethod, message, new SerializationPolicy() {
					@Override
					public void validateSerialize(Class<?> clazz)
							throws SerializationException {
					}

					@Override
					public void validateDeserialize(Class<?> clazz) throws SerializationException {}

					@Override
					public boolean shouldSerializeFields(Class<?> clazz) {
						return false;
					}

					@Override
					public boolean shouldDeserializeFields(Class<?> clazz) {
						return false;
					}
			});
			SERVICE.sendMessage(
				new ChannelMessage(Notify.CHANNEL_NAME, serialized)
			);
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SerializationException e) {
			e.printStackTrace();
		}
	}
}
