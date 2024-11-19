package com.enjoytrip.comment.model;


public class CommentDto {
	
	private String userName;
	private String title;
	private int rate;
	private String content;
	private String createAt;
	
	public CommentDto() { }
	
	public CommentDto(String userName, String title, int rate, String content) {
		super();
		this.userName = userName;
		this.title = title;
		this.rate = rate;
		this.content = content;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	public int getRate() {
		return rate;
	}

	public void setRate(int rate) {
		this.rate = rate;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getCreateAt() {
		return createAt;
	}

	public void setCreateAt(String createAt) {
		this.createAt = createAt;
	}

	@Override
	public String toString() {
		
		return "[ 작성자: " + this.getUserName() + " | 장소: " + this.getTitle() + " | 평점: " + this.getRate()
				+ "\n" + this.getContent() + " ]";
	}
}
