package com.ti.control;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;
import com.ti.dto.Board;
import com.ti.dto.PageBean;
import com.ti.dto.SearchBean;
import com.ti.exception.AddException;
import com.ti.exception.FindException;
import com.ti.exception.ModifyException;
import com.ti.exception.RemoveException;
import com.ti.service.BoardService;
import com.ti.util.UtilMgr;

public class BoardController implements Controller {
	private static final String  SAVEFOLDER = "C:/219/myweb/mypjt/fileupload";
	//String saveDirectory = getServletContext().getRealPath("public/images/");서블릿 아니라
	private static final String ENCTYPE = "utf-8";
	private static int MAXSIZE = 5*1024*1024;

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

	public String list(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		//1.요청전달 데이터 얻기
		String nowPageStr = request.getParameter("nowPage");
		String keyField =request.getParameter("keyField");
		String keyWord = request.getParameter("keyWord");
		SearchBean search = new SearchBean(keyField,keyWord);
		//2.DB에서 리스트 가져올때 필요한 값 셋팅(start,end)
		int nowPage = 1;
		if(nowPageStr !=null && !nowPageStr.equals("")) {
			nowPage = Integer.parseInt(nowPageStr);
		}
		int cntPerPage = PageBean.getCntPerPage();

		int start=0; //DB의 select 시작번호
		int end=cntPerPage; //시작번호로 부터 가져올 select 갯수
		start = (nowPage-1)*cntPerPage; 

		//3.비지니스 로직 호출
		BoardService service = BoardService.getInstance();
		List<Board> list = new ArrayList<>();
		int totalCnt = 0;
		try {
			list = service.findByPage(keyField,keyWord,start, end);
			totalCnt = service.findTotalCnt(keyField,keyWord);
		} catch (FindException e) {
			e.printStackTrace();
		}
		int totalPage = (int) Math.ceil(totalCnt/(double)cntPerPage);
		PageBean<Board> pb = new PageBean<Board>(nowPage,totalPage,totalCnt,list);
		request.setAttribute("pb", pb);
		request.setAttribute("search", search);
		return "/board/list.jsp";
	}


	public String writeboard(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		//파일업로드
		//업로드 될것을 대비해 폴더만들어놓기
		File file = new File(SAVEFOLDER);
		if (!file.exists())
			file.mkdirs();

		MultipartRequest multi = new MultipartRequest(request, SAVEFOLDER,MAXSIZE, ENCTYPE,
				new DefaultFileRenamePolicy());
		int filesize = 0;
		String filename = null;
		if (multi.getFilesystemName("filename") != null) {
			filename = multi.getFilesystemName("filename");
			filesize = (int) multi.getFile("filename").length();
		}
		String content = multi.getParameter("content");
		if (multi.getParameter("contentType").equalsIgnoreCase("TEXT")) {
			content = UtilMgr.replace(content, "<", "&lt;");
			//TEXT입력선택시 태그 시작기로호 로 인식안하게 하지않고 부등호로 인식하게 저장 
		}

		//비지니스 로직 호출
		BoardService service = BoardService.getInstance();
		int nextNum=1;  
		try {
			nextNum = service.getMaxNum()+1; //AutoIncrease 예상 ref
			Board board = new Board();
			board.setContent(content);
			board.setRef(nextNum);
			board.setFilename(filename);
			board.setFilesize(filesize);
			board.setName(multi.getParameter("name"));
			board.setSubject(multi.getParameter("subject"));
			board.setPass(multi.getParameter("pass"));
			board.setIp(multi.getParameter("ip"));
			service.setBoard(board);
			request.setAttribute("status", 1);
		} catch (AddException e) {
			e.printStackTrace();
			request.setAttribute("status", 0);
		}
		return "/board/writeProc.jsp";

	}

	public String read(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//1.요청전달 데이터 얻기
		String numStr= request.getParameter("num");
		String nowPage = request.getParameter("nowPage");
		//뒤로가기 구현(게시판읽기에서 뒤로가면 그전의 페이지,검색정보 저장해서 다시전달)
		SearchBean search = new SearchBean(request.getParameter("keyField"),request.getParameter("keyWord"));

		//2.DB에서 리스트 가져올때 필요한 값 셋팅(start,end)
		int num = 1;
		if(numStr !=null && !numStr.equals("")) {
			num = Integer.parseInt(numStr);
		}
		//2.비지니스 로직 호출
		BoardService service = BoardService.getInstance();
		try {
			Board board = service.findByNum(num);
			if(board==null){
				request.setAttribute("status", 0);//게시물 없음
			} else {
				request.setAttribute("status", 1);//정상
				request.setAttribute("board",board);
				request.setAttribute("search",search);
				request.setAttribute("nowPage",nowPage);
			}
		} catch (FindException e) {
			e.printStackTrace();
			request.setAttribute("status", -1);//SQLException
		}
		return "/board/read.jsp";
	}


