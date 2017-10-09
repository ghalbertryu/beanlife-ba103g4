package com.like_rev.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class Like_revJDBCDAO implements Like_revDAO_interface {

	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "ba103g4";
	String passwd = "123456";
	
	
	private static final String INSERT_STMT = 
		"INSERT INTO like_rev (rev_no,mem_ac) VALUES (?, ?)";
	private static final String GET_ALL_STMT = 
		"SELECT rev_no,mem_ac FROM like_rev order by rev_no";
	private static final String GET_ONE_STMT = 
		"SELECT rev_no,mem_ac FROM like_rev where rev_no = ? and mem_ac =?";
	private static final String DELETE = 
		"DELETE FROM like_rev where rev_no = ? and mem_ac =?";
	private static final String UPDATE = 
		"";	
	private static final String GET_COUNT_BY_REV =
			"SELECT count(*) FROM like_rev WHERE rev_no = ?";
	private static final String GET_LIKE_BY_MEM =
			"SELECT * FROM like_rev WHERE mem_ac = ?";
	
	@Override
	public void insert(Like_revVO like_revVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, like_revVO.getRev_no());
			pstmt.setString(2, like_revVO.getMem_ac());

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
	public void update(Like_revVO like_revVO) {
	}

	@Override
	public void delete(String rev_no, String mem_ac) {
		
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(DELETE);

			pstmt.setString(1, rev_no);
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
	public Like_revVO findByPrimaryKey(String rev_no, String mem_ac) {
		
		Like_revVO like_revVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, rev_no);
			pstmt.setString(2, mem_ac);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				like_revVO = new Like_revVO();
				like_revVO.setRev_no(rs.getString("rev_no"));
				like_revVO.setMem_ac(rs.getString("mem_ac"));
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
		return like_revVO;
	}

	@Override
	public List<Like_revVO> getAll() {
		
		List<Like_revVO> list = new ArrayList<Like_revVO>();
		Like_revVO like_revVO = null;

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
				like_revVO = new Like_revVO();
				like_revVO.setRev_no(rs.getString("rev_no"));
				like_revVO.setMem_ac(rs.getString("mem_ac"));
				list.add(like_revVO); // Store the row in the list
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
	public List<Like_revVO> getByMem(String mem_ac) {
		
		List<Like_revVO> list = new ArrayList<Like_revVO>();
		Like_revVO like_revVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_LIKE_BY_MEM);
			pstmt.setString(1, mem_ac);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVO 也稱為 Domain objects
				like_revVO = new Like_revVO();
				like_revVO.setRev_no(rs.getString("rev_no"));
				like_revVO.setMem_ac(rs.getString("mem_ac"));
				list.add(like_revVO); // Store the row in the list
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
	public int countByReview(String rev_no) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		Integer count = 0;
		
		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_COUNT_BY_REV);		
			pstmt.setString(1, rev_no);
			rs = pstmt.executeQuery();
	
			while (rs.next()) {
				count = rs.getInt(1);
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
		return count;
	}

	public static void main(String[] args) {

		Like_revJDBCDAO dao = new Like_revJDBCDAO();

		// 新增
//		Like_revVO like_revVO = new Like_revVO();
//		like_revVO.setRev_no("R1000000001");
//		like_revVO.setMem_ac("amy39");
//		dao.insert(like_revVO);

		// 修改


	
		// 查詢
//		Like_revVO like_revVO3 = dao.findByPrimaryKey("R1000000001", "amy39");
//		System.out.print(like_revVO3.getRev_no() + ",");
//		System.out.println(like_revVO3.getMem_ac() + ",");
//		System.out.println("---------------------");
		
		// 刪除
//		dao.delete("R1000000001", "amy39");

		// 查詢
//		List<Like_revVO> list = dao.getAll();
//		for (Like_revVO alike_rev : list) {
//			System.out.print(alike_rev.getRev_no() + ",");
//			System.out.print(alike_rev.getMem_ac() + ",");
//			System.out.println();
//		}
		
		//查人數
		System.out.println(dao.countByReview("R1000000001"));
		
		// 查詢
		List<Like_revVO> list2 = dao.getByMem("mamabeak");
		for (Like_revVO alike_rev : list2) {
			System.out.print(alike_rev.getRev_no() + ",");
			System.out.print(alike_rev.getMem_ac() + ",");
			System.out.println();
		}
	}

}
