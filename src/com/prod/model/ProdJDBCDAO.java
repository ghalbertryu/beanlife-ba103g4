package com.prod.model;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

import com.ord_list.model.Ord_listVO;
import com.prod.query.ProdQuery;

import java.sql.*;


public class ProdJDBCDAO implements ProdDAO_interface {
	
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "ba103g4";
	String passwd = "123456";
	private static final String INSERT_STMT = "INSERT INTO PROD VALUES ('P'||prod_NO_seq.NEXTVAL,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	private static final String UPDATE = "UPDATE PROD SET "
			+ "STORE_NO =?," 
			+ "PROD_NAME =?," 
			+ "BEAN_TYPE =?," 
			+ "BEAN_GRADE =?," 
			+ "BEAN_CONTRY =?," 
			+ "BEAN_REGION =?," 
			+ "BEAN_FARM =?," 
			+ "BEAN_FARMER =?," 
			+ "BEAN_EL =?," 
			+ "PROC =?," 
			+ "ROAST =?," 
			+ "BEAN_ATTR_ACID =?," 
			+ "BEAN_ATTR_AROMA =?," 
			+ "BEAN_ATTR_BODY =?," 
			+ "BEAN_ATTR_AFTER =?," 
			+ "BEAN_ATTR_BAL =?," 
			+ "BEAN_AROMA =?," 
			+ "PROD_PRICE =?," 
			+ "PROD_WT =?," 
			+ "SEND_FEE =?," 
			+ "PROD_SUP =?," 
			+ "PROD_CONT =?," 
			+ "PROD_PIC1 =?," 
			+ "PROD_PIC2 =?," 
			+ "PROD_PIC3 =?," 
			+ "PROD_STAT =?," 
			+ "ED_TIME =?"
			+ "WHERE PROD_NO =?";
	
	private static final String DELETE = "DELETE FROM PROD WHERE PROD_NO = ?";
	private static final String GET_ALL_STMT = "SELECT * FROM PROD";
	private static final String GET_ONE_STMT = "SELECT * FROM PROD WHERE PROD_NO = ?";
	
	private static final String GET_ALL_NO_IMG_STMT = "SELECT "
			+ "PROD_NO,"
			+ "STORE_NO," 
			+ "PROD_NAME," 
			+ "BEAN_TYPE," 
			+ "BEAN_GRADE," 
			+ "BEAN_CONTRY," 
			+ "BEAN_REGION," 
			+ "BEAN_FARM," 
			+ "BEAN_FARMER," 
			+ "BEAN_EL," 
			+ "PROC," 
			+ "ROAST," 
			+ "BEAN_ATTR_ACID," 
			+ "BEAN_ATTR_AROMA," 
			+ "BEAN_ATTR_BODY," 
			+ "BEAN_ATTR_AFTER," 
			+ "BEAN_ATTR_BAL," 
			+ "BEAN_AROMA," 
			+ "PROD_PRICE," 
			+ "PROD_WT," 
			+ "SEND_FEE," 
			+ "PROD_SUP," 
			+ "PROD_CONT,"
			+ "PROD_STAT," 
			+ "ED_TIME "
			+ "FROM PROD";
	
	private static final String GET_ONE_NO_IMG_STMT = "SELECT "
			+ "STORE_NO," 
			+ "PROD_NAME," 
			+ "BEAN_TYPE," 
			+ "BEAN_GRADE," 
			+ "BEAN_CONTRY," 
			+ "BEAN_REGION," 
			+ "BEAN_FARM," 
			+ "BEAN_FARMER," 
			+ "BEAN_EL," 
			+ "PROC," 
			+ "ROAST," 
			+ "BEAN_ATTR_ACID," 
			+ "BEAN_ATTR_AROMA," 
			+ "BEAN_ATTR_BODY," 
			+ "BEAN_ATTR_AFTER," 
			+ "BEAN_ATTR_BAL," 
			+ "BEAN_AROMA," 
			+ "PROD_PRICE," 
			+ "PROD_WT," 
			+ "SEND_FEE," 
			+ "PROD_SUP," 
			+ "PROD_CONT,"
			+ "PROD_STAT," 
			+ "ED_TIME "
			+ "FROM PROD "
			+ "WHERE PROD_NO =?";
	
	private static final String GET_IMG_BY_PK_STMT = "SELECT PROD_PIC1,PROD_PIC2,PROD_PIC3 FROM PROD WHERE PROD_NO = ?"; 
	private static final String GET_QUERY_RESULT = "SELECT * FROM PROD WHERE BEAN_CONTRY LIKE ? AND PROC LIKE ? AND ROAST LIKE ? AND PROD_NAME LIKE ?";
	
	//快速更改資料庫圖片(測試用)
	private static final String UPDATE_IMG1 = "UPDATE PROD SET PROD_PIC1 =? WHERE PROD_NO =?";
	//查詢訂單細目
	private static final String GET_ORD_LISTBYPROD_NO = "SELECT * from ORD_LIST where PROD_NO=?";
	
