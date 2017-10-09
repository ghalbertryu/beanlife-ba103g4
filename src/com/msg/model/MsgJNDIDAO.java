package com.msg.model;

import java.sql.Connection;
import java.sql.Date;
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


public class MsgJNDIDAO implements MsgDAO_interface {
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
		"INSERT INTO MSG (msg_no, mem_sen, mem_rec, msg_cont, msg_send_date, msg_stat) VALUES ('L'||QA_NO_SEQ.NEXTVAL, ?, ?, ?, ?, ?)";
	private static final String GET_ALL_STMT = 
		"SELECT msg_no, mem_sen, mem_rec, msg_cont, to_char(msg_send_date,'yyyy-mm-dd') msg_send_date, msg_stat FROM MSG order by msg_no";
	private static final String GET_ONE_STMT = 
		"SELECT msg_no, mem_sen, mem_rec, msg_cont, to_char(msg_send_date,'yyyy-mm-dd') msg_send_date, msg_stat FROM MSG where msg_no = ?";
	private static final String DELETE = 
		"DELETE FROM MSG where msg_no = ?";
	private static final String UPDATE = 
		"UPDATE MSG set mem_sen=?, mem_rec=?, msg_cont=?, msg_send_date=?, msg_stat=? where msg_no =?";
	
	
	@Override
	public void insert(MsgVO msgVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		String pk[] = {"msg_no"};

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT, pk);

			pstmt.setString(1, msgVO.getMem_sen());
			pstmt.setString(2, msgVO.getMem_rec());
			pstmt.setString(3, msgVO.getMsg_cont());
			pstmt.setDate(4, msgVO.getMsg_send_date());
			pstmt.setString(5, msgVO.getMsg_stat());


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
	public void update(MsgVO msgVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, msgVO.getMem_sen());
			pstmt.setString(2, msgVO.getMem_rec());
			pstmt.setString(3, msgVO.getMsg_cont());
			pstmt.setDate(4, msgVO.getMsg_send_date());
			pstmt.setString(5, msgVO.getMsg_stat());
			pstmt.setString(6, msgVO.getMsg_no());

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
	public void delete(String msg_no) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE);

			pstmt.setString(1, msg_no);

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
	public MsgVO findByPrimaryKey(String msg_no) {
		MsgVO msgVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, msg_no);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVo 也稱為 Domain objects
				msgVO = new MsgVO();
				msgVO.setMsg_no(rs.getString("msg_no"));
				msgVO.setMem_sen(rs.getString("mem_sen"));
				msgVO.setMem_rec(rs.getString("mem_rec"));
				msgVO.setMsg_cont(rs.getString("msg_cont"));	
				msgVO.setMsg_send_date(rs.getDate("msg_send_date"));	
				msgVO.setMsg_stat(rs.getString("msg_stat"));	
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
		return msgVO;
	}

	@Override
	public List<MsgVO> getAll() {
		List<MsgVO> list = new ArrayList<MsgVO>();
		MsgVO msgVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVO 也稱為 Domain objects
				msgVO = new MsgVO();
				msgVO.setMsg_no(rs.getString("msg_no"));
				msgVO.setMem_sen(rs.getString("mem_sen"));
				msgVO.setMem_rec(rs.getString("mem_rec"));
				msgVO.setMsg_cont(rs.getString("msg_cont"));	
				msgVO.setMsg_send_date(rs.getDate("msg_send_date"));	
				msgVO.setMsg_stat(rs.getString("msg_stat"));		
				list.add(msgVO); // Store the row in the list
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
	
	public static void main(String[] args) {

		MsgJNDIDAO dao = new MsgJNDIDAO();

		// 新增
		MsgVO msgVO = new MsgVO();
		msgVO.setMem_sen("mamabeak");
		msgVO.setMem_rec("amy39");
		msgVO.setMsg_cont("hihihihihihi");
		msgVO.setMsg_send_date(Date.valueOf("2005-01-01"));
		msgVO.setMsg_stat("未讀");
		
		dao.insert(msgVO);

		

		// 修改
		MsgVO msgVO2 = new MsgVO();
		msgVO2.setMsg_no(key);
		msgVO2.setMem_sen("mamabeak");
		msgVO2.setMem_rec("amy39");
		msgVO2.setMsg_cont("123123hihihi");
		msgVO2.setMsg_send_date(Date.valueOf(java.time.LocalDate.now()));
		msgVO2.setMsg_stat("已讀");
		dao.update(msgVO2);



		// 查詢
		MsgVO msgVO3 = dao.findByPrimaryKey(key);
		System.out.print(msgVO3.getMsg_no() + ",");
		System.out.print(msgVO3.getMem_sen() + ",");
		System.out.print(msgVO3.getMem_rec() + ",");
		System.out.print(msgVO3.getMsg_cont() + ",");
		System.out.print(msgVO3.getMsg_send_date() + ",");
		System.out.println(msgVO3.getMsg_stat() + ",");
		System.out.println("---------------------");
		
		// 刪除
		dao.delete(key);

		// 查詢
		List<MsgVO> list = dao.getAll();
		for (MsgVO amsgVO : list) {
			System.out.print(amsgVO.getMsg_no() + ",");
			System.out.print(amsgVO.getMem_sen() + ",");
			System.out.print(amsgVO.getMem_rec() + ",");
			System.out.print(amsgVO.getMsg_cont() + ",");
			System.out.print(amsgVO.getMsg_send_date() + ",");
			System.out.println(amsgVO.getMsg_stat() + ",");
			System.out.println();
		}
	}

}
