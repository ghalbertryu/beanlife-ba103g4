package com.gift_data.model;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.convert_gift.model.Convert_giftVO;

public class Gift_dataJNDIDAO implements Gift_dataDAO_interface{

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
	private static final String INSERT_STMT ="insert into gift_data values('G' || gift_no_seq.nextval,?,?,?,?,?,?)";
	private static final String GET_ALL_STMT ="select * from gift_data order by gift_no desc";
	private static final String GET_ONE_STMT="select * from gift_data where GIFT_NO=?";
	private static final String DELETE_GIFT_DATA = "delete from gift_data where gift_no=?";
	private static final String UPDATE ="update gift_data set GIFT_NAME=?,GIFT_REMAIN=?,GIFT_CONT=?,GIFT_IMG=?,GIFT_PT=?,GIFT_LAUNCH_DATE=? where gift_no=?";
	
	private static final String DELETE_CONVERT_GIFT= "delete from convert_gift where gift_no=?";
	private static final String GET_CONVERT_GIFT_ByGift_no_STMT="SELECT * FROM convert_gift where GIFT_NO = ? order by GIFT_NO";
	
	
	@Override
	public void insert(Gift_dataVO gift_data_VO) {
		// TODO Auto-generated method stub
		Connection con=null;
		PreparedStatement pstmt=null;
		
		try {
			con=ds.getConnection();
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
	public void update(Gift_dataVO gift_data_VO) {
		// TODO Auto-generated method stub
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = ds.getConnection();
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
			con = ds.getConnection();
			con.setAutoCommit(false);
			pstmt=con.prepareStatement(DELETE_CONVERT_GIFT);
			pstmt.setString(1,GIFT_NO);
			pstmt.executeUpdate();
			pstmt=con.prepareStatement(DELETE_GIFT_DATA);
			pstmt.setString(1,GIFT_NO);
			
			
			pstmt.executeUpdate();
			con.commit();
			con.setAutoCommit(true);
			
		} catch (SQLException e) {
			
			if (con != null) {
				try {
					
					// 3●設定於當有exception發生時之catch區塊內
					con.rollback();
				} catch (SQLException excep) {
					throw new RuntimeException("rollback error occured. "
							+ excep.getMessage());
				}
			}
		
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
			con = ds.getConnection();
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
			con = ds.getConnection();
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
		Connection con=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		
		try {
			con = ds.getConnection();
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



	
	
	
	
	
	
	

	
	
	
	
}
