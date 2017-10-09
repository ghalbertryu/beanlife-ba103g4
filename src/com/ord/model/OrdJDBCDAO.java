package com.ord.model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import com.ord_list.model.Ord_listDAO;
import com.ord_list.model.Ord_listDAO_interface;
import com.ord_list.model.Ord_listJDBCDAO;
import com.ord_list.model.Ord_listVO;
import com.prod.model.ProdDAO;
import com.prod.model.ProdDAO_interface;
import com.prod.model.ProdVO;



public class OrdJDBCDAO implements OrdDAO_interface {
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "ba103g4";
	String passwd = "123456";

	private static final String INSERT_STMT = "INSERT INTO ord (ORD_NO,MEM_AC,SEND_FEE,TOTAL_PAY,ORD_NAME,ORD_PHONE,ORD_ADD,"
			+ "PAY_INFO,ORD_STAT,ORD_DATE,PAY_DATE,PAY_CHK_DATE,SEND_DATE,SEND_ID) VALUES ('O'||ORD_NO_SEQ.NEXTVAL, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	private static final String GET_ALL_STMT = "SELECT * FROM ord order by ORD_DATE desc";
	private static final String GET_ONE_STMT = "SELECT ORD_NO,MEM_AC,SEND_FEE,TOTAL_PAY,ORD_NAME,ORD_PHONE,ORD_ADD,"
			+ "PAY_INFO,ORD_STAT,ORD_DATE,PAY_DATE,PAY_CHK_DATE,SEND_DATE,SEND_ID FROM ord where ORD_NO = ?";
	private static final String DELETE = "DELETE FROM ord where ORD_NO = ?";
	private static final String UPDATE_STAT = "UPDATE ord set ORD_STAT=?, PAY_CHK_DATE=?, SEND_DATE=? ,SEND_ID=? where ORD_NO = ?";
	private static final String GET_ALL_ORDER_LIST = "select * from ord_list where ORD_NO=?";
	private static final String GET_ALL_ORD_BY_MEM = "SELECT * FROM ORD WHERE MEM_AC=? order by ord_no desc";
	private static final String GET_ALL_FROM_DATE = "SELECT * FROM ORD WHERE send_date > ?";

	@Override
	public void insert(OrdVO ordVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, ordVO.getMem_ac());
			pstmt.setInt(2, ordVO.getSend_fee());
			pstmt.setInt(3, ordVO.getTotal_pay());
			pstmt.setString(4, ordVO.getOrd_name());
			pstmt.setString(5, ordVO.getOrd_phone());
			pstmt.setString(6, ordVO.getOrd_add());
			pstmt.setString(7, ordVO.getPay_info());
			pstmt.setString(8, ordVO.getOrd_stat());
			pstmt.setTimestamp(9, (ordVO.getOrd_date()!=null)? new Timestamp(ordVO.getOrd_date().getTime()):null);
			pstmt.setTimestamp(10, (ordVO.getPay_date()!=null)?new Timestamp(ordVO.getPay_date().getTime()):null);
			pstmt.setTimestamp(11, (ordVO.getPay_chk_date()!=null)?new Timestamp(ordVO.getPay_chk_date().getTime()):null);
			pstmt.setTimestamp(12, (ordVO.getSend_date()!=null)?new Timestamp(ordVO.getSend_date().getTime()):null);
			pstmt.setString(13, ordVO.getSend_id());
			pstmt.executeUpdate();
			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
			// Handle any SQL errors
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
	public void update(OrdVO ordVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE_STAT);
			
			pstmt.setString(1, ordVO.getOrd_stat());
			pstmt.setTimestamp(2, (ordVO.getPay_chk_date()!=null)?new Timestamp(ordVO.getPay_chk_date().getTime()):null);
			pstmt.setTimestamp(3, (ordVO.getSend_date()!=null)?new Timestamp(ordVO.getSend_date().getTime()):null);
			pstmt.setString(4, ordVO.getSend_id());
			pstmt.setString(5, ordVO.getOrd_no ());
			pstmt.executeUpdate();
			
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
			// Handle any SQL errors
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
	public void delete(String ord_no) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(DELETE);
			pstmt.setString(1, ord_no);

