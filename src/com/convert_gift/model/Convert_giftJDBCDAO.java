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
import java.util.TreeMap;

import com.gift_data.model.Gift_dataVO;

import jdbc.util.CompositeQuery.jdbcUtil_CompositeQuery_Convert_gift;

public class Convert_giftJDBCDAO implements Convert_giftDAO_interface{
	String driver="oracle.jdbc.driver.OracleDriver";
	String url="jdbc:oracle:thin:@localhost:1521:xe";
	String userid="ba103g4";
	String password="123456";
	
	private static final String INSERT_STMT ="insert into convert_gift values('V' || apply_no_seq.nextval,?,?,?,?,?,?,?,?,?,?)";
	private static final String GET_ALL_STMT ="select * from convert_gift";
	private static final String GET_ONE_STMT="select * from convert_gift where APPLY_NO=?";
	private static final String DELETE = "delete from convert_gift where APPLY_NO=?";
	private static final String UPDATE ="update convert_gift set MEM_AC=?,APPLY_NAME=?,APPLY_PHONE=?,GIFT_NO=?,GIFT_AMOUNT=?,APPLY_DATE=?,APPLY_STAT=?,APPLY_ADD=?,SEND_DATE=?,SEND_NO=? where APPLY_NO=?";
	//  依主鍵改變兌換申請狀態
	private static final String UPDATE_STATUS="update convert_gift set apply_stat=?,send_no=?,send_date =?where apply_no=?";
	
	


	@Override
	public void insert(Convert_giftVO convert_gift_VO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			Class.forName(driver);
		
			con = DriverManager.getConnection(url, userid, password);
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
			
		} catch (ClassNotFoundException e) {
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
		
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			Class.forName(driver);
	
				con = DriverManager.getConnection(url, userid, password);
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
		
			
		} catch (ClassNotFoundException e) {
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
			Class.forName(driver);
			
				con = DriverManager.getConnection(url, userid, password);
				pstmt=con.prepareStatement(DELETE);
				pstmt.setString(1, APPLY_NO);
				pstmt.executeUpdate();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			
			
		} catch (ClassNotFoundException e) {
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
			Class.forName(driver);
		
				con = DriverManager.getConnection(url, userid, password);
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
			
		} catch (ClassNotFoundException e) {
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
		List<Convert_giftVO> list=new ArrayList<Convert_giftVO>();
		Convert_giftVO convert_gift_vo=null;
		
		Connection con=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		
		try {
			Class.forName(driver);
	
				con = DriverManager.getConnection(url, userid, password);
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
			
		} catch (ClassNotFoundException e) {
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

	@Override
	public void updateStatus(String apply_no, String apply_stat,String send_no) {
		// TODO Auto-generated method stub
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			Class.forName(driver);
		
				con = DriverManager.getConnection(url, userid, password);
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
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	@Override
	public List<Convert_giftVO> getAll(Map<String, String[]> map) {
		List<Convert_giftVO> list=new ArrayList<Convert_giftVO>();
		Convert_giftVO convert_gift_vo=null;
		
		Connection con=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		try {
			Class.forName(driver);
	
				con = DriverManager.getConnection(url, userid, password);
				String finalSQL = "select * from convert_gift "
				          + jdbcUtil_CompositeQuery_Convert_gift.get_WhereCondition(map)
				          ;
			System.out.println(finalSQL);
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
		Convert_giftJDBCDAO dao=new Convert_giftJDBCDAO();

//		Convert_giftVO convert_gift1=new Convert_giftVO();
//		convert_gift1.setMem_ac("count123");
//		convert_gift1.setApply_name("陳智遠");
//		convert_gift1.setApply_phone("0925359524");
//		convert_gift1.setGift_no("G1000000004");
//		convert_gift1.setGift_amount(2);
//		convert_gift1.setApply_date(java.sql.Date.valueOf("2017-09-07"));
//		convert_gift1.setApply_stat("待出貨");
//		convert_gift1.setApply_add("彰化縣埔鹽鄉角樹村一號");
//		convert_gift1.setSend_date(null);
//		convert_gift1.setSend_no(null);
//		dao.insert(convert_gift1);
	
//		Convert_giftVO convert_gift2=new Convert_giftVO();
//		convert_gift2.setMem_ac("count123");
//		convert_gift2.setApply_name("陳xx");
//		convert_gift2.setApply_phone("0925359524");
//		convert_gift2.setGift_no("G1000000004");
//		convert_gift2.setGift_amount(5);
//		convert_gift2.setApply_date(java.sql.Date.valueOf("2017-09-07"));
//		convert_gift2.setApply_stat("已出貨");
//		convert_gift2.setApply_add("彰化縣埔鹽鄉角樹村一號");
//		convert_gift2.setSend_date(null);
//		convert_gift2.setSend_no(null);
//		convert_gift2.setApply_no("V1000000006");
//		dao.update(convert_gift2);
//		
//		
//		dao.delete("V1000000006");
//		
//		Convert_giftVO convert_gift3=dao.findByPrimaryKey("V1000000002");
//		System.out.println(convert_gift3.getApply_no());
//		System.out.println(convert_gift3.getMem_ac());
//		System.out.println(convert_gift3.getApply_name());
//		System.out.println(convert_gift3.getApply_phone());
//		System.out.println(convert_gift3.getGift_no());
//		System.out.println(convert_gift3.getGift_amount());
//		System.out.println(convert_gift3.getApply_date());
//		System.out.println(convert_gift3.getApply_stat());
//		System.out.println(convert_gift3.getApply_add());
//		System.out.println(convert_gift3.getSend_date());
//		System.out.println(convert_gift3.getSend_no());
//		
//		List<Convert_giftVO> list=dao.getAll();
//		for(Convert_giftVO convert: list){
//			System.out.print(convert.getApply_no());
//			System.out.print(convert.getMem_ac());
//			System.out.print(convert.getApply_name());
//			System.out.print(convert.getApply_phone());
//			System.out.print(convert.getGift_no());
//			System.out.print(convert.getGift_amount());
//			System.out.print(convert.getApply_date());
//			System.out.print(convert.getApply_stat());
//			System.out.print(convert.getApply_add());
//			System.out.print(convert.getSend_date());
//			System.out.print(convert.getSend_no());
//			System.out.println();
//		}
		
//		dao.updateStatus("V1000000003","待出貨","182548");
Map<String, String[]> map = new TreeMap<String, String[]>();
		
		map.put("APPLY_STAT", new String[] { "全顯示" });
		map.put("action", new String[] { "show_select_list" }); // 注意Map裡面會含有action的key
		map.put("requestURL", new String[] { "ssssss" });
		
		List<Convert_giftVO> list2=dao.getAll(map);
		for(Convert_giftVO convert: list2){
			System.out.print(convert.getApply_no());
			System.out.print(convert.getMem_ac());
			System.out.print(convert.getApply_name());
			System.out.print(convert.getApply_phone());
			System.out.print(convert.getGift_no());
			System.out.print(convert.getGift_amount());
			System.out.print(convert.getApply_date());
			System.out.print(convert.getApply_stat());
			System.out.print(convert.getApply_add());
			System.out.print(convert.getSend_date());
			System.out.print(convert.getSend_no());
			System.out.println();
		}
		
	}



	



	
}
