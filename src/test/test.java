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
	public HashSet orgaAll ;
	
	@Test
	public void linkData () throws Exception { 
		
		Date date = new Date();
		SimpleDateFormat yearModer = new SimpleDateFormat ("YYYY");
		SimpleDateFormat MonthModer = new SimpleDateFormat("M");
		
		String timeYear = yearModer.format(date);
		String timeMonth = MonthModer.format(date);
		
		
		returnData();
		
		rs = st.executeQuery("select id as quota_id,quota_name,unit,direction,min_monitor_cycle,table_name from oc_quota_lib order by quota_id");
		
		String issue = "";
		
		if (rs.next()) {
			
			while (rs.next()) {
				
				System.out.println();
				
				returnData();
				ResultSet index = st.executeQuery("select b.orga_alias,a.orga_id,a.stat_date,a.this_period_value,a.same_period_value,a.data_source,a.update_time from "+rs.getString(6) + " a join oc_orga_dimension b on a.orga_id = b.id  where stat_date like '"+timeYear+"%'");
				
				if (index.next()) {
					
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
						System.out.println(index.getString(3));
							
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
					for (int i = 0 ; i < judgeYear.size() ; i++) {
						
						Type type = (Type) dataAll0.get(i);
						//处理 年数据
						if (type.getThisPeriodValue() == "") {
							issue += index.getString(1)+"-年数据:累计值为空,";
						}
						if (judgeYear.contains(type.getOrgaId())){
							judgeYear.remove(type.getOrgaId());				
						}
					}
					for (Object object : judgeYear) {
						//issue += index.getString(1)+"-"+j+"月:累计值为空,";
					}
					
					//月数据 判断
					Object monthAllData [][] = {dataAll1.toArray(),dataAll2.toArray(),dataAll3.toArray(),dataAll4.toArray(),dataAll5.toArray(),dataAll6.toArray(),dataAll7.toArray(),dataAll8.toArray(),dataAll9.toArray(),dataAll10.toArray(),dataAll11.toArray(),dataAll12.toArray()};
					for(int j = 0 ; j < Integer.parseInt(timeMonth) ;j++) {
						
						HashSet judgeMonth = orgaAll;
						
						for (int e = 0 ; e < monthAllData[j].length ; e++ ) {
							Type type = (Type) monthAllData[j][e];
							if (type.getThisPeriodValue() == "") {
								issue += index.getString(1)+"-"+j+"月:累计值为空,";
							}
							if (judgeYear.contains(type.getOrgaId())){
								judgeYear.remove(type.getOrgaId());				
							}
						}
						
						for (Object object : judgeMonth) {
							//issue += index.getString(1)+"-"+j+"月:累计值为空,";
						}
						
						
					}
					
					//st.execute("insert into oc_check_data (quota_id,quota_name,table_name,data_source,problem)VALUES('"+rs.getString(1)+"','"+rs.getString(2)+"','"+rs.getString(6)+"',null,'表中无数据')");

				}else {
					
					//表中无数据
					st.execute("insert into oc_check_data (quota_id,quota_name,table_name,data_source,problem)VALUES('"+rs.getString(1)+"','"+rs.getString(2)+"','"+rs.getString(6)+"',null,'表中无数据')");

				}
				
			}
		} else {
			
			System.out.println("数据库lib表中无数据！！");
			
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
	
		HashSet a = new HashSet();
		a.add("1");
		a.add("2");
		a.add("3");
		a.add("4");
		String b [] = {"1","2"};
		
		ArrayList list = new ArrayList<Type>();
		list.add(new Type("7","1","2","3","4","5","6"));
		Object [][] e = {list.toArray(),{},{},{}};
		
		for (int i = 0 ; i < e.length ; i++ ) {
			for (int j = 0;j < e[i].length ; j ++) {
				Type type = (Type) e[i][j];
			}
			System.out.println();
		}
		
		System.out.println(Arrays.toString(a.toArray()));
	}
	
	@Test 
	public void test3() {
		
		Date date = new Date();
		SimpleDateFormat yearModer = new SimpleDateFormat ("YYYY");
		SimpleDateFormat MonthModer = new SimpleDateFormat("M");
		System.out.println(MonthModer.format(date));
		HashSet a = new HashSet();
		a.add("1");
		a.add("2");
		a.add("3");
		a.add("4");
		for (Object o : a) {
			System.out.println(o);
		}
		
	}
	
}
