package com.rep_prod.model;

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

public class Rep_prodDAO implements Rep_prodDAO_interface{
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
		"INSERT INTO REP_PROD (prod_no, mem_ac, rep_type, rep_cont, rep_date, rep_stat, rep_stat_cont, stat_date) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
	private static final String GET_ALL_STMT = 
		"SELECT prod_no, mem_ac, rep_type, rep_cont, to_char(rep_date,'yyyy-mm-dd') rep_date, rep_stat, rep_stat_cont, to_char(stat_date,'yyyy-mm-dd') stat_date FROM REP_PROD order by rep_date";
	private static final String GET_ONE_STMT = 
		"SELECT prod_no, mem_ac, rep_type, rep_cont, to_char(rep_date,'yyyy-mm-dd') rep_date, rep_stat, rep_stat_cont, to_char(stat_date,'yyyy-mm-dd') stat_date FROM REP_PROD where prod_no = ? AND mem_ac = ?";
	private static final String DELETE = 
		"DELETE FROM REP_PROD where prod_no = ? AND mem_ac = ?";
	private static final String UPDATE = 
		"UPDATE REP_PROD set rep_type= ?, rep_cont=?, rep_date=?, rep_stat=?, rep_stat_cont=?, stat_date=? where prod_no = ? AND mem_ac = ?";
	
	
	@Override
	public void insert(Rep_prodVO rep_prodVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, rep_prodVO.getProd_no());
			pstmt.setString(2, rep_prodVO.getMem_ac());
			pstmt.setString(3, rep_prodVO.getRep_type());
			pstmt.setString(4, rep_prodVO.getRep_cont());
			pstmt.setDate(5, rep_prodVO.getRep_date());
			pstmt.setString(6, rep_prodVO.getRep_stat());
			pstmt.setString(7, rep_prodVO.getRep_stat_cont());
			pstmt.setDate(8, rep_prodVO.getStat_date());

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
	public void update(Rep_prodVO rep_prodVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(7, rep_prodVO.getProd_no());
			pstmt.setString(8, rep_prodVO.getMem_ac());
			pstmt.setString(1, rep_prodVO.getRep_type());
			pstmt.setString(2, rep_prodVO.getRep_cont());
			pstmt.setDate(3, rep_prodVO.getRep_date());
			pstmt.setString(4, rep_prodVO.getRep_stat());
			pstmt.setString(5, rep_prodVO.getRep_stat_cont());
			pstmt.setDate(6, rep_prodVO.getStat_date());

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
	public Rep_prodVO findByPrimaryKey(String prod_no, String mem_ac) {
		Rep_prodVO rep_prodVO = null;
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
				// empVo 也稱為 Domain objects
				rep_prodVO = new Rep_prodVO();
				rep_prodVO.setProd_no(rs.getString("prod_no"));
				rep_prodVO.setMem_ac(rs.getString("mem_ac"));
				rep_prodVO.setRep_type(rs.getString("rep_type"));
				rep_prodVO.setRep_cont(rs.getString("rep_cont"));
				rep_prodVO.setRep_date(rs.getDate("rep_date"));
				rep_prodVO.setRep_stat(rs.getString("rep_stat"));
				rep_prodVO.setRep_stat_cont(rs.getString("rep_stat_cont"));
				rep_prodVO.setStat_date(rs.getDate("stat_date"));

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
		return rep_prodVO;
	}

	@Override
	public List<Rep_prodVO> getAll() {
		List<Rep_prodVO> list = new ArrayList<Rep_prodVO>();
		Rep_prodVO rep_prodVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVO 也稱為 Domain objects
				rep_prodVO = new Rep_prodVO();
				rep_prodVO.setProd_no(rs.getString("prod_no"));
				rep_prodVO.setMem_ac(rs.getString("mem_ac"));
				rep_prodVO.setRep_type(rs.getString("rep_type"));
				rep_prodVO.setRep_cont(rs.getString("rep_cont"));
				rep_prodVO.setRep_date(rs.getDate("rep_date"));
				rep_prodVO.setRep_stat(rs.getString("rep_stat"));
				rep_prodVO.setRep_stat_cont(rs.getString("rep_stat_cont"));
				rep_prodVO.setStat_date(rs.getDate("stat_date"));
				list.add(rep_prodVO); // Store the row in the list
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
