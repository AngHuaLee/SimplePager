package com.google.gwt.sample.dynatable.server;

import com.google.gwt.sample.dynatable.shared.vo.Person;
import com.google.gwt.sample.dynatable.shared.vo.Professor;
import com.google.gwt.sample.dynatable.shared.vo.Schedule;
import com.google.gwt.sample.dynatable.shared.vo.Student;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyFactory;
import com.googlecode.objectify.ObjectifyService;

public class OfyService {
    static {
        factory().register(Person.class);
        factory().register(Professor.class);
        factory().register(Schedule.class);
        factory().register(Student.class);
        //factory().register(TimeSlot.class);
    }

    public static Objectify ofy() {
        return ObjectifyService.ofy();
    }

    public static ObjectifyFactory factory() {
        return ObjectifyService.factory();
    }
}
