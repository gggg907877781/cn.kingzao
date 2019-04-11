CREATE OR REPLACE FUNCTION "public"."check_data"()
  RETURNS "pg_catalog"."void" AS $BODY$ 
DECLARE

   v_stat_date varchar(32); -- 正式的指标统计时间格式(可以是 年yyyy 季yyyy-Qx 月yyyy-mm 日yyyy-mm-dd 等)
   v_stat_date_pre varchar(32); -- 上期统计周期
   
   v_quota_name varchar(100); -- 指标名称
   v_unit varchar(64); -- 指标计量单位
   v_direction numeric(1); --指标方向  1-正 0-负 2-中
   v_min_monitor_cycle numeric(1); --最小监控周期 (1-年 2-半年 3-季 4-月 5-日)
   v_table_name varchar(64); --对应主数据表
   v_sort_code numeric(6); -- 展示顺序
   
   v_cycle_text varchar(32); -- 监控周期的中文描述
   v_cycle varchar(32); -- 监控周期, 格式为: 年: yyyy , 上半年:yyyyFHY (FHY为固定值), 下半年:yyyySHY (SHY为固定值), 季度:yyyyQx (其中x为1至4), 月:yyyy-MM , 日: yyyy-MM-dd 
   
   v_sql text; 
	 v_sql2 text;
   v_count numeric(20);
   v_stat_date_last varchar(32); -- 最末一次统计周期
   v_this_period_value numeric(20,4); -- 指标值
   v_plan_value numeric(20,4); -- 计划值
   v_same_period_value numeric(20,4); -- 同期值(或累计值)
   v_yoy_status numeric(1); -- 同比状态 (1-优 0-差 2-不变)
   v_yoy_value numeric(20,4);--同比绝对值(正负关系通过指标方向来判断)
   v_rr_status numeric(1); -- 环比状态 (1-优 0-差 2-不变)
   v_rr_value numeric(20,4);--环比绝对值(正负关系通过指标方向来判断)
   v_plan_comp_rate numeric(6,2); --计划完成率
   v_light_status numeric(1); -- 红绿灯状态 1-绿 2-黄 3-红(记录当前该指标的红绿灯状态)
   
   v_plan_name varchar(32); -- 计划值的中文名称描述
   
   v_is_month_focus numeric(1); -- 是否月度关注指标 (1-是 2-否)
   v_source_update_time TIMESTAMP;--源数据更新时间
   
   v_guid varchar(64); --主键
   v_data_source varchar(64);
   ret RECORD;
	 indexs RECORD;
	 this_id_data RECORD;
	 data_all RECORD;
	 v_length numeric(20);
	 v_problem varchar(4000); --数据问题
BEGIN
	--v_quota_id := 'Q023';
	-- 按照最小周期来抽取
	-- 如果当前周期没有数据,则取最后一期的数据(约束条件中要限制不能超过当前日期)
	-- 取同比和环比的结果
	-- 
   
	--清空数据
	delete from oc_check_data;
  
 	
   	-- 取当前年月(yyyy-MM)
	v_stat_date := to_char(now(), 'yyyy-MM');

	--select count(1) into v_cnt from oc_quota_lib;


	for ret in select id as quota_id,quota_name,unit,direction,min_monitor_cycle,table_name from oc_quota_lib
	 loop
		v_problem = ''; 
   	--检查指标表是否有数据
   	v_sql := ' select count(1) ' || ' from ' ||quote_ident(ret.table_name);
    execute v_sql into v_count;
		if v_count <= 0 then
				v_problem:='指标无数据';
				insert into oc_check_data (quota_id,quota_name,problem,table_name)VALUES(ret.quota_id,ret.quota_name,v_problem,ret.table_name);
				continue;

		else 
		
		v_sql := 'select case when data_source = 2 then ''系统'' else ''手工'' end data_source from '||quote_ident(ret.table_name);

		execute v_sql into v_data_source;


		--监测周期 年的
			if ret.min_monitor_cycle =1 then 

			elseif ret.min_monitor_cycle = 3 then

			elseif ret.min_monitor_cycle = 4 or v_min_monitor_cycle = 5 then  
				
			else 
		--监测周期为空
				end if;
		--select data_source from oc_quota_lib


		 insert into oc_check_data (quota_id,quota_name,table_name,data_source)VALUES(ret.quota_id,ret.quota_name,ret.table_name,v_data_source);


	  end if;
		
		--判断单指标表中数据层级
		v_length = null;
		v_sql := ' select  length(max(orga_id)) from '|| quote_ident(ret.table_name);
		execute v_sql into v_length;
		
		--检查单指标表中数据是否全
		for indexs in select id,orga_name  from oc_orga_dimension where id != 'ONE_000' and length(id) = v_length
	 loop
			
			--判断地区 有没有数据
			v_sql := ' select count(*) from ' || quote_ident(ret.table_name) || 'where orga_id = ' || indexs.id || ' and stat_date like '|| substr(v_stat_date,1,4);
		
			execute v_sql into v_count;
			
			if v_count <= 0 then 
					v_problem :=v_problem || indexs.orga_name || '：无数据';
					continue;
			else 	
					v_sql := ' select  orga_id,stat_date,this_period_value from ' || quote_ident(ret.table_name) || 'where orga_id = ' || indexs.id || ' and stat_date like '|| substr(v_stat_date,1,4);
		
					execute v_sql into data_all;
					
					--for this_id_data in data_all loop 
						
					--end loop;
		
			end if;

		
		end loop;


	end loop;
   	
 
END; 
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100