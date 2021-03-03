package org.ponmmains.bank.servlet;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.Servlet;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class ActTransferServlet implements Servlet {

	@Override
	public void service(ServletRequest request, ServletResponse response) {
		//------------获取页面请求的参数------------
		String actFrom = request.getParameterValue("actFrom");
		double balance = Double.parseDouble(request.getParameterValue("balance"));
		String actTo = request.getParameterValue("actTo");
		
		//-----------连接数据库---------------
		Connection conn = null;
		PreparedStatement ps = null;
		int count = 0;
		try {
			//1.注册驱动
			Class.forName("com.mysql.jdbc.Driver");
			//2.获取数据库连接
			String url = "jdbc:mysql://127.0.0.1:3306/tomcat?characterEncoding=utf8";
			String user = "root";
			String password = "Ponmma1234";
			conn = DriverManager.getConnection(url, user, password);
			//开启事务，关闭自动提交
			conn.setAutoCommit(false);
			//3.定义SQL语句框架
			String sql_from = "update t_act set balance = balance - ? where actno = ?";
			//4.进行SQL语句的预编译
			ps = conn.prepareStatement(sql_from);
			//5.进行赋值
			ps.setDouble(1, balance);
			ps.setString(2, actFrom);
			//6.执行SQL语句
			count = ps.executeUpdate();
			
			
			String sql_to = "update t_act set balance = balance + ? where actno = ?";
			ps = conn.prepareStatement(sql_to);
			ps.setDouble(1, balance);
			ps.setString(2, actTo);
			count = count + ps.executeUpdate();
			
			//提交事务
			conn.commit();
		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}finally{
			//关闭资源
			if(ps != null){
				try {
					ps.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(conn != null){
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		//获取响应流对象
		PrintWriter out = response.getWriter();
		if(count == 2){
			out.print("<html>");
			out.print("<head>");
			out.print("<title>银行帐户-转帐结果</title>");
			out.print("<meta content='text/html;charset=utf-8'/>");
			out.print("</head>");
			out.print("<body>");
			out.print("<center><font size='35px' color='green'>转帐成功！</font></center>");
			out.print("</body>");
			out.print("</html>");
		}else{
			out.print("<html>");
			out.print("<head>");
			out.print("<title>银行帐户-转帐结果</title>");
			out.print("<meta content='text/html;charset=utf-8'/>");
			out.print("</head>");
			out.print("<body>");
			out.print("<center><font size='35px' color='red'>转帐失败！</font></center>");
			out.print("</body>");
			out.print("</html>");
			
		}
	}

}