	@Override
	public void insert(ProdVO prodVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
				
		try {		
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT_STMT);
			
			pstmt.setString(1, prodVO.getStore_no());
			pstmt.setString(2, prodVO.getProd_name());
			pstmt.setString(3, prodVO.getBean_type());
			pstmt.setString(4, prodVO.getBean_grade());
			pstmt.setString(5, prodVO.getBean_contry());
			pstmt.setString(6, prodVO.getBean_region());
			pstmt.setString(7, prodVO.getBean_farm());
			pstmt.setString(8, prodVO.getBean_farmer());
			pstmt.setInt(9, prodVO.getBean_el());
			pstmt.setString(10, prodVO.getProc());
			pstmt.setString(11, prodVO.getRoast());
			pstmt.setInt(12, prodVO.getBean_attr_acid());
			pstmt.setInt(13, prodVO.getBean_attr_aroma());
			pstmt.setInt(14, prodVO.getBean_attr_body());
			pstmt.setInt(15, prodVO.getBean_attr_after());
			pstmt.setInt(16, prodVO.getBean_attr_bal());
			pstmt.setString(17, prodVO.getBean_aroma());
			pstmt.setInt(18, prodVO.getProd_price());
			pstmt.setDouble(19, prodVO.getProd_wt());
			pstmt.setInt(20, prodVO.getSend_fee());
			pstmt.setInt(21, prodVO.getProd_sup());
			pstmt.setString(22, prodVO.getProd_cont());
			pstmt.setBytes(23, prodVO.getProd_pic1());
			pstmt.setBytes(24, prodVO.getProd_pic2());
			pstmt.setBytes(25, prodVO.getProd_pic3());
			pstmt.setString(26, prodVO.getProd_stat());
			pstmt.setDate(27, prodVO.getEd_time());
			
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			System.out.println(e);
			e.printStackTrace();
		} finally{
			if (pstmt != null) {
				try{
					pstmt.close();
				} catch (SQLException se){
					se.printStackTrace(System.err);
				}
			}
			if(con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}		
	}

	@Override
	public void update(ProdVO prodVO) {
		Connection con = null;
		PreparedStatement pstmt = null;	
		
		try {		
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE);
			
			pstmt.setString(1, prodVO.getStore_no());
			pstmt.setString(2, prodVO.getProd_name());
			pstmt.setString(3, prodVO.getBean_type());
			pstmt.setString(4, prodVO.getBean_grade());
			pstmt.setString(5, prodVO.getBean_contry());
			pstmt.setString(6, prodVO.getBean_region());
			pstmt.setString(7, prodVO.getBean_farm());
			pstmt.setString(8, prodVO.getBean_farmer());
			pstmt.setInt(9, prodVO.getBean_el());
			pstmt.setString(10, prodVO.getProc());
			pstmt.setString(11, prodVO.getRoast());
			pstmt.setInt(12, prodVO.getBean_attr_acid());
			pstmt.setInt(13, prodVO.getBean_attr_aroma());
			pstmt.setInt(14, prodVO.getBean_attr_body());
			pstmt.setInt(15, prodVO.getBean_attr_after());
			pstmt.setInt(16, prodVO.getBean_attr_bal());
			pstmt.setString(17, prodVO.getBean_aroma());
			pstmt.setInt(18, prodVO.getProd_price());
			pstmt.setDouble(19, prodVO.getProd_wt());
			pstmt.setInt(20, prodVO.getSend_fee());
			pstmt.setInt(21, prodVO.getProd_sup());
			pstmt.setString(22, prodVO.getProd_cont());
			pstmt.setBytes(23, prodVO.getProd_pic1());
			pstmt.setBytes(24, prodVO.getProd_pic2());
			pstmt.setBytes(25, prodVO.getProd_pic3());
			pstmt.setString(26, prodVO.getProd_stat());
			pstmt.setDate(27, prodVO.getEd_time());
			pstmt.setString(28, prodVO.getProd_no());
			
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			throw new RuntimeException("A database error occured. "
					+ e.getMessage());
			
		}
		 catch (ClassNotFoundException e) {
				e.printStackTrace();
		}finally{
			if (pstmt != null) {
				try{
					pstmt.close();
				} catch (SQLException se){
					se.printStackTrace(System.err);
				}
			}
			if(con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}	
	}
	
	
	
