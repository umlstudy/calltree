package com.sample.calltree.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

import org.apache.commons.io.IOUtils;

import com.sample.calltree.packet.Packet;
import com.sample.calltree.packet.body.Job;
import com.sample.calltree.packet.body.JobIdentifier;
import com.sample.calltree.packet.body.JobList;
import com.sample.calltree.packet.body.LoginRequest;
import com.sample.calltree.packet.body.Resource;
import com.sample.calltree.packet.enums.JobStatus;
import com.sample.calltree.packet.enums.MessageId;
import com.sample.calltree.packet.socket.ReceviedPacketHandler;
import com.sample.calltree.packet.socket.SocketHandler;

public class SocketServer {

	public final static int PORT = 19991;
	
	public static JobList jobList = null;
	
	public static void main(String[] args) {
		ServerSocket serverSocket = null;
		try {
			serverSocket = new ServerSocket(PORT);
			System.out.println("ServerSocket Initialized");

			while (true) {
				System.out.println("Waiting for client...");
				Socket clientSocket = serverSocket.accept();
				System.out.println("Client accepted...");
				SocketHandler socketHandler = null;
				socketHandler = SocketHandler.newInstance(clientSocket);
				final SocketHandler fSocketHandler = socketHandler;
				socketHandler.setPacketHandler(new ReceviedPacketHandler() {
					@Override
					public void packetReceived(Packet receivedPacket) throws Exception {
						SocketServer.packetReceived(fSocketHandler, receivedPacket);
					}
				});
				Thread socketThread = new Thread(socketHandler);
				System.out.println("새로운 스레스 생성 및 소켓에 대한 통신 위임...");
				socketThread.start();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			IOUtils.closeQuietly(serverSocket);
		}
	}

//	private static void handlePacket(BufferedOutputStream os) throws IOException {
//		OutputStreamWriter osw = new OutputStreamWriter(os, "US-ASCII");
//		osw.write("OK");
//		osw.flush();
//	}
//
//	private static String recevingPacket(BufferedInputStream is) throws IOException {
//		StringBuffer process;
//		int character;
//		String TimeStamp;
//		InputStreamReader isr = new InputStreamReader(is);
//		process = new StringBuffer();
//		while ((character = isr.read()) != 13) {
//			process.append((char) character);
//		}
//		System.out.println(process);
//		try {
//			Thread.sleep(10000);
//		} catch (Exception e) {
//		}
//		TimeStamp = new java.util.Date().toString();
//		return "SocketServer repsonded at " + TimeStamp + (char) 13;
//		
//	}

	private static void packetReceived(SocketHandler socketHandler, Packet receivedPacket) throws IOException {

		Packet resPacket = null;
		JobIdentifier jobIdent = null;
		Job jobStatus = null;
		
		switch ( receivedPacket.getMessageId() ) {
		case REQ_LOGIN :
			LoginRequest loginRequest = (LoginRequest)receivedPacket.getBody();
			if ( "test".equals(loginRequest.getLoginId()) && "test".equals(loginRequest.getLoginPassword() ) ) {
				resPacket = Packet.createResPacket(receivedPacket, null);
				socketHandler.send(resPacket);
			} else {
				resPacket = Packet.errorResPacket(receivedPacket, 011, "로그인 ID 및 암호를 확인하세요.");
				socketHandler.send(resPacket);
			}
			break;
		case REQ_JOBLIST :
			if ( jobList == null ) {
				jobList = JobList.createMockupJobsForTest();
			}
			resPacket = Packet.createResPacket(receivedPacket, jobList);
			socketHandler.send(resPacket);
			break;
			
		case REQ_HOLD :
			jobIdent = (JobIdentifier)receivedPacket.getBody();
			jobStatus = jobList.getJob(jobIdent);
			jobStatus.setJobStatus(JobStatus.HOLD);
			resPacket = Packet.createResPacket(receivedPacket, jobStatus);
			socketHandler.send(resPacket);
			break;
			
		case REQ_CONFIRM :
			jobIdent = (JobIdentifier)receivedPacket.getBody();
			// 1. 먼저 응답 패킷 전송
			resPacket = Packet.createResPacket(receivedPacket, null);
			socketHandler.send(resPacket);
			
			startJob(socketHandler, jobIdent);
			// 
		}
	}

	private static void startJob(SocketHandler socketHandler, JobIdentifier jobIdent) throws IOException {
		Job job = jobList.getJob(jobIdent);
		
		if ( job.getJobStatus() != JobStatus.HOLD ) {
			job.setJobStatus(JobStatus.RUNNING);
			Packet packet = Packet.createPushPacket(MessageId.PUSH_JOBSTATUS, Resource.newInstance(job.getResourceId(), job));
			try {
				Thread.sleep(500);
			} catch ( Exception e ) {
				
			}
			socketHandler.send(packet);
			
			List<Job> childJobs = jobList.getChildJobs(jobIdent.getJobId());
			for ( Job childJob : childJobs ) {
				startJob(socketHandler, childJob.createJobIdentifier());
			}
		}
	}
}