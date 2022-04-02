package ResumeMatcher;


public class ResumeJobDetails {

	public ResumeJobDetails(Integer key, String value, String s) {
		this.resumeLink = value;
		this.rank = key;
		this.jobTitle = s;
	}

	public String getResumeLink() {
		return resumeLink;
	}

	public void setResumeLink(String resumeLink) {
		this.resumeLink = resumeLink;
	}

	public String getJobTitle() {
		return jobTitle;
	}

	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}

	public Integer getRank() {
		return rank;
	}

	public void setRank(Integer rank) {
		this.rank = rank;
	}

	public String resumeLink;
	public String jobTitle;
	public Integer rank;

}
