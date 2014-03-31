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

//    public static Query<Person> queryPerson() {
//		return ofy().load().type(Person.class);
//	}
//
//	public static <T, R, H> Query<T> query(Query<T> query, OfyQueryParameter<T> para) {
//		if (para.getMaxCount() > 0) {
//			query = query.limit(para.getMaxCount());
//		}
//		if (para.getStartIndex() > 0) {
//			query = query.offset(para.getStartIndex());
//		}
//		if (para.getSortList() != null && !para.isSortEmpty()) {
//			query = querySort(query, para.getSortList());
//		}
//		return query;
//	}
//
//	private static <T, R> Query<T> querySort(Query<T> query, List<R> sort) {
//		if (sort != null) {
//			for (R s : sort) {
//				query = query.order(s.toString());
//			}
//		}
//		return query;
//	}
}