	@Override
	public void updateByCon(ProdVO prodVO, Connection con) {
		
		PreparedStatement pstmt = null;	
		
		try {		
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE);
			
			pstmt.setString(1, prodVO.getStore_no());
			pstmt.setString(2, prodVO.getProd_name());
			pstmt.setString(3, prodVO.getBean_type());
			pstmt.setString(4, prodVO.getBean_grade());
			pstmt.setString(5, prodVO.getBean_contry());
			pstmt.setString(6, prodVO.getBean_region());
			pstmt.setString(7, prodVO.getBean_farm());
			pstmt.setString(8, prodVO.getBean_farmer());
			pstmt.setInt(9, prodVO.getBean_el());
			pstmt.setString(10, prodVO.getProc());
			pstmt.setString(11, prodVO.getRoast());
			pstmt.setInt(12, prodVO.getBean_attr_acid());
			pstmt.setInt(13, prodVO.getBean_attr_aroma());
			pstmt.setInt(14, prodVO.getBean_attr_body());
			pstmt.setInt(15, prodVO.getBean_attr_after());
			pstmt.setInt(16, prodVO.getBean_attr_bal());
			pstmt.setString(17, prodVO.getBean_aroma());
			pstmt.setInt(18, prodVO.getProd_price());
			pstmt.setDouble(19, prodVO.getProd_wt());
			pstmt.setInt(20, prodVO.getSend_fee());
			pstmt.setInt(21, prodVO.getProd_sup());
			pstmt.setString(22, prodVO.getProd_cont());
			pstmt.setBytes(23, prodVO.getProd_pic1());
			pstmt.setBytes(24, prodVO.getProd_pic2());
			pstmt.setBytes(25, prodVO.getProd_pic3());
			pstmt.setString(26, prodVO.getProd_stat());
			pstmt.setDate(27, prodVO.getEd_time());
			pstmt.setString(28, prodVO.getProd_no());
			
			pstmt.executeUpdate();
			
		} catch (ClassNotFoundException e) {
			
				e.printStackTrace();
			if (con != null) {
				try {
					// 3●設定於當有exception發生時之catch區塊內
					System.err.print("Transaction is being ");
					System.err.println("rolled back-由-prod");
					con.rollback();
				} catch (SQLException excep) {
					throw new RuntimeException("rollback error occured. "
							+ excep.getMessage());
				}
			}
			throw new RuntimeException("A database error occured. "
					+ e.getMessage());

		} catch (SQLException e) {
			
			if (con != null) {
				try {
					// 3●設定於當有exception發生時之catch區塊內
					System.err.print("Transaction is being ");
					System.err.println("rolled back-由-prod");
					con.rollback();
				} catch (SQLException excep) {
					throw new RuntimeException("rollback error occured. "
							+ excep.getMessage());
				}
			}
			throw new RuntimeException("A database error occured. "
					+ e.getMessage());	
		} finally{
			if (pstmt != null) {
				try{
					pstmt.close();
				} catch (SQLException se){
					se.printStackTrace(System.err);
				}
			}
		}	
	}
	
	@Override
	public void delete(String prod_no) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(DELETE);
			pstmt.setString(1, prod_no);
			pstmt.executeUpdate();
			
			con.setAutoCommit(true);
			
		} catch (SQLException e) {
			e.printStackTrace();
			if(con != null){
				try {
					con.rollback();
				} catch (SQLException exc){
					throw new RuntimeException("rollback error occured"
							+ exc.getMessage());
				}
			}
			throw new RuntimeException("A database error occured ."
					+ e.getMessage());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally{
			if(pstmt != null){
				try{
					pstmt.close();
				}
				catch(SQLException se){
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
	public ProdVO findByPrimaryKey(String prod_no) {
		ProdVO prodVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ONE_STMT);
			pstmt.setString(1, prod_no);
			rs = pstmt.executeQuery();
			
			while (rs.next()){
				prodVO = new ProdVO();
				prodVO.setStore_no(rs.getString("store_no"));
				prodVO.setProd_no(rs.getString("prod_no"));
				prodVO.setProd_name(rs.getString("prod_name"));
				prodVO.setBean_type(rs.getString("bean_type"));
				prodVO.setBean_grade(rs.getString("bean_grade"));
				prodVO.setBean_contry(rs.getString("bean_contry"));
				prodVO.setBean_region(rs.getString("bean_region"));
				prodVO.setBean_farm(rs.getString("bean_farm"));
				prodVO.setBean_farmer(rs.getString("bean_farmer"));
				prodVO.setBean_el(rs.getInt("bean_el"));
				prodVO.setProc(rs.getString("proc"));
				prodVO.setRoast(rs.getString("roast"));
				prodVO.setBean_attr_acid(rs.getInt("bean_attr_acid"));
				prodVO.setBean_attr_aroma(rs.getInt("bean_attr_aroma"));
				prodVO.setBean_attr_body(rs.getInt("bean_attr_body"));
				prodVO.setBean_attr_after(rs.getInt("bean_attr_after"));
				prodVO.setBean_attr_bal(rs.getInt("bean_attr_bal"));
				prodVO.setBean_aroma(rs.getString("Bean_aroma"));
				prodVO.setProd_price(rs.getInt("prod_price"));
				prodVO.setProd_wt(rs.getDouble("prod_wt"));
				prodVO.setSend_fee(rs.getInt("send_fee"));
				prodVO.setProd_sup(rs.getInt("prod_sup"));
				prodVO.setProd_cont(rs.getString("prod_cont"));
				prodVO.setProd_pic1(rs.getBytes("prod_pic1"));
				prodVO.setProd_pic2(rs.getBytes("prod_pic2"));
				prodVO.setProd_pic3(rs.getBytes("prod_pic3"));
				prodVO.setProd_stat(rs.getString("prod_stat"));
				prodVO.setEd_time(rs.getDate("ed_time"));
			}
			
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
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
		return prodVO;
	}

	@Override
	public List<ProdVO> getAll() {
		List<ProdVO> list = new ArrayList<ProdVO>();
		ProdVO prodVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();
			
			while (rs.next()){
				prodVO = new ProdVO();
				prodVO.setProd_no(rs.getString("prod_no"));
				prodVO.setStore_no(rs.getString("store_no"));
				prodVO.setProd_name(rs.getString("prod_name"));
				prodVO.setBean_type(rs.getString("bean_type"));
				prodVO.setBean_grade(rs.getString("bean_grade"));
				prodVO.setBean_contry(rs.getString("bean_contry"));
				prodVO.setBean_region(rs.getString("bean_region"));
				prodVO.setBean_farm(rs.getString("bean_farm"));
				prodVO.setBean_farmer(rs.getString("bean_farmer"));
				prodVO.setBean_el(rs.getInt("bean_el"));
				prodVO.setProc(rs.getString("proc"));
				prodVO.setRoast(rs.getString("roast"));
				prodVO.setBean_attr_acid(rs.getInt("bean_attr_acid"));
				prodVO.setBean_attr_aroma(rs.getInt("bean_attr_aroma"));
				prodVO.setBean_attr_body(rs.getInt("bean_attr_body"));
				prodVO.setBean_attr_after(rs.getInt("bean_attr_after"));
				prodVO.setBean_attr_bal(rs.getInt("bean_attr_bal"));
				prodVO.setBean_aroma(rs.getString("Bean_aroma"));
				prodVO.setProd_price(rs.getInt("prod_price"));
				prodVO.setProd_wt(rs.getDouble("prod_wt"));
				prodVO.setSend_fee(rs.getInt("send_fee"));
				prodVO.setProd_sup(rs.getInt("prod_sup"));
				prodVO.setProd_cont(rs.getString("prod_cont"));
				prodVO.setProd_pic1(rs.getBytes("prod_pic1"));
				prodVO.setProd_pic2(rs.getBytes("prod_pic2"));
				prodVO.setProd_pic3(rs.getBytes("prod_pic3"));
				prodVO.setProd_stat(rs.getString("prod_stat"));
				prodVO.setEd_time(rs.getDate("ed_time"));
				list.add(prodVO);
			}
			
		}  catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
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
	public List<ProdVO> getAll(Map<String, String[]> map, Map<String, String[]> map2) {
		List<ProdVO> list = new ArrayList<ProdVO>();
		ProdVO prodVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			

			String str1 = (ProdQuery.get_WhereCondition(map).trim().length()==0)?"where (":"and (";
			String finalSQL = "select * from prod "
					          + ProdQuery.get_WhereCondition(map)
					          + str1
					          + ProdQuery.get_ElseCondition(map2)
					          + ")"
					          + "order by prod_no desc";
			System.out.println(finalSQL);
			
			pstmt = con.prepareStatement(finalSQL);
			rs = pstmt.executeQuery();
			
			while (rs.next()){
				prodVO = new ProdVO();
				prodVO.setProd_no(rs.getString("prod_no"));
				prodVO.setStore_no(rs.getString("store_no"));
				prodVO.setProd_name(rs.getString("prod_name"));
				prodVO.setBean_type(rs.getString("bean_type"));
				prodVO.setBean_grade(rs.getString("bean_grade"));
				prodVO.setBean_contry(rs.getString("bean_contry"));
				prodVO.setBean_region(rs.getString("bean_region"));
				prodVO.setBean_farm(rs.getString("bean_farm"));
				prodVO.setBean_farmer(rs.getString("bean_farmer"));
				prodVO.setBean_el(rs.getInt("bean_el"));
				prodVO.setProc(rs.getString("proc"));
				prodVO.setRoast(rs.getString("roast"));
				prodVO.setBean_attr_acid(rs.getInt("bean_attr_acid"));
				prodVO.setBean_attr_aroma(rs.getInt("bean_attr_aroma"));
				prodVO.setBean_attr_body(rs.getInt("bean_attr_body"));
				prodVO.setBean_attr_after(rs.getInt("bean_attr_after"));
				prodVO.setBean_attr_bal(rs.getInt("bean_attr_bal"));
				prodVO.setBean_aroma(rs.getString("Bean_aroma"));
				prodVO.setProd_price(rs.getInt("prod_price"));
				prodVO.setProd_wt(rs.getDouble("prod_wt"));
				prodVO.setSend_fee(rs.getInt("send_fee"));
				prodVO.setProd_sup(rs.getInt("prod_sup"));
				prodVO.setProd_cont(rs.getString("prod_cont"));
				prodVO.setProd_pic1(rs.getBytes("prod_pic1"));
				prodVO.setProd_pic2(rs.getBytes("prod_pic2"));
				prodVO.setProd_pic3(rs.getBytes("prod_pic3"));
				prodVO.setProd_stat(rs.getString("prod_stat"));
				prodVO.setEd_time(rs.getDate("ed_time"));
				list.add(prodVO);
			}
			
		}  catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
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
	public List<ProdVO> getAllNoImg() {
		List<ProdVO> list = new ArrayList<ProdVO>();
		ProdVO prodVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;		
		
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ALL_NO_IMG_STMT);
			rs = pstmt.executeQuery();
			
			while (rs.next()){
				prodVO = new ProdVO();
				prodVO.setProd_no(rs.getString("prod_no"));
				prodVO.setStore_no(rs.getString("store_no"));
				prodVO.setProd_name(rs.getString("prod_name"));
				prodVO.setBean_type(rs.getString("bean_type"));
				prodVO.setBean_grade(rs.getString("bean_grade"));
				prodVO.setBean_contry(rs.getString("bean_contry"));
				prodVO.setBean_region(rs.getString("bean_region"));
				prodVO.setBean_farm(rs.getString("bean_farm"));
				prodVO.setBean_farmer(rs.getString("bean_farmer"));
				prodVO.setBean_el(rs.getInt("bean_el"));
				prodVO.setProc(rs.getString("proc"));
				prodVO.setRoast(rs.getString("roast"));
				prodVO.setBean_attr_acid(rs.getInt("bean_attr_acid"));
				prodVO.setBean_attr_aroma(rs.getInt("bean_attr_aroma"));
				prodVO.setBean_attr_body(rs.getInt("bean_attr_body"));
				prodVO.setBean_attr_after(rs.getInt("bean_attr_after"));
				prodVO.setBean_attr_bal(rs.getInt("bean_attr_bal"));
				prodVO.setBean_aroma(rs.getString("Bean_aroma"));
				prodVO.setProd_price(rs.getInt("prod_price"));
				prodVO.setProd_wt(rs.getDouble("prod_wt"));
				prodVO.setSend_fee(rs.getInt("send_fee"));
				prodVO.setProd_sup(rs.getInt("prod_sup"));
				prodVO.setProd_cont(rs.getString("prod_cont"));
				prodVO.setProd_stat(rs.getString("prod_stat"));
				prodVO.setEd_time(rs.getDate("ed_time"));
				list.add(prodVO);
			}
			
		}  catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
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
	public List<byte[]> getImageByPK(String prod_no) {
		List<byte[]> prodImgList = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_IMG_BY_PK_STMT);
			pstmt.setString(1, prod_no);
			rs = pstmt.executeQuery();
			
			while (rs.next()){
				prodImgList = new ArrayList<byte[]>();
				prodImgList.add(rs.getBytes(1));
				prodImgList.add(rs.getBytes(2));
				prodImgList.add(rs.getBytes(3));
			}
			
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
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
		return prodImgList;
	}
	
	@Override
	public List<ProdVO> getQueryResult(String bean_contry, String proc, String roast, String prod_name) {
			List<ProdVO> list = new ArrayList<ProdVO>();
			ProdVO prodVO = null;
			Connection con = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			
	
			try {
				Class.forName(driver);
				con = DriverManager.getConnection(url, userid, passwd);
				pstmt = con.prepareStatement(GET_QUERY_RESULT);
				pstmt.setString(1, bean_contry);
				pstmt.setString(2, proc);
				pstmt.setString(3, roast);
				pstmt.setString(4, prod_name);
				rs = pstmt.executeQuery();
				
				while (rs.next()){
					prodVO = new ProdVO();
					prodVO.setProd_no(rs.getString("prod_no"));
					prodVO.setStore_no(rs.getString("store_no"));
					prodVO.setProd_name(rs.getString("prod_name"));
					prodVO.setBean_type(rs.getString("bean_type"));
					prodVO.setBean_grade(rs.getString("bean_grade"));
					prodVO.setBean_contry(rs.getString("bean_contry"));
					prodVO.setBean_region(rs.getString("bean_region"));
					prodVO.setBean_farm(rs.getString("bean_farm"));
					prodVO.setBean_farmer(rs.getString("bean_farmer"));
					prodVO.setBean_el(rs.getInt("bean_el"));
					prodVO.setProc(rs.getString("proc"));
					prodVO.setRoast(rs.getString("roast"));
					prodVO.setBean_attr_acid(rs.getInt("bean_attr_acid"));
					prodVO.setBean_attr_aroma(rs.getInt("bean_attr_aroma"));
					prodVO.setBean_attr_body(rs.getInt("bean_attr_body"));
					prodVO.setBean_attr_after(rs.getInt("bean_attr_after"));
					prodVO.setBean_attr_bal(rs.getInt("bean_attr_bal"));
					prodVO.setBean_aroma(rs.getString("Bean_aroma"));
					prodVO.setProd_price(rs.getInt("prod_price"));
					prodVO.setProd_wt(rs.getDouble("prod_wt"));
					prodVO.setSend_fee(rs.getInt("send_fee"));
					prodVO.setProd_sup(rs.getInt("prod_sup"));
					prodVO.setProd_cont(rs.getString("prod_cont"));
	//				prodVO.setProd_pic1(rs.getBytes("prod_pic1"));
	//				prodVO.setProd_pic2(rs.getBytes("prod_pic2"));
	//				prodVO.setProd_pic3(rs.getBytes("prod_pic3"));
					prodVO.setProd_stat(rs.getString("prod_stat"));
					prodVO.setEd_time(rs.getDate("ed_time"));
					list.add(prodVO);
				}
				
			} catch (ClassNotFoundException e) {
				throw new RuntimeException("Couldn't load database driver. "
						+ e.getMessage());
			} catch (SQLException se) {
				throw new RuntimeException("A database error occured. "
						+ se.getMessage());
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
	
	public void updateImg1(ProdVO prodVO) {
		Connection con = null;
		PreparedStatement pstmt = null;	
		
		try {		
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE_IMG1);
			
			pstmt.setBytes(1, prodVO.getProd_pic1());
			pstmt.setString(2, prodVO.getProd_no());
			
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			throw new RuntimeException("A database error occured. "
					+ e.getMessage());
			
		}
		 catch (ClassNotFoundException e) {
				e.printStackTrace();
		}finally{
			if (pstmt != null) {
				try{
					pstmt.close();
				} catch (SQLException se){
					se.printStackTrace(System.err);
				}
			}
			if(con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}	
	}

	@Override
	public ProdVO findByPrimaryKeyNoImg(String prod_no) {
		ProdVO prodVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ONE_NO_IMG_STMT);
			pstmt.setString(1, prod_no);
			rs = pstmt.executeQuery();
			
			while (rs.next()){
				prodVO = new ProdVO();
				prodVO.setStore_no(rs.getString("store_no"));
				prodVO.setProd_no(rs.getString("prod_no"));
				prodVO.setProd_name(rs.getString("prod_name"));
				prodVO.setBean_type(rs.getString("bean_type"));
				prodVO.setBean_grade(rs.getString("bean_grade"));
				prodVO.setBean_contry(rs.getString("bean_contry"));
				prodVO.setBean_region(rs.getString("bean_region"));
				prodVO.setBean_farm(rs.getString("bean_farm"));
				prodVO.setBean_farmer(rs.getString("bean_farmer"));
				prodVO.setBean_el(rs.getInt("bean_el"));
				prodVO.setProc(rs.getString("proc"));
				prodVO.setRoast(rs.getString("roast"));
				prodVO.setBean_attr_acid(rs.getInt("bean_attr_acid"));
				prodVO.setBean_attr_aroma(rs.getInt("bean_attr_aroma"));
				prodVO.setBean_attr_body(rs.getInt("bean_attr_body"));
				prodVO.setBean_attr_after(rs.getInt("bean_attr_after"));
				prodVO.setBean_attr_bal(rs.getInt("bean_attr_bal"));
				prodVO.setBean_aroma(rs.getString("Bean_aroma"));
				prodVO.setProd_price(rs.getInt("prod_price"));
				prodVO.setProd_wt(rs.getDouble("prod_wt"));
				prodVO.setSend_fee(rs.getInt("send_fee"));
				prodVO.setProd_sup(rs.getInt("prod_sup"));
				prodVO.setProd_cont(rs.getString("prod_cont"));
				prodVO.setProd_stat(rs.getString("prod_stat"));
				prodVO.setEd_time(rs.getDate("ed_time"));
			}
			
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
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
		return prodVO;
	}
	
	@Override
	public Set<Ord_listVO> getOrd_listByProd(String prod_no) {
		Set<Ord_listVO> set = new LinkedHashSet<Ord_listVO>();
		Ord_listVO ord_listVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ORD_LISTBYPROD_NO);
			pstmt.setString(1, prod_no);

			rs = pstmt.executeQuery();
			while (rs.next()) {
				ord_listVO = new Ord_listVO();
				ord_listVO.setOrd_no(rs.getString("ORD_NO"));
				ord_listVO.setProd_no(rs.getString("PROD_NO"));
				ord_listVO.setAmont(rs.getInt("AMONT"));
				set.add(ord_listVO);
				
			}

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return set;
	}

	public static void main (String[] args) throws IOException{
		ProdJDBCDAO dao = new ProdJDBCDAO();		
//		insertTest(dao);
//		updateTest(dao);
//		getQueryResultTest("衣索比亞", "水洗", "中焙", "%gg%");
//		dao.delete("P1000000019");
//		getImageTest(dao);
//		getAllTest(dao);
//		getAllNoImgTest(dao);
//		getOneNoImgTest(dao);
		//只新增照片方法，暫為測試用
//		for(int i = 1; i<10 ;i++){
//			String prod_no = "P100000000" + i;
//			updateImg1Test(dao,prod_no ,"D:\\apache-tomcat-7.0.75\\webapps\\BeanLife_front\\res\\img\\p"+((i % 3)+1)+".jpg" );
//		}
		
		//getAllMap
		Map<String, String[]> map = new TreeMap<String, String[]>();
		map.put("bean_contry", new String[] {""});
		map.put("proc", new String[] { "" });
		map.put("roast", new String[] {"中深焙" });


		Map<String, String[]> map2 = new TreeMap<String, String[]>();
		map2.put("prod_no", new String[] {" " });
		map2.put("store_no", new String[] {" " });
		map2.put("prod_name", new String[] {" " });
		map2.put("bean_type", new String[] {" " });
		map2.put("bean_grade", new String[] {" " });
		map2.put("bean_region", new String[] {" " });
		map2.put("bean_farm", new String[] {" " });
		map2.put("bean_farmer", new String[] {" " });
		map2.put("bean_aroma", new String[] {" " });
		map2.put("prod_stat", new String[] {" " });
		
		List<ProdVO> list = dao.getAll(map, map2);
		for (ProdVO prodVO : list) {
			System.out.print(prodVO.getProd_no() + ", ");
			System.out.print(prodVO.getStore_no() + ", ");
			System.out.print(prodVO.getProd_name() + ", ");
			System.out.print(prodVO.getBean_type() + ", ");
			System.out.print(prodVO.getBean_grade() + ", ");
			System.out.print(prodVO.getBean_contry() + ", ");
			System.out.print(prodVO.getBean_region() + ", ");
			System.out.print(prodVO.getBean_farm() + ", ");
			System.out.print(prodVO.getBean_farmer() + ", ");
			System.out.print(prodVO.getBean_el() + ", ");
			System.out.print(prodVO.getProc() + ", ");
			System.out.print(prodVO.getRoast() + ", ");
			System.out.print(prodVO.getBean_attr_acid() + ", ");
			System.out.print(prodVO.getBean_attr_aroma() + ", ");
			System.out.print(prodVO.getBean_attr_body() + ", ");
			System.out.print(prodVO.getBean_attr_after() + ", ");
			System.out.print(prodVO.getBean_attr_bal() + ", ");
			System.out.print(prodVO.getBean_aroma() + ", ");
			System.out.print(prodVO.getProd_price() + ", ");
			System.out.print(prodVO.getProd_wt() + ", ");
			System.out.print(prodVO.getSend_fee() + ", ");
			System.out.print(prodVO.getProd_sup() + ", ");
			System.out.print(prodVO.getProd_cont() + ", ");
			System.out.print(prodVO.getProd_pic1() + ", ");
			System.out.print(prodVO.getProd_pic2() + ", ");
			System.out.print(prodVO.getProd_pic3() + ", ");
			System.out.print(prodVO.getProd_stat() + ", ");
			System.out.print(prodVO.getEd_time() + ", ");
			System.out.println();
		}
		
	}
	
	public static void insertTest(ProdJDBCDAO dao) throws IOException{
		ProdVO prodVO01 = new ProdVO();
				
		prodVO01.setStore_no("S1000000002");
		prodVO01.setProd_name("凱勇山 Kara批次");
		prodVO01.setBean_type("衣索比亞原生");
		prodVO01.setBean_grade("G1");
		prodVO01.setBean_contry("衣索比亞");
		prodVO01.setBean_region("歌迪歐");
		prodVO01.setBean_farm("班可果丁丁村");
		prodVO01.setBean_farmer("貝內費加");
		prodVO01.setBean_el(1800);
		prodVO01.setProc("水洗");
		prodVO01.setRoast("中焙");
		prodVO01.setBean_attr_acid(4);
		prodVO01.setBean_attr_aroma(5);
		prodVO01.setBean_attr_body(2);
		prodVO01.setBean_attr_after(3);
		prodVO01.setBean_attr_bal(3);
		prodVO01.setBean_aroma("咖啡花檸檬甜香");
		prodVO01.setProd_price(250);
		prodVO01.setProd_wt(1.1);
		prodVO01.setSend_fee(80);
		prodVO01.setProd_sup(170);
		prodVO01.setProd_cont("沿襲傳統工法於正午時分覆蓋棚架約三小時，以免過強的日曬加速乾燥時程而影響品質。");
		prodVO01.setProd_pic1(new byte[1]);
		prodVO01.setProd_pic2(new byte[1]);
		prodVO01.setProd_pic3(new byte[1]);
		prodVO01.setProd_stat("上架");
		prodVO01.setEd_time(java.sql.Date.valueOf("2002-01-01"));
		
		dao.insert(prodVO01);
		System.out.println("新增一筆商品");
	}
	
	public static void updateTest(ProdJDBCDAO dao) throws IOException{
		ProdVO prodVO01 = new ProdVO();
		prodVO01.setProd_no("P1000000002");	
		prodVO01.setStore_no("S1000000002");
		prodVO01.setProd_name("勇士莊園 波旁Bourbon");
		prodVO01.setBean_type("波旁Bourbon");
		prodVO01.setBean_grade("G1G1");
		prodVO01.setBean_contry("衣索比亞");
		prodVO01.setBean_region("歌迪歐");
		prodVO01.setBean_farm("班可果丁丁村");
		prodVO01.setBean_farmer("貝內費加");
		prodVO01.setBean_el(1800);
		prodVO01.setProc("水洗");
		prodVO01.setRoast("中焙");
		prodVO01.setBean_attr_acid(4);
		prodVO01.setBean_attr_aroma(5);
		prodVO01.setBean_attr_body(2);
		prodVO01.setBean_attr_after(3);
		prodVO01.setBean_attr_bal(3);
		prodVO01.setBean_aroma("咖啡花檸檬甜香");
		prodVO01.setProd_price(250);
		prodVO01.setProd_wt(0.5);
		prodVO01.setSend_fee(80);
		prodVO01.setProd_sup(170);
		prodVO01.setProd_cont("沿襲傳統工法於正午時分覆蓋棚架約三小時，以免過強的日曬加速乾燥時程而影響品質。");
		prodVO01.setProd_pic1(new byte[1]);
		prodVO01.setProd_pic2(new byte[1]);
		prodVO01.setProd_pic3(new byte[1]);
		prodVO01.setProd_stat("上架");
		prodVO01.setEd_time(java.sql.Date.valueOf("2002-01-01"));
		
		dao.update(prodVO01);
		System.out.println("修改一筆商品");
	}
	
	public static void getImageByPKTest(ProdJDBCDAO dao) throws IOException{
		List<byte[]> prodImgList;
		prodImgList= dao.getImageByPK("P1000000004");
		System.out.print(prodImgList.get(0).toString()+ ",");
		System.out.print(prodImgList.get(1).toString() + ",");
		System.out.println(prodImgList.get(2).toString());
		System.out.print("============取得商品圖片==============");
	}
	
	public static void getAllTest(ProdJDBCDAO dao){
		List<ProdVO> list = dao.getAll();
		for (ProdVO prodVO : list) {
			System.out.print(prodVO.getProd_no() + ", ");
			System.out.print(prodVO.getStore_no() + ", ");
			System.out.print(prodVO.getProd_name() + ", ");
			System.out.print(prodVO.getBean_type() + ", ");
			System.out.print(prodVO.getBean_grade() + ", ");
			System.out.print(prodVO.getBean_contry() + ", ");
			System.out.print(prodVO.getBean_region() + ", ");
			System.out.print(prodVO.getBean_farm() + ", ");
			System.out.print(prodVO.getBean_farmer() + ", ");
			System.out.print(prodVO.getBean_el() + ", ");
			System.out.print(prodVO.getProc() + ", ");
			System.out.print(prodVO.getRoast() + ", ");
			System.out.print(prodVO.getBean_attr_acid() + ", ");
			System.out.print(prodVO.getBean_attr_aroma() + ", ");
			System.out.print(prodVO.getBean_attr_body() + ", ");
			System.out.print(prodVO.getBean_attr_after() + ", ");
			System.out.print(prodVO.getBean_attr_bal() + ", ");
			System.out.print(prodVO.getBean_aroma() + ", ");
			System.out.print(prodVO.getProd_price() + ", ");
			System.out.print(prodVO.getProd_wt() + ", ");
			System.out.print(prodVO.getSend_fee() + ", ");
			System.out.print(prodVO.getProd_sup() + ", ");
			System.out.print(prodVO.getProd_cont() + ", ");
			System.out.print(prodVO.getProd_pic1() + ", ");
			System.out.print(prodVO.getProd_pic2() + ", ");
			System.out.print(prodVO.getProd_pic3() + ", ");
			System.out.print(prodVO.getProd_stat() + ", ");
			System.out.print(prodVO.getEd_time() + ", ");
			System.out.println();
		}
	}
	
	public static void getAllNoImgTest(ProdJDBCDAO dao){
		List<ProdVO> list = dao.getAllNoImg();
		for (ProdVO prodVO : list) {
			System.out.print(prodVO.getProd_no() + ", ");
			System.out.print(prodVO.getStore_no() + ", ");
			System.out.print(prodVO.getProd_name() + ", ");
			System.out.print(prodVO.getBean_type() + ", ");
			System.out.print(prodVO.getBean_grade() + ", ");
			System.out.print(prodVO.getBean_contry() + ", ");
			System.out.print(prodVO.getBean_region() + ", ");
			System.out.print(prodVO.getBean_farm() + ", ");
			System.out.print(prodVO.getBean_farmer() + ", ");
			System.out.print(prodVO.getBean_el() + ", ");
			System.out.print(prodVO.getProc() + ", ");
			System.out.print(prodVO.getRoast() + ", ");
			System.out.print(prodVO.getBean_attr_acid() + ", ");
			System.out.print(prodVO.getBean_attr_aroma() + ", ");
			System.out.print(prodVO.getBean_attr_body() + ", ");
			System.out.print(prodVO.getBean_attr_after() + ", ");
			System.out.print(prodVO.getBean_attr_bal() + ", ");
			System.out.print(prodVO.getBean_aroma() + ", ");
			System.out.print(prodVO.getProd_price() + ", ");
			System.out.print(prodVO.getProd_wt() + ", ");
			System.out.print(prodVO.getSend_fee() + ", ");
			System.out.print(prodVO.getProd_sup() + ", ");
			System.out.print(prodVO.getProd_cont() + ", ");
			System.out.print(prodVO.getProd_stat() + ", ");
			System.out.print(prodVO.getEd_time() + ", ");
			System.out.println();
		}
	}
		
	public static byte[] getPictureByteArray(String path) throws IOException {
		File file = new File(path);
		FileInputStream fis = new FileInputStream(file);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] buffer = new byte[8192];
		int i;
		while ((i = fis.read(buffer)) != -1) {
			baos.write(buffer, 0, i);
		}
		baos.close();
		fis.close();

		return baos.toByteArray();
	}

	public static void getQueryResultTest(String bean_contry, String proc, String roast, String prod_name){
		ProdJDBCDAO dao = new ProdJDBCDAO();	
		List<ProdVO> list = dao.getQueryResult(bean_contry, proc, roast, prod_name);
		for (ProdVO prodVO : list) {
			System.out.print(prodVO.getProd_no() + ", ");
			System.out.print(prodVO.getStore_no() + ", ");
			System.out.print(prodVO.getProd_name() + ", ");
			System.out.print(prodVO.getBean_type() + ", ");
			System.out.print(prodVO.getBean_grade() + ", ");
			System.out.print(prodVO.getBean_contry() + ", ");
			System.out.print(prodVO.getBean_region() + ", ");
			System.out.print(prodVO.getBean_farm() + ", ");
			System.out.print(prodVO.getBean_farmer() + ", ");
			System.out.print(prodVO.getBean_el() + ", ");
			System.out.print(prodVO.getProc() + ", ");
			System.out.print(prodVO.getRoast() + ", ");
			System.out.print(prodVO.getBean_attr_acid() + ", ");
			System.out.print(prodVO.getBean_attr_aroma() + ", ");
			System.out.print(prodVO.getBean_attr_body() + ", ");
			System.out.print(prodVO.getBean_attr_after() + ", ");
			System.out.print(prodVO.getBean_attr_bal() + ", ");
			System.out.print(prodVO.getBean_aroma() + ", ");
			System.out.print(prodVO.getProd_price() + ", ");
			System.out.print(prodVO.getProd_wt() + ", ");
			System.out.print(prodVO.getSend_fee() + ", ");
			System.out.print(prodVO.getProd_sup() + ", ");
			System.out.print(prodVO.getProd_cont() + ", ");
			System.out.print(prodVO.getProd_stat() + ", ");
			System.out.print(prodVO.getEd_time() + ", ");
			System.out.println();
		}
	}
	
	//只新增照片方法，暫為測試用
	public static void updateImg1Test(ProdJDBCDAO dao, String ptod_no, String path) throws IOException{
		ProdVO prodVO01 = new ProdVO();
		prodVO01.setProd_no(ptod_no);	
		prodVO01.setProd_pic1(getPictureByteArray(path));
		
		dao.updateImg1(prodVO01);
		System.out.println("修改一筆商品照片");
	}

	public static void getOneNoImgTest(ProdJDBCDAO dao){
		ProdVO prodVO = dao.findByPrimaryKey("P1000000001");

			System.out.print(prodVO.getProd_no() + ", ");
			System.out.print(prodVO.getStore_no() + ", ");
			System.out.print(prodVO.getProd_name() + ", ");
			System.out.print(prodVO.getBean_type() + ", ");
			System.out.print(prodVO.getBean_grade() + ", ");
			System.out.print(prodVO.getBean_contry() + ", ");
			System.out.print(prodVO.getBean_region() + ", ");
			System.out.print(prodVO.getBean_farm() + ", ");
			System.out.print(prodVO.getBean_farmer() + ", ");
			System.out.print(prodVO.getBean_el() + ", ");
			System.out.print(prodVO.getProc() + ", ");
			System.out.print(prodVO.getRoast() + ", ");
			System.out.print(prodVO.getBean_attr_acid() + ", ");
			System.out.print(prodVO.getBean_attr_aroma() + ", ");
			System.out.print(prodVO.getBean_attr_body() + ", ");
			System.out.print(prodVO.getBean_attr_after() + ", ");
			System.out.print(prodVO.getBean_attr_bal() + ", ");
			System.out.print(prodVO.getBean_aroma() + ", ");
			System.out.print(prodVO.getProd_price() + ", ");
			System.out.print(prodVO.getProd_wt() + ", ");
			System.out.print(prodVO.getSend_fee() + ", ");
			System.out.print(prodVO.getProd_sup() + ", ");
			System.out.print(prodVO.getProd_cont() + ", ");
			System.out.print(prodVO.getProd_stat() + ", ");
			System.out.print(prodVO.getEd_time() + ", ");
			System.out.println();
		
	}
}
