package test;

/**
 * @author Administrator
 *
 */
public class Type {

	public String orgaAlias;
	public String orgaId;
	public String statDate;
	public String thisPeriodValue;
	public String samePeriodValue;
	public String dataSource;
	public String updateTime;
	
	public Type(String orgaAlias, String orgaId, String statDate, String thisPeriodValue, String samePeriodValue,
			String dataSource, String updateTime) {
		super();
		this.orgaAlias = orgaAlias;
		this.orgaId = orgaId;
		this.statDate = statDate;
		this.thisPeriodValue = thisPeriodValue;
		this.samePeriodValue = samePeriodValue;
		this.dataSource = dataSource;
		this.updateTime = updateTime;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dataSource == null) ? 0 : dataSource.hashCode());
		result = prime * result + ((orgaAlias == null) ? 0 : orgaAlias.hashCode());
		result = prime * result + ((orgaId == null) ? 0 : orgaId.hashCode());
		result = prime * result + ((samePeriodValue == null) ? 0 : samePeriodValue.hashCode());
		result = prime * result + ((statDate == null) ? 0 : statDate.hashCode());
		result = prime * result + ((thisPeriodValue == null) ? 0 : thisPeriodValue.hashCode());
		result = prime * result + ((updateTime == null) ? 0 : updateTime.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Type other = (Type) obj;
		if (dataSource == null) {
			if (other.dataSource != null)
				return false;
		} else if (!dataSource.equals(other.dataSource))
			return false;
		if (orgaAlias == null) {
			if (other.orgaAlias != null)
				return false;
		} else if (!orgaAlias.equals(other.orgaAlias))
			return false;
		if (orgaId == null) {
			if (other.orgaId != null)
				return false;
		} else if (!orgaId.equals(other.orgaId))
			return false;
		if (samePeriodValue == null) {
			if (other.samePeriodValue != null)
				return false;
		} else if (!samePeriodValue.equals(other.samePeriodValue))
			return false;
		if (statDate == null) {
			if (other.statDate != null)
				return false;
		} else if (!statDate.equals(other.statDate))
			return false;
		if (thisPeriodValue == null) {
			if (other.thisPeriodValue != null)
				return false;
		} else if (!thisPeriodValue.equals(other.thisPeriodValue))
			return false;
		if (updateTime == null) {
			if (other.updateTime != null)
				return false;
		} else if (!updateTime.equals(other.updateTime))
			return false;
		return true;
	}


	public Type() {
		super();
		// TODO Auto-generated constructor stub
	}


	@Override
	public String toString() {
		return "Type [orgaAlias=" + orgaAlias + ", orgaId=" + orgaId + ", statDate=" + statDate + ", thisPeriodValue="
				+ thisPeriodValue + ", samePeriodValue=" + samePeriodValue + ", dataSource=" + dataSource
				+ ", updateTime=" + updateTime + "]";
	}
	
	
	public String getOrgaAlias() {
		return orgaAlias;
	}
	public void setOrgaAlias(String orgaAlias) {
		this.orgaAlias = orgaAlias;
	}
	public String getOrgaId() {
		return orgaId;
	}
	public void setOrgaId(String orgaId) {
		this.orgaId = orgaId;
	}
	public String getStatDate() {
		return statDate;
	}
	public void setStatDate(String statDate) {
		this.statDate = statDate;
	}
	public String getThisPeriodValue() {
		return thisPeriodValue;
	}
	public void setThisPeriodValue(String thisPeriodValue) {
		this.thisPeriodValue = thisPeriodValue;
	}
	public String getSamePeriodValue() {
		return samePeriodValue;
	}
	public void setSamePeriodValue(String samePeriodValue) {
		this.samePeriodValue = samePeriodValue;
	}
	public String getDataSource() {
		return dataSource;
	}
	public void setDataSource(String dataSource) {
		this.dataSource = dataSource;
	}
	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	
	
}
