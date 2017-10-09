package com.act_comm.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Act_commJDBCDAO implements Act_commDAO_interface{

	String driver="oracle.jdbc.driver.OracleDriver";
	String url="jdbc:oracle:thin:@localhost:1521:xe";
	String userid="ba103g4";
	String password="123456";
	
	
	private static final String INSERT_STMT ="insert into act_comm values('C' ||  comm_no_seq.nextval,?,?,?,?,?,?)";
	private static final String GET_ALL_STMT ="select * from act_comm";
	private static final String GET_ONE_STMT="select * from act_comm where COMM_NO=?";
	private static final String DELETE = "delete from act_comm where COMM_NO=?";
	private static final String UPDATE ="update act_comm set ACT_NO=?,MEM_AC=?,COMM_CONT=?,COMM_DATE=?,COMM_REPLY_CONT=?,COMM_REPLY_DATE=? where comm_no=?";
	private static final String UPDATE_RESPONSE_COMM ="update act_comm  set comm_reply_cont=? ,comm_reply_date=? where comm_no=?";
	@Override
	public void insert(Act_commVO act_comm_VO) {
		// TODO Auto-generated method stub
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			Class.forName(driver);
		
				con = DriverManager.getConnection(url, userid, password);
				pstmt = con.prepareStatement(INSERT_STMT);
				
				pstmt.setString(1, act_comm_VO.getAct_no());
				pstmt.setString(2,act_comm_VO.getMem_ac());
				pstmt.setString(3,act_comm_VO.getComm_cont());
				pstmt.setDate(4,act_comm_VO.getComm_date());
				pstmt.setString(5,act_comm_VO.getComm_reply_cont());
				pstmt.setDate(6,act_comm_VO.getComm_reply_date());
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
	public void update_response(String comm_reply_cont, java.sql.Date comm_reply_date, String comm_no) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			Class.forName(driver);
	
				con = DriverManager.getConnection(url, userid, password);
				pstmt = con.prepareStatement(UPDATE_RESPONSE_COMM);
				pstmt.setString(1,comm_reply_cont);
				pstmt.setDate(2,comm_reply_date);
				pstmt.setString(3, comm_no);
				
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
	public void update(Act_commVO act_comm_VO) {
		// TODO Auto-generated method stub
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			Class.forName(driver);
			
				con = DriverManager.getConnection(url, userid, password);
				pstmt = con.prepareStatement(UPDATE);
				
				pstmt.setString(1, act_comm_VO.getAct_no());
				pstmt.setString(2,act_comm_VO.getMem_ac());
				pstmt.setString(3,act_comm_VO.getComm_cont());
				pstmt.setDate(4,act_comm_VO.getComm_date());
				pstmt.setString(5,act_comm_VO.getComm_reply_cont());
				pstmt.setDate(6,act_comm_VO.getComm_reply_date());
				pstmt.setString(7, act_comm_VO.getComm_no());
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
	public void delete(String COMM_NO) {
		// TODO Auto-generated method stub
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			Class.forName(driver);
	
				con = DriverManager.getConnection(url, userid, password);
				pstmt=con.prepareStatement(DELETE);
				pstmt.setString(1, COMM_NO);
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
	public Act_commVO findByPrimaryKey(String COMM_NO) {
		// TODO Auto-generated method stub
		Act_commVO act_comm_vo=null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			Class.forName(driver);

				con = DriverManager.getConnection(url, userid, password);
				pstmt = con.prepareStatement(GET_ONE_STMT);
				pstmt.setString(1, COMM_NO);
				 rs=pstmt.executeQuery();
				 
				 while(rs.next()){
					 act_comm_vo=new Act_commVO();
					 act_comm_vo.setComm_no(rs.getString("COMM_NO"));
					 act_comm_vo.setAct_no(rs.getString("ACT_NO"));
					 act_comm_vo.setMem_ac(rs.getString("MEM_AC"));
					 act_comm_vo.setComm_cont(rs.getString("COMM_CONT"));
					 act_comm_vo.setComm_date(rs.getDate("COMM_DATE"));
					 act_comm_vo.setComm_reply_cont(rs.getString("COMM_REPLY_CONT"));
					 act_comm_vo.setComm_reply_date(rs.getDate("COMM_REPLY_DATE"));
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
				Class.forName(driver);
	
					con = DriverManager.getConnection(url, userid, password);
					pstmt = con.prepareStatement(GET_ALL_STMT);
					rs=pstmt.executeQuery();
					
					while(rs.next()){
						 act_comm_vo=new Act_commVO();
						 act_comm_vo.setComm_no(rs.getString("COMM_NO"));
						 act_comm_vo.setAct_no(rs.getString("ACT_NO"));
						 act_comm_vo.setMem_ac(rs.getString("MEM_AC"));
						 act_comm_vo.setComm_cont(rs.getString("COMM_CONT"));
						 act_comm_vo.setComm_date(rs.getDate("COMM_DATE"));
						 act_comm_vo.setComm_reply_cont(rs.getString("COMM_REPLY_CONT"));
						 act_comm_vo.setComm_reply_date(rs.getDate("COMM_REPLY_DATE"));
						list.add(act_comm_vo);
						
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
		
		Act_commJDBCDAO dao=new 	Act_commJDBCDAO();
//		Act_commVO act_comm_vo1=new Act_commVO();
//		act_comm_vo1.setAct_no("A1000000005");
//		act_comm_vo1.setMem_ac("starter9244");
//		act_comm_vo1.setComm_cont("bababa~~");
//		act_comm_vo1.setComm_date(java.sql.Date.valueOf("2017-09-08"));
//		act_comm_vo1.setComm_reply_cont("haha~");
//		act_comm_vo1.setComm_reply_date(java.sql.Date.valueOf("2017-09-10"));
//		dao.insert(act_comm_vo1);
		
		
//		Act_commVO act_comm_vo2=new Act_commVO();
//		act_comm_vo2.setComm_no("C1000000010");
//		act_comm_vo2.setAct_no("A1000000005");
//		act_comm_vo2.setMem_ac("starter9244");
//		act_comm_vo2.setComm_cont("baBBAAbaba~~");
//		act_comm_vo2.setComm_date(java.sql.Date.valueOf("2017-09-08"));
//		act_comm_vo2.setComm_reply_cont("haha~");
//		act_comm_vo2.setComm_reply_date(java.sql.Date.valueOf("2017-09-10"));
//		dao.update(act_comm_vo2);
		
		
		dao.update_response("JDBC測試", new java.sql.Date(new java.util.Date().getTime()), "C1000000007");
      		
		
//		dao.delete("C1000000011");
//		Act_commVO act_comm_vo3=dao.findByPrimaryKey("C1000000004");
//		System.out.print(act_comm_vo3.getComm_no()+",");
//		System.out.print(act_comm_vo3.getAct_no()+",");
//		System.out.print(act_comm_vo3.getMem_ac()+",");
//		System.out.print(act_comm_vo3.getComm_cont()+",");
//		System.out.print(act_comm_vo3.getComm_date()+",");
//		System.out.print(act_comm_vo3.getComm_reply_cont()+",");
//		System.out.print(act_comm_vo3.getComm_reply_date()+",");
		
//		List<Act_commVO> list=dao.getAll();
//		for(Act_commVO act_comm_vo4:list){
//		System.out.print(act_comm_vo4.getComm_no()+",");
//		System.out.print(act_comm_vo4.getAct_no()+",");
//		System.out.print(act_comm_vo4.getMem_ac()+",");
//		System.out.print(act_comm_vo4.getComm_cont()+",");
//		System.out.print(act_comm_vo4.getComm_date()+",");
//		System.out.print(act_comm_vo4.getComm_reply_cont()+",");
//		System.out.print(act_comm_vo4.getComm_reply_date()+",");
//		System.out.println();
//		}
		
		
		
	}
	
	
	
	
	
	
}
