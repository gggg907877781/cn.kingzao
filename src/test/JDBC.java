package test;


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
		Prop useTb = PropKit.use("tb.properties");
		DruidPlugin db = new DruidPlugin(useTb.get("connection.url"),useTb.get("connection.username"),useTb.get("connection.password"));
		System.err.println(db);
	}
}
