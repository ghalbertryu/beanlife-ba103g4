package com.act_pair.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ACT_PAIRJDBCDAO implements Act_pairDAO_interface{
	String driver="oracle.jdbc.driver.OracleDriver";
	String url="jdbc:oracle:thin:@localhost:1521:xe";
	String userid="ba103g4";
	String password="123456";
	
	private static final String INSERT_STMT ="insert into act_pair values(?,?,?,?,?)";
	private static final String GET_ALL_STMT ="select * from act_pair";
	private static final String GET_ONE_STMT="select * from act_pair where ACT_NO=? AND MEM_AC=?";
	private static final String DELETE = "delete from act_pair where ACT_NO=? AND MEM_AC=?";
	private static final String UPDATE ="update act_pair set APPLY_DATE=?,PAY_STATE=?,CHK_STATE=? where ACT_NO=? AND MEM_AC=?";
	
	@Override
	public void insert(Act_pairVO act_pair_VO) {
		// TODO Auto-generated method stub
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			Class.forName(driver);
			
				con = DriverManager.getConnection(url, userid, password);
				pstmt = con.prepareStatement(INSERT_STMT);
				pstmt.setString(1,act_pair_VO.getAct_no());
				pstmt.setString(2, act_pair_VO.getMem_ac());
				pstmt.setDate(3,act_pair_VO.getApply_date());
				pstmt.setString(4,act_pair_VO.getPay_state());
				pstmt.setString(5,act_pair_VO.getChk_state());
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
	public void update(Act_pairVO act_pair_VO) {
		// TODO Auto-generated method stub
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			Class.forName(driver);
		
				con = DriverManager.getConnection(url, userid, password);
				pstmt = con.prepareStatement(UPDATE);
				
				pstmt.setDate(1, act_pair_VO.getApply_date());
				pstmt.setString(2, act_pair_VO.getPay_state());
				pstmt.setString(3, act_pair_VO.getChk_state());
				pstmt.setString(4, act_pair_VO.getAct_no());
				pstmt.setString(5, act_pair_VO.getMem_ac());
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
	public void delete(String ACT_NO,String MEM_AC) {
		// TODO Auto-generated method stub
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			Class.forName(driver);
	
				con = DriverManager.getConnection(url, userid, password);
				pstmt=con.prepareStatement(DELETE);
				pstmt.setString(1, ACT_NO);
				pstmt.setString(2, MEM_AC);
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
	public Act_pairVO findByPrimaryKey(String ACT_NO,String MEM_AC) {
		// TODO Auto-generated method stub
		Act_pairVO act_pair_vo=null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			Class.forName(driver);

				con = DriverManager.getConnection(url, userid, password);
				pstmt = con.prepareStatement(GET_ONE_STMT);
				pstmt.setString(1, ACT_NO);
				pstmt.setString(2, MEM_AC);
				rs=pstmt.executeQuery();
				 while(rs.next()){
					 act_pair_vo=new Act_pairVO();
					 act_pair_vo.setAct_no(rs.getString("ACT_NO"));
					 act_pair_vo.setMem_ac(rs.getString("MEM_AC"));
					 act_pair_vo.setApply_date(rs.getDate("APPLY_DATE"));
					 act_pair_vo.setPay_state(rs.getString("PAY_STATE"));
					 act_pair_vo.setChk_state(rs.getString("CHK_STATE"));
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
		return  act_pair_vo;
		
		
		
	}
	@Override
	public List<Act_pairVO> getAll() {
		// TODO Auto-generated method stub
		 List<Act_pairVO> list=new ArrayList<Act_pairVO>();
		 Act_pairVO act_pair_vo=new Act_pairVO();
		 Connection con=null;
			PreparedStatement pstmt=null;
			ResultSet rs=null;
			
			try {
				Class.forName(driver);
	
					con = DriverManager.getConnection(url, userid, password);
					pstmt = con.prepareStatement(GET_ALL_STMT);
					rs=pstmt.executeQuery();
					while(rs.next()){
						 act_pair_vo=new Act_pairVO();
						 act_pair_vo.setAct_no(rs.getString("ACT_NO"));
						 act_pair_vo.setMem_ac(rs.getString("MEM_AC"));
						 act_pair_vo.setApply_date(rs.getDate("APPLY_DATE"));
						 act_pair_vo.setPay_state(rs.getString("PAY_STATE"));
						 act_pair_vo.setChk_state(rs.getString("CHK_STATE"));
						list.add(act_pair_vo);
						
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
	
	public static void main(String[] args){
		ACT_PAIRJDBCDAO dao=new ACT_PAIRJDBCDAO();
		
//		Act_pairVO act_pair_vo1=new Act_pairVO();
//		act_pair_vo1.setAct_no("A1000000002");
//		act_pair_vo1.setMem_ac("tony141");
//		act_pair_vo1.setApply_date(java.sql.Date.valueOf("2017-09-08"));
//		act_pair_vo1.setPay_state("已繳費");
//		act_pair_vo1.setChk_state("未報到");
//		dao.insert(act_pair_vo1);
		
//		Act_pairVO act_pair_vo2=new Act_pairVO();
//		act_pair_vo2.setAct_no("A1000000001");
//		act_pair_vo2.setMem_ac("tony141");
//		act_pair_vo2.setApply_date(java.sql.Date.valueOf("2017-09-08"));
//		act_pair_vo2.setPay_state("不爽繳費");
//		act_pair_vo2.setChk_state("未報到");
//		dao.update(act_pair_vo2);
//		
//		dao.delete("A1000000002", "tony141");
//		
		Act_pairVO act_pair_vo3=dao.findByPrimaryKey("A1000000001","mrbrown");
		System.out.print(act_pair_vo3.getAct_no()+",");
		System.out.print(act_pair_vo3.getMem_ac()+",");
		System.out.print(act_pair_vo3.getApply_date()+",");
		System.out.print(act_pair_vo3.getPay_state()+",");
		System.out.print(act_pair_vo3.getChk_state()+",");
//		
//		 List<Act_pairVO> list=dao.getAll();
//		 for(Act_pairVO act_pair_vo4:list){
//			 System.out.print(act_pair_vo4.getAct_no()+",");
//				System.out.print(act_pair_vo4.getMem_ac()+",");
//				System.out.print(act_pair_vo4.getApply_date()+",");
//				System.out.print(act_pair_vo4.getPay_state()+",");
//				System.out.print(act_pair_vo4.getChk_state()+",");
//				System.out.println();
//			 
//			 
//			 
//		 }
		
		
		
		
	}
	
	
	
	
	
}
