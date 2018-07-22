package Servlet;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Dao.ProductOperator;
import Entity.Product;

public class DetailProductServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");
		String id = request.getParameter("id");
		ProductOperator dao = new ProductOperator();
		Product product = dao.findById(id);
		response.setContentType("text/html;charset=utf-8");
		//显示单个商品详情
		String html = "";
		html += "<html>";
		html += "<head><title>查看商品详情</title></head>";
		html += "<body>";
		html += "<table border='1' align='center' width='250px'>";
		html += "<tr><th>编号</th><td>"+product.getId()+"</td></tr>";
		html += "<tr><th>商品名称</th><td>"+product.getName()+"</td></tr>";
		html += "<tr><th>商品型号</th><td>"+product.getType()+"</td></tr>";
		html += "<tr><th>价格</th><td>"+product.getPrice()+"</td></tr>";
		html += "</table>";

		html += "<center><a href='"+request.getContextPath()+"/ListProductServlet'>[返回商品列表]</a></center>";
		
		//Cookie
		Cookie cookie = new Cookie("prodHist", getCookieValue(request, product.getId()));
		response.addCookie(cookie);
		
		html += "</body>";
		html += "</html>";
		
		response.getWriter().write(html);

	}
	
	private String getCookieValue(HttpServletRequest request,String id) {
		//最近浏览过的三个商品的id
		Cookie[] cookies = request.getCookies();
		String prodHist = null;
		if(cookies!=null){
			for (Cookie cookie : cookies) {
				if(cookie.getName().equals("prodHist")){
					prodHist = cookie.getValue();
					break;
				}
			}
		}
		//1、没有之前没有浏览，直接添加
		if(cookies==null || prodHist==null){
			return id;
		}
		
		// List: ArrayList LinkedList(链表)---使用LinkedList对商品的顺序进行排序
		// String-> String[]
		String[] prodHists = prodHist.split(",");
		// String->Collection
		Collection colls = Arrays.asList(prodHists);
		// Collectoin->LinkedList
		LinkedList list = new LinkedList(colls);
		
		//2、浏览的商品数是2时								
		if(list.size()<3){
			//2.1、若是重复浏览
			if(list.contains(id)){
				list.remove(id);
				list.addFirst(id);
			}else{
			//2.2、非重复直接添加
				list.addFirst(id);
			}
		}else{
		//3、浏览的商品数是3时
			if(list.contains(id)){
			//3.1、重复浏览
				list.remove(id);
				list.addFirst(id);
			}else{
			//3.2、非重复直接添加
				list.removeLast();
				list.addFirst(id);
			}
		}				
		
		//传String
		//List->String
		String str = "";
		for (Object obj : list) {
			str += obj+",";
		}
		//删最后的","
		str = str.substring(0, str.length()-1);
		return str;
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
