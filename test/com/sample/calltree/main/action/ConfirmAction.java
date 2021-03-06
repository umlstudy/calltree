package com.sample.calltree.main.action;

import java.io.IOException;

import org.eclipse.jface.action.Action;

import com.sample.calltree.model.CTItem;
import com.sample.calltree.packet.Packet;
import com.sample.calltree.packet.body.JobIdentifier;
import com.sample.calltree.packet.body.Job;
import com.sample.calltree.packet.enums.MessageId;
import com.sample.calltree.packet.socket.SocketHandler;

public class ConfirmAction extends Action {
	
	private SocketHandler socketHandler ;
	private CTItem item;
	
	public ConfirmAction(SocketHandler socketHandler, CTItem item) {
		super("컨핌요청");
		this.socketHandler = socketHandler;
		this.item = item;
	}
	
	public void run() {
		try {
			Job job = item.getJob();
			JobIdentifier jobIdentifier = new JobIdentifier(job.getResourceId(), job.getJobId());
			socketHandler.send(Packet.createReqPacket(MessageId.REQ_CONFIRM, jobIdentifier));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}