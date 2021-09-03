package com.ti.dao;

import java.sql.Connection;
import java.util.List;

import com.ti.dto.Board;
import com.ti.exception.AddException;
import com.ti.exception.FindException;
import com.ti.exception.ModifyException;
import com.ti.exception.RemoveException;

public interface BoardDAO {

	public List<Board> selectByPage(String keyField, String keyWord,int start, int end) throws FindException;
	public int selectTotalCnt(String keyField, String keyWord) throws FindException;
	public void updateCount(int num) throws ModifyException;
	public Board selectByNum(int num) throws FindException;
	public int selectMaxNum() throws AddException;
	public void insertBoard(Board board) throws AddException;
	public void updateBoard(Board board) throws ModifyException;
	public void deleteBoard(int num) throws RemoveException;
	public void reply(Board reBoard) throws AddException;
	public void insertReply(Connection con,Board board) throws AddException;
	public void updateReplyUp(Connection con,int ref, int pos) throws ModifyException;
}
