package cn.hanquan.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.hanquan.pojo.PageInfo;
import cn.hanquan.service.PeopleService;
import cn.hanquan.service.impl.PeopleServiceImpl;

/**
 * 分页显示People信息
 * 
 * @author Buuug
 *
 */
@WebServlet("/ShowServlet")
public class ShowServlet extends HttpServlet {

	private PeopleService peopleService = new PeopleServiceImpl();

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 第一次访问的验证,如果没有传递参数,设置默认值
		String pageSizeStr = req.getParameter("pageSize");
		int pageSize = 2;
		if (pageSizeStr != null && !pageSizeStr.equals("")) {
			pageSize = Integer.parseInt(pageSizeStr);
		}
		
		String pageNumberStr = req.getParameter("pageNum");
		int pageNum = 1;
		if (pageNumberStr != null && !pageNumberStr.equals("")) {
			pageNum = Integer.parseInt(pageNumberStr);
		}
		
		PageInfo pi = peopleService.showPage(pageSize, pageNum);
		System.out.println(pi.getDataList());
		req.setAttribute("PageInfo", pi);
		req.getRequestDispatcher("index.jsp").forward(req, resp);
	}
}
