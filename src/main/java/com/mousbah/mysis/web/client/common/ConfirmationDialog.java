package com.mousbah.mysis.web.client.common;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

public class ConfirmationDialog extends DialogBox {

	private Button ok = new Button("OK");
	private Button cancel = new Button("Cancel");
	private String title;
	private String message;

	public ConfirmationDialog(String title, String message) {
		super();
		this.title = title;
		this.message = message;
		display();
	}

	public ConfirmationDialog() {
		display();
	}

	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public String getMessage() {
		return message;
	}


	public void setMessage(String message) {
		this.message = message;
	}

	public  Button getOkButton() {
		return ok;
	}

	private void display() {
		setText(this.title);
		setAnimationEnabled(true);
		setGlassEnabled(true);

		VerticalPanel panel = new VerticalPanel();
		panel.setHeight("100");
		panel.setWidth("300");
		panel.setSpacing(10);
		panel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		Label messageLabel=new Label(message);
		HorizontalPanel hPanel=new HorizontalPanel();
		hPanel.add(ok);
		hPanel.add(cancel);
		panel.add(messageLabel);
		panel.add(hPanel);
		setWidget(panel);

		cancel.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				ConfirmationDialog.this.hide();
			}
		});
	}

}
