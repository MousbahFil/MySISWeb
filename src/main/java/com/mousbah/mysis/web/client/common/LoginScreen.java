
package com.mousbah.mysis.web.client.common;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DecoratorPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.mousbah.mysis.web.client.DatabaseOperations;
import com.mousbah.mysis.web.client.DatabaseOperationsAsync;
import com.mousbah.mysis.web.client.admin.AdministrationScreen;
import com.mousbah.mysis.web.client.beans.GWTStudent;
import com.mousbah.mysis.web.client.beans.GWTUser;
import com.mousbah.mysis.web.client.enduser.HomeScreen;

public class LoginScreen extends DecoratorPanel{
	
	private static Label userNameLabel = new Label("User Name:");
	private static TextBox userNameTextBox = new TextBox();
	private static Label passwordLabel = new Label("Password:");
	private static PasswordTextBox passwordTextBox = new PasswordTextBox();
	private static Button loginButton = new Button("Login");
	private DatabaseOperationsAsync messageService = GWT.create(DatabaseOperations.class);
	private Grid grid = new Grid(2,2);
	private GridPaneHelper gridPaneHelper=new GridPaneHelper(grid);
	private RootPanel rootPanel=RootPanel.get("gwtContainer");


	public LoginScreen() {
		drawScreen();
		History.newItem(HistoryTokens.LOGIN.getValue(), false);
	}

	private void drawScreen(){
		loginButton.addClickHandler(new LoginHandler());
		passwordTextBox.addKeyDownHandler(new LoginKeyDownHandler());

		gridPaneHelper.addRowToGridPane(userNameLabel,userNameTextBox);
		gridPaneHelper.addRowToGridPane( passwordLabel,passwordTextBox);

		VerticalPanel panel = new VerticalPanel();
		panel.setSpacing(10);
		panel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		panel.setSize("300", "100");
		panel.add(grid);
		panel.add(loginButton);
		add(panel);
	}
	

	private class LoginHandler implements ClickHandler {
		public void onClick(ClickEvent event) {
			messageService.validateUser(userNameTextBox.getText(), passwordTextBox.getText(),new AuthenticateUserCallback());
		}
	}

	private class LoginKeyDownHandler implements KeyDownHandler {
		public void onKeyDown(KeyDownEvent event) {
			if(event.getNativeKeyCode() == KeyCodes.KEY_ENTER){
				messageService.validateUser(userNameTextBox.getText(), passwordTextBox.getText(),new AuthenticateUserCallback());
			}
		}
	}
	
	private class AuthenticateUserCallback implements AsyncCallback<GWTUser> {
		public void onFailure(Throwable caught) {
			Window.alert(caught.toString());
		}

		public void onSuccess(GWTUser result) {
			switch (result.getUserType()) {
			case STUDENT:
				rootPanel.clear();
				HomeScreen screen = new HomeScreen((GWTStudent) result);
				rootPanel.add(screen);
				break; 

			case ADMINISTRATOR:
				rootPanel.clear();
				AdministrationScreen admin = new AdministrationScreen(result);
				rootPanel.add(admin);
				break;

			default:
				Window.alert("this user type is not supported!");
				break;
			}
		}	   
	}

}
