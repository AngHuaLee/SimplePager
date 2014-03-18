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

import java.util.List;
import java.util.Map;

import com.google.gwt.sample.dynatable.shared.vo.Person;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * The interface for the RPC server endpoint to get school calendar information.
 */
@RemoteServiceRelativePath("calendar")
public interface SchoolCalendarService extends RemoteService {
	Person[] getPeoples(int startIndex, int visibleRange);
	List<Person> getPeoplesList(int startIndex, int visibleRange);
	List<Person> getPeoplesListTest(int startIndex, int visibleRange, List<String> sort, Map<String, String> search);
}
