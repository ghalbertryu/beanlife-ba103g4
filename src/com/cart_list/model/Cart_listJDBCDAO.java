package com.cart_list.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class Cart_listJDBCDAO implements Cart_listDAO_interface{
	
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "ba103g4";
	String passwd = "123456";
	
	
	private static final String INSERT_STMT = 
		"INSERT INTO CART_LIST (prod_no,mem_ac,prod_amount) VALUES (?, ?, ?)";
	private static final String GET_ALL_STMT = 
		"SELECT prod_no,mem_ac,prod_amount FROM CART_LIST order by prod_no";
	private static final String GET_ONE_STMT = 
		"SELECT prod_no,mem_ac,prod_amount FROM CART_LIST where prod_no = ? and mem_ac =?";
	private static final String DELETE = 
		"DELETE FROM CART_LIST where prod_no = ? and mem_ac =?";
	private static final String UPDATE = 
		"UPDATE CART_LIST set prod_amount = ? where prod_no=? and mem_ac=?";
	private static final String GET_BY_MEM = 
		"SELECT * FROM CART_LIST where mem_ac=? order by PROD_NO";
	
	
	@Override
	public void insert(Cart_listVO cart_listVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, cart_listVO.getProd_no());
			pstmt.setString(2, cart_listVO.getMem_ac());
			pstmt.setInt(3, cart_listVO.getProd_amount());

			pstmt.executeUpdate();

			// Handle any SQL errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
		
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		
	}

	@Override
	public void update(Cart_listVO cart_listVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE);
			
			pstmt.setInt(1, cart_listVO.getProd_amount());
			pstmt.setString(2, cart_listVO.getProd_no());
			pstmt.setString(3, cart_listVO.getMem_ac());
			
			pstmt.executeUpdate();

			// Handle any driver errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			// Clean up JDBC resources
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}

		
	}

	@Override
	public void delete(String prod_no, String mem_ac) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(DELETE);

			pstmt.setString(1, prod_no);
			pstmt.setString(2, mem_ac);

			pstmt.executeUpdate();

			// Handle any driver errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			// Clean up JDBC resources
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
	}

	@Override
	public Cart_listVO findByPrimaryKey(String prod_no, String mem_ac) {
		Cart_listVO cart_listVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, prod_no);
			pstmt.setString(2, mem_ac);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				cart_listVO = new Cart_listVO();
				cart_listVO.setProd_no(rs.getString("prod_no"));
				cart_listVO.setMem_ac(rs.getString("mem_ac"));
				cart_listVO.setProd_amount(rs.getInt("prod_amount"));
			}

			// Handle any driver errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			// Clean up JDBC resources
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return cart_listVO;
	}

	@Override
	public List<Cart_listVO> getAll() {
		List<Cart_listVO> list = new ArrayList<Cart_listVO>();
		Cart_listVO cart_listVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVO 也稱為 Domain objects
				cart_listVO = new Cart_listVO();
				cart_listVO.setProd_no(rs.getString("prod_no"));
				cart_listVO.setMem_ac(rs.getString("mem_ac"));
				cart_listVO.setProd_amount(rs.getInt("prod_amount"));
				list.add(cart_listVO); // Store the row in the list
			}

			// Handle any driver errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			// Clean up JDBC resources
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return list;
	}
	
	
	@Override
	public Set<Cart_listVO> getByMem(String mem_ac) {
		Set<Cart_listVO> set = new LinkedHashSet<Cart_listVO>();
		Cart_listVO cart_listVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_BY_MEM);
			pstmt.setString(1, mem_ac);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVO 也稱為 Domain objects
				cart_listVO = new Cart_listVO();
				cart_listVO.setProd_no(rs.getString("prod_no"));
				cart_listVO.setMem_ac(rs.getString("mem_ac"));
				cart_listVO.setProd_amount(rs.getInt("prod_amount"));
				set.add(cart_listVO); // Store the row in the list
			}

			// Handle any driver errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			// Clean up JDBC resources
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return set;
	}
	
	
	public static void main(String[] args) {

		Cart_listJDBCDAO dao = new Cart_listJDBCDAO();
//
//		// 新增
//		Cart_listVO cart_listVO = new Cart_listVO();
//		cart_listVO.setProd_no("P1000000001");
//		cart_listVO.setMem_ac("amy39");
//		cart_listVO.setProd_amount(6);
//		dao.insert(cart_listVO);
//
//		// 修改
//		Cart_listVO cart_listVO2 = new Cart_listVO();
//		cart_listVO2.setProd_no("P1000000001");
//		cart_listVO2.setMem_ac("amy39");
//		cart_listVO2.setProd_amount(10);
//		dao.update(cart_listVO2);
//
//		
//
//		// 查詢
//		Cart_listVO cart_listVO3 = dao.findByPrimaryKey("P1000000001", "amy39");
//		System.out.print(cart_listVO3.getProd_no() + ",");
//		System.out.print(cart_listVO3.getMem_ac() + ",");
//		System.out.println(cart_listVO3.getProd_amount() + ",");
//		System.out.println("---------------------");
//		
//		// 刪除
//		dao.delete("P1000000001", "amy39");
//
//		// 查詢
//		List<Cart_listVO> list = dao.getAll();
//		for (Cart_listVO acart_listVO : list) {
//			System.out.print(acart_listVO.getProd_no() + ",");
//			System.out.print(acart_listVO.getMem_ac() + ",");
//			System.out.println(acart_listVO.getProd_amount() + ",");
//			System.out.println();
//		}
		
		// 查詢
		Set<Cart_listVO> set = dao.getByMem("amy39");
		for (Cart_listVO acart_listVO : set) {
			System.out.print(acart_listVO.getProd_no() + ",");
			System.out.print(acart_listVO.getMem_ac() + ",");
			System.out.println(acart_listVO.getProd_amount() + ",");
			System.out.println();
		}
	}

}