	//update.jsp에 기존 게시물 번호,제목,내용, 현재페이지를 화면에 출력(Ajax로 안해서 2번 요청, 책:세션에 게시물 저장)
	public String updateboard(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//1.요청전달 데이터 얻기
		String numStr= request.getParameter("num");
		String nowPage = request.getParameter("nowPage");
		int num = 1;
		if(numStr !=null && !numStr.equals("")) {
			num = Integer.parseInt(numStr);
		}
		//2.비지니스 로직 호출
		BoardService service = BoardService.getInstance();
		try {
			Board board = service.findByNum(num);
			if(board==null){
				request.setAttribute("status", 0);//게시물 없음
			} else {
				request.setAttribute("status", 1);//정상
				request.setAttribute("board",board);
				request.setAttribute("nowPage",nowPage);
			}
		} catch (FindException e) {
			e.printStackTrace();
			request.setAttribute("status", -1);//SQLException
		}
		return "/board/update.jsp";
	}

	//실제 게시물수정요청
	public String modifyboard(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//1.요청전달 데이터 얻기
		String numStr= request.getParameter("num");
		String nowPage = request.getParameter("nowPage");
		int num = 1;
		if(numStr !=null && !numStr.equals("")) {
			num = Integer.parseInt(numStr);
		}
		Board board = new Board();
		board.setNum(num);
		board.setName(request.getParameter("name"));
		board.setSubject(request.getParameter("subject"));
		board.setContent(request.getParameter("content"));
		//2.비지니스 로직 호출
		try {
			BoardService service = BoardService.getInstance();
			service.modifyBoard(board);
			request.setAttribute("status", 1);
			request.setAttribute("num",num);
			request.setAttribute("nowPage",nowPage);


		}catch (ModifyException e) {
			request.setAttribute("status", 0);
		}
		return "/board/updateProc.jsp";
	}

	//delete.jsp에  게시번호, 현재페이지를 전달(모달창이 나아보임. 이것도 Ajax로하면 쉬움,책:세션에 게시물 저장)
	public String deleteboard(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//1.요청전달 데이터 얻기
		String num= request.getParameter("num");
		String nowPage = request.getParameter("nowPage");
		request.setAttribute("num",num);
		request.setAttribute("nowPage",nowPage);
		return "/board/delete.jsp";
	}

	public String removeboard(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//1.요청전달 데이터 얻기
		String numStr= request.getParameter("num");
		String nowPage = request.getParameter("nowPage");
		String inPass = request.getParameter("pass");
		int num = 1;
		if(numStr !=null && !numStr.equals("")) {
			num = Integer.parseInt(numStr);
		}

		//2.비지니스 로직 호출
		BoardService service = BoardService.getInstance();
		try {
			Board board = service.findByNum(num);
			String dbPass = board.getPass();
			String fileName = board.getFilename();
			//비밀번호가 맞으면 업로드한 파일과 삭제
			if (inPass.equals(dbPass)) {
				if (fileName!= null || !fileName.equals("")) {
					File file = new File(SAVEFOLDER + "/" +fileName);
					//if (file.exists())   
					file.delete();
				}
				service.removeBoard(num);
				request.setAttribute("status", 1);//정상
			} else {
				request.setAttribute("status", 0);//비밀번호 불일치
			}
		} catch (FindException e1) {
			e1.printStackTrace();
			request.setAttribute("status", -1);//삭제하려는 게시물 찾기 실패
		} catch(RemoveException e2) {
			e2.printStackTrace();
			request.setAttribute("status", -1);//삭제실패
		}
		return "/board/deleteProc.jsp";
	}
	
	//답글쓰기전 부모글 불러오기(Ajax로 안해서 2번 요청,책:세션에 게시물 저장)
	public String parent(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//1.요청전달 데이터 얻기
		String numStr= request.getParameter("num");
		String nowPage = request.getParameter("nowPage");
		int num = 1;
		if(numStr !=null && !numStr.equals("")) {
			num = Integer.parseInt(numStr);
		}
		//2.비지니스 로직 호출
		BoardService service = BoardService.getInstance();
		try {
			Board board = service.findByNum(num);
			request.setAttribute("status", 1);//정상
			request.setAttribute("board", board);
			request.setAttribute("nowPage", nowPage);

		} catch(FindException e) {
			e.printStackTrace();
			request.setAttribute("status", 0);//부모글 불러오기 실패
		}
		return "/board/reply.jsp";
	}
	
	public String reply(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//1.요청전달 데이터 얻기
		String nowPage = request.getParameter("nowPage");
			
		Board reBoard = new Board();
		reBoard.setName(request.getParameter("name"));
		reBoard.setSubject(request.getParameter("subject"));
		reBoard.setContent(request.getParameter("content"));
		reBoard.setRef(Integer.parseInt(request.getParameter("ref"))); 
		reBoard.setPos(Integer.parseInt(request.getParameter("pos"))); 
		reBoard.setDepth(Integer.parseInt(request.getParameter("depth"))); 
		reBoard.setPass(request.getParameter("pass"));
		reBoard.setIp(request.getParameter("ip"));

		//2.비지니스 로직 호출
		BoardService service = BoardService.getInstance();
		try {
			service.replyBoard(reBoard);
			request.setAttribute("status", 1);
			request.setAttribute("nowPage", nowPage);
		}catch (AddException e) {
			e.printStackTrace();
			request.setAttribute("status", 0);//답글쓰기 실패
			}
		return "/board/replyProc.jsp";
	}
	
	

}

