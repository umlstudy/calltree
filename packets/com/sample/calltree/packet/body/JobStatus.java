package com.sample.calltree.packet.body;

import com.sample.calltree.model.CTItem;
import com.sample.calltree.packet.BodyPacketBase;
import com.sample.calltree.packet.enums.JobStatusType;

public class JobStatus extends BodyPacketBase {
	
	private String resourceId;
	private String jobId;
	private String parentJobId;
	private JobStatusType jobStatusType;
	private transient CTItem ctItem;
	
	public JobStatus(String resourceId, String jobId, String parentJobId, JobStatusType jobStatusType) {
		setResourceId(resourceId);
		setJobId(jobId);
		setParentJobId(parentJobId);
		setJobStatusType(jobStatusType);
	}
	
	public JobStatus(String jobId, String parentJobId, JobStatusType jobStatusType) {
		setJobId(jobId);
		setParentJobId(parentJobId);
		setJobStatusType(jobStatusType);
	}

	public String getResourceId() {
		return resourceId;
	}

	private void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}

	public String getJobId() {
		return jobId;
	}

	private void setJobId(String jobId) {
		this.jobId = jobId;
	}

	public String getParentJobId() {
		return parentJobId;
	}

	public void setParentJobId(String parentJobId) {
		this.parentJobId = parentJobId;
	}

	public JobStatusType getJobStatusType() {
		return jobStatusType;
	}

	public void setJobStatusType(JobStatusType jobStatusType) {
		this.jobStatusType = jobStatusType;
		
		if ( getCtItem() != null ) {
			getCtItem().changeBackground();
		}
	}

	public CTItem getCtItem() {
		return ctItem;
	}

	public void setCtItem(CTItem ctItem) {
		this.ctItem = ctItem;
	}
	
	public void fireModelUpdated(){
		if ( getCtItem() != null ) {
			getCtItem().fireModelUpdated();
		}
	}
}
