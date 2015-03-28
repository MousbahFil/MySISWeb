package com.mousbah.mysis.web.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.RootPanel;
import com.mousbah.mysis.web.client.common.HistoryTokens;
import com.mousbah.mysis.web.client.common.LoginScreen;

public class MySISWebEntryPoint implements EntryPoint {
	private RootPanel rootPanel =RootPanel.get("gwtContainer");


	public void onModuleLoad() {
		LoginScreen loginScreen=new LoginScreen();
		rootPanel.add(loginScreen);

		History.addValueChangeHandler(new ValueChangeHandler<String>() {
			public void onValueChange(ValueChangeEvent<String> event) {
				String historyToken = event.getValue();
				if (historyToken.equals(HistoryTokens.LOGIN.getValue())) { 
					rootPanel.clear();
					LoginScreen loginScreen=new LoginScreen();
					rootPanel.add(loginScreen);
				} 
			}
		});
	}

}