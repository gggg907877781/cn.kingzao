package com.blit.lp.jf.controller;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.blit.lp.bus.auditlog.LogStatusEnum;
import com.blit.lp.bus.auditlog.LogTypeEnum;
import com.blit.lp.bus.security.Audit;
import com.blit.lp.core.context.Global;
import com.blit.lp.core.context.User;
import com.blit.lp.jf.config.LPController;
import com.blit.lp.jf.interceptor.AuthInterceptor;
import com.blit.lp.tools.AuditLogKit;
import com.blit.lp.tools.GuidKit;
import com.jfinal.aop.Clear;
import com.jfinal.core.Controller;
import com.jfinal.ext.LPJsonRender;
import com.jfinal.kit.PropKit;
import com.jfinal.kit.Ret;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;


/**
 * 登录和首面控制器
 * @author dkomj
 *
 */

public class IndexController extends Controller {

	public void indexold(){
		renderTemplate("/sys/index.html");
	}
	
	public void index(){
		setAttr("title", Global.getProp("title"));
		setAttr("homepage", Global.getProp("homepage"));
		renderTemplate("/sys/index/themes/default/index.html");
		//renderTemplate("/sys/index.html");
	}
	
	
	@Clear(AuthInterceptor.class)
	public void login(){
		setAttr("title", Global.getProp("title"));
		renderTemplate("/sys/index/themes/default/login.html");
		
		//setAttr("title", Global.getProp("title"));
		//renderTemplate("/sys/login.html");
	}
	
	//密码登录处理逻辑
	@Clear(AuthInterceptor.class)
	public void dologin(){
		String usernum = getPara("usernum");
		String pwd = getPara("pwd");
		
		String clientIp = AuditLogKit.getRealIp(getRequest());
		Audit.checkLimitIp(clientIp);

		User.doLogin(usernum, pwd);
		renderJson(Ret.ok());
		AuditLogKit.log(LogTypeEnum.SYSLOGIN,LogStatusEnum.SUCCESS,"登录成功");
	}
	
	//获取菜单树
	public void getOutLookTree(){
		User user = User.getCurrUser();
		String sql = "";
		if(user.isSuperAdmin()){
			sql = "select m.id,m.pid,menuname as text,url,openway,iconcls,opensubmenuindex from lp_sys_menu m order by px asc,addtime asc";
			List<Record> list = Db.find(sql);
			Pattern pattern = Pattern.compile("#\\((\\w+)\\)");
			for (Record record : list) {
				String url = record.get("url", "");
				Matcher matche = pattern.matcher(url);
				while(matche.find()){
					String paramStr = matche.group(0);
					String paramName = matche.group(1);
					String val = PropKit.use("global.properties").get(paramName,"");
					url = url.replace(paramStr, val);
					matche = pattern.matcher(url);
				}
				record.set("url", url);
			}
			renderJson(new LPJsonRender(list));
		}
		else{
			sql = "select m.id,m.pid,menuname as text,url,openway,iconcls from lp_sys_menu m where m.id in " +
				" (select rm.menuid from lp_sys_rolemenu rm " +
				" join lp_sys_role r on r.id=rm.roleid" +
				" join lp_sys_userrole ur on r.id=ur.roleid" +
				" where ur.userid=? ) order by px asc,addtime asc";
			List<Record> list = Db.find(sql,user.get("id"));
			Pattern pattern = Pattern.compile("#\\((\\w+)\\)");
			for (Record record : list) {
				String url = record.get("url", "");
				Matcher matche = pattern.matcher(url);
				while(matche.find()){
					String paramStr = matche.group(0);
					String paramName = matche.group(1);
					String val = PropKit.use("global.properties").get(paramName,"");
					url = url.replace(paramStr, val);
					matche = pattern.matcher(url);
				}
				record.set("url", url);
			}
			renderJson(new LPJsonRender(list));
		}
	}
	
	//退出登录逻辑
	@Clear(AuthInterceptor.class)
	public void dologout(){
		User.doLogout();
		renderJson(Ret.ok());
	}
	
	//修改密码
	public void dorestpwd(){
		String pwd1 = getPara("pwd1");
		String pwd2 = getPara("pwd2");
		
		if(StrKit.isBlank("pwd1") || StrKit.isBlank("pwd2")){
			renderJson(Ret.fail("msg", "原、新密码不能为空!"));
			return;
		}
		
		User user = User.getCurrUser();
		
		String dbPwd = user.get("pwd");
		if(!dbPwd.equalsIgnoreCase(pwd1)){
			renderJson(Ret.fail("msg", "原密码错误！"));
			return;
		}
		
		Db.update("update lp_sys_user set pwd=? where id=?",pwd2,user.get("id"));
		renderJson(Ret.ok("msg", ""));
		AuditLogKit.log(LogTypeEnum.SYSMANAGER,LogStatusEnum.SUCCESS,"修改密码成功");
	}
	//注册
	@Clear(AuthInterceptor.class)
	public void regist(){
		String usernum = getPara("usernum");
		String pwd = getPara("pwd");
		Record record = Db.findFirst("select * from rsb_ryxx where upper(idcard) =upper(?)",usernum);
		if(record==null){
			renderJson(Ret.fail("msg", "身份证号码不存在,请联系管理员添加！"));
			return;
		}
		Record user = Db.findFirst("select * from lp_sys_user where upper(usernum) =upper(?)",usernum);
		if(user!=null){
			renderJson(Ret.fail("msg", "该身份证号已经注册！"));
			return;
		}
		Record newUser =new Record();
		newUser.set("id", GuidKit.createGuid());
		newUser.set("deptid", "0");//0代表 组织机构名为报名用户
		newUser.set("usernum", usernum.toUpperCase());//身份证号统一转成大写
		newUser.set("username", record.getStr("name"));
		newUser.set("pwd", pwd);
		newUser.set("status", "1");
		newUser.set("addtime", new Timestamp(new Date().getTime()));
		Db.save("lp_sys_user", "id", newUser);
		Record role =new Record();
		Record roledata = Db.findFirst("select * from lp_sys_role where rolename=?","报名用户");
		role.set("id", GuidKit.createGuid());
		role.set("USERID", newUser.getStr("id"));
		role.set("ROLEID", roledata.getStr("id"));
		Db.save("LP_SYS_USERROLE", "id", role);
		renderJson(Ret.ok("msg", ""));
		AuditLogKit.log(LogTypeEnum.SYSMANAGER,LogStatusEnum.SUCCESS,usernum+"注册成功");
	}
	//验证码
	@Clear(AuthInterceptor.class)
	public void code(){
		renderCaptcha();
	}
	//校验验证码
	@Clear(AuthInterceptor.class)
	public void validate() {
		boolean validateCaptcha = validateCaptcha("code");
		if(validateCaptcha){
			renderJson(Ret.ok());
		}else{
			renderJson(Ret.fail("msg","验证码错误！"));
		}
	}
}
