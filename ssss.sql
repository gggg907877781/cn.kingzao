CREATE OR REPLACE FUNCTION "public"."check_data"()
  RETURNS "pg_catalog"."void" AS $BODY$ 
DECLARE

   v_stat_date varchar(32); -- ��ʽ��ָ��ͳ��ʱ���ʽ(������ ��yyyy ��yyyy-Qx ��yyyy-mm ��yyyy-mm-dd ��)
   v_stat_date_pre varchar(32); -- ����ͳ������
   
   v_quota_name varchar(100); -- ָ������
   v_unit varchar(64); -- ָ�������λ
   v_direction numeric(1); --ָ�귽��  1-�� 0-�� 2-��
   v_min_monitor_cycle numeric(1); --��С������� (1-�� 2-���� 3-�� 4-�� 5-��)
   v_table_name varchar(64); --��Ӧ�����ݱ�
   v_sort_code numeric(6); -- չʾ˳��
   
   v_cycle_text varchar(32); -- ������ڵ���������
   v_cycle varchar(32); -- �������, ��ʽΪ: ��: yyyy , �ϰ���:yyyyFHY (FHYΪ�̶�ֵ), �°���:yyyySHY (SHYΪ�̶�ֵ), ����:yyyyQx (����xΪ1��4), ��:yyyy-MM , ��: yyyy-MM-dd 
   
   v_sql text; 
	 v_sql2 text;
   v_count numeric(20);
   v_stat_date_last varchar(32); -- ��ĩһ��ͳ������
   v_this_period_value numeric(20,4); -- ָ��ֵ
   v_plan_value numeric(20,4); -- �ƻ�ֵ
   v_same_period_value numeric(20,4); -- ͬ��ֵ(���ۼ�ֵ)
   v_yoy_status numeric(1); -- ͬ��״̬ (1-�� 0-�� 2-����)
   v_yoy_value numeric(20,4);--ͬ�Ⱦ���ֵ(������ϵͨ��ָ�귽�����ж�)
   v_rr_status numeric(1); -- ����״̬ (1-�� 0-�� 2-����)
   v_rr_value numeric(20,4);--���Ⱦ���ֵ(������ϵͨ��ָ�귽�����ж�)
   v_plan_comp_rate numeric(6,2); --�ƻ������
   v_light_status numeric(1); -- ���̵�״̬ 1-�� 2-�� 3-��(��¼��ǰ��ָ��ĺ��̵�״̬)
   
   v_plan_name varchar(32); -- �ƻ�ֵ��������������
   
   v_is_month_focus numeric(1); -- �Ƿ��¶ȹ�עָ�� (1-�� 2-��)
   v_source_update_time TIMESTAMP;--Դ���ݸ���ʱ��
   
   v_guid varchar(64); --����
   v_data_source varchar(64);
   ret RECORD;
	 indexs RECORD;
	 this_id_data RECORD;
	 data_all RECORD;
	 v_length numeric(20);
	 v_problem varchar(4000); --��������
BEGIN
	--v_quota_id := 'Q023';
	-- ������С��������ȡ
	-- �����ǰ����û������,��ȡ���һ�ڵ�����(Լ��������Ҫ���Ʋ��ܳ�����ǰ����)
	-- ȡͬ�Ⱥͻ��ȵĽ��
	-- 
   
	--�������
	delete from oc_check_data;
  
 	
   	-- ȡ��ǰ����(yyyy-MM)
	v_stat_date := to_char(now(), 'yyyy-MM');

	--select count(1) into v_cnt from oc_quota_lib;


	for ret in select id as quota_id,quota_name,unit,direction,min_monitor_cycle,table_name from oc_quota_lib
	 loop
		v_problem = ''; 
   	--���ָ����Ƿ�������
   	v_sql := ' select count(1) ' || ' from ' ||quote_ident(ret.table_name);
    execute v_sql into v_count;
		if v_count <= 0 then
				v_problem:='ָ��������';
				insert into oc_check_data (quota_id,quota_name,problem,table_name)VALUES(ret.quota_id,ret.quota_name,v_problem,ret.table_name);
				continue;

		else 
		
		v_sql := 'select case when data_source = 2 then ''ϵͳ'' else ''�ֹ�'' end data_source from '||quote_ident(ret.table_name);

		execute v_sql into v_data_source;


		--������� ���
			if ret.min_monitor_cycle =1 then 

			elseif ret.min_monitor_cycle = 3 then

			elseif ret.min_monitor_cycle = 4 or v_min_monitor_cycle = 5 then  
				
			else 
		--�������Ϊ��
				end if;
		--select data_source from oc_quota_lib


		 insert into oc_check_data (quota_id,quota_name,table_name,data_source)VALUES(ret.quota_id,ret.quota_name,ret.table_name,v_data_source);


	  end if;
		
		--�жϵ�ָ��������ݲ㼶
		v_length = null;
		v_sql := ' select  length(max(orga_id)) from '|| quote_ident(ret.table_name);
		execute v_sql into v_length;
		
		--��鵥ָ����������Ƿ�ȫ
		for indexs in select id,orga_name  from oc_orga_dimension where id != 'ONE_000' and length(id) = v_length
	 loop
			
			--�жϵ��� ��û������
			v_sql := ' select count(*) from ' || quote_ident(ret.table_name) || 'where orga_id = ' || indexs.id || ' and stat_date like '|| substr(v_stat_date,1,4);
		
			execute v_sql into v_count;
			
			if v_count <= 0 then 
					v_problem :=v_problem || indexs.orga_name || '��������';
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