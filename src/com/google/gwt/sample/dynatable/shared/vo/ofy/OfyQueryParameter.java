package com.google.gwt.sample.dynatable.shared.vo.ofy;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * OfyQueryParameter 主要用途為存放透過 Ofy 進行查詢時所需之參數。
 * @param <T>
 */
public class OfyQueryParameter<T> implements IsSerializable {
	private boolean countAll = false;
	private int startIndex = 0;
	private int maxCount = 0;
	//private String cursor = "";
	private List<OfySortParameter> sortList;
	private List<OfySearchParameter> searchList;
	//private Class<T> clazz;

	public OfyQueryParameter() {
		sortList = new ArrayList<OfySortParameter>();
		searchList = new ArrayList<OfySearchParameter>();
	}

	public boolean isCountAll() {
		return countAll;
	}

	public void setCountAll(boolean countAll) {
		this.countAll = countAll;
	}

	public int getStartIndex() {
		return startIndex;
	}

	public void setStartIndex(int startIndex) {
		this.startIndex = startIndex;
	}

	public int getMaxCount() {
		return maxCount;
	}

	public void setMaxCount(int maxCount) {
		this.maxCount = maxCount;
	}

	public boolean isSortEmpty() {
		return sortList.size() == 0;
	}

	public List<OfySortParameter> getSortList() {
		return sortList;
	}

	public String[] getSortFieldList() {
		String[] result = new String[sortList.size()];
		for (int i = 0; i < sortList.size(); i++) {
			if (sortList.get(i) != null) {
				result[i] = sortList.get(i).getName();
			} else {
				result[i] = "";
			}
		}
		return result;
	}

	public boolean addSort(OfySortParameter sort) {
		if (sort == null) 
			return false;
		for (OfySortParameter para : sortList) {
			// 重複檢查
			if (para.getName().equals(sort.getName())) {
				para.setSortType(sort.getSortType());
				return true;
			}
		}
		sortList.add(sort);
		return true;
	}

	public boolean addSort(String t, OfySortType type) {
		if (t == null || t.trim().length() == 0) 
			return false;
		for (OfySortParameter para : sortList) {
			// 重複檢查
			if (para.getName().equals(t)) {
				para.setSortType(type);
				return true;
			}
		}
		sortList.add(new OfySortParameter(t, type));
		return true;
	}
	
	public boolean removeSort(String t) {
		for (OfySortParameter para : sortList) {
			if (para.getName().equals(t)) {
				sortList.remove(para);
				return true;
			}			
		}
		return false;
	}

	public void clearSort() {
		sortList.clear();
	}	
	
	public boolean isSearchEmpty() {
		return searchList.size() == 0;
	}

	public List<OfySearchParameter> getSearchList() {
		return searchList;
	}

	public String[] getSearchFieldList() {
		String[] result = new String[searchList.size()];
		for (int i = 0; i < searchList.size(); i++) {
			if (searchList.get(i) != null) {
				result[i] = searchList.get(i).getName();
			} else {
				result[i] = "";
			}
		}
		return result;
	}

	public boolean addSearch(OfySearchParameter search) {
		if (search == null) 
			return false;
		for (OfySearchParameter para : searchList) {
			// 重複檢查
			if (para.getName().equals(search.getName())) {
				para.setType(search.getType());
				para.setStartvalue(search.getStartvalue());
				para.setEndvalue(search.getEndvalue());
				return true;
			}
		}
		searchList.add(search);
		return true;
	}

	public boolean addSearchGreater(String t, String v) {
		if (t == null || t.trim().length() == 0) 
			return false;
		for (OfySearchParameter para : searchList) {
			// 重複檢查
			if (para.getName().equals(t)) {
				para.setType(OfySearchType.greater);
				para.setStartvalue(v);
				return true;
			}
		}
		OfySearchParameter result = new OfySearchParameter();
		result.greater(t, v);
		searchList.add(result);
		return true;
	}

	public boolean addSearchGreaterthan(String t, String v) {
		if (t == null || t.trim().length() == 0) 
			return false;
		for (OfySearchParameter para : searchList) {
			// 重複檢查
			if (para.getName().equals(t)) {
				para.setType(OfySearchType.greaterthan);
				para.setStartvalue(v);
				return true;
			}
		}
		OfySearchParameter result = new OfySearchParameter();
		result.greaterthan(t, v);
		searchList.add(result);
		return true;
	}

	public boolean addSearchLess(String t, String v) {
		if (t == null || t.trim().length() == 0) 
			return false;
		for (OfySearchParameter para : searchList) {
			// 重複檢查
			if (para.getName().equals(t)) {
				para.setType(OfySearchType.less);
				para.setStartvalue(v);
				return true;
			}
		}
		OfySearchParameter result = new OfySearchParameter();
		result.less(t, v);
		searchList.add(result);
		return true;
	}

	public boolean addSearchLessthan(String t, String v) {
		if (t == null || t.trim().length() == 0) 
			return false;
		for (OfySearchParameter para : searchList) {
			// 重複檢查
			if (para.getName().equals(t)) {
				para.setType(OfySearchType.lessthan);
				para.setStartvalue(v);
				return true;
			}
		}
		OfySearchParameter result = new OfySearchParameter();
		result.lessthan(t, v);
		searchList.add(result);
		return true;
	}
	public boolean addSearchIn(String t, String v) {
		if (t == null || t.trim().length() == 0) 
			return false;
		for (OfySearchParameter para : searchList) {
			// 重複檢查
			if (para.getName().equals(t)) {
				para.setType(OfySearchType.in);
				para.setStartvalue(v);
				return true;
			}
		}
		OfySearchParameter result = new OfySearchParameter();
		result.in(t, v);
		searchList.add(result);
		return true;
	}

	public boolean addSearchLike(String t, String v) {
		if (t == null || t.trim().length() == 0) 
			return false;
		for (OfySearchParameter para : searchList) {
			// 重複檢查
			if (para.getName().equals(t)) {
				para.setType(OfySearchType.like);
				para.setStartvalue(v);
				return true;
			}
		}
		OfySearchParameter result = new OfySearchParameter();
		result.like(t, v);
		searchList.add(result);
		return true;
	}

	public boolean addSearchBetween(String t, String start, String end) {
		if (t == null || t.trim().length() == 0) 
			return false;
		for (OfySearchParameter para : searchList) {
			// 重複檢查
			if (para.getName().equals(t)) {
				para.setType(OfySearchType.between);
				para.setStartvalue(start);
				para.setEndvalue(end);
				return true;
			}
		}
		OfySearchParameter result = new OfySearchParameter();
		result.between(t, start, end);
		searchList.add(result);
		return true;
	}

	public boolean removeSearch(String t) {
		for (OfySearchParameter para : searchList) {
			if (para.getName().equals(t)) {
				searchList.remove(para);
				return true;
			}			
		}
		return false;
	}

//	public Class<T> getGenericClass() {
//		return clazz;
//	}
//
//	public void setGenericClass(Class<T> clazz) {
//		this.clazz = clazz;
//	}
}