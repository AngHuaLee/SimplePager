package com.google.gwt.sample.dynatable.server;

import static com.google.gwt.sample.dynatable.server.OfyService.ofy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import com.google.appengine.api.datastore.QueryResultIterator;
import com.google.gwt.sample.dynatable.shared.vo.Person;
import com.google.gwt.sample.dynatable.shared.vo.Professor;
import com.google.gwt.sample.dynatable.shared.vo.Schedule;
import com.google.gwt.sample.dynatable.shared.vo.Student;
import com.google.gwt.sample.dynatable.shared.vo.TimeSlot;
import com.googlecode.objectify.cmd.Query;

public class Dao {
	
	private static Query<Person> queryPerson() {
		return ofy().load().type(Person.class);
	}

	private static <T> Query<T> queryPager(Query<T> query, int startIndex, int maxCount) {
		return query.limit(maxCount).offset(startIndex);
	}

	private static <T> Query<T> querySort(Query<T> query, List<String> sort) {
		if (sort != null) {
			// TODO: Parameter check. Database column name whitelist + "-".
			for (String s : sort) {
				query = query.order(s);
			}
		}
		return query;
	}
	
	private static <T> Query<T> querySearch(Query<T> query, Map<String, String> search) {
		if (search != null) {
			// TODO: Parameter check. Database column name whitelist + special character like < > = ...blahblah.
			Set<String> keys = search.keySet();
			for (String s : keys) {
				query = query.filter(s + " >=", search.get(s)).filter(s + " <", search.get(s) + "\uFFFD");
			}
		}
		return query;
	}

	public static List<Person> getPeoplesList(int startIndex, int maxCount) {
		Query<Person> query = queryPerson();
		query = queryPager(query, startIndex, maxCount);
		
		QueryResultIterator<Person> iter = query.iterator();
		List<Person> result = new ArrayList<Person>();
		while (iter.hasNext()) {
			result.add(iter.next());
		}
		return result;
	}

	public static List<Person> getPeoplesListTest(int startIndex, int maxCount,
			List<String> sort, Map<String, String> search) {
		Query<Person> query = queryPerson();
		query = queryPager(query, startIndex, maxCount);
		query = querySort(query, sort);
		query = querySearch(query, search);

		QueryResultIterator<Person> iter = query.iterator();
		List<Person> result = new ArrayList<Person>();
		while (iter.hasNext()) {
			result.add(iter.next());
		}
		return result;
	}

	public static Person[] getPeoples(int startIndex, int maxCount) {
		Query<Person> query = queryPerson();
		query = queryPager(query, startIndex, maxCount);

		Person[] person;
		if (query.count() > 0) {
			QueryResultIterator<Person> iterator = query.iterator();
			person = new Person[query.count()];
			int i = 0;
			while (iterator.hasNext()) {
				person[i] = iterator.next();
				i++;
			}
		} else {
			person = new Person[0];
		}
		return person;
	}

	// Random data generate related
	private static final String[] FIRST_NAMES = new String[] { "Inman",
			"Sally", "Omar", "Teddy", "Jimmy", "Cathy", "Barney", "Fred",
			"Eddie", "Carlos" };

	private static final String[] LAST_NAMES = new String[] { "Smith", "Jones",
			"Epps", "Gibbs", "Webber", "Blum", "Mendez", "Crutcher", "Needler",
			"Wilson", "Chase", "Edelstein" };

	private static final String[] SUBJECTS = new String[] { "Chemistry",
			"Phrenology", "Geometry", "Underwater Basket Weaving",
			"Basketball", "Computer Science", "Statistics",
			"Materials Engineering", "English Literature", "Geology" };

	private static final int CLASS_LENGTH_MINS = 50;

	private static final int MAX_SCHED_ENTRIES = 5;

	private static final int MIN_SCHED_ENTRIES = 1;

	private static final int MAX_PEOPLE = 100;

	private static final int STUDENTS_PER_PROF = 5;

	private final static Random rnd = new Random(3);

	public static boolean isDataNotExists() {
		Person p = ofy().load().type(Person.class).first().now();
		return p == null;
	}
	
	static void generateRandomPeople() {
		for (int i = 0; i < MAX_PEOPLE; ++i) {
			Person person = generateRandomPerson();
			ofy().save().entity(person).now();
		}
	}

	private static Person generateRandomPerson() {
		// 1 out of every so many people is a prof.
		//
		if (rnd.nextInt(STUDENTS_PER_PROF) == 1) {
			return generateRandomProfessor();
		} else {
			return generateRandomStudent();
		}
	}

	private static Person generateRandomProfessor() {
		Professor prof = new Professor();

		String firstName = pickRandomString(FIRST_NAMES);
		String lastName = pickRandomString(LAST_NAMES);
		prof.setName("Dr. " + firstName + " " + lastName);

		String subject = pickRandomString(SUBJECTS);
		prof.setDescription("Professor of " + subject);
		prof.setTeachingSchedule(generateRandomSchedule());

		return prof;
	}

	private static Schedule generateRandomSchedule() {
		Schedule sched = new Schedule();
		int range = MAX_SCHED_ENTRIES - MIN_SCHED_ENTRIES + 1;
		int howMany = MIN_SCHED_ENTRIES + rnd.nextInt(range);

		TimeSlot[] timeSlots = new TimeSlot[howMany];

		for (int i = 0; i < howMany; ++i) {
			int startHrs = 8 + rnd.nextInt(9); // 8 am - 5 pm
			int startMins = 15 * rnd.nextInt(4); // on the hour or some quarter
			int dayOfWeek = 1 + rnd.nextInt(5); // Mon - Fri

			int absStartMins = 60 * startHrs + startMins; // convert to minutes
			int absStopMins = absStartMins + CLASS_LENGTH_MINS;

			timeSlots[i] = new TimeSlot(dayOfWeek, absStartMins, absStopMins);
		}

		Arrays.sort(timeSlots);

		for (int i = 0; i < howMany; ++i) {
			sched.addTimeSlot(timeSlots[i]);
		}
		ofy().save().entity(sched).now();
		return sched;

	}

	private static Person generateRandomStudent() {
		Student student = new Student();

		String firstName = pickRandomString(FIRST_NAMES);
		String lastName = pickRandomString(LAST_NAMES);
		student.setName(firstName + " " + lastName);

		String subject = pickRandomString(SUBJECTS);
		student.setDescription("Majoring in " + subject);
		student.setClassSchedule(generateRandomSchedule());

		return student;
	}

	private static String pickRandomString(String[] a) {
		int i = rnd.nextInt(a.length);
		return a[i];
	}
}
