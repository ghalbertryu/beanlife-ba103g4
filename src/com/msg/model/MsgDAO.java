package com.msg.model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;


public class MsgDAO implements MsgDAO_interface {
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
		"INSERT INTO MSG (msg_no, mem_sen, mem_rec, msg_cont, msg_send_date, msg_stat) VALUES ('L'||QA_NO_SEQ.NEXTVAL, ?, ?, ?, sysdate, ?)";
	private static final String GET_ALL_STMT = 
		"SELECT msg_no, mem_sen, mem_rec, msg_cont, to_char(msg_send_date,'yyyy-mm-dd') msg_send_date, msg_stat FROM MSG order by msg_no";
	private static final String GET_ONE_STMT = 
		"SELECT msg_no, mem_sen, mem_rec, msg_cont, to_char(msg_send_date,'yyyy-mm-dd') msg_send_date, msg_stat FROM MSG where msg_no = ?";
	private static final String DELETE = 
		"DELETE FROM MSG where msg_no = ?";
	private static final String UPDATE = 
		"UPDATE MSG set mem_sen=?, mem_rec=?, msg_cont=?, msg_send_date=?, msg_stat=? where msg_no =?";
	private static final String GET_ALLPAIR_BY_MEMSEN = 
			"select mem_sen from msg where (mem_sen = ? or mem_rec = ? ) and msg_stat = '開啟' group by mem_sen";
	private static final String GET_ALLPAIR_BY_MEMREC = 
			"select mem_rec from msg where (mem_sen = ? or mem_rec = ? ) and msg_stat = '開啟' group by mem_rec";
	private static final String GET_ALL_BY_PAIR = 
			"SELECT * FROM MSG where ((mem_sen=? and mem_rec =?) or (mem_rec = ? and mem_sen=?) )and msg_stat = '開啟' order by msg_no";
	
	
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
//			pstmt.setDate(4, msgVO.getMsg_send_date());
			pstmt.setString(4, msgVO.getMsg_stat());


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
			pstmt.setTimestamp(4, new Timestamp(msgVO.getMsg_send_date().getTime()));
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
	
	@Override
	public Set<String> getAllPairByMem(String mem_ac) {
		Set<String> set = new LinkedHashSet<>();
	
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
	
		try {
	
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALLPAIR_BY_MEMSEN);
			pstmt.setString(1, mem_ac);
			pstmt.setString(2, mem_ac);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				set.add(rs.getString("mem_sen"));
			}
			
			pstmt = con.prepareStatement(GET_ALLPAIR_BY_MEMREC);
			pstmt.setString(1, mem_ac);
			pstmt.setString(2, mem_ac);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				set.add(rs.getString("mem_rec"));
			}
		
			
			set.remove(mem_ac);
	
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

	@Override
	public Set<MsgVO> getAllByPair(String mem_ac1, String mem_ac2) {
		Set<MsgVO> set = new LinkedHashSet<MsgVO>();
		MsgVO msgVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_BY_PAIR);
			pstmt.setString(1, mem_ac1);
			pstmt.setString(2, mem_ac2);
			pstmt.setString(3, mem_ac1);
			pstmt.setString(4, mem_ac2);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVO 也稱為 Domain objects
				msgVO = new MsgVO();
				msgVO.setMsg_no(rs.getString("msg_no"));
				msgVO.setMem_sen(rs.getString("mem_sen"));
				msgVO.setMem_rec(rs.getString("mem_rec"));
				msgVO.setMsg_cont(rs.getString("msg_cont"));	
				msgVO.setMsg_send_date(new Date(rs.getTimestamp("msg_send_date").getTime()));	
				msgVO.setMsg_stat(rs.getString("msg_stat"));		
				set.add(msgVO); // Store the row in the list
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
