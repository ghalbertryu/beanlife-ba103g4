package com.fo_act.model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Fo_actJDBCDAO implements Fo_actDAO_interface{
	String driver="oracle.jdbc.driver.OracleDriver";
	String url="jdbc:oracle:thin:@localhost:1521:xe";
	String userid="ba103g4";
	String password="123456";
	
	private static final String INSERT_STMT ="insert into fo_act values(?,?,?)";
	private static final String GET_ALL_STMT ="select * from fo_act";
	private static final String GET_ONE_STMT="select * from fo_act where MEM_AC=? AND ACT_NO=?";
	private static final String DELETE = "delete from fo_act where MEM_AC=? AND ACT_NO=?";
	private static final String UPDATE ="update fo_act set FO_ACT_DATE=? where MEM_AC=? AND ACT_NO=?";
	
	
	@Override
	public void insert(Fo_actVO fo_act_VO) {
		// TODO Auto-generated method stub
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			Class.forName(driver);
	
				con = DriverManager.getConnection(url, userid, password);
				pstmt = con.prepareStatement(INSERT_STMT);
				pstmt.setString(1, fo_act_VO.getMem_ac());
				pstmt.setString(2, fo_act_VO.getAct_no());
				pstmt.setDate(3, fo_act_VO.getFo_act_date());
//				pstmt.setTimestamp(3, fo_act_VO.getFO_ACT_DATE());
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
	public void update(Fo_actVO fo_act_VO) {
		// TODO Auto-generated method stub
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			Class.forName(driver);
	
				con = DriverManager.getConnection(url, userid, password);
				pstmt = con.prepareStatement(UPDATE);
				
				pstmt.setDate(1,fo_act_VO.getFo_act_date());
				pstmt.setString(2, fo_act_VO.getMem_ac());
				pstmt.setString(3, fo_act_VO.getAct_no());
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
	public void delete(String MEM_AC,String ACT_NO) {
		// TODO Auto-generated method stub
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			Class.forName(driver);
	
				con = DriverManager.getConnection(url, userid, password);
				pstmt=con.prepareStatement(DELETE);
				pstmt.setString(1,MEM_AC);
				pstmt.setString(2, ACT_NO);
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
	public Fo_actVO findByPrimaryKey(String MEM_AC,String ACT_NO) {
		// TODO Auto-generated method stub
		 Fo_actVO fo_act_vo=null;
		 Connection con = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			
			try {
				Class.forName(driver);
			
					con = DriverManager.getConnection(url, userid, password);
					pstmt = con.prepareStatement(GET_ONE_STMT);
					pstmt.setString(1, MEM_AC);
					pstmt.setString(2, ACT_NO);
					 rs=pstmt.executeQuery();
					 
					 while(rs.next()){
						 fo_act_vo=new Fo_actVO();
						 fo_act_vo.setMem_ac(rs.getString("MEM_AC"));
						 fo_act_vo.setAct_no(rs.getString("ACT_NO"));
						 fo_act_vo.setFo_act_date(rs.getDate("FO_ACT_DATE"));
						 
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
			return  fo_act_vo;
			
	}
	@Override
	public List<Fo_actVO> getAll() {
		// TODO Auto-generated method stub
		List<Fo_actVO> list=new ArrayList<Fo_actVO>();
		 Fo_actVO fo_act_vo=null;
		 Connection con=null;
			PreparedStatement pstmt=null;
			ResultSet rs=null;
			try {
				Class.forName(driver);

					con = DriverManager.getConnection(url, userid, password);
					pstmt = con.prepareStatement(GET_ALL_STMT);
					rs=pstmt.executeQuery();
					
					while(rs.next()){
						fo_act_vo=new Fo_actVO();
						fo_act_vo.setMem_ac(rs.getString("MEM_AC"));
						fo_act_vo.setAct_no(rs.getString("ACT_NO"));
						fo_act_vo.setFo_act_date(rs.getDate("FO_ACT_DATE"));
						list.add(fo_act_vo);
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
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Fo_actJDBCDAO dao=new Fo_actJDBCDAO();
		
		SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss.SSS");
				       java.util.Date current = new  java.util.Date();
				        System.out.println(sdFormat.format(current));
		
		
		Fo_actVO fo_act_vo1=new Fo_actVO();
		fo_act_vo1.setMem_ac("david88");
		fo_act_vo1.setAct_no("A1000000009");
		//存入年月日
		fo_act_vo1.setFo_act_date(java.sql.Date.valueOf("2017-09-09")); 
		fo_act_vo1.setFo_act_date(new java.sql.Date(new java.util.Date().getTime()));
		//存入年月日 時分秒 須改用java.sql.Timestamp preparestatement用pstmt.setTimestamp vo類別用java.sql.Timestamp
//		fo_act_vo1.setFo_act_date(new java.sql.Timestamp(new java.util.Date().getTime()));
		dao.insert(fo_act_vo1);
		
		Fo_actVO fo_act_vo2=new Fo_actVO();
		fo_act_vo2.setMem_ac("amy89");
		fo_act_vo2.setAct_no("A1000000009");
		fo_act_vo2.setFo_act_date(java.sql.Date.valueOf("2017-09-29"));
		dao.update(fo_act_vo2);
		
		dao.delete("amy89","A1000000009");
		
		Fo_actVO fo_act_vo3=dao.findByPrimaryKey("winter12","A1000000004");
		System.out.print(fo_act_vo3.getMem_ac()+",");
		System.out.print(fo_act_vo3.getAct_no()+",");
		System.out.print(fo_act_vo3.getFo_act_date()+",");
		
		List<Fo_actVO> list=dao.getAll();
		for(Fo_actVO fo_act_vo4: list){
		System.out.print(fo_act_vo4.getMem_ac()+",");
		System.out.print(fo_act_vo4.getAct_no()+",");
		System.out.print(fo_act_vo4.getFo_act_date()+",");
		System.out.println();
		}
		
		
	}
	private static char[] simpleDateFormat(String string) {
		// TODO Auto-generated method stub
		return null;
	}
}
