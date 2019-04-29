package test;

import java.io.RandomAccessFile;

import org.junit.Test;

import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.JFinalConfig;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.druid.DruidPlugin;
import com.jfinal.template.Engine;

public class JDBC extends JFinalConfig {
	
	@Test
	public  void JDBC () {
		PropKit.use("./tb.properties");
		DruidPlugin dp = new DruidPlugin(PropKit.get("connection.url"), PropKit.get("connection.username"),
				PropKit.get("connection.password"));
		dp.setDriverClass("org.postgresql.Driver");
	}
	
	
	@Test
	public void test1 () throws Exception {
		
		RandomAccessFile  random = new RandomAccessFile ("./tb.properties","rw");
		System.out.println(random.readLine());
		
	}

	@Override
	public void configConstant(Constants me) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void configRoute(Routes me) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void configEngine(Engine me) {
		// TODO Auto-generated method stub
		
	}

	@Test
	public void configPlugin() {

		PropKit.use("tb.properties");
		DruidPlugin dp = new DruidPlugin(PropKit.get("connection.url"), PropKit.get("connection.username"),
				PropKit.get("connection.password"));
		dp.setDriverClass("org.postgresql.Driver");
		
	}

	@Override
	public void configInterceptor(Interceptors me) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void configHandler(Handlers me) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void configPlugin(Plugins me) {
		// TODO Auto-generated method stub
		
	}
	
}
