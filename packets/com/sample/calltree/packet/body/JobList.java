package com.sample.calltree.packet.body;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.sample.calltree.packet.BodyPacketBase;
import com.sample.calltree.packet.enums.JobStatusType;

public class JobList extends BodyPacketBase {
	
	private String resourceId;
	private List<JobStatus> jobs;
	
	public JobList(String resourceId, List<JobStatus> jobs) {
		setResourceId(resourceId);
		setJobs(jobs);
	}
	
	public String getResourceId() {
		return resourceId;
	}

	private void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}

	public List<JobStatus> getJobs() {
		return jobs;
	}

	public void setJobs(List<JobStatus> jobs) {
		this.jobs = jobs;
	}

	public static JobList createRandom() {
		
		Map<String, JobStatus> mapJobs = new HashMap<String, JobStatus>();
		for ( int i=0; i<80; i++ ) {
			JobStatus js = createJobStatus(mapJobs);
			mapJobs.put(js.getJobId(), js);
		}

		List<JobStatus> jobs = new ArrayList<JobStatus>();
		jobs.addAll(mapJobs.values());
		Random r = new Random();
		int intval = r.nextInt();
		return new JobList(Integer.toString(intval), jobs);
	}

	private static JobStatus createJobStatus(Map<String, JobStatus> jobs) {
		Random r = new Random();
		int intval = r.nextInt(15000);
		while ( jobs.containsKey(Integer.toString(intval)) ) {
			intval = r.nextInt(15000);
		}
		String jobId = Integer.toString(intval);
		JobStatus jobStatus = new JobStatus(jobId, null, JobStatusType.STOPPED);
		if ( jobs.size() > 1 ) {
			jobStatus.setParentJobId(jobs.keySet().toArray(new String[0])[r.nextInt(jobs.size())]);
		}
		return jobStatus;
	}
}
