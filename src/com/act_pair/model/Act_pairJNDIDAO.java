package com.act_pair.model;

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

public class Act_pairJNDIDAO implements Act_pairDAO_interface{
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
	private static final String INSERT_STMT ="insert into act_pair values(?,?,?,?,?)";
	private static final String GET_ALL_STMT ="select * from act_pair";
	private static final String GET_ONE_STMT="select * from act_pair where ACT_NO=? AND MEM_AC=?";
	private static final String DELETE = "delete from act_pair where ACT_NO=? AND MEM_AC=?";
	private static final String UPDATE ="update act_pair set APPLY_DATE=?,PAY_STATE=?,CHK_STATE=? where ACT_NO=? AND MEM_AC=?";
	
	
	
	@Override
	public void insert(Act_pairVO act_pair_VO) {
		// TODO Auto-generated method stub
		Connection con=null;
		PreparedStatement pstmt=null;
		
		try {
			con=ds.getConnection();
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
			con = ds.getConnection();
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
	public void delete(String ACT_NO, String MEM_AC) {
		// TODO Auto-generated method stub
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = ds.getConnection();
			pstmt=con.prepareStatement(DELETE);
			pstmt.setString(1, ACT_NO);
			pstmt.setString(2, MEM_AC);
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
	public Act_pairVO findByPrimaryKey(String ACT_NO, String MEM_AC) {
		// TODO Auto-generated method stub
		Act_pairVO act_pair_vo=null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = ds.getConnection();
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
				con = ds.getConnection();
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