			pstmt.executeUpdate();
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
	public OrdVO findByPrimaryKey(String ord_no) {
		OrdVO ordVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ONE_STMT);
			pstmt.setString(1, ord_no);
			rs=pstmt.executeQuery();
			while (rs.next()){
				ordVO = new  OrdVO();
				ordVO.setOrd_no(rs.getString("ORD_NO"));
				ordVO.setMem_ac(rs.getString("MEM_AC"));
				ordVO.setSend_fee(rs.getInt("SEND_FEE"));
				ordVO.setTotal_pay(rs.getInt("TOTAL_PAY"));
				ordVO.setOrd_name(rs.getString("ORD_NAME"));
				ordVO.setOrd_phone(rs.getString("ORD_PHONE"));
				ordVO.setOrd_add(rs.getString("ORD_ADD"));
				ordVO.setPay_info(rs.getString("PAY_INFO"));
				ordVO.setOrd_stat(rs.getString("ORD_STAT"));
				ordVO.setOrd_date((rs.getTimestamp("ORD_DATE")!=null)?new Date(rs.getTimestamp("ORD_DATE").getTime()):null);
				ordVO.setPay_date((rs.getTimestamp("PAY_DATE")!=null)?new Date(rs.getTimestamp("PAY_DATE").getTime()):null);
				ordVO.setPay_chk_date((rs.getTimestamp("PAY_CHK_DATE")!=null)?new Date(rs.getTimestamp("PAY_CHK_DATE").getTime()):null);
				ordVO.setSend_date((rs.getTimestamp("SEND_DATE")!=null)?new Date(rs.getTimestamp("SEND_DATE").getTime()):null);
				ordVO.setSend_id(rs.getString("SEND_ID"));
				
			}
			
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
			// Handle any SQL errors
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

