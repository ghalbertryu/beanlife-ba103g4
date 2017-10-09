package com.mgr.model;

import java.sql.*;
import java.util.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class MgrDAO implements MgrDAO_interface {

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
		"INSERT INTO MGR (MGR_NO,MGR_PWD,MGR_NAME,MGR_GENDER,MGR_BIRTH,MGR_EMAIL,MGR_PHONE,MGR_REG_DATE) VALUES (emp2_seq.NEXTVAL, ?, ?, ?, ?, ?, ?, ?)";
	private static final String GET_ALL_STMT = 
		"SELECT * FROM MGR order by MGR_NO";
	private static final String GET_ONE_STMT = 
		"SELECT * FROM MGR where MGR_NO = ?";
	private static final String DELETE = 
		"DELETE FROM MGR where MGR_NO = ?";
	private static final String UPDATE = 
		"UPDATE MGR set MGR_NO=?, MGR_PWD=?, MGR_NAME=?, MGR_GENDER=?, MGR_BIRTH=?, MGR_EMAIL=?, MGR_PHONE=?, MGR_REG_DATE=? where MGR_NO = ?";

	@Override
	public void insert(MgrVO mgrVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, mgrVO.getMgr_no());
			pstmt.setString(2, mgrVO.getMgr_pwd());
			pstmt.setString(3, mgrVO.getMgr_name());
			pstmt.setString(4, mgrVO.getMgr_gender());
			pstmt.setDate(5, mgrVO.getMgr_birth());
			pstmt.setString(6, mgrVO.getMgr_email());
			pstmt.setString(7, mgrVO.getMgr_phone());
			pstmt.setDate(8, mgrVO.getMgr_reg_date());

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
	public void update(MgrVO mgrVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, mgrVO.getMgr_no());
			pstmt.setString(2, mgrVO.getMgr_pwd());
			pstmt.setString(3, mgrVO.getMgr_name());
			pstmt.setString(4, mgrVO.getMgr_gender());
			pstmt.setDate(5, mgrVO.getMgr_birth());
			pstmt.setString(6, mgrVO.getMgr_email());
			pstmt.setString(7, mgrVO.getMgr_phone());
			pstmt.setDate(8, mgrVO.getMgr_reg_date());

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
	public void delete(String MGR_NO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE);

			pstmt.setString(1, MGR_NO);

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
	public MgrVO findByPrimaryKey(String MGR_NO) {

		MgrVO mgrVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, MGR_NO);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVo 也稱為 Domain objects
				mgrVO = new MgrVO();
				mgrVO.setMgr_no(rs.getString("MGR_NO"));
				mgrVO.setMgr_pwd(rs.getString("MGR_PWD"));
				mgrVO.setMgr_name(rs.getString("MGR_NAME"));
				mgrVO.setMgr_gender(rs.getString("MGR_GENDER"));
				mgrVO.setMgr_birth(rs.getDate("MGR_BIRTH"));
				mgrVO.setMgr_email(rs.getString("MGR_EMAIL"));
				mgrVO.setMgr_phone(rs.getString("MGR_PHONE"));
				mgrVO.setMgr_reg_date(rs.getDate("MGR_REG_DATE"));
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
		return mgrVO;
	}

	@Override
	public List<MgrVO> getAll() {
		List<MgrVO> list = new ArrayList<MgrVO>();
		MgrVO mgrVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVO 也稱為 Domain objects
				mgrVO = new MgrVO();
				mgrVO.setMgr_no(rs.getString("MGR_NO"));
				mgrVO.setMgr_pwd(rs.getString("MGR_PWD"));
				mgrVO.setMgr_name(rs.getString("MGR_NAME"));
				mgrVO.setMgr_gender(rs.getString("MGR_GENDER"));
				mgrVO.setMgr_birth(rs.getDate("MGR_BIRTH"));
				mgrVO.setMgr_email(rs.getString("MGR_EMAIL"));
				mgrVO.setMgr_phone(rs.getString("MGR_PHONE"));
				mgrVO.setMgr_reg_date(rs.getDate("MGR_REG_DATE"));
				list.add(mgrVO); // Store the row in the list
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
