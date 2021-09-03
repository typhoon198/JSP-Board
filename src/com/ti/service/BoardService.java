package com.ti.service;

import java.io.FileInputStream;
import java.util.List;
import java.util.Properties;

import com.ti.dao.BoardDAO;
import com.ti.dto.Board;
import com.ti.exception.AddException;
import com.ti.exception.FindException;
import com.ti.exception.ModifyException;
import com.ti.exception.RemoveException;

public class BoardService {
	private BoardDAO dao;
	private static BoardService service;
	public static String envProp;;
	private BoardService() {
		Properties env = new Properties();
		try {
			env.load(new FileInputStream(envProp));  
			String className = env.getProperty("boardDAO"); 
			Class c = Class.forName(className);  
			dao = (BoardDAO)c.newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		} 

	}
	public static BoardService getInstance() {
		if(service == null) {
			service = new BoardService();
		}
		return service;
	}
	public List<Board> findByPage(String keyField, String keyWord, int start,int end) throws FindException{
		return dao.selectByPage(keyField,keyWord,start, end);
	}
	public int findTotalCnt(String keyField, String keyWord) throws FindException {
		return dao.selectTotalCnt(keyField,keyWord);
	}

	public Board findByNum(int num) throws FindException {

		try {
			dao.updateCount(num);
		} catch (ModifyException e) {
			e.printStackTrace();
			throw new FindException("조회수 증가 실패:" + e.getMessage());
		}
		return dao.selectByNum(num);
	}
	public void setBoard(Board board) throws AddException {
		dao.insertBoard(board);
	}
	public int getMaxNum() throws AddException {
		return dao.selectMaxNum();
	}
	
	public void modifyBoard(Board board) throws ModifyException {
		dao.updateBoard(board);
	}
	
	public void removeBoard(int num) throws RemoveException {
		dao.deleteBoard(num);
	}
	
	public void replyBoard(Board reBoard) throws AddException {
		dao.reply(reBoard);
	}
	
	
}
