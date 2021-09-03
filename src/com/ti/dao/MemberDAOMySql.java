package com.ti.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.ti.dto.Member;
import com.ti.exception.AddException;
import com.ti.exception.FindException;
import com.ti.exception.ModifyException;
import com.ti.sql.MyConnection;

public class MemberDAOMySql implements MemberDAO {
	public MemberDAOMySql() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			System.out.println("JDBC 드라이버 로딩 성공");
		}catch(Exception e) {
			System.out.println("Error : JDBC 드라이버 로딩 실패");
		}
	} 

	@Override
	public String login(String id, String pwd) throws FindException{
		Connection con = null;
		try {
			con=MyConnection.getConnection();
		} catch (SQLException e) {

			e.printStackTrace();
		}
		String loginSQL = "SELECT pwd FROM tblMember WHERE id = ?";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String result = "0";
		try {
			pstmt = con.prepareStatement(loginSQL);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				String dbPwd = rs.getString("pwd");
				result = dbPwd.equals(pwd) ? id :"-1" ;	
				//비번까지 같으면 id 아이디만 맞으면 -1
			}
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new FindException(e.getMessage());

		} finally {
			MyConnection.close(con, pstmt, rs);
		}
	}

	@Override
	public boolean checkId(String id) throws FindException {
		Connection con = null;
		try {
			con=MyConnection.getConnection();
		} catch (SQLException e) {

			e.printStackTrace();
		}
		String checkIdSQL = "SELECT id FROM tblMember WHERE id = ?";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		boolean flag = false;
		try { 
			pstmt = con.prepareStatement(checkIdSQL);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			flag = rs.next();
			return flag;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new FindException(e.getMessage());
		} finally {
			MyConnection.close(con, pstmt, rs);
		}
	}

	@Override
	public boolean insert(Member mem) throws AddException {
		Connection con = null;
		try {
			con=MyConnection.getConnection();
		} catch (SQLException e) {

			e.printStackTrace();
		}
		String insertSQL = "INSERT tblMember(id,pwd,name,gender,birthday,email,zipcode"
				+ ",address1,address2,hobby,job) VALUES(?,?,?,?,?,?,?,?,?,?,?)";
		PreparedStatement pstmt = null;
		boolean flag = false;
		try {
			pstmt = con.prepareStatement(insertSQL);
			pstmt.setString(1, mem.getId());
			pstmt.setString(2, mem.getPwd());
			pstmt.setString(3, mem.getName());
			pstmt.setString(4, mem.getGender());
			pstmt.setString(5, mem.getBirthday());
			pstmt.setString(6, mem.getEmail());
			pstmt.setString(7, mem.getZipcode());
			pstmt.setString(8, mem.getAddress1());
			pstmt.setString(9, mem.getAddress2());
			String hobby[] = mem.getHobby();
			char hb[] = { '0', '0', '0', '0', '0' };
			String lists[] = { "인터넷", "여행", "게임", "영화", "운동" };
			for (int i = 0; i < hobby.length; i++) {
				for (int j = 0; j < lists.length; j++) {
					if (hobby[i].equals(lists[j]))
						hb[j] = '1';
				}
			}
			pstmt.setString(10, new String(hb));
			pstmt.setString(11, mem.getJob());
			int rowcnt = pstmt.executeUpdate();
			if (rowcnt == 1) {
				flag = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new AddException("가입실패");
		} finally {
			MyConnection.close(con, pstmt, null);
		}
		return flag;
	}
	@Override
	public boolean update(Member mem) throws ModifyException {
		Connection con = null;
		try {
			con=MyConnection.getConnection();
		} catch (SQLException e) {

			e.printStackTrace();
		}

		String updateSQL ="update tblMember set pwd=?, name=?, gender=?, birthday=?,"
				+ "email=?, zipcode=?, address1=?, address2=?, hobby=?, job=? where id = ?";
		PreparedStatement pstmt = null;
		boolean flag = false;
		try {
			pstmt = con.prepareStatement(updateSQL);
			pstmt.setString(1, mem.getPwd());
			pstmt.setString(2, mem.getName());
			pstmt.setString(3, mem.getGender());
			pstmt.setString(4, mem.getBirthday());
			pstmt.setString(5, mem.getEmail());
			pstmt.setString(6, mem.getZipcode());
			pstmt.setString(7, mem.getAddress1());
			pstmt.setString(8, mem.getAddress2());
			char hobby[] = { '0', '0', '0', '0', '0' };
			if (mem.getHobby() != null) {
				String hobbys[] = mem.getHobby();
				String list[] = { "인터넷", "여행", "게임", "영화", "운동" };
				for (int i = 0; i < hobbys.length; i++) {
					for (int j = 0; j < list.length; j++)
						if (hobbys[i].equals(list[j]))
							hobby[j] = '1';
				}
			}
			pstmt.setString(9, new String(hobby));
			pstmt.setString(10, mem.getJob());
			pstmt.setString(11, mem.getId());
			int count = pstmt.executeUpdate();
			if (count > 0)
				flag = true;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ModifyException("수정실패");

		} finally {
			MyConnection.close(con, pstmt, null);
		}
		return flag;
	}		

	@Override
	public Member selectById(String id) throws FindException {
		Connection con = null;
		try {
			con=MyConnection.getConnection();
		} catch (SQLException e) {

			e.printStackTrace();
		}
		String SelectByIdSQL = "SELECT * FROM tblMember WHERE id = ?";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Member mem = null;
		try {
			pstmt = con.prepareStatement(SelectByIdSQL);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				mem = new Member();
				mem.setId(rs.getString("id"));
				mem.setPwd(rs.getString("pwd"));
				mem.setName(rs.getString("name"));
				mem.setGender(rs.getString("gender"));
				mem.setBirthday(rs.getString("birthday"));
				mem.setEmail(rs.getString("email"));
				mem.setZipcode(rs.getString("zipcode"));
				mem.setAddress1(rs.getString("address1"));
				mem.setAddress2(rs.getString("address2"));
				String hobbys[] = new String[5];
				String hobby = rs.getString("hobby");// 01001
				for (int i = 0; i < hobbys.length; i++) {
					hobbys[i] = hobby.substring(i, i + 1);
				}
				mem.setHobby(hobbys);
				mem.setJob(rs.getString("job"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			MyConnection.close(con, pstmt, rs);
		}
		return mem;
	}

	public static void main(String[] args) {
		MemberDAO dao = new MemberDAOMySql();
/*
  //id 체크
		try {
			Boolean flag1 = dao.checkId("id1");
			Boolean flag2 = dao.checkId("id2");
			System.out.println("flag1 " + flag1);
			System.out.println("flag2 " + flag2);

		} catch (FindException e) {
			e.printStackTrace();
		}
  //로그인 체크
		try {
			int result1 = dao.login("id1","p1");
			int result2 = dao.login("id1","p2");
			int result3 = dao.login("id2","p2");
			System.out.println("result1 " + result1);
			System.out.println("result2 " + result2);
			System.out.println("result3 " + result3);
		} catch (FindException e) {
			e.printStackTrace();
		}
	}
*/
/*		
		try {
			Member mem = dao.selectById("id1");
			System.out.println(mem);
		} catch (FindException e) {
			e.printStackTrace();
		}
	
	
*/
	}
}
