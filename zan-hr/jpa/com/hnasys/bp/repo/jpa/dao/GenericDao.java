package com.epolleo.bp.repo.jpa.dao;

import java.util.Set;

import javax.persistence.Table;
import javax.persistence.metamodel.EntityType;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

@Repository
public class GenericDao extends AbstractDaoSupport {
	public Set<EntityType<?>> getPersistentEntities() {
		Set<EntityType<?>> entities = entityManager.getMetamodel().getEntities();
		return entities;
	}

	public Class<? extends Object> getPersistentClassByName(String tableName) {
		EntityType<?> enttity = getEntityByName(tableName);
		if(enttity == null){
			return null;
		}else{
			return enttity.getJavaType();
		}
	}

	public EntityType<?> getEntityByName(String tableName) {
		Set<EntityType<?>> entities = entityManager.getMetamodel().getEntities();
		EntityType<?> entity = null;
		for (EntityType<?> entityType : entities) {
			String entityName = entityType.getName();
			if (StringUtils.equals(tableName, entityName)) {
				entity = entityType;
				break;
			}
		}
		for (EntityType<?> entityType : entities) {
			Class<?> javaType = entityType.getJavaType();
			if (javaType.isAnnotationPresent(Table.class)) {
				String annotationName = javaType.getAnnotation(Table.class).name();
				if (StringUtils.equals(tableName, annotationName)) {
					entity = entityType;
					break;
				}
			}
		}
		return entity;
	}

	public EntityType<?> getGridConfig(String gridId, String tableName) {
		if(gridId.endsWith("internal.table")){
			EntityType<?> entity = getEntityByName(tableName);
			if(entity == null){
				return null;
			}
			return entity;
		}
		return  null;
	}

}
