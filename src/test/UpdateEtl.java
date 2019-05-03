package test;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UpdateEtl {
	
	public static void main(String[] args) {
		
	}
	
	
	public void run () throws Exception {
		
		DAO sqlLib = new DAO();
		
		ResultSet lib = sqlLib.resulet().executeQuery("select id,quota_name,table_name from oc_quota_lib ordre by id");
		
		while (lib.next()){
			
			DAO sqlIndex = new DAO();
			DAO sqlEtl = new DAO();
			DAO sqlExecute = new DAO();
			
			
			String etlDate = null;
			
			ResultSet index = sqlIndex.resulet().executeQuery("select eltdate from "+lib.getString(3)+" limit 1");
			if (index.next()) {
				etlDate = index.getString(1);
			}
					
			ResultSet etl = sqlEtl.resulet().executeQuery("select 1 2 3 from **-* where quota_id = "+lib.getString(1));
			
			if (etl.next()){
				 if (etlDate != null && (etl.getString(3) != null || etl.getString(3) != "" )) {
					 
					 if (etl.getString(3) != etlDate) {
						 sqlExecute.resulet().executeQuery("");
					 }
					 
				 }
			}else {
					sqlExecute.resulet().executeQuery("");
			}
			
			
			
			
			
			sqlIndex.stopClose();
			sqlEtl.stopClose();
			sqlExecute.stopClose();
			sqlIndex = null;
			sqlEtl = null;
			sqlExecute = null;
		}
		
		sqlLib.stopClose();
		sqlLib = null;
	}
	
}
