package com.google.gwt.sample.dynatable.client.ui;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.sample.dynatable.client.DynaTable;
import com.google.gwt.sample.dynatable.client.RpcCenter;
import com.google.gwt.sample.dynatable.client.event.GetPeoplesEvent;
import com.google.gwt.sample.dynatable.client.event.GetPeoplesHandler;
import com.google.gwt.sample.dynatable.client.rpc.GetPeoplesTask;
import com.google.gwt.sample.dynatable.shared.vo.Person;
import com.google.gwt.sample.dynatable.shared.vo.Student;
import com.google.gwt.sample.dynatable.shared.vo.ofy.OfyQueryParameter;
import com.google.gwt.sample.dynatable.shared.vo.ofy.OfyQueryResult;
import com.google.gwt.sample.dynatable.shared.vo.ofy.OfySearchParameter;
import com.google.gwt.sample.dynatable.shared.vo.ofy.OfySortParameter;
import com.google.gwt.sample.dynatable.shared.vo.ofy.OfySortType;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.ColumnSortEvent;
import com.google.gwt.user.cellview.client.ColumnSortEvent.AsyncHandler;
import com.google.gwt.user.cellview.client.ColumnSortList;
import com.google.gwt.user.cellview.client.ColumnSortList.ColumnSortInfo;
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.user.cellview.client.SimplePager.TextLocation;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.AsyncDataProvider;
import com.google.gwt.view.client.HasData;

import dontCare.gf.gwt.client.taskFlow.TaskAgent;

public class CellTableMain extends Composite {

	private static CellTableMainUiBinder uiBinder = GWT
			.create(CellTableMainUiBinder.class);

	interface CellTableMainUiBinder extends UiBinder<Widget, CellTableMain> {
	}

	@UiField
	Button addButton;
	@UiField
	Button searchButton;
	@UiField
	TextBox nameSearch;
	@UiField
	TextBox descSearch;
	@UiField
	TextBox phoneSearchStart;
	@UiField
	TextBox phoneSearchEnd;
	@UiField
	ListBox visibleRangeListBox;
	@UiField
	CellTable<Person> calendarCellTable;
	@UiField(provided = true)
	SimplePager pager = new SimplePager(TextLocation.CENTER,
			DynaTable.pagerResource, true, 100, true);
	private AsyncDataProvider<Person> provider = new AsyncDataProvider<Person>() {
		@Override
		protected void onRangeChanged(HasData<Person> display) {
			fetchData();
		}
	};
	private OfyQueryResult<List<Person>> data;
	private OfySortParameter sort;
	private List<OfySearchParameter> search;

	public CellTableMain() {
		initWidget(uiBinder.createAndBindUi(this));
		buildHandler();
		buildTable();
	}

	private void buildHandler() {
		RpcCenter.addGetPeoplesHandler(new GetPeoplesHandler() {
			@Override
			public void onGetPeoples(GetPeoplesEvent event) {
				data = event.getData();
				refresh();
			}
		});
//		RpcCenter.addGetPeoplesListHandler(new GetPeoplesListHandler() {
//			@Override
//			public void onGetPeoplesList(GetPeoplesListEvent event) {
//				data = event.getData();
//				refresh();
//			}
//		});
	}

