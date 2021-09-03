package com.ti.service;

import java.io.FileInputStream;
import java.util.List;
import java.util.Properties;

import com.ti.dao.MemberDAO;
import com.ti.dto.Member;
import com.ti.exception.AddException;
import com.ti.exception.FindException;
import com.ti.exception.ModifyException;

public class MemberService {
	private MemberDAO dao;
	//	private static CustomerService service = new CustomerService();
	private static MemberService service;
	public static String envProp;

	private MemberService() {
		Properties env = new Properties();
		try {
			env.load(new FileInputStream(envProp));  
			String className = env.getProperty("memberDAO"); 
			Class c = Class.forName(className);  
			dao = (MemberDAO)c.newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}

	public static MemberService getInstance() {
		if(service == null) {
			service = new MemberService();
		}
		return service;
	}
	public String login(String id, String pwd) throws FindException{
		return dao.login(id,pwd);
	}
	public boolean checkId(String id) throws FindException{
		return dao.checkId(id);
	}
	public boolean signup(Member m) throws AddException{
		return dao.insert(m);
	}
	public boolean modify(Member mem) throws ModifyException{
		return dao.update(mem);
	}

	public Member findById(String id) throws FindException {
		return dao.selectById(id);
	}
}


