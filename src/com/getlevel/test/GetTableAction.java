package com.getlevel.test;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.getlevel.test.jdbc.JDBCUtils;

public class GetTableAction extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public GetTableAction() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	/**
	 * The doGet method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		this.doPost(request, response);
	}

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");

		PrintWriter out = response.getWriter();
		JDBCUtils jdbcUtils = new JDBCUtils();
		jdbcUtils.getConnection();
		// 获得信号名称
		String[] nameString = new String[3];
		nameString[0] = request.getParameter("ap1");
		nameString[1] = request.getParameter("ap2");
		nameString[2] = request.getParameter("ap3");

		String levelString = "";
		levelString += nameString[0] + nameString[1] + nameString[2] ;
		System.out.println(levelString);
		// 匹配一级表产生的tableName
		String namepostable = "";
		try {
			namepostable = jdbcUtils.locationFirstPos(nameString);
			out.println(namepostable);
			System.out.print(namepostable);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		out.flush();
		out.close();
	}

	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}

}
