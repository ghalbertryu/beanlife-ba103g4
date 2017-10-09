package com.qa.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;


public class QaJNDIDAO implements QaDAO_interface {

	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/BA103G4DB");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
	static String key;

	private static final String INSERT_STMT = 
		"INSERT INTO QA (qa_no, prod_no, mem_ac, qa_cont, qa_date, qa_reply_cont, qa_reply_date) VALUES ('Q'||QA_NO_SEQ.NEXTVAL, ?, ?, ?, ?, ?, ?)";
	private static final String GET_ALL_STMT = 
		"SELECT qa_no, prod_no, mem_ac, qa_cont, to_char(qa_date,'yyyy-mm-dd') qa_date, qa_reply_cont, to_char(qa_reply_date,'yyyy-mm-dd') qa_reply_date FROM QA order by qa_no";
	private static final String GET_ONE_STMT = 
		"SELECT qa_no, prod_no, mem_ac, qa_cont, to_char(qa_date,'yyyy-mm-dd') qa_date, qa_reply_cont, to_char(qa_reply_date,'yyyy-mm-dd') qa_reply_date FROM QA where qa_no = ?";
	private static final String DELETE = 
		"DELETE FROM QA where qa_no = ?";
	private static final String UPDATE = 
		"UPDATE QA set prod_no=?, mem_ac=?, qa_cont=?, qa_date=?, qa_reply_cont=?, qa_reply_date=? where qa_no =?";
	private static final String GET_VO_BY_PROD = 
			"SELECT * FROM QA where prod_no = ? order by qa_no desc";
		
	
	@Override
	public void insert(QaVO qaVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		String pk[] = {"qa_no"};

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT, pk);

			pstmt.setString(1, qaVO.getProd_no());
			pstmt.setString(2, qaVO.getMem_ac());
			pstmt.setString(3, qaVO.getQa_cont());
			pstmt.setDate(4, qaVO.getQa_date());
			pstmt.setString(5, qaVO.getQa_reply_cont());
			pstmt.setDate(6, qaVO.getQa_reply_date());

			pstmt.executeUpdate();
			
			
			//掘取對應的自增主鍵值
			ResultSet rs = pstmt.getGeneratedKeys();
			ResultSetMetaData rsmd = rs.getMetaData();
			int columnCount = rsmd.getColumnCount();
			if (rs.next()) {
				do {
					for (int i = 1; i <= columnCount; i++) {
						key = rs.getString(i);
						System.out.println("自增主鍵值(" + i + ") = " + key);
					}
				} while (rs.next());
			} else {
				System.out.println("NO KEYS WERE GENERATED.");
			}
			rs.close();

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
	public void update(QaVO qaVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(7, qaVO.getQa_no());
			pstmt.setString(1, qaVO.getProd_no());
			pstmt.setString(2, qaVO.getMem_ac());
			pstmt.setString(3, qaVO.getQa_cont());
			pstmt.setDate(4, qaVO.getQa_date());
			pstmt.setString(5, qaVO.getQa_reply_cont());
			pstmt.setDate(6, qaVO.getQa_reply_date());

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
	public void delete(String qa_no) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE);

			pstmt.setString(1, qa_no);

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
	public QaVO findByPrimaryKey(String qa_no) {
		QaVO qaVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, qa_no);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVo 也稱為 Domain objects
				qaVO = new QaVO();
				qaVO.setQa_no(rs.getString("qa_no"));
				qaVO.setProd_no(rs.getString("prod_no"));
				qaVO.setMem_ac(rs.getString("mem_ac"));
				qaVO.setQa_cont(rs.getString("qa_cont"));
				qaVO.setQa_date(rs.getDate("qa_date"));
				qaVO.setQa_reply_cont(rs.getString("qa_reply_cont"));
				qaVO.setQa_reply_date(rs.getDate("qa_reply_date"));
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
		return qaVO;
	}

	@Override
	public List<QaVO> getAll() {
		List<QaVO> list = new ArrayList<QaVO>();
		QaVO qaVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVO 也稱為 Domain objects
				qaVO = new QaVO();
				qaVO.setQa_no(rs.getString("qa_no"));
				qaVO.setProd_no(rs.getString("prod_no"));
				qaVO.setMem_ac(rs.getString("mem_ac"));
				qaVO.setQa_cont(rs.getString("qa_cont"));
				qaVO.setQa_date(rs.getDate("qa_date"));
				qaVO.setQa_reply_cont(rs.getString("qa_reply_cont"));
				qaVO.setQa_reply_date(rs.getDate("qa_reply_date"));
				list.add(qaVO); // Store the row in the list
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
	public List<QaVO> getByProd(String prod_no) {
		List<QaVO> list = new ArrayList<QaVO>();
		QaVO qaVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_VO_BY_PROD);
			pstmt.setString(1, prod_no);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVO 也稱為 Domain objects
				qaVO = new QaVO();
				qaVO.setQa_no(rs.getString("qa_no"));
				qaVO.setProd_no(rs.getString("prod_no"));
				qaVO.setMem_ac(rs.getString("mem_ac"));
				qaVO.setQa_cont(rs.getString("qa_cont"));
				qaVO.setQa_date(rs.getDate("qa_date"));
				qaVO.setQa_reply_cont(rs.getString("qa_reply_cont"));
				qaVO.setQa_reply_date(rs.getDate("qa_reply_date"));
				list.add(qaVO); // Store the row in the list
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
