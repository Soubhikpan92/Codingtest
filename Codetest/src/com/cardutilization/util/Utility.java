package com.cardutilization.util;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.cardutilization.model.EntityPOJO;

public class Utility {

	public static List<EntityPOJO> getRoot(List<EntityPOJO> list) {

		return list.stream().filter(c -> c.getParent() == null).collect(Collectors.toList());
	}

	public static List<EntityPOJO> populateChild(List<EntityPOJO> list) {

		List<EntityPOJO> newList = new ArrayList<EntityPOJO>();
		list.forEach(item -> {
			item.setChilds(
					list.stream().filter(c -> item.getEntity().equals(c.getParent())).collect(Collectors.toList()));
			newList.add(item);
		});

		return newList;
	}

	public static List<EntityPOJO> getLowestChilds(List<EntityPOJO> list) {

		List<EntityPOJO> childes = new ArrayList<EntityPOJO>();
		list.forEach(item -> {
			if (list.stream().filter(c -> c.getParent().equals(item.getEntity())).collect(Collectors.toList())
					.isEmpty()) {

				childes.add(item);
			}
		});

		return childes;
	}

	public static EntityPOJO combinedUtilization(EntityPOJO entity) {

		EntityPOJO entityPOJO = new EntityPOJO();
		double totalChildUtilization = 0;

		for (EntityPOJO item : entity.getChilds()) {

			totalChildUtilization = totalChildUtilization + item.getUtilisation();
		}
		entityPOJO.setEntity(entity.getEntity());
		entityPOJO.setParent(entity.getParent());
		entityPOJO.setLimit(entity.getLimit());
		entityPOJO.setUtilisation(entity.getUtilisation());
		entityPOJO.setChilds(entity.getChilds());
		entityPOJO.setCombindUtilisation(
				entity.getUtilisation() + entity.getCombindUtilisation() + totalChildUtilization);

		return entityPOJO;

	}

	static String group = "";

	public static void traverseForGroup(EntityPOJO entity) {

		group = group + "/" + entity.getEntity();

		if (entity == null) {

		} else {
			if (entity.getChilds() != null) {
				for (EntityPOJO i : entity.getChilds())

				{
					traverseForGroup(i);

				}
			}
		}
	}

	public static List<EntityPOJO> populateGroup(List<EntityPOJO> root, List<EntityPOJO> inputList) {
		List<EntityPOJO> entitypojolist = new ArrayList<>();

		ArrayList<String> groups = new ArrayList<>();
		for (EntityPOJO item : root) {

			Utility.group = "";
			traverseForGroup(item);
			groups.add(Utility.group);

		}
		for (EntityPOJO item : inputList) {
			EntityPOJO entity = new EntityPOJO();

			entity.setEntity(item.getEntity());
			entity.setParent(item.getParent());
			entity.setLimit(item.getLimit());
			entity.setUtilisation(item.getUtilisation());
			entity.setChilds(item.getChilds());
			entity.setCombindUtilisation(item.getCombindUtilisation());
			groups.forEach(c -> {
				if (c.contains(item.getEntity())) {
					entity.setGroup(c);
				}

			});
			entitypojolist.add(entity);

		}
		return entitypojolist;

	}
}