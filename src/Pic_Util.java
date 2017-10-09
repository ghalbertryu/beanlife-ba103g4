

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;
import java.sql.*;


public class Pic_Util {


	public static void main (String[] args) throws IOException{
					
			//塞PROD
			for(Integer i = 1; i<=10 ;i++){  //編號1~11
				String no = "P10000000" + String.format("%02d", i);  // P10000000 S10000000 A10000000
				String table = "PROD"; //  STORE PROD ACT  table名稱
				String col = "PROD";
				for(int j = 1; j<=3; j++){
					String index= String.valueOf(j); //  1 2 3 第幾張照片
					//p0.jpg p1.jpg p2.jpg 三張圖片循環塞入 自己的圖片位置
					String path ="..\\BA103G4ryu\\WebContent\\FrontEnd\\res\\img\\p"+(((i+j) % 3)+1)+".jpg";
					updateImg(no,table,col,index,getPictureByteArray(path));
					System.out.println("修改"+table+" "+no+"照片"+index);			
				}	
			}
		
//			//塞ACT
			for(Integer i = 1; i<=4 ;i++){  //編號1~11
				String no = "A10000000" + String.format("%02d", i);  // P10000000 S10000000 A10000000
				String table = "ACT"; //  STORE PROD ACT  table名稱
				String col = "ACT";
				for(int j = 1; j<=3; j++){
					String index= String.valueOf(j); //  1 2 3 第幾張照片
    				//p0.jpg p1.jpg p2.jpg 三張圖片循環塞入 自己的圖片位置
					String path ="..\\BA103G4ryu\\WebContent\\FrontEnd\\res\\img\\a"+(((i+j) % 4)+1)+".jpg";
					updateImg(no,table,col,index,getPictureByteArray(path));
					System.out.println("修改"+table+" "+no+"照片"+index);			
				}	
			}
			
			//塞STORE
			for(Integer i = 1; i<=10 ;i++){  //編號1~11
				String no = "S10000000" + String.format("%02d", i);  // P10000000 S10000000 A10000000
				String table = "STORE"; //  STORE PROD ACT  table名稱
				String col = "STORE";
				for(int j = 1; j<=3; j++){
					String index= String.valueOf(j); //  1 2 3 第幾張照片
    				//p0.jpg p1.jpg p2.jpg 三張圖片循環塞入 自己的圖片位置
					String path ="..\\BA103G4ryu\\WebContent\\FrontEnd\\res\\img\\s"+(((i+j) % 3)+1)+".jpg";
					updateImg(no,table,col,index,getPictureByteArray(path));
					System.out.println("修改"+table+" "+no+"照片"+index);			
				}	
			}
			
			//塞STORE WIN_ID_PIC
			for(Integer i = 1; i<=10 ;i++){  //編號1~11

				String no = "S10000000" + String.format("%02d", i);  // P10000000 S10000000 A10000000
				String table = "STORE"; //
				String col = "WIN_ID";

					String index= ""; //  1 2 3 第幾張照片
    				//p0.jpg p1.jpg p2.jpg 三張圖片循環塞入 自己的圖片位置
					String path ="..\\BA103G4ryu\\WebContent\\FrontEnd\\res\\img\\d"+(((i) % 1)+1)+".jpg";
					updateImg(no,table, col, index,getPictureByteArray(path));
					System.out.println("修改"+table+" "+no+"照片"+col);			

			}
			
			//塞MEM
			for(Integer i = 1; i<=20 ;i++){  //編號1~11

				String no = "U10000000" + String.format("%02d", i);  // P10000000 S10000000 A10000000
				String table = "MEM"; //
				String col = "MEM";

					String index= ""; //  1 2 3 第幾張照片
    				//p0.jpg p1.jpg p2.jpg 三張圖片循環塞入 自己的圖片位置
					String path ="..\\BA103G4ryu\\WebContent\\FrontEnd\\res\\img\\m"+(((i) % 6)+1)+".jpg";
					updateImg(no,table, col, index,getPictureByteArray(path));
					System.out.println("修改"+table+" "+no+"照片"+index);			

			}
			
			
			
		}

	public static void updateImg(String no, String table, String col, String index, byte[] pic) {
		String driver = "oracle.jdbc.driver.OracleDriver";
//		String url = "jdbc:oracle:thin:@54.92.7.228:1521:XE";
		String url = "jdbc:oracle:thin:@localhost:1521:XE";
		String userid = "ba103g4";
		String passwd = "123456";
		

		Connection con = null;
		PreparedStatement pstmt = null;	
		
		try {		
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			
			pstmt = con.prepareStatement("UPDATE "+table+" SET "+col+"_PIC"+index+"=? WHERE "+table+"_NO =?");
			
			pstmt.setBytes(1, pic);
			pstmt.setString(2, no);
			
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			throw new RuntimeException("A database error occured. "
					+ e.getMessage());
			
		}
		 catch (ClassNotFoundException e) {
				e.printStackTrace();
		}finally{
			if (pstmt != null) {
				try{
					pstmt.close();
				} catch (SQLException se){
					se.printStackTrace(System.err);
				}
			}
			if(con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}	
	}

	
	public static byte[] getPictureByteArray(String path) throws IOException {
		File file = new File(path);
		FileInputStream fis = new FileInputStream(file);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] buffer = new byte[8192];
		int i;
		while ((i = fis.read(buffer)) != -1) {
			baos.write(buffer, 0, i);
		}
		baos.close();
		fis.close();

		return baos.toByteArray();
	}
}
