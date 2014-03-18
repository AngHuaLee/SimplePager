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
package com.google.gwt.sample.dynatable.shared.vo;

import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.EntitySubclass;
import com.googlecode.objectify.annotation.Load;

/**
 * Holds relevant data for a Professor type Person. This class is intended to be
 * serialized in RPC calls.
 */
@EntitySubclass(index = true)
public class Professor extends Person {

	@Load Ref<Schedule> teachingSchedule;

	public Professor() {
	}

	@Override
	public String getSchedule(boolean[] daysFilter) {
		return teachingSchedule.get().getDescription(daysFilter);
	}

	public Schedule getTeachingSchedule() {
		return teachingSchedule.get();
	}

	public void setTeachingSchedule(Ref<Schedule> sched) {
		teachingSchedule = sched;
	}

	public void setTeachingSchedule(Schedule sched) {
		teachingSchedule = Ref.create(sched);
	}
}
