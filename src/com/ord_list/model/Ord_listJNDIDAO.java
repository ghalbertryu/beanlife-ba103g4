package com.ord_list.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class Ord_listJNDIDAO implements Ord_listDAO_interface {
	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/BA103G4DB");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	private static final String INSERT_STMT = "INSERT INTO Ord_list (ORD_NO,PROD_NO,AMONT) VALUES (?, ?, ?)";
	private static final String GET_ALL_STMT = "SELECT * FROM Ord_list order by ORD_NO";
	private static final String GET_ONE_STMT = "SELECT * FROM Ord_list where ORD_NO = ? and PROD_NO = ?";
	private static final String DELETE = "DELETE FROM Ord_list where ORD_NO = ? and PROD_NO = ?";
	private static final String UPDATE = "UPDATE Ord_list set AMONT=? where ORD_NO = ? and PROD_NO = ? ";

	@Override
	public void insert(Ord_listVO ord_listvo) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);
			pstmt.setString(1, ord_listvo.getOrd_no());
			pstmt.setString(2, ord_listvo.getProd_no());
			pstmt.setInt(3, ord_listvo.getAmont());
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
	public void insertByCon(Ord_listVO ord_listvo, Connection con) {
		
		PreparedStatement pstmt = null;

		try {
			pstmt = con.prepareStatement(INSERT_STMT);
			pstmt.setString(1, ord_listvo.getOrd_no());
			pstmt.setString(2, ord_listvo.getProd_no());
			pstmt.setInt(3, ord_listvo.getAmont());
			pstmt.executeUpdate();
			
		} catch (SQLException se) {
			if (con != null) {
				try {
					// 3●設定於當有exception發生時之catch區塊內
					System.err.print("Transaction is being ");
					System.err.println("rolled back-由-emp");
					con.rollback();
				} catch (SQLException excep) {
					throw new RuntimeException("rollback error occured. "
							+ excep.getMessage());
				}
			}
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
		}

	}

	
	@Override
	public void update(Ord_listVO ord_listvo) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setInt(1, ord_listvo.getAmont());
			pstmt.setString(2, ord_listvo.getOrd_no());
			pstmt.setString(3, ord_listvo.getProd_no());
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
	public void delete(String ord_no, String prod_no) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE);
			pstmt.setString(1, ord_no);
			pstmt.setString(2, prod_no);

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
	public Ord_listVO findByPrimaryKey(String ord_no, String prod_no) {
		Ord_listVO ord_listvo = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT);
			pstmt.setString(1, ord_no);
			pstmt.setString(2, prod_no);

			rs = pstmt.executeQuery();
			while (rs.next()) {
				ord_listvo = new Ord_listVO();
				ord_listvo.setOrd_no(rs.getString("ORD_NO"));
				ord_listvo.setProd_no(rs.getString("PROD_NO"));
				ord_listvo.setAmont(rs.getInt("AMONT"));
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return ord_listvo;
	}

	@Override
	public List<Ord_listVO> getAll() {
		List<Ord_listVO> list = new ArrayList<>();
		Ord_listVO ord_listVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				ord_listVO = new Ord_listVO();
				ord_listVO.setOrd_no(rs.getString("ORD_NO"));
				ord_listVO.setProd_no(rs.getString("PROD_NO"));
				ord_listVO.setAmont(rs.getInt("AMONT"));
				list.add(ord_listVO);
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

		return list;
	}

}
