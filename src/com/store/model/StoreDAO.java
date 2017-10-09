package com.store.model;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.prod.model.ProdVO;

public class StoreDAO implements StoreDAO_interface{
	
	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/BA103G4DB");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	private static final String INSERT_STMT = "INSERT INTO store (STORE_NO,MEM_AC,TAX_ID_NO,WIN_ID_PIC,STORE_PHONE,STORE_ADD,STORE_ADD_LAT,"
			+ "STORE_ADD_LON,STORE_NAME,STORE_CONT,STORE_PIC1,STORE_PIC2,STORE_PIC3,STORE_FREE_SHIP,STORE_ATM_INFO,"
			+ "STORE_STAT,STORE_STAT_CONT,STORE_STAT_CDATE) VALUES ('S'||STORE_NO_SEQ.NEXTVAL, ?, ?, ?, ?, ?, ?, ?, ?,?, ?, ?, ?, ?, ?, '待審中', null, sysdate)";
	private static final String GET_ALL_STMT = "SELECT * FROM store order by STORE_NO";
	private static final String GET_ONE_STMT = "SELECT STORE_NO,MEM_AC,TAX_ID_NO,WIN_ID_PIC,STORE_PHONE,STORE_ADD,STORE_ADD_LAT,"
			+ "STORE_ADD_LON,STORE_NAME,STORE_CONT,STORE_PIC1,STORE_PIC2,STORE_PIC3,STORE_FREE_SHIP,STORE_ATM_INFO,"
			+ "STORE_STAT,STORE_STAT_CONT,STORE_STAT_CDATE FROM store where STORE_NO = ?";
	private static final String GET_ONE_BY_MEM = "SELECT * FROM store where mem_ac = ?";
	private static final String DELETE = "DELETE FROM store where STORE_NO = ?";
	private static final String UPDATE = "UPDATE store set TAX_ID_NO=?, WIN_ID_PIC=?, STORE_PHONE=? ,STORE_ADD=?,STORE_ADD_LAT=?,STORE_ADD_LON=?,STORE_NAME=?,STORE_CONT=?,STORE_PIC1=?,STORE_PIC2=?,STORE_PIC3=?,STORE_FREE_SHIP=?,STORE_ATM_INFO=?,STORE_STAT=?,STORE_STAT_CDATE=?  where STORE_NO = ? ";
	private static final String UPDATE_STAT ="UPDATE store set STORE_STAT=?,store_stat_cdate=sysdate,STORE_STAT_CONT=? where STORE_NO = ?";
	private static final String SELECT_STAT = "select * from store where store_stat like ?";
	private static final String GET_PROD_BY_STORE = "SELECT * FROM PROD WHERE STORE_NO = ? order by prod_no";
	
	
	@Override
	public void insert(StoreVO storeVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, storeVO.getMem_ac());
			pstmt.setString(2, storeVO.getTax_id_no());
			
			byte [] WIN_ID_PIC = storeVO.getWin_id_pic();
			Blob blobWIN_ID_PIC = con.createBlob();
			
			blobWIN_ID_PIC.setBytes(1, WIN_ID_PIC);
			pstmt.setBlob(3, blobWIN_ID_PIC);

			pstmt.setString(4, storeVO.getStore_phone());
			pstmt.setString(5, storeVO.getStore_add());
			pstmt.setString(6, storeVO.getStore_add_lat());
			pstmt.setString(7, storeVO.getStore_add_lon());
			pstmt.setString(8, storeVO.getStore_name());
			pstmt.setString(9, storeVO.getStore_cont());
			byte [] STORE_PIC1 = storeVO.getStore_pic1();
			Blob blobSTORE_PIC1 = con.createBlob();
			blobSTORE_PIC1.setBytes(1, STORE_PIC1);
			pstmt.setBlob(10, blobSTORE_PIC1);
			
			byte [] STORE_PIC2 = storeVO.getStore_pic2();
			Blob blobSTORE_PIC2 = con.createBlob();
			blobSTORE_PIC2.setBytes(1, STORE_PIC2);
			pstmt.setBlob(11, blobSTORE_PIC2);
			
			byte [] STORE_PIC3 = storeVO.getStore_pic3();
			Blob blobSTORE_PIC3 = con.createBlob();
			blobSTORE_PIC3.setBytes(1, STORE_PIC3);
			pstmt.setBlob(12, blobSTORE_PIC3);
			
			
			pstmt.setInt(13, storeVO.getStore_free_ship());
			pstmt.setString(14, storeVO.getStore_atm_info());
			pstmt.executeUpdate();

			// Handle any driver errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
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
	public void update(StoreVO storeVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);
			

			pstmt.setString(1, storeVO.getTax_id_no());
			
			byte [] WIN_ID_PIC = storeVO.getWin_id_pic();
			Blob blobWIN_ID_PIC = con.createBlob();
			blobWIN_ID_PIC.setBytes(1, WIN_ID_PIC);
			pstmt.setBlob(2, blobWIN_ID_PIC);
			
			
			pstmt.setString(3, storeVO.getStore_phone());
			pstmt.setString(4, storeVO.getStore_add());
			pstmt.setString(5, storeVO.getStore_add_lat());
			pstmt.setString(6, storeVO.getStore_add_lon());
			pstmt.setString(7, storeVO.getStore_name());
			pstmt.setString(8, storeVO.getStore_cont());
			
			byte [] STORE_PIC1 = storeVO.getStore_pic1();
			Blob blobSTORE_PIC1 = con.createBlob();
			blobSTORE_PIC1.setBytes(1, STORE_PIC1);
			pstmt.setBlob(9, blobSTORE_PIC1);
			
			byte [] STORE_PIC2 = storeVO.getStore_pic2();
			Blob blobSTORE_PIC2 = con.createBlob();
			blobSTORE_PIC2.setBytes(1, STORE_PIC2);
			pstmt.setBlob(10, blobSTORE_PIC2);
			
			byte [] STORE_PIC3 = storeVO.getStore_pic3();
			Blob blobSTORE_PIC3 = con.createBlob();
			blobSTORE_PIC3.setBytes(1, STORE_PIC3);
			pstmt.setBlob(11, blobSTORE_PIC3);
			
			pstmt.setInt(12, storeVO.getStore_free_ship());
			pstmt.setString(13, storeVO.getStore_atm_info());
			pstmt.setString(14, storeVO.getStore_stat());
			pstmt.setDate(15, storeVO.getStore_stat_cdate());
			pstmt.setString(16, storeVO.getStore_no());
			pstmt.executeUpdate();
			// Handle any driver errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
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
	public void update_stat(StoreVO storeVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE_STAT);
			
			pstmt.setString(1, storeVO.getStore_stat());
//			pstmt.setDate(2, storeVO.getStore_stat_cdate());
			pstmt.setString(2, storeVO.getStore_stat_cont());
			pstmt.setString(3, storeVO.getStore_no());
			
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
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
	public void delete(String STORE_NO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE);
			
			pstmt.setString(1, STORE_NO);

			pstmt.executeUpdate();

		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
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
	public StoreVO findByPrimaryKey(String STORE_NO) {
		StoreVO storeVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT);
			pstmt.setString(1, STORE_NO);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				storeVO = new StoreVO();
				storeVO.setStore_no(rs.getString("store_no"));
				storeVO.setMem_ac(rs.getString("mem_ac"));
				storeVO.setTax_id_no(rs.getString("tax_id_no"));
				storeVO.setWin_id_pic(rs.getBytes("win_id_pic"));
				storeVO.setStore_phone(rs.getString("store_phone"));
				storeVO.setStore_add(rs.getString("store_add"));
				storeVO.setStore_add_lat(rs.getString("store_add_lat"));
				storeVO.setStore_add_lon(rs.getString("store_add_lon"));
				storeVO.setStore_name(rs.getString("store_name"));
				storeVO.setStore_cont(rs.getString("store_cont"));
				storeVO.setStore_pic1(rs.getBytes("store_pic1"));
				storeVO.setStore_pic2(rs.getBytes("store_pic2"));
				storeVO.setStore_pic3(rs.getBytes("store_pic3"));
				storeVO.setStore_free_ship(rs.getInt("store_free_ship"));
				storeVO.setStore_atm_info(rs.getString("store_atm_info"));
				storeVO.setStore_stat(rs.getString("store_stat"));
				storeVO.setStore_stat_cont(rs.getString("store_stat_cont"));
				storeVO.setStore_stat_cdate(rs.getDate("store_stat_cdate"));
			}

		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
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
		return storeVO;
	}

	@Override
	public StoreVO findByMem(String mem_ac) {
		StoreVO storeVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
	
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_BY_MEM);
			pstmt.setString(1, mem_ac);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				storeVO = new StoreVO();
				storeVO.setStore_no(rs.getString("store_no"));
				storeVO.setMem_ac(rs.getString("mem_ac"));
				storeVO.setTax_id_no(rs.getString("tax_id_no"));
				storeVO.setWin_id_pic(rs.getBytes("win_id_pic"));
				storeVO.setStore_phone(rs.getString("store_phone"));
				storeVO.setStore_add(rs.getString("store_add"));
				storeVO.setStore_add_lat(rs.getString("store_add_lat"));
				storeVO.setStore_add_lon(rs.getString("store_add_lon"));
				storeVO.setStore_name(rs.getString("store_name"));
				storeVO.setStore_cont(rs.getString("store_cont"));
				storeVO.setStore_pic1(rs.getBytes("store_pic1"));
				storeVO.setStore_pic2(rs.getBytes("store_pic2"));
				storeVO.setStore_pic3(rs.getBytes("store_pic3"));
				storeVO.setStore_free_ship(rs.getInt("store_free_ship"));
				storeVO.setStore_stat(rs.getString("store_stat"));
				storeVO.setStore_stat_cont(rs.getString("store_stat_cont"));
				storeVO.setStore_stat_cdate(rs.getDate("store_stat_cdate"));
			}
	
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
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
		return storeVO;
	}

	@Override
	public List<StoreVO> getAll() {
		List<StoreVO> list = new ArrayList<StoreVO>();
		StoreVO storeVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				storeVO = new StoreVO();
				storeVO.setStore_no(rs.getString("store_no"));
				storeVO.setMem_ac(rs.getString("mem_ac"));
				storeVO.setTax_id_no(rs.getString("tax_id_no"));
				storeVO.setWin_id_pic(rs.getBytes("win_id_pic"));
				storeVO.setStore_phone(rs.getString("store_phone"));
				storeVO.setStore_add(rs.getString("store_add"));
				storeVO.setStore_add_lat(rs.getString("store_add_lat"));
				storeVO.setStore_add_lon(rs.getString("store_add_lon"));
				storeVO.setStore_name(rs.getString("store_name"));
				storeVO.setStore_cont(rs.getString("store_cont"));
				storeVO.setStore_pic1(rs.getBytes("store_pic1"));
				storeVO.setStore_pic2(rs.getBytes("store_pic2"));
				storeVO.setStore_pic3(rs.getBytes("store_pic3"));
				storeVO.setStore_free_ship(rs.getInt("store_free_ship"));
				storeVO.setStore_stat(rs.getString("store_stat"));
				storeVO.setStore_stat_cont(rs.getString("store_stat_cont"));
				storeVO.setStore_stat_cdate(rs.getDate("store_stat_cdate"));
				list.add(storeVO); 
				// Store the row in the list
			}
			
			// Handle any driver errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
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
	public List<StoreVO> getAll_stat(String store_stat) {
		List<StoreVO> list = new ArrayList<StoreVO>();
		StoreVO storeVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(SELECT_STAT);
			pstmt.setString(1, store_stat);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				storeVO = new StoreVO();
				storeVO.setStore_no(rs.getString("store_no"));
				storeVO.setMem_ac(rs.getString("mem_ac"));
				storeVO.setTax_id_no(rs.getString("tax_id_no"));
				storeVO.setWin_id_pic(rs.getBytes("win_id_pic"));
				storeVO.setStore_phone(rs.getString("store_phone"));
				storeVO.setStore_add(rs.getString("store_add"));
				storeVO.setStore_add_lat(rs.getString("store_add_lat"));
				storeVO.setStore_add_lon(rs.getString("store_add_lon"));
				storeVO.setStore_name(rs.getString("store_name"));
				storeVO.setStore_cont(rs.getString("store_cont"));
				storeVO.setStore_pic1(rs.getBytes("store_pic1"));
				storeVO.setStore_pic2(rs.getBytes("store_pic2"));
				storeVO.setStore_pic3(rs.getBytes("store_pic3"));
				storeVO.setStore_free_ship(rs.getInt("store_free_ship"));
				storeVO.setStore_stat(rs.getString("store_stat"));
				storeVO.setStore_stat_cont(rs.getString("store_stat_cont"));
				storeVO.setStore_stat_cdate(rs.getDate("store_stat_cdate"));
				list.add(storeVO); 
				// Store the row in the list
			}
			
			// Handle any driver errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
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
	public Set<ProdVO> getProdsByStore_no(String store_no) {
		Set<ProdVO> set = new LinkedHashSet<ProdVO>();
		ProdVO prodVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;		
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_PROD_BY_STORE);
			pstmt.setString(1, store_no);
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
				set.add(prodVO);
			}
			
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
		return set;
	}


}
