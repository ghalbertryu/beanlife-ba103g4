package com.fo_store.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;



public class Fo_storeJDBCDAO implements Fo_storeDAO_interface{
	
		String driver = "oracle.jdbc.driver.OracleDriver";
		String url = "jdbc:oracle:thin:@localhost:1521:XE";
		String userid = "ba103g4";
		String passwd = "123456";
		
		private static final String INSERT_STMT = 
			"INSERT INTO fo_store (store_no,mem_ac,fo_date) VALUES (?, ?, ?)";
		private static final String GET_ALL_STMT = 
			"SELECT store_no,mem_ac,to_char(fo_date,'yyyy-mm-dd') fo_date FROM fo_store order by fo_date";
		private static final String GET_ONE_STMT = 
			"SELECT store_no,mem_ac,to_char(fo_date,'yyyy-mm-dd') fo_date FROM fo_store where store_no = ? and mem_ac =?";
		private static final String DELETE = 
			"DELETE FROM fo_store where store_no = ? and mem_ac =?";
		private static final String UPDATE = 
			"UPDATE fo_store set fo_date = ? where store_no=? and mem_ac=?";
		private static final String GET_COUNT_BY_STORE =
			"SELECT count(*) FROM fo_store WHERE store_no = ?";
		private static final String GET_FO_BY_MEM =
				"SELECT * FROM fo_store WHERE mem_ac = ? order by fo_date desc";

		@Override
		public void insert(Fo_storeVO fo_storeVO) {
			Connection con = null;
			PreparedStatement pstmt = null;

			try {

				Class.forName(driver);
				con = DriverManager.getConnection(url, userid, passwd);
				pstmt = con.prepareStatement(INSERT_STMT);

				pstmt.setString(1, fo_storeVO.getStore_no());
				pstmt.setString(2, fo_storeVO.getMem_ac());
				pstmt.setDate(3, fo_storeVO.getFo_date());

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
		public void update(Fo_storeVO fo_storeVO) {
			Connection con = null;
			PreparedStatement pstmt = null;

			try {

				Class.forName(driver);
				con = DriverManager.getConnection(url, userid, passwd);
				pstmt = con.prepareStatement(UPDATE);
				
				pstmt.setDate(1, fo_storeVO.getFo_date());
				pstmt.setString(2, fo_storeVO.getStore_no());
				pstmt.setString(3, fo_storeVO.getMem_ac());

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
		public void delete(String store_no, String mem_ac) {
			Connection con = null;
			PreparedStatement pstmt = null;

			try {

				Class.forName(driver);
				con = DriverManager.getConnection(url, userid, passwd);
				pstmt = con.prepareStatement(DELETE);

				pstmt.setString(1, store_no);
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
		public Fo_storeVO findByPrimaryKey(String store_no, String mem_ac) {
			Fo_storeVO fo_storeVO = null;
			Connection con = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;

			try {

				Class.forName(driver);
				con = DriverManager.getConnection(url, userid, passwd);
				pstmt = con.prepareStatement(GET_ONE_STMT);

				pstmt.setString(1, store_no);
				pstmt.setString(2, mem_ac);

				rs = pstmt.executeQuery();

				while (rs.next()) {
					fo_storeVO = new Fo_storeVO();
					fo_storeVO.setStore_no(rs.getString("store_no"));
					fo_storeVO.setMem_ac(rs.getString("mem_ac"));
					fo_storeVO.setFo_date(rs.getDate("fo_date"));
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
			return fo_storeVO;
		}

		@Override
		public List<Fo_storeVO> getAll() {
			List<Fo_storeVO> list = new ArrayList<Fo_storeVO>();
			Fo_storeVO fo_storeVO = null;

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
					fo_storeVO = new Fo_storeVO();
					fo_storeVO.setStore_no(rs.getString("store_no"));
					fo_storeVO.setMem_ac(rs.getString("mem_ac"));
					fo_storeVO.setFo_date(rs.getDate("fo_date"));
					list.add(fo_storeVO); // Store the row in the list
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
		public int countByStore(String store_no) {
			Connection con = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			
			Integer count = 0;
			
			try {

				Class.forName(driver);
				con = DriverManager.getConnection(url, userid, passwd);
				pstmt = con.prepareStatement(GET_COUNT_BY_STORE);		
				pstmt.setString(1, store_no);
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
		
		
		@Override
		public List<Fo_storeVO> getByMem(String mem_ac) {
			List<Fo_storeVO> list = new ArrayList<Fo_storeVO>();
			Fo_storeVO fo_storeVO = null;

			Connection con = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;

			try {

				Class.forName(driver);
				con = DriverManager.getConnection(url, userid, passwd);
				pstmt = con.prepareStatement(GET_FO_BY_MEM);
				pstmt.setString(1, mem_ac);
				rs = pstmt.executeQuery();

				while (rs.next()) {
					// empVO 也稱為 Domain objects
					fo_storeVO = new Fo_storeVO();
					fo_storeVO.setStore_no(rs.getString("store_no"));
					fo_storeVO.setMem_ac(rs.getString("mem_ac"));
					fo_storeVO.setFo_date(rs.getDate("fo_date"));
					list.add(fo_storeVO); // Store the row in the list
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
		

		public static void main(String[] args) {

			Fo_storeJDBCDAO dao = new Fo_storeJDBCDAO();

			// 新增
//			Fo_storeVO fo_storeVO = new Fo_storeVO();
//			fo_storeVO.setStore_no("S1000000001");
//			fo_storeVO.setMem_ac("amy39");
//			fo_storeVO.setFo_date(Date.valueOf("2015-01-01"));
//			dao.insert(fo_storeVO);

			// 修改
//			Fo_storeVO fo_storeVO2 = new Fo_storeVO();
//			fo_storeVO2.setStore_no("S1000000001");
//			fo_storeVO2.setMem_ac("amy39");
//			fo_storeVO2.setFo_date(Date.valueOf("2017-01-01"));
//			dao.update(fo_storeVO2);

			

			// 查詢
//			Fo_storeVO fo_storeVO3 = dao.findByPrimaryKey("S1000000001", "amy39");
//			System.out.print(fo_storeVO3.getStore_no() + ",");
//			System.out.print(fo_storeVO3.getMem_ac() + ",");
//			System.out.println(fo_storeVO3.getFo_date() + ",");
//			System.out.println("---------------------");
			
			// 刪除
//			dao.delete("S1000000001", "amy39");

			// 查詢
//			List<Fo_storeVO> list = dao.getAll();
//			for (Fo_storeVO fo_store : list) {
//				System.out.print(fo_store.getStore_no() + ",");
//				System.out.print(fo_store.getMem_ac() + ",");
//				System.out.print(fo_store.getFo_date() + ",");
//				System.out.println();
//			}
			
			//查人數
			System.out.println(dao.countByStore("S1000000001"));
			
			// 查詢
			List<Fo_storeVO> list2 = dao.getByMem("mamabeak");
			for (Fo_storeVO afo_storeVO : list2) {
				System.out.print(afo_storeVO.getStore_no() + ",");
				System.out.print(afo_storeVO.getMem_ac() + ",");
				System.out.print(afo_storeVO.getFo_date() + ",");
				System.out.println();
			}
		}
}
