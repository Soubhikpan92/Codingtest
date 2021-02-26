package com.cardutilization.model;

import java.util.List;

public class EntityPOJO {

	String entity;
	String parent;
	double limit;
	double utilisation;
	double combindUtilisation;
	EntityPOJO parentObj;
	List<EntityPOJO> childs;
	String group;

	public String getEntity() {
		return entity;
	}

	public void setEntity(String entity) {
		this.entity = entity;
	}

	public String getParent() {
		return parent;
	}

	public void setParent(String parent) {
		this.parent = parent;
	}

	public double getLimit() {
		return limit;
	}

	public void setLimit(double limit) {
		this.limit = limit;
	}

	public double getUtilisation() {
		return utilisation;
	}

	public void setUtilisation(double utilisation) {
		this.utilisation = utilisation;
	}

	public double getCombindUtilisation() {
		return combindUtilisation;
	}

	public void setCombindUtilisation(double combindUtilisation) {
		this.combindUtilisation = combindUtilisation;
	}

	public EntityPOJO getParentObj() {
		return parentObj;
	}

	public void setParentObj(EntityPOJO parentObj) {
		this.parentObj = parentObj;
	}

	public List<EntityPOJO> getChilds() {
		return childs;
	}

	public void setChilds(List<EntityPOJO> childs) {
		this.childs = childs;
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	@Override
	public String toString() {
		return "EntityPOJO [entity=" + entity + ", parent=" + parent + ", limit=" + limit + ", utilisation="
				+ utilisation + ", combindUtilisation=" + combindUtilisation + ",group=" + group + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((entity == null) ? 0 : entity.hashCode());
		long temp;
		temp = Double.doubleToLongBits(limit);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((parent == null) ? 0 : parent.hashCode());
		temp = Double.doubleToLongBits(utilisation);
		result = prime * result + (int) (temp ^ (temp >>> 32));
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
		EntityPOJO other = (EntityPOJO) obj;
		if (entity == null) {
			if (other.entity != null)
				return false;
		} else if (!entity.equals(other.entity))
			return false;
		if (Double.doubleToLongBits(limit) != Double.doubleToLongBits(other.limit))
			return false;
		if (parent == null) {
			if (other.parent != null)
				return false;
		} else if (!parent.equals(other.parent))
			return false;
		if (Double.doubleToLongBits(utilisation) != Double.doubleToLongBits(other.utilisation))
			return false;
		return true;
	}

}
