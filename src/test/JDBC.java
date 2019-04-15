package test;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.lang.reflect.WildcardType;

import org.junit.Test;

import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.JFinalConfig;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.kit.Prop;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.druid.DruidPlugin;
import com.jfinal.template.Engine;

public class JDBC  {
	
	@Test
	public  void main () {
		PropKit.use("tb.properties");
		DruidPlugin dp = new DruidPlugin(PropKit.get("connection.url"), PropKit.get("connection.username"),
				PropKit.get("connection.password"));
		dp.setDriverClass("org.postgresql.Driver");
	}
	
	@Test
	public void test1 () throws IOException {
		
		RandomAccessFile  random = new RandomAccessFile ("tb.properties","rw");
		int a = -1;
		while ((a=random.read())!=-1) {
			System.out.println(a);
		}
		
	}
	
}
