package com.ti.dto;

import java.util.List;

public class PageBean<T> {
	private int nowPage = 1;
	private int totalPage=0;
	private int totalCnt=0;
	private int prev=0;
	private int next=0;
	/**
	 * 페이지별 보여줄 목록수
	 */
	private static final int CNT_PER_PAGE = 10;
	public int getTotalCnt() {
		return totalCnt;
	}
	public void setTotalCnt(int totalCnt) {
		this.totalCnt = totalCnt;
	}
	public int getPrev() {
		return prev;
	}
	public void setPrev(int prev) {
		this.prev = prev;
	}
	public int getNext() {
		return next;
	}
	public void setNext(int next) {
		this.next = next;
	}
	public static int getCntPerPageGroup() {
		return CNT_PER_PAGE_GROUP;
	}

	private List<T> list; //타입제네릭
	
	/**
	 * 페이지그룹의 페이지수
	 */
	private static final int CNT_PER_PAGE_GROUP = 15;
	private int startPage = 1;
	private int endPage;
	
	private String url;
	

	public PageBean() {}
	public PageBean(int nowPage, int totalPage,int totalCnt, List<T> list) {
		this.nowPage=nowPage;
		this.totalPage=totalPage;
		this.totalCnt=totalCnt;
		this.list=list;
		int endPage = (int)(Math.ceil(nowPage/(double)CNT_PER_PAGE_GROUP))*CNT_PER_PAGE_GROUP;	
		this.startPage = endPage-CNT_PER_PAGE_GROUP+1;
		this.endPage = (totalPage-startPage<CNT_PER_PAGE_GROUP)? totalPage : endPage; 
		this.prev=startPage-1;
		this.next=endPage+1;
	}


	public int getNowPage() {
		return nowPage;
	}

	public void setNowPage(int nowPage) {
		this.nowPage = nowPage;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public List<T> getList() {
		return list;
	}

	public void setList(List<T> list) {
		this.list = list;
	}

	public int getStartPage() {
		return startPage;
	}

	public void setStartPage(int startPage) {
		this.startPage = startPage;
	}

	public int getEndPage() {
		return endPage;
	}

	public void setEndPage(int endPage) {
		this.endPage = endPage;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public static int getCntPerPage() {
		return CNT_PER_PAGE;
	}

	public static int getCntPerGroup() {
		return CNT_PER_PAGE_GROUP;
	}
	@Override
	public String toString() {
		return "PageBean [nowPage=" + nowPage + ", totalPage=" + totalPage + ", totalCnt=" + totalCnt + ", prev=" + prev
				+ ", next=" + next + ", list=" + list + ", startPage=" + startPage + ", endPage=" + endPage + ", url="
				+ url + "]";
	}

}
