package com.fo_prod.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;


public class Fo_prodJNDIDAO implements Fo_prodDAO_interface{

	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/BA103G4DB");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
	private static final String INSERT_STMT = 
		"INSERT INTO fo_prod (prod_no,mem_ac,fo_date) VALUES (?, ?, ?)";
	private static final String GET_ALL_STMT = 
		"SELECT prod_no,mem_ac,to_char(fo_date,'yyyy-mm-dd') fo_date FROM fo_prod order by fo_date";
	private static final String GET_ONE_STMT = 
		"SELECT prod_no,mem_ac,to_char(fo_date,'yyyy-mm-dd') fo_date FROM fo_prod where prod_no = ? and mem_ac =?";
	private static final String DELETE = 
		"DELETE FROM fo_prod where prod_no = ? and mem_ac =?";
	private static final String UPDATE = 
		"UPDATE fo_prod set fo_date = ? where prod_no=? and mem_ac=?";
	private static final String GET_COUNT_BY_PROD =
			"SELECT count(*) FROM FO_PROD WHERE PROD_NO = ?";
	private static final String GET_FO_BY_MEM =
			"SELECT * FROM FO_PROD WHERE mem_ac = ? order by fo_date desc";

	
	@Override
	public void insert(Fo_prodVO fo_prodVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, fo_prodVO.getProd_no());
			pstmt.setString(2, fo_prodVO.getMem_ac());
			pstmt.setDate(3, fo_prodVO.getFo_date());

			pstmt.executeUpdate();

			// Handle any SQL errors
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
	public void update(Fo_prodVO fo_prodVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);
			
			pstmt.setDate(1, fo_prodVO.getFo_date());
			pstmt.setString(2, fo_prodVO.getProd_no());
			pstmt.setString(3, fo_prodVO.getMem_ac());
			
			pstmt.executeUpdate();

			// Handle any driver errors
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
	public void delete(String prod_no, String mem_ac) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE);

			pstmt.setString(1, prod_no);
			pstmt.setString(2, mem_ac);

			pstmt.executeUpdate();

			// Handle any driver errors
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
	public Fo_prodVO findByPrimaryKey(String prod_no, String mem_ac) {
		
		Fo_prodVO fo_prodVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, prod_no);
			pstmt.setString(2, mem_ac);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				fo_prodVO = new Fo_prodVO();
				fo_prodVO.setProd_no(rs.getString("prod_no"));
				fo_prodVO.setMem_ac(rs.getString("mem_ac"));
				fo_prodVO.setFo_date(rs.getDate("fo_date"));
			}

			// Handle any driver errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
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
		return fo_prodVO;
	}

	@Override
	public List<Fo_prodVO> getAll() {
		List<Fo_prodVO> list = new ArrayList<Fo_prodVO>();
		Fo_prodVO fo_prodVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVO 也稱為 Domain objects
				fo_prodVO = new Fo_prodVO();
				fo_prodVO.setProd_no(rs.getString("prod_no"));
				fo_prodVO.setMem_ac(rs.getString("mem_ac"));
				fo_prodVO.setFo_date(rs.getDate("fo_date"));
				list.add(fo_prodVO); // Store the row in the list
			}

			// Handle any driver errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
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
	public int countByProd(String prod_no) {
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		Integer count = 0;
		
		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_COUNT_BY_PROD);
			
			pstmt.setString(1, prod_no);
			rs = pstmt.executeQuery();
	
			while (rs.next()) {
				count = rs.getInt(1);
			}

			// Handle any driver errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
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
		return count;
	}
	
	@Override
	public List<Fo_prodVO> getByMem(String mem_ac) {
		List<Fo_prodVO> list = new ArrayList<Fo_prodVO>();
		Fo_prodVO fo_prodVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_FO_BY_MEM);
			pstmt.setString(1, mem_ac);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVO 也稱為 Domain objects
				fo_prodVO = new Fo_prodVO();
				fo_prodVO.setProd_no(rs.getString("prod_no"));
				fo_prodVO.setMem_ac(rs.getString("mem_ac"));
				fo_prodVO.setFo_date(rs.getDate("fo_date"));
				list.add(fo_prodVO); // Store the row in the list
			}

			// Handle any driver errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
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

}
