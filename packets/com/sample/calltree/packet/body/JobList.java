package com.sample.calltree.packet.body;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.sample.calltree.packet.BodyPacketBase;
import com.sample.calltree.packet.enums.JobStatus;

public class JobList extends BodyPacketBase {
	
	private String resourceId;
	private List<Job> jobs;
	private transient Map<String, Job> jobsMap;
	
	public JobList(String resourceId, List<Job> jobs) {
		setResourceId(resourceId);
		setJobs(jobs);
	}
	
	public void initJobsMap() {
		if ( jobsMap == null ) {
			jobsMap = new HashMap<String, Job>();
		}
		if ( getJobs() != null ) {
			for ( Job js : getJobs() ) {
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
	
	public Job getJob(JobIdentifier jobIdentifier) {
		return jobsMap.get(jobIdentifier.getJobId());
	}

	public List<Job> getJobs() {
		return Collections.unmodifiableList(jobs);
	}

	public void setJobs(List<Job> jobs) {
		this.jobs = jobs;
		
		initJobsMap();
		
	}

	public static JobList createMockupJobsForTest() {
		
		Map<String, Job> mapJobs = new HashMap<String, Job>();
		for ( int i=0; i<10; i++ ) {
			Job job = createJob(mapJobs);
			mapJobs.put(job.getJobId(), job);
		}
		
		// TODO LOG
		for ( String jobId : mapJobs.keySet() ) {
			Job job = mapJobs.get(jobId);
			System.out.printf("id:%s, pId:%s\n", jobId, job.getParentJobId());
		}

		List<Job> jobs = new ArrayList<Job>();
		jobs.addAll(mapJobs.values());
		Random r = new Random();
		int intval = r.nextInt();
		return new JobList(Integer.toString(intval), jobs);
	}

	private static Job createJob(Map<String, Job> jobs) {
		Random r = new Random();
		int intval = r.nextInt(15000);
		while ( jobs.containsKey(Integer.toString(intval)) ) {
			intval = r.nextInt(15000);
		}
		String jobId = Integer.toString(intval);
		Job job = new Job(jobId, null, JobStatus.STOPPED);
		if ( jobs.size() > 0 ) {
			String[] keys = jobs.keySet().toArray(new String[0]);
			int pos = r.nextInt(jobs.size());
			String parentJobId = keys[pos];
			if ( parentJobId == null ) {
				throw new RuntimeException();
			}
			job.setParentJobId(parentJobId);
		}
		return job;
	}
}
