package com.ad.model;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class AdJNDIDAO implements AdDAO_interface{
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
	private static final String INSERT_STMT ="insert into ad values('D' || ad_no_seq.nextval,?,?,?,?,?)";
	private static final String GET_ALL_STMT ="select * from ad";
	private static final String GET_ONE_STMT="select * from ad where AD_NO=?";
	private static final String DELETE = "delete from ad where ad_no=?";
	private static final String UPDATE ="update ad set PROD_NO=?,AD_TITLE=?,AD_IMG=?,AD_OP_DATE=?,AD_ED_DATE=? where AD_NO=?";
	private static final String GET_NOW_AD_STMT ="select * from ad where sysdate between  ad_op_date and  ad_ed_date ";
	private static final String GET_NOW_AD_NO_IMG_STMT ="select AD_NO, PROD_NO,AD_TITLE,AD_OP_DATE,AD_ED_DATE from AD where sysdate between  AD_OP_DATE and  AD_ED_DATE ";
	private static final String GET_NOW_AD_IMG_STMT ="select AD_IMG from AD where AD_NO =? ";

	
	@Override
	public void insert(AdVO ad_VO) {
		// TODO Auto-generated method stub
		Connection con=null;
		PreparedStatement pstmt=null;
		
		try {
			con=ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);
			
			pstmt.setString(1,ad_VO.getProd_no());
			pstmt.setString(2, ad_VO.getAd_title());
			byte[] pic=ad_VO.getAd_img();
			Blob blobpic=con.createBlob();
			blobpic.setBytes(1, pic);
			pstmt.setBlob(3,blobpic);
			pstmt.setDate(4,ad_VO.getAd_op_date());
			pstmt.setDate(5, ad_VO.getAd_ed_date());
		
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
	public void update(AdVO ad_VO) {
		// TODO Auto-generated method stub
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);
			
			pstmt.setString(1,ad_VO.getProd_no());
			pstmt.setString(2, ad_VO.getAd_title());
			byte[] pic=ad_VO.getAd_img();
			Blob blobpic=con.createBlob();
			blobpic.setBytes(1, pic);
			pstmt.setBlob(3,blobpic);
			pstmt.setDate(4,ad_VO.getAd_op_date());
			pstmt.setDate(5, ad_VO.getAd_ed_date());
			pstmt.setString(6, ad_VO.getAd_no());
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
	public void delete(String AD_NO) {
		// TODO Auto-generated method stub
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = ds.getConnection();
			pstmt=con.prepareStatement(DELETE);
			pstmt.setString(1, AD_NO);
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
	public AdVO findByPrimaryKey(String AD_NO) {
		// TODO Auto-generated method stub
		AdVO ad_vo=null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT);
			pstmt.setString(1, AD_NO);
			rs=pstmt.executeQuery();
			
			while(rs.next()){
			 ad_vo=new AdVO();
			 ad_vo.setAd_no(rs.getString("AD_NO"));
			 ad_vo.setProd_no(rs.getString("PROD_NO"));
			 ad_vo.setAd_title(rs.getString("AD_TITLE"));
			 ad_vo.setAd_img(rs.getBytes("AD_IMG"));
			 ad_vo.setAd_op_date(rs.getDate("AD_OP_DATE"));
			 ad_vo.setAd_ed_date(rs.getDate("AD_ED_DATE"));
				
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
		return ad_vo;
		
	}

	@Override
	public List<AdVO> getAll() {
		// TODO Auto-generated method stub
		List<AdVO> list=new ArrayList<AdVO>();
		AdVO ad_vo=null;
		Connection con=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs=pstmt.executeQuery();
			
			while(rs.next()){
				ad_vo=new AdVO();
				 ad_vo.setAd_no(rs.getString("AD_NO"));
				 ad_vo.setProd_no(rs.getString("PROD_NO"));
				 ad_vo.setAd_title(rs.getString("AD_TITLE"));
				 ad_vo.setAd_img(rs.getBytes("AD_IMG"));
				 ad_vo.setAd_op_date(rs.getDate("AD_OP_DATE"));
				 ad_vo.setAd_ed_date(rs.getDate("AD_ED_DATE"));
				 list.add(ad_vo);
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
	public List<AdVO> getNowAd() {
		// TODO Auto-generated method stub
		List<AdVO> list = new ArrayList<AdVO>();
		AdVO ad_vo = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_NOW_AD_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				ad_vo = new AdVO();
				ad_vo.setAd_no(rs.getString("AD_NO"));
				ad_vo.setProd_no(rs.getString("PROD_NO"));
				ad_vo.setAd_title(rs.getString("AD_TITLE"));
				ad_vo.setAd_img(rs.getBytes("AD_IMG"));
				ad_vo.setAd_op_date(rs.getDate("AD_OP_DATE"));
				ad_vo.setAd_ed_date(rs.getDate("AD_ED_DATE"));
				list.add(ad_vo);
			}

		} catch (SQLException e) {
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
	public List<AdVO> getNowAdNoImg() {
		// TODO Auto-generated method stub
		List<AdVO> list = new ArrayList<AdVO>();
		AdVO ad_vo = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_NOW_AD_NO_IMG_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				ad_vo = new AdVO();
				ad_vo.setAd_no(rs.getString("AD_NO"));
				ad_vo.setProd_no(rs.getString("PROD_NO"));
				ad_vo.setAd_title(rs.getString("AD_TITLE"));
				ad_vo.setAd_op_date(rs.getDate("AD_OP_DATE"));
				ad_vo.setAd_ed_date(rs.getDate("AD_ED_DATE"));
				list.add(ad_vo);
			}

		} catch (SQLException e) {
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
	public byte[] getNowAdImg(String ad_no) {
		// TODO Auto-generated method stub
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		byte[] img = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_NOW_AD_IMG_STMT);
			pstmt.setString(1, ad_no);
				
			rs = pstmt.executeQuery();
			while (rs.next()){
				img = rs.getBytes("AD_IMG");
			}


		} catch (SQLException e) {
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
		return img;
	}

}
