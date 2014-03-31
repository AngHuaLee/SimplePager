/*
 * Copyright 2007 Google Inc.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.google.gwt.sample.dynatable.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.sample.dynatable.client.event.GetTokenEvent;
import com.google.gwt.sample.dynatable.client.event.GetTokenHandler;
import com.google.gwt.sample.dynatable.client.rpc.GetPersonCountTask;
import com.google.gwt.sample.dynatable.client.ui.RootLayout;
import com.google.gwt.sample.dynatable.shared.vo.channel.Notify;
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.user.client.ui.RootLayoutPanel;

import dontCare.gf.gae.gwtChannel.client.Channel;
import dontCare.gf.gae.gwtChannel.client.event.MessageEvent;
import dontCare.gf.gae.gwtChannel.client.event.MessageEvent.MessageHandler;
import dontCare.gf.gwt.client.taskFlow.TaskAgent;

/**
 * The entry point class which performs the initial loading of the DynaTable
 * application.
 */
public class DynaTable implements EntryPoint {
	public static SimplePager.Resources pagerResource = GWT.create(SimplePager.Resources.class);
	private RootLayout rootLayout = new RootLayout();
	private static Channel channel;
	
	public void onModuleLoad() {
		RpcCenter.addGetTokenHandler(new GetTokenHandler() {
			@Override
			public void onGetToken(GetTokenEvent event) {
				buildChannel(event.getData());
			}
		});
		RootLayoutPanel mainTab = RootLayoutPanel.get();
		if (mainTab != null) {
			mainTab.add(rootLayout);
		}
	}

	/**
	 * 處理 server 主動丟過來的 message，
	 * 主要是提醒 client 要向 server update 資料。
	 * @param token
	 */
	private void buildChannel(String token) {
		channel = new Channel(token);
		channel.addMessageHandler(new MessageHandler() {
			@Override
			public void onMessage(MessageEvent e) {
				TaskAgent taskAgent = new TaskAgent();
				Notify msg = (Notify) e.getMessage();
				
				switch(msg.getCode()) {
				case Notify.PERSON_COUNT:
					taskAgent.addTask(new GetPersonCountTask()); 
					break;
				}
				
				taskAgent.start();
			}
		});
		channel.open();
	}
}
