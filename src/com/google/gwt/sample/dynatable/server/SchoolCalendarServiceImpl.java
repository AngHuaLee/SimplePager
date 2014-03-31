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
package com.google.gwt.sample.dynatable.server;

import java.util.List;
import java.util.Map;

import com.google.gwt.sample.dynatable.client.SchoolCalendarService;
import com.google.gwt.sample.dynatable.shared.vo.Person;
import com.google.gwt.sample.dynatable.shared.vo.ofy.OfyQueryParameter;
import com.google.gwt.sample.dynatable.shared.vo.ofy.OfyQueryResult;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import dontCare.gf.gwt.client.util.ArrayUtil;

/**
 * The implemenation of the RPC service which runs on the server.
 */
@SuppressWarnings("serial")
public class SchoolCalendarServiceImpl extends RemoteServiceServlet implements
		SchoolCalendarService {

	public SchoolCalendarServiceImpl() {
		if (Dao.isDataNotExists()) {
			Dao.generateRandomPeople();
		}
	}

	public int getPersonCount() {
		return Dao.getPersonCount();
	}
	
	@SuppressWarnings("deprecation")
	public List<Person> getPeoplesList(int startIndex, int maxCount) {
		return ArrayUtil.convert(Dao.getPeoplesList(startIndex, maxCount));
	}

	@SuppressWarnings("deprecation")
	public List<Person> getPeoplesListTest(int startIndex, int maxCount,
			List<String> sort, Map<String, String> search) {
		return ArrayUtil.convert(Dao.getPeoplesListTest(startIndex, maxCount, sort, search));
	}

	public OfyQueryResult<List<Person>> getPeoples(OfyQueryParameter<Person> para) {
		return Dao.getPeoplesList(para);
	}

	/**
	 * Write the serialized response out to stdout. This is a very unusual thing
	 * to do, but it allows us to create a static file version of the response
	 * without deploying a servlet.
	 */
	@Override
	protected void onAfterResponseSerialized(String serializedResponse) {
		System.out.println(serializedResponse);
	}
}
