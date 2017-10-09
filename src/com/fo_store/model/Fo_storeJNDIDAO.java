package com.fo_store.model;

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


public class Fo_storeJNDIDAO implements Fo_storeDAO_interface{
	
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

				con = ds.getConnection();
				pstmt = con.prepareStatement(INSERT_STMT);

				pstmt.setString(1, fo_storeVO.getStore_no());
				pstmt.setString(2, fo_storeVO.getMem_ac());
				pstmt.setDate(3, fo_storeVO.getFo_date());

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
		public void update(Fo_storeVO fo_storeVO) {
			Connection con = null;
			PreparedStatement pstmt = null;

			try {

				con = ds.getConnection();
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

				con = ds.getConnection();
				pstmt = con.prepareStatement(DELETE);

				pstmt.setString(1, store_no);
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
		public Fo_storeVO findByPrimaryKey(String store_no, String mem_ac) {
			Fo_storeVO fo_storeVO = null;
			Connection con = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;

			try {

				con = ds.getConnection();
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

				con = ds.getConnection();
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

				con = ds.getConnection();
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

				con = ds.getConnection();
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
