package com.google.gwt.sample.dynatable.client.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Widget;

public class DynaTableMain extends Composite {

	private static DynaTableMainUiBinder uiBinder = GWT
			.create(DynaTableMainUiBinder.class);

	interface DynaTableMainUiBinder extends UiBinder<Widget, DynaTableMain> {
	}

	@UiField(provided=true)
	SchoolCalendarWidget calendar;
	@UiField(provided=true)
	DayFilterWidget filter;
	@UiField
	ListBox pageSize;

	public DynaTableMain() {
		calendar = new SchoolCalendarWidget(15);
		filter = new DayFilterWidget(calendar);
		initWidget(uiBinder.createAndBindUi(this));
	}

	@UiHandler("pageSize")
	void onChange(ChangeEvent e) {
		//calendar.set
		calendar.setVisibleRows(Integer.valueOf(pageSize.getValue(pageSize.getSelectedIndex())));
	}
}
