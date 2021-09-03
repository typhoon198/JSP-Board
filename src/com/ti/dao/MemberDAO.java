package com.ti.dao;

import com.ti.dto.Member;
import com.ti.exception.AddException;
import com.ti.exception.FindException;
import com.ti.exception.ModifyException;

public interface MemberDAO {

	/**
	 * 로그인 성공여부를 판단
	 * @param id 입력 아이디
	 * @param pwd 입력 비밀번호
	 * @return 모두일치 id, id만 일치 -1, 그외  0
	 * @throws 
	 */
	public String login(String id, String pwd) throws FindException;

	/**
	 * 아이디 중복체크
	 * @param id 아이디
	 * @return 아이디가 존재하면 true
	 * @throws 
	 */
	public boolean checkId(String id) throws FindException;
	
	/**
	 * 회원가입
	 * @param  고객이 입력한 고객정보
	 * @return 성공시 true
	 * @throws 
	 */
	public boolean insert(Member mem) throws AddException;


	/**
	 * 회원정보 수정
	 * @param  id 아이디
	 * @return 아이디가 존재하면 true
	 */
	public boolean update(Member mem) throws ModifyException;

	/**
	 * 회원정보 가져오기
	 * @param id 아이디
	 * @return Member
	 * @throws 
	 */
	public Member selectById(String id) throws FindException;
	
}
