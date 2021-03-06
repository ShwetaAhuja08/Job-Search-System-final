package com.cg.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="applied_jobs")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AppliedJobs {
	@Id   
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id",nullable=false)
	private Integer appliedJob_id;
	@ManyToOne
	@JoinColumn(name="jobseeker_id", nullable= false)
	private JobSeeker jobseeker;
	@ManyToOne
	@JoinColumn(name="job_id", nullable=false)
	private Job job;
	
	public AppliedJobs(JobSeeker jobseeker, Job job) {
		this.jobseeker = jobseeker;
		this.job = job;
	}
	
	
}