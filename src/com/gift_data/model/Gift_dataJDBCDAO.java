package com.gift_data.model;

import java.util.*;
import java.util.Date;

import com.convert_gift.model.Convert_giftVO;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;


public class Gift_dataJDBCDAO implements Gift_dataDAO_interface{
	String driver="oracle.jdbc.driver.OracleDriver";
	String url="jdbc:oracle:thin:@localhost:1521:xe";
	String userid="ba103g4";
	String password="123456";
	
	private static final String INSERT_STMT ="insert into gift_data values('G' || gift_no_seq.nextval,?,?,?,?,?,?)";
	private static final String GET_ALL_STMT ="select * from gift_data";
	private static final String GET_ONE_STMT="select * from gift_data where GIFT_NO=?";
	private static final String DELETE_GIFT_DATA = "delete from gift_data where gift_no=?";
	private static final String UPDATE ="update gift_data set GIFT_NAME=?,GIFT_REMAIN=?,GIFT_CONT=?,GIFT_IMG=?,GIFT_PT=?,GIFT_LAUNCH_DATE=? where gift_no=?";
	
	private static final String DELETE_CONVERT_GIFT= "delete from convert_gift where gift_no=?";
	private static final String GET_CONVERT_GIFT_ByGift_no_STMT="SELECT * FROM convert_gift where GIFT_NO = ? order by GIFT_NO";

	private static final String GET_PRIMARY_KEY_ByOthers="SELECT GIFT_NO FROM gift_data where GIFT_NAME=? AND GIFT_CONT=? AND GIFT_PT=? GIFT_REMAIN=?";
	@Override
	public void insert(Gift_dataVO gift_data_VO) {
		// TODO Auto-generated method stub
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, password);
			pstmt = con.prepareStatement(INSERT_STMT);
			

