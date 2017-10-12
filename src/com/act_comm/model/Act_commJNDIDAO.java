package com.act_comm.model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class Act_commJNDIDAO implements Act_commDAO_interface{
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
	private static final String INSERT_STMT ="insert into act_comm values('C' ||  comm_no_seq.nextval,?,?,?,?,?,?)";
	private static final String GET_ALL_STMT ="select * from act_comm";
	private static final String GET_ONE_STMT="select * from act_comm where COMM_NO=?";
	private static final String DELETE = "delete from act_comm where COMM_NO=?";
	private static final String UPDATE ="update act_comm set ACT_NO=?,MEM_AC=?,COMM_CONT=?,COMM_DATE=?,COMM_REPLY_CONT=?,COMM_REPLY_DATE=? where comm_no=?";
	private static final String UPDATE_RESPONSE_COMM ="update act_comm  set comm_reply_cont=? ,comm_reply_date=? where comm_no=?";
	
	@Override
	public void insert(Act_commVO act_comm_VO) {
		// TODO Auto-generated method stub
		Connection con=null;
		PreparedStatement pstmt=null;
		
		try {
			con=ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);
			
			pstmt.setString(1, act_comm_VO.getAct_no());
			pstmt.setString(2,act_comm_VO.getMem_ac());
			pstmt.setString(3,act_comm_VO.getComm_cont());
//			pstmt.setDate(4,act_comm_VO.getComm_date());
			pstmt.setTimestamp(4,dateToTimestamp(act_comm_VO.getComm_date()));
			pstmt.setString(5,act_comm_VO.getComm_reply_cont());
//			pstmt.setDate(6,act_comm_VO.getComm_reply_date());
			if(act_comm_VO.getComm_reply_date()==null){
				pstmt.setDate(6,act_comm_VO.getComm_reply_date());
			}else{
			pstmt.setTimestamp(6,dateToTimestamp(act_comm_VO.getComm_reply_date()));
			}
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
	public void update_response(String comm_reply_cont, Date comm_reply_date, String comm_no) {
		// TODO Auto-generated method stub
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE_RESPONSE_COMM);
			pstmt.setString(1,comm_reply_cont);
			pstmt.setDate(2,comm_reply_date);
			pstmt.setString(3, comm_no);
			
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
	public void update(Act_commVO act_comm_VO) {
		// TODO Auto-generated method stub
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);
			
			pstmt.setString(1, act_comm_VO.getAct_no());
			pstmt.setString(2,act_comm_VO.getMem_ac());
			pstmt.setString(3,act_comm_VO.getComm_cont());
//			pstmt.setDate(4,act_comm_VO.getComm_date());
			pstmt.setTimestamp(4,dateToTimestamp(act_comm_VO.getComm_date()));
			pstmt.setString(5,act_comm_VO.getComm_reply_cont());
//			pstmt.setDate(6,act_comm_VO.getComm_reply_date());
			if(act_comm_VO.getComm_reply_date()==null){
				pstmt.setDate(6,act_comm_VO.getComm_reply_date());
			}else{
			pstmt.setTimestamp(6,dateToTimestamp(act_comm_VO.getComm_reply_date()));
			}
			pstmt.setString(7, act_comm_VO.getComm_no());
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
	public void delete(String COMM_NO) {
		// TODO Auto-generated method stub
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = ds.getConnection();
			pstmt=con.prepareStatement(DELETE);
			pstmt.setString(1, COMM_NO);
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
	public Act_commVO findByPrimaryKey(String COMM_NO) {
		// TODO Auto-generated method stub
		Act_commVO act_comm_vo=null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT);
			pstmt.setString(1, COMM_NO);
			 rs=pstmt.executeQuery();
			 
			 while(rs.next()){
				 act_comm_vo=new Act_commVO();
				 act_comm_vo.setComm_no(rs.getString("COMM_NO"));
				 act_comm_vo.setAct_no(rs.getString("ACT_NO"));
				 act_comm_vo.setMem_ac(rs.getString("MEM_AC"));
				 act_comm_vo.setComm_cont(rs.getString("COMM_CONT"));
//				 act_comm_vo.setComm_date(rs.getDate("COMM_DATE"));
				 
				 if(rs.getTimestamp("COMM_DATE")==null){
					 act_comm_vo.setComm_date(rs.getDate("COMM_DATE"));
				 }else{
				 act_comm_vo.setComm_date(timestampToDate(rs.getTimestamp("COMM_DATE")));
				 }
				 act_comm_vo.setComm_reply_cont(rs.getString("COMM_REPLY_CONT"));
//				 act_comm_vo.setComm_reply_date(rs.getDate("COMM_REPLY_DATE"));
				 if(rs.getTimestamp("COMM_REPLY_DATE")==null){
					 act_comm_vo.setComm_reply_date(rs.getDate("COMM_REPLY_DATE"));
				 }else{
				 act_comm_vo.setComm_reply_date(timestampToDate(rs.getTimestamp("COMM_REPLY_DATE")));
				 }
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
		return act_comm_vo;
		
		
	}
	@Override
	public List<Act_commVO> getAll() {
		// TODO Auto-generated method stub
		 List<Act_commVO> list=new  ArrayList<Act_commVO>();
		 Act_commVO act_comm_vo=new Act_commVO();
			Connection con=null;
			PreparedStatement pstmt=null;
			ResultSet rs=null;
			try {
				con = ds.getConnection();
				pstmt = con.prepareStatement(GET_ALL_STMT);
				rs=pstmt.executeQuery();
				
				while(rs.next()){
					 act_comm_vo=new Act_commVO();
					 act_comm_vo.setComm_no(rs.getString("COMM_NO"));
					 act_comm_vo.setAct_no(rs.getString("ACT_NO"));
					 act_comm_vo.setMem_ac(rs.getString("MEM_AC"));
					 act_comm_vo.setComm_cont(rs.getString("COMM_CONT"));
//					 act_comm_vo.setComm_date(rs.getDate("COMM_DATE"));
					 
					 if(rs.getTimestamp("COMM_DATE")==null){
						 act_comm_vo.setComm_date(rs.getDate("COMM_DATE"));
					 }else{
					 act_comm_vo.setComm_date(timestampToDate(rs.getTimestamp("COMM_DATE")));
					 }
					 act_comm_vo.setComm_reply_cont(rs.getString("COMM_REPLY_CONT"));
//					 act_comm_vo.setComm_reply_date(rs.getDate("COMM_REPLY_DATE"));
					 if(rs.getTimestamp("COMM_REPLY_DATE")==null){
						 act_comm_vo.setComm_reply_date(rs.getDate("COMM_REPLY_DATE"));
					 }else{
					 act_comm_vo.setComm_reply_date(timestampToDate(rs.getTimestamp("COMM_REPLY_DATE")));
					 }
					list.add(act_comm_vo);
					
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
	
	
	public static java.sql.Date timestampToDate(java.sql.Timestamp timestamp){
		java.util.Date test_timestamp=timestamp;
		java.sql.Date test_date=new java.sql.Date(test_timestamp.getTime());
		return test_date;
	}

	public static java.sql.Timestamp dateToTimestamp(java.sql.Date date){
		
		java.sql.Timestamp timestamp = new java.sql.Timestamp(date.getTime());
		return timestamp;
		
	}
	
}
