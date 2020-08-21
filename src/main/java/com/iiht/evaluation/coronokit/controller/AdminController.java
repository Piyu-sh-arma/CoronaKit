package com.iiht.evaluation.coronokit.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.iiht.evaluation.coronokit.dao.ProductMasterDao;
import com.iiht.evaluation.coronokit.model.ProductMaster;

@WebServlet("/admin")
public class AdminController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ProductMasterDao productMasterDao;

	public void setProductMasterDao(ProductMasterDao productMasterDao) {
		this.productMasterDao = productMasterDao;
	}

	public void init(ServletConfig config) {
		String jdbcURL = config.getServletContext().getInitParameter("jdbcUrl");
		String jdbcUsername = config.getServletContext().getInitParameter("jdbcUsername");
		String jdbcPassword = config.getServletContext().getInitParameter("jdbcPassword");

		this.productMasterDao = new ProductMasterDao(jdbcURL, jdbcUsername, jdbcPassword);

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		String viewName = "";
		try {
			switch (action) {
			case "login":
				viewName = adminLogin(request, response);
				break;
			case "list":
				viewName = listAllProducts(request, response);
				break;
			case "newproduct":
				viewName = showNewProductForm(request, response);
				break;
			case "insertproduct":
				viewName = insertProduct(request, response);
				break;
			case "deleteproduct":
				viewName = deleteProduct(request, response);
				break;
			case "editproduct":
				viewName = showEditProductForm(request, response);
				break;
			case "updateproduct":
				viewName = updateProduct(request, response);
				break;

			case "logout":
				viewName = adminLogout(request, response);
				break;
			default:
				viewName = "notfound.jsp";
				break;
			}
		} catch (Exception ex) {
			throw new ServletException(ex.getMessage());
		}
		RequestDispatcher dispatch = request.getRequestDispatcher(viewName);
		dispatch.forward(request, response);

	}

	private String adminLogin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String view = "index.jsp";
		HttpSession session = request.getSession();
		if (request.getParameter("loginid").equalsIgnoreCase("admin") && request.getParameter("password").equalsIgnoreCase("admin")) {
			session.setAttribute("isAdminVerified", true);
			view = "admin?action=list";
		}else {
			request.setAttribute("txFailMsg", "Invalid Username/Passowrd. Please try again !!");
		}
		return view;
	}

	private String listAllProducts(HttpServletRequest request, HttpServletResponse response) {
		String view = "listproducts.jsp";
		try {
			List<ProductMaster> products = productMasterDao.getAllProducts();
			request.setAttribute("products", products);
		} catch (Exception exception) {
			request.setAttribute("exception", exception);
			view = "errorPage.jsp";
		}
		return view;
	}

	private String showNewProductForm(HttpServletRequest request, HttpServletResponse response) {
		String view = "newproduct.jsp";
		try {
			request.setAttribute("productId", productMasterDao.getNextProductId());
		} catch (Exception e) {
			request.setAttribute("exception", e);
			view = "errorPage.jsp";
		}
		return view;
	}

	private String insertProduct(HttpServletRequest request, HttpServletResponse response) {
		String view = "admin?action=newproduct";
		if (request.getParameter("productId") != null) {
			int productId = Integer.parseInt(request.getParameter("productId"));
			String price = request.getParameter("productPrice");
			String pName = request.getParameter("productName").trim();
			String pDescription = request.getParameter("productDescription").trim();
			ProductMaster product = new ProductMaster(productId, pName, price, pDescription);
			try {
				if (productMasterDao.addProduct(product))
					request.setAttribute("txSucssMsg", "Product - " + productId + " is added successfully.");
			} catch (Exception e) {
				request.setAttribute("exception", e);
				view = "errorPage.jsp";
			}
		} else {
			view = "errorPage.jsp";
		}
		return view;
	}

	private String deleteProduct(HttpServletRequest request, HttpServletResponse response) {
		String view = "admin?action=list";
		if (null != request.getParameter("productId")) {
			int productId = Integer.parseInt(request.getParameter("productId"));
			try {
				if (productMasterDao.deleteProduct(productId))
					request.setAttribute("txSucssMsg", "Product - " + productId + " is removed successfully.");
			} catch (Exception e) {
				request.setAttribute("exception", e);
				view = "errorPage.jsp";
			}
		}
		return view;
	}

	private String adminLogout(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		session.removeAttribute("isAdminVerified");
		return "index.jsp";
	}

	private String showEditProductForm(HttpServletRequest request, HttpServletResponse response) {
		String view = "editproduct.jsp";
		if (request.getParameter("productId") != null) {
			int productId = Integer.parseInt(request.getParameter("productId"));
			try {
				request.setAttribute("product", productMasterDao.getProductById(productId));
			} catch (Exception e) {
				request.setAttribute("exception", e);
				view = "errorPage.jsp";
			}
		}
		return view;
	}

	private String updateProduct(HttpServletRequest request, HttpServletResponse response) {
		String view = "";
		if (request.getParameter("productId") != null) {
			int productId = Integer.parseInt(request.getParameter("productId"));
			String price = request.getParameter("productPrice");
			String pName = request.getParameter("productName").trim();
			String pDescription = request.getParameter("productDescription").trim();
			ProductMaster product = new ProductMaster(productId, pName, price, pDescription);
			try {
				if (productMasterDao.updateProduct(product))
					request.setAttribute("txSucssMsg", "Product - " + productId + " is updated successfully.");
			} catch (Exception e) {
				request.setAttribute("exception", e);
				view = "errorPage.jsp";
			}
			request.setAttribute("product", productId);
			view = "admin?action=editproduct&productId=" + productId;
		} else {
			view = "errorPage.jsp";
		}
		return view;
	}

}