			pstmt.setString(1,gift_data_VO.getGift_name());
			pstmt.setInt(2,gift_data_VO.getGift_remain());
			pstmt.setString(3,gift_data_VO.getGift_cont());
			byte[] pic=gift_data_VO.getGift_img();
			Blob blobpic=con.createBlob();
			
			
			pstmt.setBlob(4,blobpic);
			blobpic.setBytes(1, pic);
			pstmt.setInt(5,gift_data_VO.getGift_pt());
			pstmt.setDate(6,gift_data_VO.getGift_launch_date());
			pstmt.executeUpdate();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
		
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException("A database error occured. "
					+ e.getMessage());
		
			
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
	public void update(Gift_dataVO gift_data_VO) {
		// TODO Auto-generated method stub
		
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			
				con = DriverManager.getConnection(url, userid, password);
				pstmt = con.prepareStatement(UPDATE);
				
				pstmt.setString(1,gift_data_VO.getGift_name());
				pstmt.setInt(2, gift_data_VO.getGift_remain());
				pstmt.setString(3, gift_data_VO.getGift_cont());
				byte[] img=gift_data_VO.getGift_img();
				Blob blobChange=con.createBlob();
				blobChange.setBytes(1,img);
				pstmt.setBlob(4,blobChange);
				pstmt.setInt(5,gift_data_VO.getGift_pt());
				pstmt.setDate(6,gift_data_VO.getGift_launch_date());
				pstmt.setString(7,gift_data_VO.getGift_no());
				pstmt.executeUpdate();
				con.commit();
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
	public void delete(String GIFT_NO) {
		// TODO Auto-generated method stub
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, password);
			con.setAutoCommit(false);
			pstmt=con.prepareStatement(DELETE_CONVERT_GIFT);
			pstmt.setString(1,GIFT_NO);
			pstmt.executeUpdate();
			pstmt=con.prepareStatement(DELETE_GIFT_DATA);
			pstmt.setString(1,GIFT_NO);
			
			
			pstmt.executeUpdate();
			con.commit();
			con.setAutoCommit(true);

			} catch (SQLException se) {
				
				if (con != null) {
					try {
						
						// 3●設定於當有exception發生時之catch區塊內
						con.rollback();
					} catch (SQLException excep) {
						throw new RuntimeException("rollback error occured. "
								+ excep.getMessage());
					}
				}
				throw new RuntimeException("A database error occured. "
						+ se.getMessage());
			} catch (ClassNotFoundException e) {
				
				throw new RuntimeException("Couldn't load database driver. "
						+ e.getMessage());
				// Handle any SQL errors
			}catch(Exception a){
				
				System.out.println("something wrong");
				a.printStackTrace();
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
	public Gift_dataVO findByPrimaryKey(String GIFT_NO) {
		// TODO Auto-generated method stub
		Gift_dataVO gift_data_vo = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, password);
			pstmt = con.prepareStatement(GET_ONE_STMT);
			pstmt.setString(1, GIFT_NO);
			 rs=pstmt.executeQuery();
			 
			 while(rs.next()){
				 gift_data_vo=new Gift_dataVO();
				 gift_data_vo.setGift_no(rs.getString("GIFT_NO"));
				 gift_data_vo.setGift_name(rs.getString("GIFT_NAME"));
				 gift_data_vo.setGift_remain(rs.getInt("GIFT_REMAIN"));
				 gift_data_vo.setGift_cont(rs.getString("GIFT_CONT"));
				 gift_data_vo.setGift_img(rs.getBytes("GIFT_IMG"));
				 gift_data_vo.setGift_pt(rs.getInt("GIFT_PT"));
				 gift_data_vo.setGift_launch_date(rs.getDate("GIFT_LAUNCH_DATE"));
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
		return gift_data_vo;
		
	}

	@Override
	public List<Gift_dataVO> getAll() {
		// TODO Auto-generated method stub
		List<Gift_dataVO> list=new ArrayList<Gift_dataVO>();
		Gift_dataVO gift_data_vo=null;
		
		Connection con=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		
		try {
			Class.forName(driver);
	
				con = DriverManager.getConnection(url, userid, password);
				pstmt = con.prepareStatement(GET_ALL_STMT);
				rs=pstmt.executeQuery();
				
				while(rs.next()){
					gift_data_vo=new Gift_dataVO();
					gift_data_vo.setGift_no(rs.getString("GIFT_NO"));
					gift_data_vo.setGift_name(rs.getString("GIFT_NAME"));
					gift_data_vo.setGift_remain(rs.getInt("GIFT_REMAIN"));
					gift_data_vo.setGift_cont(rs.getString("GIFT_CONT"));
					gift_data_vo.setGift_img(rs.getBytes("GIFT_IMG"));
					gift_data_vo.setGift_pt(rs.getInt("GIFT_PT"));
					gift_data_vo.setGift_launch_date(rs.getDate("GIFT_LAUNCH_DATE"));
					list.add(gift_data_vo);
					
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
	public Set<Convert_giftVO> getConvert_giftByGift_no(String GIFT_NO) {
		Set<Convert_giftVO> set=new LinkedHashSet<Convert_giftVO>();
		Convert_giftVO convert_giftVO=null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, password);
				pstmt = con.prepareStatement(GET_CONVERT_GIFT_ByGift_no_STMT);
				pstmt.setString(1, GIFT_NO);
				rs=pstmt.executeQuery();
				
				while (rs.next()) {
				
					convert_giftVO=new Convert_giftVO();
					convert_giftVO.setApply_no(rs.getString("APPLY_NO"));
					convert_giftVO.setMem_ac(rs.getString("MEM_AC"));
					convert_giftVO.setApply_name(rs.getString("APPLY_NAME"));
					convert_giftVO.setApply_phone(rs.getString("APPLY_PHONE"));
					convert_giftVO.setGift_no(rs.getString("GIFT_NO"));
					convert_giftVO.setApply_date(rs.getDate("APPLY_DATE"));
					convert_giftVO.setApply_stat(rs.getString("APPLY_STAT"));
					convert_giftVO.setApply_add(rs.getString("APPLY_ADD"));
					convert_giftVO.setSend_date(rs.getDate("SEND_DATE"));
					convert_giftVO.setSend_no(rs.getString("SEND_NO"));
					set.add(convert_giftVO);
					
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
		return set;
		
	}

	
// test	
//	public String getPrimaryKeyByOthers(String gift_name, String gift_cont, int gift_pt, int gift_remain
//			) {
//		Connection con = null;
//		PreparedStatement pstmt = null;
//		ResultSet rs = null;
//		String GIFT_NO=null;
//		try {
//			Class.forName(driver);
//		
//				con = DriverManager.getConnection(url, userid, password);
//				pstmt = con.prepareStatement(GET_PRIMARY_KEY_ByOthers);
//				pstmt.setString(1, gift_name);
//				pstmt.setString(2, gift_cont);
//				pstmt.setInt(3, gift_pt);
//				pstmt.setInt(4, gift_remain);
//				
//				
//				
//				
//				rs=pstmt.executeQuery();
//				while(rs.next()){
//				 GIFT_NO=rs.getString("GIFT_NO");
//					
//					
//				}
//			} catch (SQLException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			
//		} catch (ClassNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}finally {
//			if (rs != null) {
//				try {
//					rs.close();
//				} catch (SQLException se) {
//					se.printStackTrace(System.err);
//				}
//			}
//			if (pstmt != null) {
//				try {
//					pstmt.close();
//				} catch (SQLException se) {
//					se.printStackTrace(System.err);
//				}
//			}
//			if (con != null) {
//				try {
//					con.close();
//				} catch (Exception e) {
//					e.printStackTrace(System.err);
//				}
//			}
//		}	
//		return GIFT_NO;
//	}
//	
//	
	
	
	
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
	
		Gift_dataJDBCDAO dao=new Gift_dataJDBCDAO();
		
		Gift_dataVO gift_data_vo1=new Gift_dataVO();
		gift_data_vo1.setGift_name("鋼杯");
		gift_data_vo1.setGift_remain(100);
		gift_data_vo1.setGift_cont("當兵喝咖啡的好幫手");
		byte[ ]pic1=getByteArray("C:\\Users\\Java\\Desktop\\專題照片\\forcoffee.jpg");
		gift_data_vo1.setGift_img(pic1);
		gift_data_vo1.setGift_pt(5);
		gift_data_vo1.setGift_launch_date(java.sql.Date.valueOf("2017-09-07"));
		dao.insert(gift_data_vo1);
//		System.out.println("ee");
		
//		Gift_dataVO gift_data_vo2=new Gift_dataVO();
//		gift_data_vo2.setGift_name("小小鋼杯");
//		gift_data_vo2.setGift_remain(77);
//		gift_data_vo2.setGift_cont("小鋼杯 大享受");
//		byte[]pic2=getByteArray("C:\\Users\\Java\\Desktop\\專題照片\\a101.jpg");
//		gift_data_vo2.setGift_img(pic2);
//		gift_data_vo2.setGift_pt(5);
//		gift_data_vo2.setGift_launch_date(java.sql.Date.valueOf("2017-04-01"));
//		gift_data_vo2.setGift_no("G1000000006");
//		dao.update(gift_data_vo2);
		
//		dao.delete("G1000000001");
		
		
//		Gift_dataVO gift_data_vo3=dao.findByPrimaryKey("G1000000005");
//		System.out.println(gift_data_vo3.getGift_no());
//		System.out.println(gift_data_vo3.getGift_name());
//		System.out.println(gift_data_vo3.getGift_remain());
//		System.out.println(gift_data_vo3.getGift_cont());
//		System.out.println(gift_data_vo3.getGift_img());
//		System.out.println(gift_data_vo3.getGift_pt());
//		System.out.println(gift_data_vo3.getGift_launch_date());
		
//		List<Gift_dataVO> list=dao.getAll();
//		for(Gift_dataVO gift:list){
//			System.out.print(gift.getGift_no()+",");
//			System.out.print(gift.getGift_name()+",");
//			System.out.print(gift.getGift_remain()+",");
//			System.out.print(gift.getGift_cont()+",");
//			System.out.print(gift.getGift_img()+",");
//			System.out.print(gift.getGift_pt()+",");
//			System.out.print(gift.getGift_launch_date()+",");
//			System.out.println();
//		}
//		
//		Set<Convert_giftVO> set=dao.getConvert_giftByGift_no("G1000000002");
//		for(Convert_giftVO convert:set){
//			System.out.print(convert.getApply_no());
//			System.out.print(convert.getMem_ac());
//			System.out.print(convert.getApply_name());
//			System.out.print(convert.getApply_phone());
//			System.out.print(convert.getGift_no());
//			System.out.print(convert.getApply_date());
//			System.out.print(convert.getApply_stat());
//			System.out.print(convert.getApply_add());
//			System.out.print(convert.getSend_date());
//			System.out.print(convert.getSend_no());
//			System.out.println();
//			
//			
//			
//		}
		
		//test
//		System.out.println("11");
//		String gift_no=dao.getPrimaryKeyByOthers("陶杯", "質感渾厚，適合深度烘焙且口感濃郁的咖啡。",30,30);
//		
//		System.out.println(gift_no);
		
		
	}
	public static byte[] getByteArray(String path) throws IOException{
		FileInputStream fis=new FileInputStream(new File(path));
		ByteArrayOutputStream baos=new ByteArrayOutputStream();
		int i;
		byte [] buffer=new byte [8193];
		if((i=fis.read(buffer))!=-1){
			baos.write(buffer,0,i);
		}
		return baos.toByteArray();
	}


	

	
	
}
