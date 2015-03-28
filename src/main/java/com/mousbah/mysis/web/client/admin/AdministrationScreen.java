package com.mousbah.mysis.web.client.admin;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.HasKeyboardSelectionPolicy.KeyboardSelectionPolicy;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DecoratorPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.mousbah.mysis.web.client.DatabaseOperations;
import com.mousbah.mysis.web.client.DatabaseOperationsAsync;
import com.mousbah.mysis.web.client.beans.GWTUser;
import com.mousbah.mysis.web.client.common.ConfirmationDialog;
import com.mousbah.mysis.web.client.common.HistoryTokens;

public class AdministrationScreen extends DecoratorPanel{

	@SuppressWarnings("unused")
	private GWTUser user;
	private CellTable<GWTUser> table = new CellTable<GWTUser>();
	private Button editButton = new Button("Edit");
	private Button deleteButton = new Button("Delete");
	private Button newButton = new Button("New User");
	private DatabaseOperationsAsync userService = GWT.create(DatabaseOperations.class);

	public AdministrationScreen(GWTUser user)   {
		this.user=user;
		drawScreen();
		History.newItem(HistoryTokens.HOME_SCREEN.getValue(), false);
	}

	public void drawScreen() { 
		table.setKeyboardSelectionPolicy(KeyboardSelectionPolicy.ENABLED);

		TextColumn<GWTUser> userNameColumn = new TextColumn<GWTUser>() {
			@Override
			public String getValue(GWTUser object) {
				return object.getUserName();
			}
		};

		TextColumn<GWTUser> firstNameColumn =new TextColumn<GWTUser>() {
			@Override
			public String getValue(GWTUser object) {
				return object.getFirstName();
			}
		};


		TextColumn<GWTUser> lastNameColumn = new TextColumn<GWTUser>() {
			@Override
			public String getValue(GWTUser object) {
				return object.getLastName();
			}
		};
		TextColumn<GWTUser> idColumn = new TextColumn<GWTUser>() {
			@Override
			public String getValue(GWTUser object) {
				return object.getId()+"";
			}
		};

		TextColumn<GWTUser> emailColumn = new TextColumn<GWTUser>() {
			@Override
			public String getValue(GWTUser object) {
				return object.getEmail();
			}
		};

		TextColumn<GWTUser> userTypeColumn = new TextColumn<GWTUser>() {
			@Override
			public String getValue(GWTUser object) {
				return object.getUserType().getValue();
			}
		};


		table.addColumn(userNameColumn,"User Name");
		table.addColumn(firstNameColumn,"First Name");
		table.addColumn(lastNameColumn,"Last Name");
		table.addColumn(idColumn,"ID");
		table.addColumn(emailColumn,"Email");
		table.addColumn(userTypeColumn,"User Type");

		HorizontalPanel hPanel=new HorizontalPanel();
		hPanel.add(newButton);
		hPanel.add(editButton);
		hPanel.add(deleteButton);
		VerticalPanel panel = new VerticalPanel();
		panel.setBorderWidth(1);	    
		panel.setWidth("400");
		panel.add(hPanel);
		panel.add(table);
		add(panel);
		userService.getAllUsers(new AllUsersRetrievalCallback());
		newButton.addClickHandler(new NewUserHandler());
		deleteButton.addClickHandler(new DeleteUserHandler());
	}


	private class AllUsersRetrievalCallback implements AsyncCallback<List<GWTUser>> {

		public void onFailure(Throwable caught) {
			Window.alert(caught.toString());
		}

		public void onSuccess(List<GWTUser> result) {
			table.setRowData(result);
		}	   
	}

	private class NewUserHandler implements ClickHandler {
		public void onClick(ClickEvent event) {
			UserCreationScreen dialog = new UserCreationScreen();
			dialog.show();
		}
	}
	
	private class DeleteUserHandler implements ClickHandler {
		public void onClick(ClickEvent event) {
			final ConfirmationDialog dialog=new ConfirmationDialog("Delete User", "Are you sure you want to delete this user");
			dialog.show();
			dialog.getOkButton().addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent event) { 
					GWTUser user=table.getVisibleItem(table.getKeyboardSelectedRow());
					userService.deleteUser(user.getId(), new AllUsersRetrievalCallback());
					dialog.hide();
				}
			});
		}
	}

}
