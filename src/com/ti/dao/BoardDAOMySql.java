package com.ti.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ti.dto.Board;
import com.ti.exception.AddException;
import com.ti.exception.FindException;
import com.ti.exception.ModifyException;
import com.ti.exception.RemoveException;
import com.ti.sql.MyConnection;

public class BoardDAOMySql implements BoardDAO {
	public BoardDAOMySql() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			System.out.println("JDBC 드라이버 로딩 성공");
		}catch(Exception e) {
			System.out.println("Error : JDBC 드라이버 로딩 실패");
		}
	} 

	public List<Board> selectByPage(String keyField, String keyWord,
			int start, int end) throws FindException {
		Connection con = null;
		try {
			con = MyConnection.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new FindException(e.getMessage());
		}
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Board> list = new ArrayList<>();
		try {
			if (keyWord==null || keyWord.equals("")) {
				String SelectByPageSQL = "SELECT * FROM tblBoard ORDER BY ref DESC, pos LIMIT ?, ?";
				pstmt = con.prepareStatement(SelectByPageSQL);
				pstmt.setInt(1, start);
				pstmt.setInt(2, end);

			} else {
				String SelectByPageSQL = "SELECT * FROM  tblBoard WHERE " + keyField + " LIKE ? ";
				SelectByPageSQL += "ORDER BY ref desc, pos limit ? , ?";
				pstmt = con.prepareStatement(SelectByPageSQL);
				pstmt.setString(1, "%" + keyWord + "%");
				pstmt.setInt(2, start);
				pstmt.setInt(3, end);
			}
			rs = pstmt.executeQuery();
			while(rs.next()) {
				Board board = new Board();
				board.setNum(rs.getInt("num"));
				board.setName(rs.getString("name"));
				board.setSubject(rs.getString("subject"));
				board.setPos(rs.getInt("pos"));
				board.setRef(rs.getInt("ref"));
				board.setDepth(rs.getInt("depth"));
				board.setRegdate(rs.getString("regdate"));
				board.setCount(rs.getInt("count"));
				list.add(board);
			}
			if(list.size() == 0) {
				throw new FindException("게시물이 없습니다.");
			}
			return list; 
		} catch (SQLException e) {
			e.printStackTrace(); 
			throw new FindException(e.getMessage());
		} finally {
			MyConnection.close(con, pstmt, rs);
		}				
	}

	public int selectTotalCnt(String keyField, String keyWord) throws FindException {
		Connection con = null;

		try {
			con = MyConnection.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new FindException(e.getMessage());
		}
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int totalCount= 0;
		try {
			if (keyWord==null || keyWord.equals("")) {
				String SelectTotalCntSQL = "SELECT COUNT(num) FROM tblBoard";
				pstmt = con.prepareStatement(SelectTotalCntSQL);
			} else {
				String SelectByPageSQL = "SELECT count(num) FROM  tblBoard WHERE " + keyField + " LIKE ? ";
				pstmt = con.prepareStatement(SelectByPageSQL);
				pstmt.setString(1, "%" + keyWord + "%");
			}
			rs = pstmt.executeQuery();

			if (rs.next()) {
				totalCount = rs.getInt(1);
			}
			return totalCount;
		} catch (SQLException e) {
			e.printStackTrace();  
			throw new FindException(e.getMessage());
		} finally {
			MyConnection.close(con, pstmt, rs);
		}				
	}

	@Override
	public void updateCount(int num) throws ModifyException {
		Connection con = null;
		try {
			con = MyConnection.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ModifyException(e.getMessage());
		}
		PreparedStatement pstmt = null;
		String updateCountSQL = "UPDATE tblBoard SET count=count+1 WHERE num=?";
		try {
			pstmt = con.prepareStatement(updateCountSQL);
			pstmt.setInt(1, num);
			int rowCnt = pstmt.executeUpdate();
			if(rowCnt == 0) {
				throw new ModifyException("글번호가 없습니다");
			}
		} catch (SQLException e) {
			e.printStackTrace();  
			throw new ModifyException(e.getMessage());
		} finally {
			MyConnection.close(con, pstmt, null);
		}				
	}

	@Override
	public Board selectByNum(int num) throws FindException {
		Connection con = null;
		try {
			con = MyConnection.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new FindException(e.getMessage());
		}
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String SelectByNumSQL ="SELECT * FROM tblBoard WHERE num=?";
		Board board = null;
		try {
			pstmt = con.prepareStatement(SelectByNumSQL);
			pstmt.setInt(1, num);
			rs = pstmt.executeQuery();
			board = new Board();
			if (rs.next()) {
				board.setNum(rs.getInt("num"));
				board.setName(rs.getString("name"));
				board.setSubject(rs.getString("subject"));
				board.setContent(rs.getString("content"));
				board.setPos(rs.getInt("pos"));
				board.setRef(rs.getInt("ref"));
				board.setDepth(rs.getInt("depth"));
				board.setRegdate(rs.getString("regdate"));
				board.setPass(rs.getString("pass"));
				board.setCount(rs.getInt("count"));
				board.setFilename(rs.getString("filename"));
				board.setFilesize(rs.getInt("filesize"));
				board.setIp(rs.getString("ip"));
			}
			return board;
		} catch (SQLException e) {
			e.printStackTrace();  
			throw new FindException(e.getMessage());
		} finally {
			MyConnection.close(con, pstmt, rs);
		}
	}
	public void insertBoard(Board board) throws AddException{
		Connection con = null;
		try {
			con = MyConnection.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new AddException(e.getMessage());
		}
		PreparedStatement pstmt = null;
		String insertBoardSQL = "insert tblBoard(name,content,subject,ref,pos,depth,regdate,pass,count,ip,filename,filesize)";
		insertBoardSQL += "values(?, ?, ?, ?, 0, 0, now(), ?, 0, ?, ?, ?)";
		try {
			pstmt = con.prepareStatement(insertBoardSQL);
			pstmt.setString(1, board.getName());
			pstmt.setString(2, board.getContent());
			pstmt.setString(3, board.getSubject());
			pstmt.setInt(4, board.getRef());
			pstmt.setString(5, board.getPass());
			pstmt.setString(6, board.getIp());
			pstmt.setString(7, board.getFilename());
			pstmt.setInt(8, board.getFilesize());
			int rowCnt = pstmt.executeUpdate();
			if(rowCnt == 0) {
				throw new AddException("글쓰기 실패");
			}
		} catch (SQLException e) {
			e.printStackTrace();  
			throw new AddException(e.getMessage());//SQLException
		} finally {
			MyConnection.close(con, pstmt, null);
		}				
	}
	public void updateBoard(Board board) throws ModifyException{
		Connection con = null;
		try {
			con = MyConnection.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ModifyException(e.getMessage());
		}
		PreparedStatement pstmt = null;
		String updateBoardSQL = "UPDATE tblBoard SET name=?,subject=?,content=? where num=?";
		try {
			pstmt = con.prepareStatement(updateBoardSQL);
			pstmt.setString(1, board.getName());
			pstmt.setString(2, board.getSubject());
			pstmt.setString(3, board.getContent());
			pstmt.setInt(4, board.getNum());
			pstmt.executeUpdate();
			int rowCnt = pstmt.executeUpdate();
			if(rowCnt == 0) {
				throw new ModifyException("글수정 실패");
			}
		} catch (SQLException e) {
			e.printStackTrace();  
			throw new ModifyException(e.getMessage());//SQLException
		} finally {
			MyConnection.close(con, pstmt, null);
		}				
	}

	public void deleteBoard(int num) throws RemoveException{
		Connection con = null;
		try {
			con = MyConnection.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RemoveException(e.getMessage());
		}
		PreparedStatement pstmt = null;
		try {
			String DelelteByNumSQL = "DELETE FROM tblBoard WHERE num=?";
			pstmt = con.prepareStatement(DelelteByNumSQL);
			pstmt.setInt(1, num);
			int rowCnt = pstmt.executeUpdate();
			if(rowCnt == 0) {
				throw new RemoveException("글삭제 실패");
			}
		} catch (SQLException e) {
			e.printStackTrace();  
			throw new RemoveException(e.getMessage());
		} finally {
			MyConnection.close(con, pstmt, null);
		}				
	}

	@Override
	public int selectMaxNum() throws AddException {
		Connection con = null;
		try {
			con = MyConnection.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new AddException(e.getMessage());
		}
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int maxNum= 0;
		try {
			String SelectByPageSQL = "SELECT MAX(num) FROM tblBoard";
			pstmt = con.prepareStatement(SelectByPageSQL);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				maxNum = rs.getInt(1);
			}
			return maxNum;
		} catch (SQLException e) {
			e.printStackTrace();  
			throw new AddException(e.getMessage());
		} finally {
			MyConnection.close(con, pstmt, rs);
		}				
	}

	public void reply(Board reBoard) throws AddException{
		Connection con = null;
		try {
			con = MyConnection.getConnection();
			con.setAutoCommit(false); //자동커밋 해제
		} catch (SQLException e) {
			e.printStackTrace();
			throw new AddException(e.getMessage());
		}
		try {
			updateReplyUp(con,reBoard.getRef(),reBoard.getPos());
			insertReply(con,reBoard);
			con.commit(); //커밋
		}catch(Exception e) {
			try {
				con.rollback(); //롤백
			} catch (SQLException e1) {
			}
			throw new AddException(e.getMessage());
		}finally {
			MyConnection.close(con, null, null);
		}
	}

	public void updateReplyUp(Connection con,int ref, int pos) throws ModifyException{
		PreparedStatement pstmt = null;
		String updateReplyUp = "UPDATE tblBoard SET pos = pos + 1 WHERE ref=? and pos > ?";
		try {
			pstmt = con.prepareStatement(updateReplyUp);
			pstmt.setInt(1, ref);
			pstmt.setInt(2, pos);
			pstmt.executeUpdate();
			int rowCnt = pstmt.executeUpdate();
			if(rowCnt == 0) {
				throw new ModifyException("답글 추가 실패");
			}
		} catch (SQLException e) {
			e.printStackTrace();  
			throw new ModifyException(e.getMessage());//SQLException
		} finally {
			MyConnection.close(null, pstmt, null);
		}				
	}

	public void insertReply(Connection con, Board reBoard) throws AddException{
		PreparedStatement pstmt = null;
		String insertReplySQL = "INSERT tblBoard (name,content,subject,ref,pos,depth,regdate,pass,count,ip)";
		insertReplySQL += " VALUES(?,?,?,?,?,?,now(),?,0,?)";
		try {
			int depth = reBoard.getDepth() + 1;
			int pos = reBoard.getPos() + 1;
			pstmt = con.prepareStatement(insertReplySQL);
			pstmt.setString(1, reBoard.getName());
			pstmt.setString(2, reBoard.getContent());
			pstmt.setString(3, reBoard.getSubject());
			pstmt.setInt(4, reBoard.getRef());
			pstmt.setInt(5, pos);
			pstmt.setInt(6, depth);
			pstmt.setString(7, reBoard.getPass());
			pstmt.setString(8, reBoard.getIp());
			pstmt.executeUpdate();
			int rowCnt = pstmt.executeUpdate();
			if(rowCnt == 0) {
				throw new AddException("답글 쓰기 실패");
			}
		} catch (SQLException e) {
			e.printStackTrace();  
			throw new AddException(e.getMessage());//SQLException
		} finally {
			MyConnection.close(null, pstmt, null);
		}				
	}

}

