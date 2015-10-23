package com.sample.calltree.packet.body;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.sample.calltree.packet.BodyPacketBase;
import com.sample.calltree.packet.enums.JobStatusType;

public class JobList extends BodyPacketBase {
	
	private String resourceId;
	private List<JobStatus> jobs;
	private transient Map<String, JobStatus> jobsMap;
	
	public JobList(String resourceId, List<JobStatus> jobs) {
		setResourceId(resourceId);
		setJobs(jobs);
	}
	
	public void initJobsMap() {
		if ( jobsMap == null ) {
			jobsMap = new HashMap<String, JobStatus>();
		}
		if ( getJobs() != null ) {
			for ( JobStatus js : getJobs() ) {
				jobsMap.put(js.getJobId(), js);
			}
		}
	}
	
	public String getResourceId() {
		return resourceId;
	}

	private void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}
	
	public JobStatus getJobStatus(JobIdentifier jobIdentifier) {
		return jobsMap.get(jobIdentifier.getJobId());
	}

	public List<JobStatus> getJobs() {
		return Collections.unmodifiableList(jobs);
	}

	public void setJobs(List<JobStatus> jobs) {
		this.jobs = jobs;
		
		initJobsMap();
		
	}

	public static JobList createMockupJobsForTest() {
		
		Map<String, JobStatus> mapJobs = new HashMap<String, JobStatus>();
		for ( int i=0; i<10; i++ ) {
			JobStatus js = createJobStatus(mapJobs);
			mapJobs.put(js.getJobId(), js);
		}
		
		// TODO LOG
		for ( String jobId : mapJobs.keySet() ) {
			JobStatus jobStatus = mapJobs.get(jobId);
			System.out.printf("id:%s, pId:%s\n", jobId, jobStatus.getParentJobId());
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
		if ( jobs.size() > 0 ) {
			String[] keys = jobs.keySet().toArray(new String[0]);
			int pos = r.nextInt(jobs.size());
			String parentJobId = keys[pos];
			if ( parentJobId == null ) {
				throw new RuntimeException();
			}
			jobStatus.setParentJobId(parentJobId);
		}
		return jobStatus;
	}
}
