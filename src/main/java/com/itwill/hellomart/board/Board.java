package com.itwill.hellomart.board;

import java.util.Date;

public class Board {
	private int boardno;
	private String title;
	private String content;
	private Date regdate;
	private int readcount;
	//글의 논리적인 순서번호를 관리하기 위한 필드 (3개)
	private int groupno;
	private int step;
	private int depth;
	/********FK*********/
	private String userId;
	private int p_no;
	
	public int getBoardno() {
		return boardno;
	}
	public void setBoardno(int boardno) {
		this.boardno = boardno;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Date getRegdate() {
		return regdate;
	}
	public void setRegdate(Date regdate) {
		this.regdate = regdate;
	}
	public int getReadcount() {
		return readcount;
	}
	public void setReadcount(int readcount) {
		this.readcount = readcount;
	}
	public int getGroupno() {
		return groupno;
	}
	public void setGroupno(int groupno) {
		this.groupno = groupno;
	}
	public int getStep() {
		return step;
	}
	public void setStep(int step) {
		this.step = step;
	}
	public int getDepth() {
		return depth;
	}
	public void setDepth(int depth) {
		this.depth = depth;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public int getP_no() {
		return p_no;
	}
	public void setP_no(int p_no) {
		this.p_no = p_no;
	}
	@Override
	public String toString() {
		return "Board [boardno=" + boardno + ", title=" + title + ", content=" + content + ", regdate=" + regdate
				+ ", readcount=" + readcount + ", groupno=" + groupno + ", step=" + step + ", depth=" + depth
				+ ", userId=" + userId + ", p_no=" + p_no + "]\n";
	}
	
	
}
