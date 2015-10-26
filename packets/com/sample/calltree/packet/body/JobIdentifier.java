package com.sample.calltree.packet.body;

import com.sample.calltree.packet.BodyPacketBase;

public class JobIdentifier extends BodyPacketBase {

	private String resourceId;
	private String jobId;

	public JobIdentifier(String resourceId, String jobId) {
		this.setResourceId(resourceId);
		this.setJobId(jobId);
	}
	
	public static JobIdentifier newInstance(Job job) {
		return new JobIdentifier(job.getResourceId(), job.getJobId());
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
}
