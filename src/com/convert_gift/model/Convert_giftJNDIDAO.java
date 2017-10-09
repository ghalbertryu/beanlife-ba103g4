package com.convert_gift.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.gift_data.model.Gift_dataVO;

import jdbc.util.CompositeQuery.jdbcUtil_CompositeQuery_Convert_gift;

public class Convert_giftJNDIDAO implements Convert_giftDAO_interface{

	private static DataSource ds=null;
	
	static{
		Context ctx;
		try {
			ctx = new InitialContext();
			ds=(DataSource) ctx.lookup("java:comp/env/jdbc/BA103G4DB");
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
		
	}
	
	private static final String INSERT_STMT ="insert into convert_gift values('V' || apply_no_seq.nextval,?,?,?,?,?,?,?,?,?,?)";
	private static final String GET_ALL_STMT ="select * from convert_gift";
	private static final String GET_ONE_STMT="select * from convert_gift where APPLY_NO=?";
	private static final String DELETE = "delete from convert_gift where APPLY_NO=?";
	private static final String UPDATE ="update convert_gift set MEM_AC=?,APPLY_NAME=?,APPLY_PHONE=?,GIFT_NO=?,GIFT_AMOUNT=?,APPLY_DATE=?,APPLY_STAT=?,APPLY_ADD=?,SEND_DATE=?,SEND_NO=? where APPLY_NO=?";
	//  依主鍵改變兌換申請狀態
	private static final String UPDATE_STATUS="update convert_gift set apply_stat=?,send_no=?,send_date =?where apply_no=?";
	
	@Override
	public void insert(Convert_giftVO convert_gift_VO) {
		// TODO Auto-generated method stub
		Connection con=null;
		PreparedStatement pstmt=null;
		
		try {
			con=ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);
			
			pstmt.setString(1, convert_gift_VO.getMem_ac());
			pstmt.setString(2, convert_gift_VO.getApply_name());
			pstmt.setString(3, convert_gift_VO.getApply_phone());
			pstmt.setString(4, convert_gift_VO.getGift_no());
			pstmt.setInt(5, convert_gift_VO.getGift_amount());
			pstmt.setDate(6, convert_gift_VO.getApply_date());
			pstmt.setString(7, convert_gift_VO.getApply_stat());
			pstmt.setString(8, convert_gift_VO.getApply_add());
			pstmt.setDate(9, convert_gift_VO.getSend_date());
			pstmt.setString(10, convert_gift_VO.getSend_no());
			pstmt.executeUpdate();
			
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if(pstmt!=null){
				try {
					pstmt.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			if(con!=null){
				try {
					con.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}	
		}
		
		
	}

	@Override
	public void update(Convert_giftVO convert_gift_VO) {
		// TODO Auto-generated method stub
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = ds.getConnection();
			
			pstmt = con.prepareStatement(UPDATE);
			
			pstmt.setString(1, convert_gift_VO.getMem_ac());
			pstmt.setString(2, convert_gift_VO.getApply_name());
			pstmt.setString(3, convert_gift_VO.getApply_phone());
			pstmt.setString(4, convert_gift_VO.getGift_no());
			pstmt.setInt(5, convert_gift_VO.getGift_amount());
			pstmt.setDate(6, convert_gift_VO.getApply_date());
			pstmt.setString(7, convert_gift_VO.getApply_stat());
			pstmt.setString(8, convert_gift_VO.getApply_add());
			pstmt.setDate(9, convert_gift_VO.getSend_date());
			pstmt.setString(10, convert_gift_VO.getSend_no());
			pstmt.setString(11, convert_gift_VO.getApply_no());
			pstmt.executeUpdate();
			
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
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
	public void delete(String APPLY_NO) {
		// TODO Auto-generated method stub
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = ds.getConnection();
			pstmt=con.prepareStatement(DELETE);
			pstmt.setString(1, APPLY_NO);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			
			if(pstmt!=null){
				try {
					pstmt.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(con!=null){
				try {
					con.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		}
		
	}

	@Override
	public Convert_giftVO findByPrimaryKey(String APPLY_NO) {
		// TODO Auto-generated method stub
		Convert_giftVO convert_gift_vo=null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT);
			
			pstmt.setString(1, APPLY_NO);
			 rs=pstmt.executeQuery();
			 while(rs.next()){
				 convert_gift_vo=new Convert_giftVO();
				 convert_gift_vo.setApply_no(rs.getString("APPLY_NO"));
				 convert_gift_vo.setMem_ac(rs.getString("MEM_AC"));
				 convert_gift_vo.setApply_name(rs.getString("APPLY_NAME"));
				 convert_gift_vo.setApply_phone(rs.getString("APPLY_PHONE"));
				 convert_gift_vo.setGift_no(rs.getString("GIFT_NO"));
				 convert_gift_vo.setGift_amount(rs.getInt("GIFT_AMOUNT"));
				 convert_gift_vo.setApply_date(rs.getDate("APPLY_DATE"));
				 convert_gift_vo.setApply_stat(rs.getString("APPLY_STAT"));
				 convert_gift_vo.setApply_add(rs.getString("APPLY_ADD"));
				 convert_gift_vo.setSend_date(rs.getDate("SEND_DATE"));
				 convert_gift_vo.setSend_no(rs.getString("SEND_NO"));
			 }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{

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
		return convert_gift_vo;
		
		
		
		
		
	}

	@Override
	public List<Convert_giftVO> getAll() {
		// TODO Auto-generated method stub
		List<Convert_giftVO> list=new ArrayList<Convert_giftVO>();
		Convert_giftVO convert_gift_vo=null;
		Connection con=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		
		
		try {
			
	
				con = ds.getConnection();
				pstmt = con.prepareStatement(GET_ALL_STMT);
				rs=pstmt.executeQuery();
				 while(rs.next()){
					 convert_gift_vo=new Convert_giftVO();
					 convert_gift_vo.setApply_no(rs.getString("APPLY_NO"));
					 convert_gift_vo.setMem_ac(rs.getString("MEM_AC"));
					 convert_gift_vo.setApply_name(rs.getString("APPLY_NAME"));
					 convert_gift_vo.setApply_phone(rs.getString("APPLY_PHONE"));
					 convert_gift_vo.setGift_no(rs.getString("GIFT_NO"));
					 convert_gift_vo.setGift_amount(rs.getInt("GIFT_AMOUNT"));
					 convert_gift_vo.setApply_date(rs.getDate("APPLY_DATE"));
					 convert_gift_vo.setApply_stat(rs.getString("APPLY_STAT"));
					 convert_gift_vo.setApply_add(rs.getString("APPLY_ADD"));
					 convert_gift_vo.setSend_date(rs.getDate("SEND_DATE"));
					 convert_gift_vo.setSend_no(rs.getString("SEND_NO"));
					 list.add(convert_gift_vo);
				 }
	
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
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
	//  依主鍵改變兌換申請狀態
	@Override
	public void updateStatus(String apply_no, String apply_stat,String send_no) {
		// TODO Auto-generated method stub
		Connection con=null;
		PreparedStatement pstmt=null;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE_STATUS);
			pstmt.setString(1, apply_stat);
			
			java.sql.Date send_date;
			if(apply_stat.equals("待出貨")){
				send_date=null;
				send_no=null;
			}else{
				send_date=new java.sql.Date(Calendar.getInstance().getTime().getTime());
			}
			pstmt.setString(2, send_no);
			pstmt.setDate(3, send_date);
			
			pstmt.setString(4, apply_no);
		
			pstmt.executeUpdate();
		
			pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public List<Convert_giftVO> getAll(Map<String, String[]> map) {
		List<Convert_giftVO> list=new ArrayList<Convert_giftVO>();
		Convert_giftVO convert_gift_vo=null;
		Connection con=null;
		ResultSet rs=null;
		PreparedStatement pstmt=null;
		try {
			con = ds.getConnection();
			String finalSQL = "select * from convert_gift "
			          + jdbcUtil_CompositeQuery_Convert_gift.get_WhereCondition(map)
			          ;
		
			pstmt = con.prepareStatement(finalSQL);
			rs=pstmt.executeQuery();
			 while(rs.next()){
				 convert_gift_vo=new Convert_giftVO();
				 convert_gift_vo.setApply_no(rs.getString("APPLY_NO"));
				 convert_gift_vo.setMem_ac(rs.getString("MEM_AC"));
				 convert_gift_vo.setApply_name(rs.getString("APPLY_NAME"));
				 convert_gift_vo.setApply_phone(rs.getString("APPLY_PHONE"));
				 convert_gift_vo.setGift_no(rs.getString("GIFT_NO"));
				 convert_gift_vo.setGift_amount(rs.getInt("GIFT_AMOUNT"));
				 convert_gift_vo.setApply_date(rs.getDate("APPLY_DATE"));
				 convert_gift_vo.setApply_stat(rs.getString("APPLY_STAT"));
				 convert_gift_vo.setApply_add(rs.getString("APPLY_ADD"));
				 convert_gift_vo.setSend_date(rs.getDate("SEND_DATE"));
				 convert_gift_vo.setSend_no(rs.getString("SEND_NO"));
				 list.add(convert_gift_vo);
			 }
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
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
