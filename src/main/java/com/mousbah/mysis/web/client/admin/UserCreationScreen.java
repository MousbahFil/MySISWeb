package com.mousbah.mysis.web.client.admin;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.mousbah.mysis.web.client.DatabaseOperations;
import com.mousbah.mysis.web.client.DatabaseOperationsAsync;
import com.mousbah.mysis.web.client.beans.GWTUser;
import com.mousbah.mysis.web.client.beans.GWTUser.GWTUserType;
import com.mousbah.mysis.web.client.common.ConfirmationDialog;
import com.mousbah.mysis.web.client.common.GridPaneHelper;
import com.mousbah.mysis.web.client.common.HistoryTokens;

public class UserCreationScreen extends DialogBox{

	private Label userNameLabel = new Label("User Name:");
	private TextBox userNameBox = new TextBox();
	private Label passwordLabel = new Label("Password");
	private PasswordTextBox passwordBox = new PasswordTextBox(); 
	private Label confirmPasswordLabel = new Label("Confirm Password");
	private PasswordTextBox confirmPasswordBox = new PasswordTextBox();
	private Label firstNameLabel = new Label("First Name:");
	private TextBox firstNameBox = new TextBox();
	private Label lastNameLabel = new Label("Last Name:");
	private TextBox lastNameBox = new TextBox();
	private Label emailLabel = new Label("Email:");
	private TextBox emailBox = new TextBox();
	private Label userTypeLabel = new Label("User Type:");
	private ListBox userTypeBox =new ListBox();
	private Button insertButton = new Button("Create User");
	private Grid grid = new Grid(7, 2);
	private GridPaneHelper gridPaneHelper=new GridPaneHelper(grid);
	private DatabaseOperationsAsync asyncService = GWT.create(DatabaseOperations.class);


	public UserCreationScreen() {
		super(true);
		drawScreen();
		History.newItem(HistoryTokens.USER_CREATION.getValue(), false);
	}

	private void drawScreen(){
		setText("Create New User");
		setModal(false);
		setAnimationEnabled(true);
		setGlassEnabled(true);

		Anchor myAnchor = new Anchor("Close"); 

		myAnchor.addClickHandler(new ClickHandler() {  
			public void onClick(ClickEvent event) {  
				hide();
			}  
		});  


		initializeComboBox();
		gridPaneHelper.addRowToGridPane(userNameLabel,userNameBox );
		gridPaneHelper.addRowToGridPane(passwordLabel,passwordBox );
		gridPaneHelper.addRowToGridPane(confirmPasswordLabel,confirmPasswordBox );
		gridPaneHelper.addRowToGridPane(firstNameLabel,firstNameBox );
		gridPaneHelper.addRowToGridPane(lastNameLabel,lastNameBox );
		gridPaneHelper.addRowToGridPane(emailLabel,emailBox );
		gridPaneHelper.addRowToGridPane(userTypeLabel,userTypeBox );

		VerticalPanel panel = new VerticalPanel();
		panel.setHeight("100");
		panel.setWidth("300");
		panel.setSpacing(10);
		panel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		panel.add(grid);

		HorizontalPanel hPanel=new HorizontalPanel();
		hPanel.add(insertButton);
		hPanel.add(myAnchor);
		panel.add(hPanel);
		setHeight("600");
		setWidth("700");
		setWidget(panel);
		
		insertButton.addClickHandler(new InsertUserHandler());
	}

	private void initializeComboBox() {
		userTypeBox.addItem(GWTUserType.STUDENT.getValue());
		userTypeBox.addItem(GWTUserType.ADMINISTRATOR.getValue());
		userTypeBox.addItem(GWTUserType.STAFF.getValue());
		userTypeBox.addItem(GWTUserType.FACULTY.getValue());
	}


	private class InsertUserHandler implements ClickHandler {

		public void onClick(ClickEvent event) {
			if(!passwordBox.getText().trim().equals(confirmPasswordBox.getText().trim())){
				Window.alert("The provided passwords do not match");
				return;
			}
			final ConfirmationDialog dialog=new ConfirmationDialog("User Insertion", "Are you sure you want to insert this user?");
			dialog.show();
			dialog.getOkButton().addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent event) {
					insertUser();
					dialog.hide();
				}
			});
		}
	}
 

	private void insertUser()  {
		GWTUser user = new GWTUser();
		user.setFirstName(firstNameBox.getText());
		user.setLastName(lastNameBox.getText());
		user.setUserName(userNameBox.getText());
		user.setPassword(passwordBox.getText());
		user.setEmail(emailBox.getText());
		user.setUserType(GWTUserType.fromString(userTypeBox.getItemText(userTypeBox.getSelectedIndex())));
		asyncService.insertUser(user, new InsertionResultCallback());
	} 



	private class InsertionResultCallback implements AsyncCallback<Void> {
		public void onSuccess(Void result) {
			Window.alert("User Successfully inserted!");
			hide();
		}

		public void onFailure(Throwable caught) {
			Window.alert(caught.toString());
		}	   
	}
}
