package com.sample.calltree.packet.body;

import java.util.Arrays;
import java.util.List;

import com.sample.calltree.packet.BodyPacketBase;

public class Resource extends BodyPacketBase {
	
	private String resourceId;
	private List<Job> jobs;

	private Resource(String resourceId, Job...jobs) {
		setResourceId(resourceId);
		setJobs(Arrays.asList(jobs));
	}

	public static Resource newInstance(String resourceId, Job...jobs) {
		return new Resource(resourceId, jobs);
	}
	
	public String getResourceId() {
		return resourceId;
	}

	private void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}

	public List<Job> getJobs() {
		return jobs;
	}

	public void setJobs(List<Job> jobs) {
		this.jobs = jobs;
	}
}
