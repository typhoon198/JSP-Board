package com.ti.control;

import java.io.IOException;
import java.lang.reflect.Method;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ti.dto.Member;
import com.ti.exception.AddException;
import com.ti.exception.FindException;
import com.ti.exception.ModifyException;
import com.ti.service.MemberService;

public class MemberController implements Controller{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		String servletPath = request.getServletPath();
		String methodName = servletPath.split("/", 2)[1];   //"/login" /를 기준으로 문자를 2개로  잘라서(""+login) 2번째 [1]
		try {
			Method method = this.getClass().getMethod(methodName, HttpServletRequest.class, HttpServletResponse.class);
			String viewPath = (String) method.invoke(this, request, response);
			return viewPath;
		} catch (Exception e) {
			e.printStackTrace();
			return "fail.jsp";
		}

	}
	public String login(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		//response.addHeader("Access-Control-Allow-Origin", "*");
		String id = request.getParameter("id");
		String pwd = request.getParameter("pwd");

		MemberService service = MemberService.getInstance();
		HttpSession session = request.getSession();
		session.removeAttribute("idKey");

		try {
			String login = service.login(id, pwd);
			//status 0 로그인 실패 둘다 틀린경우
			//status-1 로그인 실패  아이디만 맞은경우
			//status 1 로그인 성공
			if(login.equals("0")) {
				request.setAttribute("status", 0);
			} else if(login.equals("-1")) {
				request.setAttribute("status", -1);
			} else {
				session.setAttribute("idKey", login);
				//로그인 성공하면 login: 해당아이디id
			}
		} catch (FindException e) {
			e.printStackTrace();
			request.setAttribute("status", -1);
		}
		return "/member/loginProc.jsp";
	}

	public String logout(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		HttpSession session = request.getSession();
		session.invalidate();
		return "/member/logout.jsp";
	}

	public String signup(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		//1.요청전달 데이터 얻기
		String id = request.getParameter("id");
		String pwd = request.getParameter("pwd");
		String name = request.getParameter("name");
		String gender = request.getParameter("gender");
		String birthday = request.getParameter("birthday");
		String email = request.getParameter("email");
		String zipcode = request.getParameter("zipcode") ;
		String address1 = request.getParameter("address1");
		String address2 = request.getParameter("address2");
		String[] hobby = request.getParameterValues("hobby");
		String job = request.getParameter("job"); ;
		Member mem = new Member(id,pwd,name,gender,birthday,email,zipcode,address1,address2,hobby,job);

		//2.비지니스로직 호출
		MemberService service = MemberService.getInstance();
		try {
			Boolean signup = service.signup(mem);
			if(signup) {
				request.setAttribute("status", 1);
			} else {
				request.setAttribute("status", 0);//insert rowNum=0
			}
		} catch (AddException e) {
			e.printStackTrace();
			request.setAttribute("status", 0);//SQLException
		}
		return "/member/memberProc.jsp";
	}


	public String checkid(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		//1.요청전달 데이터 얻기
		String id = request.getParameter("id");
		//2.비지니스로직 호출
		MemberService service = MemberService.getInstance();
		try {
			Boolean check = service.checkId(id);

			if(check) {
				request.setAttribute("status", 1);//id 이미 존재
			} else {
				request.setAttribute("status", 0);//id 존재 x
			}
		} catch (FindException e) {
			e.printStackTrace();
			request.setAttribute("status", -1);//SQLException
		}
		return "/member/idCheck.jsp";
	}

	public String member(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		//1.세션에서 데이터 얻기
		HttpSession session = request.getSession();
		String id = (String) session.getAttribute("idKey");
		//2.비지니스로직 호출
		MemberService service = MemberService.getInstance();
		try {
			Member mem = service.findById(id);
			if(mem==null){
				request.setAttribute("status", 0);//로그인안됨
			} else {
				request.setAttribute("status", 1);//정상
				request.setAttribute("mem",mem);
			}
		} catch (FindException e) {
			e.printStackTrace();
			request.setAttribute("status", -1);//SQLException
		}
		return "/member/memberUpdate.jsp";
	}


	public String modify(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		//1.요청전달 데이터 얻기
		String id = request.getParameter("id");
		String pwd = request.getParameter("pwd");
		String name = request.getParameter("name");
		String gender = request.getParameter("gender");
		String birthday = request.getParameter("birthday");
		String email = request.getParameter("email");
		String zipcode = request.getParameter("zipcode") ;
		String address1 = request.getParameter("address1");
		String address2 = request.getParameter("address2");
		String[] hobby = request.getParameterValues("hobby");
		String job = request.getParameter("job"); ;
		Member mem = new Member(id,pwd,name,gender,birthday,email,zipcode,address1,address2,hobby,job);

		//2.비지니스로직 호출
		MemberService service = MemberService.getInstance();
		try {
			Boolean modify = service.modify(mem);
			if(modify) {
				request.setAttribute("status", 1);
			} else {
				request.setAttribute("status", 0);//insert rowNum=0
			}
		} catch (ModifyException e) {
			e.printStackTrace();
			request.setAttribute("status", -1);//SQLException
		}
		return "/member/memberUpdateProc.jsp";
	}
}


