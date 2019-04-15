package test;
import java.sql.Array;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.hamcrest.internal.ArrayIterator;
import org.junit.Test;


public class test {
	
	public Connection ccn;
	public Statement st;
	public ResultSet rs;
	public Connection ccn1;
	public Statement st1;
	public ResultSet rs1;
	
	public HashSet orgaAll = new HashSet();
	public Map corresponding = new HashMap();
	
	@Test
	public void linkData () throws Exception { 
		System.out.println(System.currentTimeMillis()+"-开始");
		Date date = new Date();
		SimpleDateFormat yearModer = new SimpleDateFormat ("YYYY");
		SimpleDateFormat MonthModer = new SimpleDateFormat("M");
		
		String timeYear = yearModer.format(date);
		String timeMonth = MonthModer.format(date);
		
		
		returnData();
		
		rs = st.executeQuery("select id as quota_id,quota_name,unit,direction,min_monitor_cycle,table_name from oc_quota_lib order by quota_id");
		
		
		
		if (rs.next()) {
			String issue = "";
			String dataSource = "1";
			
  			while (rs.next()) {
				
				returnData1();
				ResultSet exeSql = st1.executeQuery("select length(max(orga_id)) from "+rs.getString(6));
				String idLength = "";
				while(exeSql.next()) {
					idLength = exeSql.getString(1);
				}
				System.out.println(idLength);
				
				if (idLength != "") {
					orgaAll.clear();
					exeSql = st1.executeQuery("select id,orga_alias from oc_orga_dimension where length(id) = "+idLength+" and id != 'ONE_000' and orga_type != 3");
					if (exeSql.next()) {
						while(exeSql.next()) {
							orgaAll.add(exeSql.getString(1));
							corresponding.put(exeSql.getString(1),exeSql.getString(2));
						}
					}
				}
				
				stopClose1();
				
				returnData();
				ResultSet index = st.executeQuery("select b.orga_alias,a.orga_id,a.stat_date,a.this_period_value,a.same_period_value,a.data_source,a.update_time from "+rs.getString(6) + " a join oc_orga_dimension b on a.orga_id = b.id  where stat_date like '"+timeYear+"%'");
				int zeroHour;
				if (index.next()) {
					zeroHour = 0;
					++zeroHour;
					if (zeroHour == 1 && index.getString(6) == "2") {
						dataSource = "2";
					}
						
					System.out.println(index.getString(6));
					
					ArrayList dataAll0 = new ArrayList<Type>();
					ArrayList dataAll1 = new ArrayList<Type>();
					ArrayList dataAll2 = new ArrayList<Type>();
					ArrayList dataAll3 = new ArrayList<Type>();
					ArrayList dataAll4 = new ArrayList<Type>();
					ArrayList dataAll5 = new ArrayList<Type>();
					ArrayList dataAll6 = new ArrayList<Type>();
					ArrayList dataAll7 = new ArrayList<Type>();
					ArrayList dataAll8 = new ArrayList<Type>();
					ArrayList dataAll9 = new ArrayList<Type>();
					ArrayList dataAll10 = new ArrayList<Type>();
					ArrayList dataAll11 = new ArrayList<Type>();
					ArrayList dataAll12 = new ArrayList<Type>();
					
					while(index.next()) {

							
						if (index.getString(3).length() == 4) {
							dataAll0.add(new Type(index.getString(1),index.getString(2),index.getString(3),index.getString(4),index.getString(5),index.getString(6),index.getString(7)));
						}else if (index.getString(3) == timeYear+"-01") {
							dataAll0.add(new Type(index.getString(1),index.getString(2),index.getString(3),index.getString(4),index.getString(5),index.getString(6),index.getString(7)));
						}else if (index.getString(3) == timeYear+"-02") {
							dataAll0.add(new Type(index.getString(1),index.getString(2),index.getString(3),index.getString(4),index.getString(5),index.getString(6),index.getString(7)));
						}else if (index.getString(3) == timeYear+"-03") {
							dataAll0.add(new Type(index.getString(1),index.getString(2),index.getString(3),index.getString(4),index.getString(5),index.getString(6),index.getString(7)));
						}else if (index.getString(3) == timeYear+"-04") {
							dataAll0.add(new Type(index.getString(1),index.getString(2),index.getString(3),index.getString(4),index.getString(5),index.getString(6),index.getString(7)));
						}else if (index.getString(3) == timeYear+"-05") {
							dataAll0.add(new Type(index.getString(1),index.getString(2),index.getString(3),index.getString(4),index.getString(5),index.getString(6),index.getString(7)));
						}else if (index.getString(3) == timeYear+"-06") {
							dataAll0.add(new Type(index.getString(1),index.getString(2),index.getString(3),index.getString(4),index.getString(5),index.getString(6),index.getString(7)));
						}else if (index.getString(3) == timeYear+"-07") {
							dataAll0.add(new Type(index.getString(1),index.getString(2),index.getString(3),index.getString(4),index.getString(5),index.getString(6),index.getString(7)));
						}else if (index.getString(3) == timeYear+"-08") {
							dataAll0.add(new Type(index.getString(1),index.getString(2),index.getString(3),index.getString(4),index.getString(5),index.getString(6),index.getString(7)));
						}else if (index.getString(3) == timeYear+"-09") {
							dataAll0.add(new Type(index.getString(1),index.getString(2),index.getString(3),index.getString(4),index.getString(5),index.getString(6),index.getString(7)));
						}else if (index.getString(3) == timeYear+"-10") {
							dataAll0.add(new Type(index.getString(1),index.getString(2),index.getString(3),index.getString(4),index.getString(5),index.getString(6),index.getString(7)));
						}else if (index.getString(3) == timeYear+"-11") {
							dataAll0.add(new Type(index.getString(1),index.getString(2),index.getString(3),index.getString(4),index.getString(5),index.getString(6),index.getString(7)));
						}else if (index.getString(3) == timeYear+"-12") {
							dataAll0.add(new Type(index.getString(1),index.getString(2),index.getString(3),index.getString(4),index.getString(5),index.getString(6),index.getString(7)));
						}
					}
					
					HashSet judgeYear = orgaAll;
					
					for (int i = 0 ; i < dataAll0.size() ; i++) {
						if (i==0) {
							issue += "年数据累计值为空：";
						}
						Type type = (Type) dataAll0.get(i);
						//处理 年数据
						
						if (type.getThisPeriodValue() == "") {
							issue += index.getString(1)+",";
						}
						if (judgeYear.contains(type.getOrgaId())){
							judgeYear.remove(type.getOrgaId());				
						}
					}
					
					zeroHour = 0;
					for (Object object : judgeYear) {
						zeroHour++;
						if (zeroHour == 1) {
							issue += "年数据没有:";
						}
						issue += corresponding.get(object)+",";
					}

					
					//月数据 判断
					Object monthAllData [][] = {dataAll1.toArray(),dataAll2.toArray(),dataAll3.toArray(),dataAll4.toArray(),dataAll5.toArray(),dataAll6.toArray(),dataAll7.toArray(),dataAll8.toArray(),dataAll9.toArray(),dataAll10.toArray(),dataAll11.toArray(),dataAll12.toArray()};
					for(int j = 0 ; j < Integer.parseInt(timeMonth) ;j++) {
						
						HashSet judgeMonth = orgaAll;
						
						for (int e = 0 ; e < monthAllData[j].length ; e++ ) {
							if (e == 0) {
								issue += (j+1)+"月累计值为空:";
							}
							Type type = (Type) monthAllData[j][e];
							if (type.getThisPeriodValue() == "") {
								issue += index.getString(1)+",";
							}
							if (judgeYear.contains(type.getOrgaId())){
								judgeYear.remove(type.getOrgaId());				
							}
						}
						
						zeroHour = 0;
						for (Object object : judgeMonth) {
							zeroHour++;
							if (zeroHour == 1) {
								issue += (j+1)+"月数据没有：";
							}
							issue += corresponding.get(object)+",";
						}
						
						
					}
					
					System.out.println(issue);
					st.execute("insert into oc_check_data (quota_id,quota_name,table_name,data_source,problem)VALUES('"+rs.getString(1)+"','"+rs.getString(2)+"','"+rs.getString(6)+"',"+dataSource+",'"+issue+"')");
					

				}else {
					
					//表中无数据
 					st.execute("insert into oc_check_data (quota_id,quota_name,table_name,data_source,problem)VALUES('"+rs.getString(1)+"','"+rs.getString(2)+"','"+rs.getString(6)+"',null,'表中无数据')");

				}
				
 				if (index != null) {
					index.close();
				}
				
				
			}
			
			
		} else {
			
			System.out.println("数据库lib表中无数据！！");
			
		}
		
		stopClose();
		
		System.out.println(System.currentTimeMillis()+"-结束");
	}
	
	public void returnData () throws SQLException, ClassNotFoundException {
		Class.forName("org.postgresql.Driver");
		ccn = DriverManager.getConnection("jdbc:postgresql://192.168.6.100:5432/postgres", "postgres","postgres");
		st  = ccn.createStatement();
	}
	
	public void returnData1 () throws SQLException, ClassNotFoundException {
		Class.forName("org.postgresql.Driver");
		ccn1 = DriverManager.getConnection("jdbc:postgresql://192.168.6.100:5432/postgres", "postgres","postgres");
		st1  = ccn.createStatement();
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
	
	public void  stopClose1() throws SQLException {
		
		if (rs1 != null) {
			rs1.close();
		}
		if (st1 != null) {
			st1.close();
		}
		if (ccn1 != null) {
			ccn1.close();
		}

	}
	
	
}
