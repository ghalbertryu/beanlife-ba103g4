package com.sys_msg.model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class Sys_msgDAO implements Sys_msgDAO_Interface{
	
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
		"INSERT INTO SYS_MSG (sys_msg_no,  mem_ac,  msg_cont,  msg_send_date) VALUES ('N'||QA_NO_SEQ.NEXTVAL, ?, ?, sysdate)";
	private static final String GET_ALL_STMT = 
		"SELECT sys_msg_no,  mem_ac,  msg_cont,  to_char(msg_send_date,'yyyy-mm-dd') msg_send_date FROM SYS_MSG order by sys_msg_no";
	private static final String GET_ALL_BY_MEM = 
			"SELECT * FROM SYS_MSG where mem_ac = ? order by sys_msg_no";
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

			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT, pk);

			pstmt.setString(1, sys_msgVO.getMem_ac());
			pstmt.setString(2, sys_msgVO.getMsg_cont());
//			pstmt.setDate(3, sys_msgVO.getMsg_send_date());

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
	public void update(Sys_msgVO sys_msgVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(4, sys_msgVO.getSys_msg_no());
			pstmt.setString(1, sys_msgVO.getMem_ac());
			pstmt.setString(2, sys_msgVO.getMsg_cont());
			pstmt.setTimestamp(3, new Timestamp(sys_msgVO.getMsg_send_date().getTime()));

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
	public void delete(String sys_msg_no) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE);

			pstmt.setString(1, sys_msg_no);

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
	public Sys_msgVO findByPrimaryKey(String sys_msg_no) {
		Sys_msgVO sys_msgVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
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

			con = ds.getConnection();
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
	public Set<Sys_msgVO> getAllByMem(String mem_ac) {
		Set<Sys_msgVO> set = new LinkedHashSet<Sys_msgVO>();
		Sys_msgVO sys_msgVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_BY_MEM);
			pstmt.setString(1, mem_ac);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVO 也稱為 Domain objects
				sys_msgVO = new Sys_msgVO();
				sys_msgVO.setSys_msg_no(rs.getString("sys_msg_no"));
				sys_msgVO.setMem_ac(rs.getString("mem_ac"));
				sys_msgVO.setMsg_cont(rs.getString("msg_cont"));
				sys_msgVO.setMsg_send_date(new Date(rs.getTimestamp("msg_send_date").getTime()));	
				set.add(sys_msgVO); // Store the row in the list
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
		return set;
	}

}
