package com.sample.calltree.packet.body;

import com.sample.calltree.packet.BodyPacketBase;
import com.sample.calltree.packet.enums.JobStatusType;

public class JobStatus extends BodyPacketBase {
	
	private String resourceId;
	private String jobId;
	private String parentJobId;
	private JobStatusType jobStatusType;
	
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

	private void setJobStatusType(JobStatusType jobStatusType) {
		this.jobStatusType = jobStatusType;
	}
}
