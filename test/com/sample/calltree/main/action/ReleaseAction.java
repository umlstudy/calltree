package com.sample.calltree.main.action;

import java.io.IOException;

import org.eclipse.jface.action.Action;

import com.sample.calltree.model.CTItem;
import com.sample.calltree.packet.Packet;
import com.sample.calltree.packet.body.JobIdentifier;
import com.sample.calltree.packet.body.JobStatus;
import com.sample.calltree.packet.enums.MessageId;
import com.sample.calltree.packet.socket.SocketHandler;

public class ReleaseAction extends Action {
	
	private SocketHandler socketHandler ;
	private CTItem item;
	
	public ReleaseAction(SocketHandler socketHandler, CTItem item) {
		super("홀드해제");
		this.socketHandler = socketHandler;
		this.item = item;
	}
	
	public void run() {
		try {
			JobStatus js = item.getJobStatus();
			JobIdentifier jobIdentifier = new JobIdentifier(js.getResourceId(), js.getJobId());
			socketHandler.send(Packet.createReqPacket(MessageId.REQ_RELEASE, jobIdentifier));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}