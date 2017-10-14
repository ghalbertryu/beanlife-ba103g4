package com.sys_msg.model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Sys_msgJDBCDAO implements Sys_msgDAO_Interface{
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "ba103g4";
	String passwd = "123456";
	
	static String key;	

	private static final String INSERT_STMT = 
		"INSERT INTO SYS_MSG (sys_msg_no,  mem_ac,  msg_cont,  msg_send_date) VALUES ('N'||QA_NO_SEQ.NEXTVAL, ?, ?, ?)";
	private static final String GET_ALL_STMT = 
		"SELECT sys_msg_no,  mem_ac,  msg_cont,  to_char(msg_send_date,'yyyy-mm-dd') msg_send_date FROM SYS_MSG order by sys_msg_no";
	private static final String GET_ONE_STMT = 
		"SELECT sys_msg_no,  mem_ac,  msg_cont,  to_char(msg_send_date,'yyyy-mm-dd') msg_send_date FROM SYS_MSG where sys_msg_no = ?";
	private static final String DELETE = 
		"DELETE FROM SYS_MSG where sys_msg_no = ?";
	private static final String UPDATE = 
		"UPDATE SYS_MSG set mem_ac=?,  msg_cont=?,  msg_send_date=? where sys_msg_no =?";
			
	@Override
	public void insert(Sys_msgVO sys_msgVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		String pk[] = {"sys_msg_no"};

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT_STMT, pk);

			pstmt.setString(1, sys_msgVO.getMem_ac());
			pstmt.setString(2, sys_msgVO.getMsg_cont());
			pstmt.setDate(3, sys_msgVO.getMsg_send_date());

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
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
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
	public void update(Sys_msgVO sys_msgVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(4, sys_msgVO.getSys_msg_no());
			pstmt.setString(1, sys_msgVO.getMem_ac());
			pstmt.setString(2, sys_msgVO.getMsg_cont());
			pstmt.setDate(3, sys_msgVO.getMsg_send_date());

			pstmt.executeUpdate();

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
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
	public void delete(String sys_msg_no) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(DELETE);

			pstmt.setString(1, sys_msg_no);

			pstmt.executeUpdate();

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
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
	public Sys_msgVO findByPrimaryKey(String sys_msg_no) {
		Sys_msgVO sys_msgVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, sys_msg_no);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVo 也稱為 Domain objects
				sys_msgVO = new Sys_msgVO();
				sys_msgVO.setSys_msg_no(rs.getString("sys_msg_no"));
				sys_msgVO.setMem_ac(rs.getString("mem_ac"));
				sys_msgVO.setMsg_cont(rs.getString("msg_cont"));
				sys_msgVO.setMsg_send_date(rs.getDate("msg_send_date"));			
			}

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
			// Handle any SQL errors
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
		return sys_msgVO;
	}

	@Override
	public List<Sys_msgVO> getAll() {
		List<Sys_msgVO> list = new ArrayList<Sys_msgVO>();
		Sys_msgVO sys_msgVO = null;

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
				sys_msgVO = new Sys_msgVO();
				sys_msgVO.setSys_msg_no(rs.getString("sys_msg_no"));
				sys_msgVO.setMem_ac(rs.getString("mem_ac"));
				sys_msgVO.setMsg_cont(rs.getString("msg_cont"));
				sys_msgVO.setMsg_send_date(rs.getDate("msg_send_date"));	
				list.add(sys_msgVO); // Store the row in the list
			}

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
			// Handle any SQL errors
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
	
	public static void main(String[] args) {

		Sys_msgJDBCDAO dao = new Sys_msgJDBCDAO();

		// 新增
		Sys_msgVO sys_msgVO = new Sys_msgVO();
		sys_msgVO.setMem_ac("mamabeak");
		sys_msgVO.setMsg_cont("text123321123321");
		sys_msgVO.setMsg_send_date(Date.valueOf("2005-01-01"));
		
		dao.insert(sys_msgVO);

		

		// 修改
		Sys_msgVO sys_msgVO2 = new Sys_msgVO();
		sys_msgVO2.setSys_msg_no(key);
		sys_msgVO2.setMem_ac("mamabeak");
		sys_msgVO2.setMsg_cont("dsssssssssss");
		sys_msgVO2.setMsg_send_date(Date.valueOf(java.time.LocalDate.now()));
		dao.update(sys_msgVO2);



		// 查詢
		Sys_msgVO sys_msgVO3 = dao.findByPrimaryKey(key);
		System.out.print(sys_msgVO3.getSys_msg_no() + ",");
		System.out.print(sys_msgVO3.getMem_ac() + ",");
		System.out.print(sys_msgVO3.getMsg_cont() + ",");
		System.out.println(sys_msgVO3.getMsg_send_date() + ",");
		System.out.println("---------------------");
		
		
		// 刪除
		dao.delete(key);

		// 查詢
		List<Sys_msgVO> list = dao.getAll();
		for (Sys_msgVO asys_msgVO : list) {
			System.out.print(asys_msgVO.getSys_msg_no() + ",");
			System.out.print(asys_msgVO.getMem_ac() + ",");
			System.out.print(asys_msgVO.getMsg_cont() + ",");
			System.out.print(asys_msgVO.getMsg_send_date() + ",");
			System.out.println();
		}
	}

	@Override
	public Set<Sys_msgVO> getAllByMem(String mem_ac) {
		// TODO Auto-generated method stub
		return null;
	}

}
