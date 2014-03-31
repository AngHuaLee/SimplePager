package com.google.gwt.sample.dynatable.server;

import static com.google.gwt.sample.dynatable.server.OfyService.ofy;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import com.google.gwt.sample.dynatable.shared.vo.Person;
import com.google.gwt.sample.dynatable.shared.vo.Professor;
import com.google.gwt.sample.dynatable.shared.vo.Schedule;
import com.google.gwt.sample.dynatable.shared.vo.Student;
import com.google.gwt.sample.dynatable.shared.vo.TimeSlot;
import com.google.gwt.sample.dynatable.shared.vo.ofy.OfyQueryHelper;
import com.google.gwt.sample.dynatable.shared.vo.ofy.OfyQueryParameter;
import com.google.gwt.sample.dynatable.shared.vo.ofy.OfyQueryResult;
import com.googlecode.objectify.cmd.Query;

public class Dao {
	// private static <T> Query<T> querySearch(Query<T> query, Map<String,
	// String> search) {
	// if (search != null) {
	// // TODO: Parameter check. Database column name whitelist + special
	// character like < > = ...blahblah.
	// Set<String> keys = search.keySet();
	// for (String s : keys) {
	// query = query.filter(s + " >=", search.get(s)).filter(s + " <",
	// search.get(s) + "\uFFFD");
	// }
	// }
	// return query;
	// }

	/**
	 * 讓 Objectify 根據 Person.class 傳回針對 Person 的 Query 物件，
	 * 最基礎的語法，這邊不打算使用 reflection 透過 generic type 在 runtime 自動取得 class
	 * @return Query instance of Person
	 * @see Query
	 */
	private static Query<Person> queryPerson() {
		return ofy().load().type(Person.class);
	}

	/**
	 * 讓 Objectify 根據 Person.class 傳回針對 Person 的 Query 物件，
	 * 最基礎的語法，這邊不打算使用 reflection 透過 generic type 在 runtime 自動取得 class ，
	 * 一個是以後一定看不懂，另一個是現在這種作法其實很簡單，
	 * @return Query instance of Person
	 * @see Query
	 */
	public static int getPersonCount() {
		return queryPerson().count();
	}

	/**
	 * 根據 Pager 設定 Query 的起始點及最多取得幾筆資料。
	 * 在完成 OfyQueryParameter 之後，相關參數會併入 OfyQueryParameter 一起傳送。
	 * 並經由 OfyQueryHelp 統一完成相關 Query 參數設定。
	 * @param query Query instance of Type T
	 * @param startIndex Pager 起始點
	 * @param maxCount Pager 顯示的資料筆數
	 * @return Query instance of Type T in specific pager range 
	 */
	@Deprecated
	private static <T> Query<T> queryPager(Query<T> query, int startIndex,
			int maxCount) {
		return query.limit(maxCount).offset(startIndex);
	}

	/**
	 * 根據 sort list 設定 Query 會依據那些欄位排序資料。
	 * 傳入的資料包含 '-' 開頭，所以無需再特意處理。
	 * 為避免 client 端程式碼需特意重複撰寫設定傳送值是否要加上 '-' 相關排序設定的程式碼，
	 * 在完成 OfyQueryParameter 之後，相關排序參數會併入 OfyQueryParameter 一起傳送。
	 * 並經由 OfyQueryHelp 統一完成相關 Query 參數設定。
	 * @param query Query instance of Type T
	 * @param sort sort order
	 * @return Query instance of Type T in specific sort order
	 */
	@Deprecated
	private static <T> Query<T> querySort(Query<T> query, List<String> sort) {
		if (sort != null) {
			for (String s : sort) {
				query = query.order(s);
			}
		}
		return query;
	}

	/**
	 * 根據 search map 設定 Query 會依據那些欄位針對特定數值篩選資料。
	 * 傳入的資料包含不包含各類特定搜尋要求，
	 * 為避免 client 及 server 端程式碼需特意重複撰寫各篩選相關程式碼，
	 * 在完成 OfyQueryParameter 之後，相關篩選參數會併入 OfyQueryParameter 一起傳送。
	 * 並經由 OfyQueryHelp 統一完成相關 Query 參數設定。
	 * @param query Query instance of Type T
	 * @param search search option
	 * @return Query instance of Type T in specific search option
	 * @return
	 */
	@Deprecated
	private static <T> Query<T> querySearch(Query<T> query,
			Map<String, String> search) {
		if (search != null) {
			Set<String> keys = search.keySet();
			for (String s : keys) {
				query = query.filter(s + " >=", search.get(s)).filter(s + " <", search.get(s) + "\uFFFD");
			}
		}
		return query;
	}

	@Deprecated
	public static List<Person> getPeoplesList(int startIndex, int maxCount) {
		Query<Person> query = queryPerson();
		query = queryPager(query, startIndex, maxCount);
		return query.list();
	}

	@Deprecated
	public static List<Person> getPeoplesListTest(int startIndex, int maxCount,
			List<String> sort, Map<String, String> search) {
		Query<Person> query = queryPerson();
		query = queryPager(query, startIndex, maxCount);
		query = querySort(query, sort);
		query = querySearch(query, search);
		return query.list();
	}

	/**
	 * 收到 
	 * @param para
	 * @return
	 */
	public static OfyQueryResult<List<Person>> getPeoplesList(OfyQueryParameter<Person> para) {
		//para.setGenericClass(Person.class);
		return OfyQueryHelper.query(queryPerson(), para, Person.class);
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
		prof.setIdentityId(generateRandomIdentityId());
		prof.setPhoneNumber(generateRandomPhoneNumber());

		return prof;
	}

	private static Person generateRandomStudent() {
		Student student = new Student();

		String firstName = pickRandomString(FIRST_NAMES);
		String lastName = pickRandomString(LAST_NAMES);
		student.setName(firstName + " " + lastName);

		String subject = pickRandomString(SUBJECTS);
		student.setDescription("Majoring in " + subject);
		student.setClassSchedule(generateRandomSchedule());
		student.setIdentityId(generateRandomIdentityId());
		student.setPhoneNumber(generateRandomPhoneNumber());

		return student;
	}

	private static String generateRandomIdentityId() {
		StringBuilder sb = new StringBuilder();
		sb.append((char) ('A' + rnd.nextInt(26)));
		sb.append(rnd.nextInt(2) + 1);
		// XXX: 只是測試資料，不處理身分證字號驗證
		for (int i = 0; i < 8; i++) {
			sb.append(rnd.nextInt(10));
		}
		return sb.toString();
	}

	private static String generateRandomPhoneNumber() {
		StringBuilder sb = new StringBuilder();
		sb.append(0);
		sb.append(rnd.nextInt(8) + 2);
		// XXX: 只是測試資料，不處理哪些縣市電話號碼只有7位
		for (int i = 0; i < 8; i++) {
			sb.append(rnd.nextInt(10));
		}
		return sb.toString();
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

	private static String pickRandomString(String[] a) {
		int i = rnd.nextInt(a.length);
		return a[i];
	}
}
