package com.sample.calltree.main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ShellAdapter;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Menu;

import com.sample.calltree.ctrl.CTContainerCtrl;
import com.sample.calltree.ctrl.CtrlFactory;
import com.sample.calltree.main.action.ConfirmAction;
import com.sample.calltree.main.action.HoldAction;
import com.sample.calltree.main.action.ReleaseAction;
import com.sample.calltree.model.CTItem;
import com.sample.calltree.model.CTRoot;
import com.sample.calltree.packet.Packet;
import com.sample.calltree.packet.body.JobIdentifier;
import com.sample.calltree.packet.body.JobList;
import com.sample.calltree.packet.body.Job;
import com.sample.calltree.packet.enums.JobStatus;
import com.sample.calltree.packet.enums.MessageId;
import com.sample.calltree.packet.enums.ReturnCode;
import com.sample.calltree.packet.socket.ReceviedPacketHandler;
import com.sample.calltree.packet.socket.SocketHandler;
import com.sample.calltree.ui.CallTreeCanvas;
import com.sample.calltree.ui.PopupActionProvider;

public class CallTreeMain extends ApplicationWindow implements PopupActionProvider, ReceviedPacketHandler {

	private CallTreeViewer tv;
	private CallTreeCanvas canvas ;
	private CTRoot ctRoot;
	private SocketHandler socketHandler;
	
	private JobList jobList;
	
	public CallTreeMain() {
		super(null);
	}

	protected Control createContents(Composite parent_) {
		Composite parent = new Composite(parent_, SWT.NONE);
		
		GridDataFactory gdFactory = GridDataFactory.fillDefaults();
		GridLayoutFactory glFactory = GridLayoutFactory.fillDefaults();
		parent.setLayout(glFactory.numColumns(2).create());
		tv = new CallTreeViewer(parent);
		tv.getControl().setLayoutData(gdFactory.hint(250, 200).create());
		tv.setLabelProvider(new CallTreeViewerLabelProvider());
		tv.setContentProvider(new CallTreeVieweContentProvider());
		
		createContextMenu(tv);
		
		Composite gvParent = new Composite(parent, SWT.NONE);
		gvParent.setLayout(glFactory.numColumns(1).create());
		gvParent.setLayoutData(gdFactory.grab(true, true).create());
		canvas = new CallTreeCanvas(gvParent);
		canvas.setLayoutData(gdFactory.grab(true, true).create());
		//canvas.setBackground(parent.getShell().getDisplay().getSystemColor(SWT.COLOR_BLUE));
		canvas.setCtrlFactory(CtrlFactory.newInstance());
		
//		ctRoot = createDummyCTRoot();
//		//gv.setRootEditPart((RootEditPart)CTEditPartFactory.createEditPart(ctRoot));
//		canvas.setContents(ctRoot, this);
//		
//		ctRoot.setAllowFiringModelUpdate(true);
//		ctRoot.fireModelUpdated();
		
		getShell().addShellListener(new ShellAdapter() {
			
			boolean firstShow = true;
			@Override
			public void shellActivated(ShellEvent e) {
				if ( firstShow ) {
					appStarted();
					firstShow = false;
				}
			}
		});
		
		return parent;
	}

