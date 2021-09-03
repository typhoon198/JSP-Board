package com.ti.control;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ti.service.BoardService;
import com.ti.service.MemberService;


public class DispatcherServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public DispatcherServlet() {
        super();
    }

	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("DispatcherServlet이 요청됨");
		request.setCharacterEncoding("utf-8"); 
		ServletContext sc = getServletContext();
		MemberService.envProp = sc.getRealPath(sc.getInitParameter("env"));
		BoardService.envProp = sc.getRealPath(sc.getInitParameter("env"));

		String servletPath = request.getServletPath();		
		Controller controller;
		String realPath =sc.getRealPath(sc.getInitParameter("env.controller"));//프로퍼티파일의 실제 경로 찾기
		Properties env = new Properties();
		env.load(new FileInputStream(realPath));
		
		String controllerClassName = env.getProperty(servletPath);

		try {
			Class clazz = Class.forName(controllerClassName);
			Object obj = clazz.newInstance();
			controller = (Controller)obj;
			String viewPath = controller.execute(request, response);
			RequestDispatcher rd = request.getRequestDispatcher(viewPath);
			rd.forward(request, response);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}
}