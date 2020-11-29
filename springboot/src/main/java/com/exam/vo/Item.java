package com.exam.vo;

import lombok.Data;

//題目
@Data
public class Item {

    private String subject;

    private Integer paperId;

    private Integer changeNumber;

    private Integer fillNumber;

    private Integer judgeNumber;

	/**
	 * @return the subject
	 */
	public String getSubject() {
		return subject;
	}

	/**
	 * @param subject the subject to set
	 */
	public void setSubject(String subject) {
		this.subject = subject;
	}

	/**
	 * @return the paperId
	 */
	public Integer getPaperId() {
		return paperId;
	}

	/**
	 * @param paperId the paperId to set
	 */
	public void setPaperId(Integer paperId) {
		this.paperId = paperId;
	}

	/**
	 * @return the changeNumber
	 */
	public Integer getChangeNumber() {
		return changeNumber;
	}

	/**
	 * @param changeNumber the changeNumber to set
	 */
	public void setChangeNumber(Integer changeNumber) {
		this.changeNumber = changeNumber;
	}

	/**
	 * @return the fillNumber
	 */
	public Integer getFillNumber() {
		return fillNumber;
	}

	/**
	 * @param fillNumber the fillNumber to set
	 */
	public void setFillNumber(Integer fillNumber) {
		this.fillNumber = fillNumber;
	}

	/**
	 * @return the judgeNumber
	 */
	public Integer getJudgeNumber() {
		return judgeNumber;
	}

	/**
	 * @param judgeNumber the judgeNumber to set
	 */
	public void setJudgeNumber(Integer judgeNumber) {
		this.judgeNumber = judgeNumber;
	}
	
}