		return ordVO;
	}

	@Override
	public Set<OrdVO> getAll() {
		Set<OrdVO> set = new LinkedHashSet<OrdVO>();
		OrdVO ordVO = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();
			while (rs.next()){
				ordVO = new  OrdVO();
				ordVO.setOrd_no(rs.getString("ORD_NO"));
				ordVO.setMem_ac(rs.getString("MEM_AC"));
				ordVO.setSend_fee(rs.getInt("SEND_FEE"));
				ordVO.setTotal_pay(rs.getInt("TOTAL_PAY"));
				ordVO.setOrd_name(rs.getString("ORD_NAME"));
				ordVO.setOrd_phone(rs.getString("ORD_PHONE"));
				ordVO.setOrd_add(rs.getString("ORD_ADD"));
				ordVO.setPay_info(rs.getString("PAY_INFO"));
				ordVO.setOrd_stat(rs.getString("ORD_STAT"));
//				ordVO.setOrd_date(new Date(rs.getTimestamp("ORD_DATE").getTime()));
//				ordVO.setPay_date(new Date(rs.getTimestamp("PAY_DATE").getTime()));
//				ordVO.setPay_chk_date(new Date(rs.getTimestamp("PAY_CHK_DATE").getTime()));
//				ordVO.setSend_date(new Date(rs.getTimestamp("SEND_DATE").getTime()));

				ordVO.setOrd_date((rs.getTimestamp("ORD_DATE")!=null)?new Date(rs.getTimestamp("ORD_DATE").getTime()):null);
				ordVO.setPay_date((rs.getTimestamp("PAY_DATE")!=null)?new Date(rs.getTimestamp("PAY_DATE").getTime()):null);
				ordVO.setPay_chk_date((rs.getTimestamp("PAY_CHK_DATE")!=null)?new Date(rs.getTimestamp("PAY_CHK_DATE").getTime()):null);
				ordVO.setSend_date((rs.getTimestamp("SEND_DATE")!=null)?new Date(rs.getTimestamp("SEND_DATE").getTime()):null);
				ordVO.setSend_id(rs.getString("SEND_ID"));
				set.add(ordVO);
			}
			
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
			// Handle any SQL errors
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
		
		
		return set;
	}
	
	
	@Override
	public Set<OrdVO> getAll(Date date) {
		Set<OrdVO> set = new LinkedHashSet<OrdVO>();
		OrdVO ordVO = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ALL_FROM_DATE);
			pstmt.setDate(1, date);
			rs = pstmt.executeQuery();
			
			while (rs.next()){
				ordVO = new  OrdVO();
				ordVO.setOrd_no(rs.getString("ORD_NO"));
				ordVO.setMem_ac(rs.getString("MEM_AC"));
				ordVO.setSend_fee(rs.getInt("SEND_FEE"));
				ordVO.setTotal_pay(rs.getInt("TOTAL_PAY"));
				ordVO.setOrd_name(rs.getString("ORD_NAME"));
				ordVO.setOrd_phone(rs.getString("ORD_PHONE"));
				ordVO.setOrd_add(rs.getString("ORD_ADD"));
				ordVO.setPay_info(rs.getString("PAY_INFO"));
				ordVO.setOrd_stat(rs.getString("ORD_STAT"));
//				ordVO.setOrd_date(new Date(rs.getTimestamp("ORD_DATE").getTime()));
//				ordVO.setPay_date(new Date(rs.getTimestamp("PAY_DATE").getTime()));
//				ordVO.setPay_chk_date(new Date(rs.getTimestamp("PAY_CHK_DATE").getTime()));
//				ordVO.setSend_date(new Date(rs.getTimestamp("SEND_DATE").getTime()));

				ordVO.setOrd_date((rs.getTimestamp("ORD_DATE")!=null)?new Date(rs.getTimestamp("ORD_DATE").getTime()):null);
				ordVO.setPay_date((rs.getTimestamp("PAY_DATE")!=null)?new Date(rs.getTimestamp("PAY_DATE").getTime()):null);
				ordVO.setPay_chk_date((rs.getTimestamp("PAY_CHK_DATE")!=null)?new Date(rs.getTimestamp("PAY_CHK_DATE").getTime()):null);
				ordVO.setSend_date((rs.getTimestamp("SEND_DATE")!=null)?new Date(rs.getTimestamp("SEND_DATE").getTime()):null);
				ordVO.setSend_id(rs.getString("SEND_ID"));
				set.add(ordVO);
				
			}
			
			
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
			// Handle any SQL errors
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
		return set;
	}
	
	@Override
	public Set<Ord_listVO> getOrd_listByOrd(String ord_no) {
		Set<Ord_listVO> set = new LinkedHashSet<Ord_listVO>();
		Ord_listVO Ord_listVO = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ALL_ORDER_LIST);
			pstmt.setString(1, ord_no);
			rs = pstmt.executeQuery();
			while (rs.next()){
				Ord_listVO = new  Ord_listVO();
				Ord_listVO.setOrd_no(rs.getString("ORD_NO"));
				Ord_listVO.setProd_no(rs.getString("PROD_NO"));
				Ord_listVO.setAmont(rs.getInt("AMONT"));
				set.add(Ord_listVO);
			}
			
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
			// Handle any SQL errors
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
		return set ;
	}

	
	@Override
	public List<OrdVO> getOrdByMem_ac(String mem_ac) {
		List<OrdVO> list = new ArrayList<OrdVO>();
		OrdVO ordVO = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ALL_ORD_BY_MEM);
			pstmt.setString(1, mem_ac);
			rs = pstmt.executeQuery();
			while (rs.next()){
				ordVO = new  OrdVO();
				ordVO.setOrd_no(rs.getString("ORD_NO"));
				ordVO.setMem_ac(rs.getString("MEM_AC"));
				ordVO.setSend_fee(rs.getInt("SEND_FEE"));
				ordVO.setTotal_pay(rs.getInt("TOTAL_PAY"));
				ordVO.setOrd_name(rs.getString("ORD_NAME"));
				ordVO.setOrd_phone(rs.getString("ORD_PHONE"));
				ordVO.setOrd_add(rs.getString("ORD_ADD"));
				ordVO.setPay_info(rs.getString("PAY_INFO"));
				ordVO.setOrd_stat(rs.getString("ORD_STAT"));
				ordVO.setOrd_date((rs.getTimestamp("ORD_DATE")!=null)?new Date(rs.getTimestamp("ORD_DATE").getTime()):null);
				ordVO.setPay_date((rs.getTimestamp("PAY_DATE")!=null)?new Date(rs.getTimestamp("PAY_DATE").getTime()):null);
				ordVO.setPay_chk_date((rs.getTimestamp("PAY_CHK_DATE")!=null)?new Date(rs.getTimestamp("PAY_CHK_DATE").getTime()):null);
				ordVO.setSend_date((rs.getTimestamp("SEND_DATE")!=null)?new Date(rs.getTimestamp("SEND_DATE").getTime()):null);				
				ordVO.setSend_id(rs.getString("SEND_ID"));
				list.add(ordVO);
			}
			
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
			// Handle any SQL errors
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
	

	@Override
	public String insertWithOrd_list(OrdVO ordVO, Set<Ord_listVO> ord_listVOs) {
		
		Connection con = null;
		PreparedStatement pstmt = null;
		String next_ord_no = null;
		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			String pk[] = {"ord_no"};
			// 1●設定於 pstm.executeUpdate()之前
    		con.setAutoCommit(false);
    		// 先新增部門	
			pstmt = con.prepareStatement(INSERT_STMT, pk);
			pstmt.setString(1, ordVO.getMem_ac());
			pstmt.setInt(2, ordVO.getSend_fee());
			pstmt.setInt(3, ordVO.getTotal_pay());
			pstmt.setString(4, ordVO.getOrd_name());
			pstmt.setString(5, ordVO.getOrd_phone());
			pstmt.setString(6, ordVO.getOrd_add());
			pstmt.setString(7, ordVO.getPay_info());
			pstmt.setString(8, ordVO.getOrd_stat());
			pstmt.setTimestamp(9, (ordVO.getOrd_date()!=null)? new Timestamp(ordVO.getOrd_date().getTime()):null);
			pstmt.setTimestamp(10, (ordVO.getPay_date()!=null)?new Timestamp(ordVO.getPay_date().getTime()):null);
			pstmt.setTimestamp(11, (ordVO.getPay_chk_date()!=null)?new Timestamp(ordVO.getPay_chk_date().getTime()):null);
			pstmt.setTimestamp(12, (ordVO.getSend_date()!=null)?new Timestamp(ordVO.getSend_date().getTime()):null);
			pstmt.setString(13, ordVO.getSend_id());
			pstmt.executeUpdate();
			
			/*				pstmt.setString(1, ordVO.getMem_ac());
			pstmt.setInt(2, ordVO.getSend_fee());
			pstmt.setInt(3, ordVO.getTotal_pay());
			pstmt.setString(4, ordVO.getOrd_name());
			pstmt.setString(5, ordVO.getOrd_phone());
			pstmt.setString(6, ordVO.getOrd_add());
			pstmt.setString(7, ordVO.getPay_info());
			pstmt.setString(8, ordVO.getOrd_stat());
			pstmt.setTimestamp(9, (ordVO.getOrd_date()!=null)? new Timestamp(ordVO.getOrd_date().getTime()):null);
			pstmt.setTimestamp(10, (ordVO.getPay_date()!=null)?new Timestamp(ordVO.getPay_date().getTime()):null);
			pstmt.setTimestamp(11, (ordVO.getPay_chk_date()!=null)?new Timestamp(ordVO.getPay_chk_date().getTime()):null);
			pstmt.setTimestamp(12, (ordVO.getSend_date()!=null)?new Timestamp(ordVO.getSend_date().getTime()):null);
			pstmt.setString(13, ordVO.getSend_id());;*/
			
			//掘取對應的自增主鍵值

			ResultSet rs = pstmt.getGeneratedKeys();
			if (rs.next()) {
				next_ord_no = rs.getString(1);
				System.out.println("自增主鍵值= " + next_ord_no +"(剛新增成功的OrdNo)");
			} else {
				System.out.println("未取得自增主鍵值");
			}

			// 再同時新增員工
			//sub sup of prod
			Ord_listDAO_interface dao = new Ord_listDAO();
			ProdDAO_interface dao2 = new ProdDAO();
			
			System.out.println("set.size()-A="+ord_listVOs.size());
			for (Ord_listVO ord_listVO : ord_listVOs) {
				ord_listVO.setOrd_no(next_ord_no);
				dao.insertByCon(ord_listVO,con);
				
				//sub sup of prod
				ProdVO prodVO = dao2.findByPrimaryKey(ord_listVO.getProd_no());
				prodVO.setProd_sup(prodVO.getProd_sup()-ord_listVO.getAmont());
				dao2.updateByCon(prodVO, con);
			}


			// 2●設定於 pstm.executeUpdate()之後
			con.commit();
			con.setAutoCommit(true);
			System.out.println("list.size()-B="+ord_listVOs.size());
			System.out.println("訂單編號" + next_ord_no + ",共有" + ord_listVOs.size()
					+ "訂單項目");
			
			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
			
			// Handle any SQL errors
		} catch (SQLException se) {
			if (con != null) {
				try {
					// 3●設定於當有exception發生時之catch區塊內
					System.err.print("Transaction is being ");
					System.err.println("rolled back-由-dept");
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
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return next_ord_no;
	}
	
	
	@Override
	public void updateCancel(OrdVO ordVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
    		con.setAutoCommit(false);
    		
			pstmt = con.prepareStatement(UPDATE_STAT);
			
			pstmt.setString(1, ordVO.getOrd_stat());
			pstmt.setTimestamp(2, (ordVO.getPay_chk_date()!=null)?new Timestamp(ordVO.getPay_chk_date().getTime()):null);
			pstmt.setTimestamp(3, (ordVO.getSend_date()!=null)?new Timestamp(ordVO.getSend_date().getTime()):null);
			pstmt.setString(4, ordVO.getSend_id());
			pstmt.setString(5, ordVO.getOrd_no ());
			pstmt.executeUpdate();
			
			//add sup of prod
			Set<Ord_listVO> ord_listVOs = getOrd_listByOrd(ordVO.getOrd_no());
			ProdDAO_interface dao = new ProdDAO();
			for (Ord_listVO ord_listVO : ord_listVOs) {
				//sub sup of prod
				ProdVO prodVO = dao.findByPrimaryKey(ord_listVO.getProd_no());
				prodVO.setProd_sup(prodVO.getProd_sup()+ord_listVO.getAmont());
				dao.updateByCon(prodVO, con);
			}
		
			con.commit();
			con.setAutoCommit(true);
			
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
			
			// Handle any SQL errors
		} catch (SQLException se) {
			if (con != null) {
				try {
	
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
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}

	}
	
	

	public static void main(String[] args) {
		OrdJDBCDAO dao = new OrdJDBCDAO();

		 //新增
//		 OrdVO ordVO1 = new OrdVO();
//		 ordVO1.setMem_ac("dantea");
//		 ordVO1.setSend_fee(80);
//		 ordVO1.setTotal_pay(510);
//		 ordVO1.setOrd_name("萎小寶");
//		 ordVO1.setOrd_phone("0918856413");
//		 ordVO1.setOrd_add("中國天地會總部");
//		 ordVO1.setPay_info("C10102345");
//		 ordVO1.setOrd_stat("已確認付款");
//		 java.util.Date O_DATE = new java.util.Date();
//		 java.sql.Date ORD_DATE = new java.sql.Date(O_DATE.getTime());
//		 ordVO1.setOrd_date(ORD_DATE);
//		 java.util.Date P_DATE = new java.util.Date();
//		 java.sql.Date PAY_DATE = new java.sql.Date(P_DATE.getTime());
//		 ordVO1.setPay_date(PAY_DATE);
//		 java.util.Date C_DATE = new java.util.Date();
//		 java.sql.Date CHK_DATE = new java.sql.Date(C_DATE.getTime());
//		 ordVO1.setPay_chk_date(CHK_DATE);
//		 java.util.Date S_DATE = new java.util.Date();
//		 java.sql.Date SEND_DATE = new java.sql.Date(S_DATE.getTime());
//		 ordVO1.setSend_date(SEND_DATE);
//		 ordVO1.setSend_id("1324567970");
//		 dao.insert(ordVO1);

		// 修改
//		OrdVO ordVO2 = new OrdVO();
//		ordVO2.setOrd_name("王八蛋");
//		ordVO2.setOrd_phone("0212454545");
//		ordVO2.setOrd_add("修改地址");
//		ordVO2.setPay_info("修改匯款資訊");
//		
//		ordVO2.setOrd_stat("已出貨");
//		java.util.Date S_DATE = new java.util.Date();
//		java.sql.Date PAY_DATE= new java.sql.Date(S_DATE.getTime());
//		java.sql.Date PAY_CHK_DATE = new java.sql.Date(S_DATE.getTime());
//		
//		java.sql.Date SEND_DATE = new java.sql.Date(S_DATE.getTime());
//		ordVO2.setPay_date(PAY_DATE);
//		ordVO2.setPay_chk_date(PAY_CHK_DATE);
//		ordVO2.setSend_date(SEND_DATE);
//		ordVO2.setSend_id("75757576");
//		ordVO2.setOrd_no("O1000000004");
//		dao.update(ordVO2);
		
		//刪除
//		dao.delete("O1000000012");
		
		//查詢
//		OrdVO ordVO3 = dao.findByPrimaryKey("O1000000011");
//		System.out.println(ordVO3.getOrd_no());
//		System.out.println(ordVO3.getMem_ac());
//		System.out.println(ordVO3.getSend_fee());
//		System.out.println(ordVO3.getTotal_pay());
//		System.out.println(ordVO3.getOrd_name());
//		System.out.println(ordVO3.getOrd_phone());
//		System.out.println(ordVO3.getOrd_add());
//		System.out.println(ordVO3.getPay_info());
//		System.out.println(ordVO3.getOrd_stat());
//		System.out.println(ordVO3.getOrd_date().getTime());
//		System.out.println(ordVO3.getPay_date().getTime());
//		System.out.println(ordVO3.getPay_chk_date().getTime());
//		System.out.println(ordVO3.getSend_date().getTime());
//		System.out.println(ordVO3.getSend_id());
//		System.out.println("---------------------");
//		
		//查詢所有訂單
		Set<OrdVO> set =dao.getAll(new Date(System.currentTimeMillis()-7*24*60*60*1000));
		for(OrdVO aord:set){
			System.out.println(aord.getOrd_no());
			System.out.println(aord.getMem_ac());
			System.out.println(aord.getSend_fee());
			System.out.println(aord.getTotal_pay());
			System.out.println(aord.getOrd_name());
			System.out.println(aord.getOrd_phone());
			System.out.println(aord.getOrd_add());
			System.out.println(aord.getPay_info());
			System.out.println(aord.getOrd_stat());
			System.out.println(aord.getOrd_date());
			System.out.println(aord.getPay_date());
			System.out.println(aord.getPay_chk_date());
			System.out.println(aord.getSend_date());
			System.out.println(aord.getSend_id());
			System.out.println("---------------------");	
		}
		//查詢某單筆訂單的細目
//		Set<Ord_listVO> set1 = dao.getOrd_listByOrd("O1000000011");
//		for(Ord_listVO ordlist:set1){
//			System.out.println(ordlist.getOrd_no());
//			System.out.println(ordlist.getProd_no());
//			System.out.println(ordlist.getAmont());
//		}
		 
		 //
//		 Set<Ord_listVO> set= new LinkedHashSet<Ord_listVO>();
//		 Ord_listVO ord_listVO1 = new Ord_listVO();
//		 ord_listVO1.setProd_no("P1000000008");
//		 ord_listVO1.setAmont(3);
//		 set.add(ord_listVO1);
//		 ord_listVO1 = new Ord_listVO();
//		 ord_listVO1.setProd_no("P1000000003");
//		 ord_listVO1.setAmont(5);
//		 set.add(ord_listVO1);
//		 
//		 OrdVO ordVO = new OrdVO();
//		 ordVO.setMem_ac("dantea");
//		 ordVO.setSend_fee(50);
//		 ordVO.setTotal_pay(500);
//		 
//		 dao.insertWithOrd_list(ordVO, set);
		 
		 
		/////
//		List<OrdVO> list = new ArrayList<>();
//		list = dao.getOrdByMem_ac("amy39");
//		for(OrdVO aord:list){
//			System.out.println(aord.getOrd_no());
//			System.out.println(aord.getMem_ac());
//			System.out.println(aord.getSend_fee());
//			System.out.println(aord.getTotal_pay());
//			System.out.println(aord.getOrd_name());
//			System.out.println(aord.getOrd_phone());
//			System.out.println(aord.getOrd_add());
//			System.out.println(aord.getPay_info());
//			System.out.println(aord.getOrd_stat());
//			System.out.println(aord.getOrd_date());
//			System.out.println(aord.getPay_date());
//			System.out.println(aord.getPay_chk_date());
//			System.out.println(aord.getSend_date());
//			System.out.println(aord.getSend_id());
//			System.out.println("---------------------");
//			
//		}

		 
	}

}