	protected void appStarted() {
//		// 임의의 샘플 데이터를 50개 생성함
//		for ( int i=0; i<50; i++ ) {
//			int itemCnt = getItemCnt(ctRoot) + 1;
//			Random r = new Random();
//			int randomPos = r.nextInt(itemCnt);
//			final CTContainer container = (CTContainer)getItem(ctRoot, randomPos, 0);
//			final CTItem ctItem1 = new CTItem(String.format("%d", i));
//			ctItem1.setLocation(new Point(20,20));
//			ctItem1.setBackgroundColor(ColorConstants.lightBlue);
//			ctItem1.setDimension(new Dimension(170, 10));
//			container.addChild(ctItem1);
//		}
//		ctRoot.arrangeChildSizeLocations();
//		tv.refresh();
		try {
			socketHandler.send(Packet.createReqPacket(MessageId.REQ_JOBLIST, null));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

//	private CTElement getItem(CTContainer cont, int randomPos, int curPos) {
//		if ( randomPos == 0 ) return cont;
//		
//		for ( CTElement ele : cont.getChildItems(ChildItemSelectOptions.All) ) {
//			curPos ++;
//			if ( randomPos == curPos ) return ele;
//			if ( ele instanceof CTContainer ) {
//				CTContainer ctc = (CTContainer)ele;
//				CTElement ele2 = getItem(ctc, randomPos, curPos);
//				if ( ele2 != null ) {
//					return ele2;
//				} else {
//					curPos += getItemCnt(ctc);
//				}
//			}
//		} 
//		return null;
//	}
//
//	private int getItemCnt(CTContainer cont) {
//		int cnt = cont.getChildItems(ChildItemSelectOptions.All).size();
//		for ( CTElement ele : cont.getChildItems(ChildItemSelectOptions.All) ) {
//			if ( ele instanceof CTContainer ) {
//				CTContainer ctc = (CTContainer)ele;
//				cnt += getItemCnt(ctc);
//			}
//		} 
//		return cnt;
//	}

	private void createContextMenu(final CallTreeViewer tv) {
		MenuManager menuMgr = new MenuManager();
	    menuMgr.setRemoveAllWhenShown(true);
	    menuMgr.addMenuListener(new IMenuListener() {
			@Override
	        public void menuAboutToShow(IMenuManager manager) {
				tv.fillContextMenu(manager);
	        }
	    });
	    Menu menu = menuMgr.createContextMenu(tv.getControl());
	    tv.getControl().setMenu(menu);
	}

//	private static CTRoot createDummyCTRoot() {
//		CTRoot root = new CTRoot("root");
//		root.setBackgroundColor(ColorConstants.orange);
//		
//		CTItem ctItem1 = new CTItem("item1");
//		ctItem1.setLocation(new Point(100,100));
//		ctItem1.setBackgroundColor(ColorConstants.green);
//		ctItem1.setDimension(new Dimension(70, 130));
//		root.addChild(ctItem1);
//		
//		CTItem ctItem2 = new CTItem("item2");
//		ctItem2.setLocation(new Point(20,70));
//		ctItem2.setBackgroundColor(ColorConstants.darkGray);
//		ctItem2.setDimension(new Dimension(70, 40));
//		root.addChild(ctItem2);
//		
//		CTItem ctItem3 = new CTItem("item3");
//		ctItem3.setLocation(new Point(50,20));
//		ctItem3.setBackgroundColor(ColorConstants.darkGreen);
//		ctItem3.setDimension(new Dimension(30, 80));
//		root.addChild(ctItem3);
//		
////		CTConnection con1 = CTConnection.newInstance("con1");
////		con1.setSource(ctItem1);
////		con1.setTarget(ctItem2);
////		root.addConnection(con1);
//		
//		return root;
//	}

	public static void main(String[] args) {
		CallTreeMain ct = new CallTreeMain();
		ct.setBlockOnOpen(true);
		ct.open();
		Display.getCurrent().dispose();
	}

	@Override
	public List<Action> getContextActions(CTContainerCtrl ctrl) {
		List<Action> actions = new ArrayList<Action>();
		if ( ctrl != null && ctrl.getElement() instanceof CTItem ) {
			CTItem ctItem = (CTItem)ctrl.getElement();
			
			if ( ctItem.getOwner() instanceof CTRoot ) {
				if ( ctItem.getJobStatus() == JobStatus.STOPPED ) {
					actions.add(new ConfirmAction(socketHandler, ctItem));
				}
			} else {
				if ( ctItem.getJobStatus() == JobStatus.STOPPED ) {
					actions.add(new HoldAction(socketHandler, ctItem));
				}
				if ( ctItem.getJobStatus() == JobStatus.HOLD ) {
					actions.add(new ReleaseAction(socketHandler, ctItem));
				}
			}
		}
		
		return actions;
	}

	@Override
	public void packetReceived(final Packet receivedPacket) {
		
		if ( receivedPacket.getReturnCode() == ReturnCode.FAIL ) {
			final com.sample.calltree.packet.body.Error error = (com.sample.calltree.packet.body.Error)receivedPacket.getBody();
			getParentShell().getDisplay().asyncExec(new Runnable() {
				@Override
				public void run() {
					MessageDialog.openInformation(getParentShell(), "확인", error.getErrorMsg());
				}
			});
			return;
		}
		
		switch ( receivedPacket.getMessageId() ) {
		case REQ_JOBLIST :
			System.out.println("REQ_JOBLIST Consuming...");
			jobList = (JobList)receivedPacket.getBody();
			jobList.initJobsMap();
			
			// TODO LOG
			for ( Job job : jobList.getJobs() ) {
				System.out.printf("id:%s, pId:%s\n", job.getJobId(), job.getParentJobId());
			}
			
			ctRoot = createItems(jobList);
			ctRoot.arrangeChildSizeLocations();

			//gv.setRootEditPart((RootEditPart)CTEditPartFactory.createEditPart(ctRoot));
			//getParentShell().getDisplay().asyncExec(new Runnable() {
			Display.getDefault().asyncExec(new Runnable() {
				@Override
				public void run() {
					canvas.setContents(ctRoot, CallTreeMain.this);
					ctRoot.setAllowFiringModelUpdate(true);
					ctRoot.fireModelUpdated();
					tv.setInput(ctRoot);
				}
			});
			
			break;
		case REQ_HOLD :
		case REQ_RELEASE :
			Job recvJob = (Job)receivedPacket.getBody();
			JobIdentifier jobIdentifier = JobIdentifier.newInstance(recvJob);
			final Job onMemoryJob = jobList.getJob(jobIdentifier);
			
			onMemoryJob.setJobStatus(recvJob.getJobStatus());
			// 모델 변경 노티
			Display.getDefault().asyncExec(new Runnable() {
				@Override
				public void run() {
					onMemoryJob.fireModelUpdated();
				}
			});
			
			break;
		}
	}

	private CTRoot createItems(JobList jobList) {
		CTRoot root = new CTRoot("root");
		
		Job confirmJob = null;
		for ( Job js : jobList.getJobs() ) {
			if ( js.getParentJobId() == null ) {
				confirmJob = js;
				break;
			}
		}
		
		CTItem confirmJobItem = new CTItem(confirmJob);
		confirmJobItem.setLocation(new Point(100,100));
		confirmJobItem.setDimension(new Dimension(70, 130));
		root.addChild(confirmJobItem);
		
		generateItemTreeByParentJob(confirmJob, jobList, confirmJobItem);
		
		return root;
	}

	private void generateItemTreeByParentJob(Job parentJob, JobList jobList, CTItem parentItem) {
		for ( Job childJs : jobList.getJobs() ) {
			if ( parentJob.getJobId().equals(childJs.getParentJobId()) ) {
				CTItem childJobItem = new CTItem(childJs);
				childJobItem.setLocation(new Point(100,100));
				childJobItem.setDimension(new Dimension(70, 130));
				parentItem.addChild(childJobItem);
				
				generateItemTreeByParentJob(childJs, jobList, childJobItem);
			}
		}
	}

	public void setSocketHandler(SocketHandler socketHandler) {
		this.socketHandler = socketHandler;
	}
}
