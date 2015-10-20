package com.sample.calltree.dialog;

import java.io.IOException;
import java.net.Socket;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.sample.calltree.main.CallTreeMain;
import com.sample.calltree.packet.Packet;
import com.sample.calltree.packet.body.LoginRequest;
import com.sample.calltree.packet.enums.MessageId;
import com.sample.calltree.packet.socket.SocketHandler;
import com.sample.calltree.server.SocketServer;

public class UsernamePasswordDialog extends Dialog {
	
	private static final int RESET_ID = IDialogConstants.NO_TO_ALL_ID + 1;

	private Text usernameField;

	private Text passwordField;
	
	private LoginRequest loginRequest;

	public UsernamePasswordDialog(Shell parentShell) {
		super(parentShell);
	}

	protected Control createDialogArea(Composite parent) {
		Composite comp = (Composite) super.createDialogArea(parent);

		GridLayout layout = (GridLayout) comp.getLayout();
		layout.numColumns = 2;

		Label usernameLabel = new Label(comp, SWT.RIGHT);
		usernameLabel.setText("유저ID: ");

		usernameField = new Text(comp, SWT.SINGLE);
		GridData data = new GridData(GridData.FILL_HORIZONTAL);
		usernameField.setLayoutData(data);

		Label passwordLabel = new Label(comp, SWT.RIGHT);
		passwordLabel.setText("암호: ");

		passwordField = new Text(comp, SWT.SINGLE | SWT.PASSWORD);
		data = new GridData(GridData.FILL_HORIZONTAL);
		passwordField.setLayoutData(data);

		return comp;
	}

	protected void createButtonsForButtonBar(Composite parent) {
		createButton(parent, IDialogConstants.OK_ID, "로그인",
				true);
		createButton(parent, IDialogConstants.CANCEL_ID,
				"종료", false);
		createButton(parent, RESET_ID, "초기화", false);
	}

	protected void buttonPressed(int buttonId) {
		if (buttonId == RESET_ID) {
			usernameField.setText("");
			passwordField.setText("");
		} else {
			super.buttonPressed(buttonId);
		}
	}
	
	public static void main(String...args) throws IOException {
		final Display display = new Display();
		Shell shell = new Shell(display);
		shell.setLayout(new FillLayout());
		UsernamePasswordDialog upd = new UsernamePasswordDialog(shell);
		upd.open();
		display.dispose();

		LoginRequest loginRequest = upd.getLoginRequest();
		if ( loginRequest != null ) {
			Socket clientSocket = new Socket("localhost", SocketServer.port);
			SocketHandler socketHandler = SocketHandler.newInstance(clientSocket);
			socketHandler.send(Packet.createReqPacket(MessageId.REQ_LOGIN, loginRequest));
			
			CallTreeMain ct = new CallTreeMain();
			socketHandler.setPacketHandler(ct);
			Thread socketThread = new Thread(socketHandler);
			socketThread.start();
			ct.setBlockOnOpen(true);
			ct.open();
			Display.getCurrent().dispose();
		}
//		CallTreeMain.main(null);

		System.out.println("dispose!!!!!!!!!!!!!!");
	}
	
	protected void okPressed() {
		this.loginRequest = new LoginRequest(usernameField.getText().trim(), passwordField.getText().trim());
		// 서버 접속 후 .. id 체크
		super.okPressed();
	}

	public LoginRequest getLoginRequest() {
		return loginRequest;
	}
}
