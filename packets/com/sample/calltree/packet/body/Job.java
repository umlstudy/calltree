package com.sample.calltree.packet.body;

import com.sample.calltree.model.CTItem;
import com.sample.calltree.packet.BodyPacketBase;
import com.sample.calltree.packet.enums.JobStatus;

public class Job extends BodyPacketBase {
	
	private String resourceId;
	private String jobId;
	private String parentJobId;
	private JobStatus jobStatus;
	private transient CTItem ctItem;
	
	public Job(String resourceId, String jobId, String parentJobId, JobStatus jobStatus) {
		setResourceId(resourceId);
		setJobId(jobId);
		setParentJobId(parentJobId);
		setJobStatus(jobStatus);
	}
	
	public Job(String jobId, String parentJobId, JobStatus jobStatus) {
		setJobId(jobId);
		setParentJobId(parentJobId);
		setJobStatus(jobStatus);
	}
	
	public JobIdentifier createJobIdentifier() {
		return new JobIdentifier(getResourceId(), getJobId());
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

	public JobStatus getJobStatus() {
		return jobStatus;
	}

	public void setJobStatus(JobStatus jobStatus) {
		this.jobStatus = jobStatus;
		
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
