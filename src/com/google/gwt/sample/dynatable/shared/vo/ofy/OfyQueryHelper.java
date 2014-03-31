package com.google.gwt.sample.dynatable.shared.vo.ofy;

import java.lang.reflect.Field;
import java.util.List;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.cmd.Query;

import dontCare.gf.gwt.client.util.ArrayUtil;

public class OfyQueryHelper {
	public static <T> OfyQueryResult<List<T>> query(Query<T> query,
			OfyQueryParameter<T> para, Class<T> clazz) {
		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();
		String domain = "";
		String account = "";
		OfyQueryResult<List<T>> queryResult = new OfyQueryResult<List<T>>();
		if (user != null) {
			domain = user.getAuthDomain();
			account = user.getEmail();
		}

		// XXX: 暫時偷改權限，測試用
		domain = "gmail.com";
		//domain = "test.com";

		// Check Sort and Filter is in WhiteList
		// TODO: Check Filter
		Class<T> classesToVerify = clazz;//para.getGenericClass();
		String[] fieldNames = para.getSortFieldList();
		for (String f : fieldNames) {
			Field field = null;

			try {
				field = classesToVerify.getDeclaredField(f);
			} catch (NoSuchFieldException | SecurityException ex) {
				para.removeSort(f);
				ex.printStackTrace();
			}
			if (field != null) {
				if (field.getAnnotation(Index.class) == null) {
					para.removeSort(f);
					// XXX: 有空改 Exception...
					System.out.println(classesToVerify.getName() + " 中欄位 " + f
							+ " 並未添加 Index Annotation。");
				}
				OfyWhiteList whiteList = field.getAnnotation(OfyWhiteList.class);
				if (whiteList != null) {
					boolean isValid = false;
					System.out.println("valid: " + isValid);
					if (whiteList.authorizedAccounts().length > 0) {
						for (String a : whiteList.authorizedAccounts()) {
							if (a.equals(account)) {
								System.out.println("account: " + a + " : " + account);
								isValid = true;
								break;
							}
						}
					}
					if (!isValid && whiteList.authorizedDomains().length > 0) {
						for (String a : whiteList.authorizedDomains()) {
							if (a.equals(domain)) {
								System.out.println("domain: " + a + " : " + domain);
								isValid = true;
								break;
							}
						}
					}
					System.out.println("valid: " + isValid);
					if (!isValid) {
						para.removeSort(f);
						// XXX: 有空改 Exception...
						System.out.println(classesToVerify.getName() + " 中欄位 "
								+ f + " 需特定權限。");
					}
					
				}
			}
		}
		// Handle Filter
		if (!para.isSearchEmpty()) {
			for (OfySearchParameter s : para.getSearchList()) {
				switch (s.getType()) {
				case greater:
					query = query.filter(s.getName() + " >", s.getStartvalue());
					break;
				case greaterthan:
					query = query.filter(s.getName() + " >=", s.getStartvalue());
					break;
				case less:
					query = query.filter(s.getName() + " <", s.getStartvalue());
					break;
				case lessthan:
					query = query.filter(s.getName() + " <=", s.getStartvalue());
					break;
				case between:
					query = query.filter(s.getName() + " >=", s.getStartvalue()).filter(s.getName() + " <", s.getEndvalue());
					break;
				case in:
					query = query.filter(s.getName() + " in", s.getStartvalue());
					break;
				case like:
				default:
					query = query.filter(s.getName() + " >=", s.getStartvalue()).filter(s.getName() + " <", s.getStartvalue() + "\uFFFD");
					break;
				}
			}
		}

		// Handle Sort
		if (!para.isSortEmpty()) {
			for (OfySortParameter s : para.getSortList()) {
				if (s.isDescending()) {
					query = query.order("-" + s.getName());
				} else {
					query = query.order(s.getName());					
				}
			}
		}

		// Get Count
		if (para.isCountAll() && para.isSearchEmpty()) {
			queryResult.setCount(query.count());
		}

		// Handle Pager parameter
		if (para.getMaxCount() > 0) {
			query = query.limit(para.getMaxCount());
		}

		if (para.getStartIndex() > 0) {
			query = query.offset(para.getStartIndex());
		}

		// Get Result
		queryResult.setResult(ArrayUtil.convert(query.list()));

		return queryResult;
	}
}
