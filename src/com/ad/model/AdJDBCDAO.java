package com.ad.model;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.gift_data.model.Gift_dataJDBCDAO;

public class AdJDBCDAO implements AdDAO_interface{
	String driver="oracle.jdbc.driver.OracleDriver";
	String url="jdbc:oracle:thin:@localhost:1521:xe";
	String userid="ba103g4";
	String password="123456";
	
	private static final String INSERT_STMT ="insert into ad values('D' || ad_no_seq.nextval,?,?,?,?,?)";
	private static final String GET_ALL_STMT ="select * from ad";
	private static final String GET_ONE_STMT="select * from ad where AD_NO=?";
	private static final String DELETE = "delete from ad where ad_no=?";
	private static final String UPDATE ="update ad set PROD_NO=?,AD_TITLE=?,AD_IMG=?,AD_OP_DATE=?,AD_ED_DATE=? where AD_NO=?";
	private static final String GET_NOW_AD_STMT ="select * from ad where sysdate between  AD_OP_DATE and  AD_ED_DATE ";

	private static final String GET_NOW_AD_NO_IMG_STMT ="select PROD_NO,AD_TITLE,AD_OP_DATE,AD_ED_DATE from AD where sysdate between  AD_OP_DATE and  AD_ED_DATE ";
	private static final String GET_NOW_AD_IMG_STMT ="select AD_IMG from AD where AD_NO =? ";

	

	@Override
	public void insert(AdVO ad_VO) {
		// TODO Auto-generated method stub
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			Class.forName(driver);
			
				con = DriverManager.getConnection(url, userid, password);
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
	public void update(AdVO ad_VO) {
		// TODO Auto-generated method stub
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			Class.forName(driver);
	
				con = DriverManager.getConnection(url, userid, password);
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
	public void delete(String AD_NO) {
		// TODO Auto-generated method stub
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			Class.forName(driver);
		
				con = DriverManager.getConnection(url, userid, password);
				pstmt=con.prepareStatement(DELETE);
				pstmt.setString(1, AD_NO);
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
	public AdVO findByPrimaryKey(String AD_NO) {
		
		AdVO ad_vo=null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			Class.forName(driver);

				con = DriverManager.getConnection(url, userid, password);
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
			Class.forName(driver);
	
				con = DriverManager.getConnection(url, userid, password);
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
	public List<AdVO> getNowAd() {
		// TODO Auto-generated method stub
		List<AdVO> list = new ArrayList<AdVO>();
		AdVO ad_vo = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			Class.forName(driver);

			con = DriverManager.getConnection(url, userid, password);
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
	public List<AdVO> getNowAdNoImg() {
		// TODO Auto-generated method stub
		List<AdVO> list = new ArrayList<AdVO>();
		AdVO ad_vo = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			Class.forName(driver);

			con = DriverManager.getConnection(url, userid, password);
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
	public byte[] getNowAdImg(String ad_no) {
		// TODO Auto-generated method stub
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		byte[] img = null;

		try {
			Class.forName(driver);

			con = DriverManager.getConnection(url, userid, password);
			pstmt = con.prepareStatement(GET_NOW_AD_IMG_STMT);
			pstmt.setString(1, ad_no);

			rs = pstmt.executeQuery();
			
			while (rs.next()){
				img = rs.getBytes("AD_IMG");
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
		return img;
	}

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		AdJDBCDAO dao=new AdJDBCDAO();
		
//		AdVO ad_vo1=new AdVO();
//		ad_vo1.setProd_no("P1000000003");
//		ad_vo1.setAd_title("跳樓大拍賣");
//		byte[] pic1=getByteArray("C:\\Users\\Java\\Desktop\\專題照片\\coffee bean.jpg");
//		ad_vo1.setAd_img(pic1);
//		ad_vo1.setAd_op_date(java.sql.Date.valueOf("2017-09-05"));
//		ad_vo1.setAd_ed_date(java.sql.Date.valueOf("2017-09-07"));
//		dao.insert(ad_vo1);
//		
//		AdVO ad_vo2=new AdVO();
//		ad_vo2.setAd_no("D1000000004");
//		ad_vo2.setProd_no("P1000000003");
//		ad_vo2.setAd_title("跳樓");
//		byte[] pic2=getByteArray("C:\\Users\\Java\\Desktop\\專題照片\\coffee bean.jpg");
//		ad_vo2.setAd_img(pic2);
//		ad_vo2.setAd_op_date(java.sql.Date.valueOf("2017-09-15"));
//		ad_vo2.setAd_ed_date(java.sql.Date.valueOf("2017-09-17"));
//		dao.update(ad_vo2);
//		
//		dao.delete("D1000000004");
//		
//		AdVO ad_vo3=dao.findByPrimaryKey("D1000000003");
//		System.out.println(ad_vo3.getAd_no());
//		System.out.println(ad_vo3.getProd_no());
//		System.out.println(ad_vo3.getAd_title());
//		System.out.println(ad_vo3.getAd_img());
//		System.out.println(ad_vo3.getAd_op_date());
//		System.out.println(ad_vo3.getAd_ed_date());
		
		
		
//		List<AdVO> list=dao.getAll();
//		for(AdVO ad_vo4:list){
//			System.out.print(ad_vo4.getAd_no());
//			System.out.print(ad_vo4.getProd_no());
//			System.out.print(ad_vo4.getAd_title());
//			System.out.print(ad_vo4.getAd_img());
//			System.out.print(ad_vo4.getAd_op_date());
//			System.out.print(ad_vo4.getAd_ed_date());
//			System.out.println();
		
		
		List<AdVO> list=dao.getNowAd();
		for(AdVO ad_vo4:list){
			System.out.print(ad_vo4.getAd_no());
			System.out.print(ad_vo4.getProd_no());
			System.out.print(ad_vo4.getAd_title());
			System.out.print(ad_vo4.getAd_img());
			System.out.print(ad_vo4.getAd_op_date());
			System.out.print(ad_vo4.getAd_ed_date());
			System.out.println();
		}
		
		
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
