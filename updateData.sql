package test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

public class test {
	
	public  Connection ccn;
	public  Statement st;
	public  ResultSet rs;
	
	public int month [] = {1,2,3,4,5,6,7,8,9,10,11,12};
	
	public Map dataSource = new HashMap();
	public Map problem = new HashMap();
	
	@Test
	public void linkData () throws Exception { 
		
		Date date = new Date();
		SimpleDateFormat timeYear = new SimpleDateFormat ("YYYY");
		SimpleDateFormat timeMonth = new SimpleDateFormat("M");
		System.out.println(timeMonth.format(date));
		
		returnData();
		
		rs = st.executeQuery("select id as quota_id,quota_name,unit,direction,min_monitor_cycle,table_name from oc_quota_lib order by quota_id");
		
		if (rs.next()) {
			
			while (rs.next()) {
				
				System.out.println(rs.getString(6));
				returnData();
				ResultSet index = st.executeQuery("select b.orga_alias,a.orga_id,a.stat_date,a.this_period_value,a.same_period_value,a.data_source,a.update_time from "+rs.getString(6) + " a join oc_orga_dimension b on a.orga_id = b.id  where stat_date like '"+timeYear.format(date)+"%'");
				
				if (index.next()) {
					
					while(index.next()) {
						System.out.println(index.getString(1));
					}
					
					//st.execute("insert into oc_check_data (quota_id,quota_name,table_name,data_source,problem)VALUES('"+rs.getString(1)+"','"+rs.getString(2)+"','"+rs.getString(6)+"',null,'表中无数据')");

				}else {
					
					//表中无数据
					st.execute("insert into oc_check_data (quota_id,quota_name,table_name,data_source,problem)VALUES('"+rs.getString(1)+"','"+rs.getString(2)+"','"+rs.getString(6)+"',null,'表中无数据')");

				}
				
			}
		} else {
			
			
			
		}
		
		stopClose();
		
		
	}
	
	public void returnData () throws SQLException, ClassNotFoundException {
		Class.forName("org.postgresql.Driver");
		ccn = DriverManager.getConnection("jdbc:postgresql://192.168.6.100:5432/postgres", "postgres","postgres");
		st  = ccn.createStatement();
	}
	
	public void  stopClose() throws SQLException {
		
		if (rs != null) {
			rs.close();
		}
		if (st != null) {
			st.close();
		}
		if (ccn != null) {
			ccn.close();
		}

	}
	
	@Test
	public void test2 () throws Exception {
		
	}

}
