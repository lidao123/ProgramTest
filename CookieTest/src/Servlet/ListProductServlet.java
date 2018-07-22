package Servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Dao.ProductOperator;
import Entity.Product;

public class ListProductServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");
		
		ProductOperator productOperator = new ProductOperator();
		List<Product> list = productOperator.findAll();
		//输出商品列表
		String html = "";
		html += "<html>";
		html += "<head><title>商品列表</title></head>";
		html += "<body>";
		html += "<table border='1' align='center' width='600px'>";
		html += "<tr><th>编号</th><th>商品名</th><th>类型</th><th>价格</th><th>详情</th></tr>";
		if(list != null){
			for (Product product : list) {
				html += "<tr>";
				html += "<td>"+product.getId()+"</td>";
				html += "<td>"+product.getName()+"</td>";
				html += "<td>"+product.getType()+"</td>";
				html += "<td>"+product.getPrice()+"</td>";
				html += "<td><a href='"+request.getContextPath()+"/DetailProductServlet?id="+product.getId()+"'>详情</a></td>";
				html += "</tr>";
			}
		}
		
		html += "</table>";
		
		//显示浏览过的三个商品
		html += "<hr/>";
		Cookie[] cookies = request.getCookies();
		if(cookies != null){
			for (Cookie cookie : cookies) {
				if(cookie.getName().equals("prodHist")){
					String prodHist = cookie.getValue(); 
					String[] ids = prodHist.split(",");
					for (String id : ids) {
						Product p = productOperator.findById(id);
						
						html += ""+p.getId()+"&nbsp;"+p.getName()+"&nbsp;"+p.getPrice()+"<br/>";
					}
					break;
				}

			}
		}
		html += "</body>";
		html += "</html>";
		
		response.getWriter().write(html);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
