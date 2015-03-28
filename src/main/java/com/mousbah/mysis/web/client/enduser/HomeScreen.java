package com.mousbah.mysis.web.client.enduser;

import java.util.List;

import com.google.gwt.cell.client.ButtonCell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.HasKeyboardSelectionPolicy.KeyboardSelectionPolicy;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DecoratorPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.mousbah.mysis.web.client.DatabaseOperations;
import com.mousbah.mysis.web.client.DatabaseOperationsAsync;
import com.mousbah.mysis.web.client.beans.GWTCourse;
import com.mousbah.mysis.web.client.beans.GWTStudent;
import com.mousbah.mysis.web.client.common.ConfirmationDialog;
import com.mousbah.mysis.web.client.common.HistoryTokens;

public class HomeScreen extends DecoratorPanel{ 

	private GWTStudent student;
	private CellTable<GWTCourse> table = new CellTable<GWTCourse>();
	private Label crnLabel =new Label("Enter CRN:");
	private TextBox crnTextBox=new TextBox();
	private Button registerButton=new Button("Register");
	private DatabaseOperationsAsync userService = GWT.create(DatabaseOperations.class);

	public HomeScreen(GWTStudent student)  {
		this.student=student;
		drawScreen();
		History.newItem(HistoryTokens.HOME_SCREEN.getValue(), false);
	}

	public void drawScreen() {
		table.setKeyboardSelectionPolicy(KeyboardSelectionPolicy.ENABLED);
		registerButton.addClickHandler(new MyClickHandler());

		TextColumn<GWTCourse> courseNameColumn = new TextColumn<GWTCourse>() {
			@Override
			public String getValue(GWTCourse object) {
				return object.getCourseName();
			}
		};

		TextColumn<GWTCourse> crnColumn =new TextColumn<GWTCourse>() {
			@Override
			public String getValue(GWTCourse object) {
				return object.getCrn()+"";
			}
		};


		TextColumn<GWTCourse> creditsColumn = new TextColumn<GWTCourse>() {
			@Override
			public String getValue(GWTCourse object) {
				return String.valueOf(object.getCredits());
			}
		};

		ButtonCell buttonCell= new ButtonCell();
		Column<GWTCourse, String> dropColumn =new Column<GWTCourse, String>(buttonCell) {
			@Override
			public String getValue(GWTCourse object) {
				return "Drop";
			}
		};

		dropColumn.setFieldUpdater(new FieldUpdater<GWTCourse, String>() {
			public void update(int index, GWTCourse object, String value) {
				final GWTCourse finalCourse=object;
				final ConfirmationDialog dialog=new ConfirmationDialog("Course Drop","are you sure you want to drop ?"+ object.getCourseName());
				dialog.show();
				dialog.getOkButton().addClickHandler(new ClickHandler() {
					public void onClick(ClickEvent event) {
						userService.dropStudentfromCourse(student, finalCourse, new DropCourseCallback());
						dialog.hide();
					}
				});
			}
		});

		table.addColumn(courseNameColumn, "Course Name");
		table.addColumn(crnColumn, "CRN");
		table.addColumn(creditsColumn, "Credits");  	    	    	      
		table.addColumn(dropColumn, "Drop Course");


		HorizontalPanel hPanel=new HorizontalPanel();
		hPanel.add(crnLabel);
		hPanel.add(crnTextBox);
		VerticalPanel panel = new VerticalPanel();
		panel.setBorderWidth(1);	    
		panel.setWidth("400");
		panel.add(hPanel);
		panel.add(registerButton);
		panel.add(table);
		add(panel);

		userService.getRegisteredCourses(student,new RefreshTabledata());
	}


	private class MyClickHandler implements ClickHandler {
		public void onClick(ClickEvent event) {
			final ConfirmationDialog dialog = new ConfirmationDialog("Course Insertion", "Are you sure you want to register for this course?");
			dialog.show();
			dialog.getOkButton().addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent event) {
					userService.registerStudentInCourse(student, Integer.valueOf(crnTextBox.getText()), new GetEnrolledCourses());
					dialog.hide();
				}
			});
		}
	}

	private class GetEnrolledCourses implements AsyncCallback<List<GWTCourse>> {

		public void onFailure(Throwable caught) {
			Window.alert(caught.toString());
		}


		public void onSuccess(List<GWTCourse> result) {
			Window.alert("course successfully registered!");
			table.setRowData(result);
		}
	}

	private class DropCourseCallback implements AsyncCallback<List<GWTCourse>> {

		public void onFailure(Throwable caught) {
			Window.alert(caught.toString());
		}

		public void onSuccess(List<GWTCourse> result) {
			table.setRowData(result);
			Window.alert("Course successfully droped!");
		}
	}	

	private class RefreshTabledata implements AsyncCallback<List<GWTCourse>> {

		public void onFailure(Throwable caught) {
			Window.alert(caught.toString());
		}

		public void onSuccess(List<GWTCourse> result) {
			table.setRowData(result);
		}	   
	}
}