	private void buildTable() {
		search = new ArrayList<OfySearchParameter>();
		provider.addDataDisplay(calendarCellTable);
		pager.setDisplay(calendarCellTable);

		TextColumn<Person> iidColumn = new TextColumn<Person>() {
			@Override
			public String getValue(Person data) {
				if (data == null) {
					return "";
				}
				return "" + data.getIdentityId();
			}
		};
		iidColumn.setDataStoreName("identityId");
		iidColumn.setSortable(true);
		TextColumn<Person> nameColumn = new TextColumn<Person>() {
			@Override
			public String getValue(Person data) {
				if (data == null) {
					return "";
				}
				return "" + data.getName();
			}
		};
		nameColumn.setDataStoreName("name");
		nameColumn.setSortable(true);
		TextColumn<Person> typeColumn = new TextColumn<Person>() {
			@Override
			public String getValue(Person data) {
				if (data == null) {
					return "";
				}
				if (data instanceof Student) {
					return "Student";
				} else {
					return "Professor";
				}
			}
		};
		TextColumn<Person> phoneColumn = new TextColumn<Person>() {
			@Override
			public String getValue(Person data) {
				if (data == null) {
					return "";
				}
				return "" + data.getPhoneNumber();
			}
		};
		phoneColumn.setDataStoreName("phoneNumber");
		phoneColumn.setSortable(true);
		TextColumn<Person> descriptionColumn = new TextColumn<Person>() {
			@Override
			public String getValue(Person data) {
				if (data == null) {
					return "";
				}
				return "" + data.getDescription();
			}
		};
		descriptionColumn.setDataStoreName("description");
		descriptionColumn.setSortable(true);
		AsyncHandler sortHandler = new AsyncHandler(calendarCellTable) {
            @Override
            public void onColumnSort(ColumnSortEvent event) {
            	updateSort();
            	fetchData();
            }
		};
		calendarCellTable.addColumnSortHandler(sortHandler);
		calendarCellTable.addColumn(iidColumn, "ID");
		calendarCellTable.addColumn(nameColumn, "Name");
		calendarCellTable.addColumn(typeColumn, "Type");
		calendarCellTable.addColumn(phoneColumn, "Phone");
		calendarCellTable.addColumn(descriptionColumn, "Desc");
	}

	@UiHandler("addButton")
	void onAddClick(ClickEvent e) {
		Window.alert(pager.getPage() + ":" + pager.getPageCount() + ":"
				+ pager.getPageSize() + ":" + pager.getPageStart());
	}

	@UiHandler("searchButton")
	void onSearchClick(ClickEvent e) {
		updateSearch();
		fetchData();
	}

	@UiHandler("visibleRangeListBox")
	void onChange(ChangeEvent e) {
		pager.setPageSize(Integer.valueOf(visibleRangeListBox
				.getValue(visibleRangeListBox.getSelectedIndex())));
	}

	@Override
	protected void onAttach() {
		super.onAttach();
		fetchData();
	}

	private void fetchData() {
		if (pager.getPageSize() < 1)
			return;
		TaskAgent ta = new TaskAgent();
		OfyQueryParameter<Person> para = new OfyQueryParameter<Person>();
		para.setCountAll(true);
		para.setMaxCount(pager.getPageSize() + 1);
		para.setStartIndex(pager.getPageStart());
		para.addSort(sort);
		for (OfySearchParameter osp : search) {
			para.addSearch(osp);
		}
		ta.addTask(new GetPeoplesTask(para));
//		ta.addTask(new GetPeoplesListTestTask(pager.getPageStart(), pager.getPageSize() + 1, sort, search));
//		ta.addTask(new GetPeoplesListTask(pager.getPageStart(), pager.getPageSize() + 1));
		ta.start();
	}

	private void updateSort() {
		ColumnSortList csl = calendarCellTable.getColumnSortList();
		for (int i = 0; i < csl.size(); i++) {
			ColumnSortInfo info = csl.get(i);
			String fieldName = info.getColumn().getDataStoreName();
			if (fieldName != null && fieldName.length() > 0) {
				if (info.isAscending()) {
					sort = new OfySortParameter(info.getColumn().getDataStoreName());
				} else {
					sort = new OfySortParameter(info.getColumn().getDataStoreName(), OfySortType.descending);
				}
			}
		}
	}

	private void updateSearch() {
		search = new ArrayList<OfySearchParameter>();
		OfySearchParameter osp;
		String tmp = nameSearch.getText().trim();
		if (!tmp.equals("")) {
			osp = new OfySearchParameter();
			osp.like("name", tmp);
			search.add(osp);
		}
		tmp = descSearch.getText().trim();
		if (!tmp.equals("")) {
			osp = new OfySearchParameter();
			osp.like("description", tmp);
			search.add(osp);
		}
		tmp = phoneSearchStart.getText().trim();
		String end = phoneSearchEnd.getText().trim();
		if (!tmp.equals("") && !end.equals("")) {
			osp = new OfySearchParameter();
			osp.between("description", tmp, end);
			search.add(osp);
		}
	}

	private void refresh() {
		if (data != null) {
			provider.updateRowData(pager.getPageStart(), data.getResult());
			if (data.getCount() > 0) {
				provider.updateRowCount(data.getCount(), true);
			} else {
				provider.updateRowCount(pager.getPageStart() + data.getResult().size(), false);
			}
		}
	}
}
