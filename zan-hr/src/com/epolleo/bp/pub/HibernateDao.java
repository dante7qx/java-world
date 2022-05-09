package com.epolleo.bp.pub;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * AbstractHibernateDao
 * <p>
 * Date: 2012-11-09,10:59:49 +0800
 * 
 * @author Song Sun
 * @version 1.0
 */
@SuppressWarnings("unchecked")
public class HibernateDao<T, ID extends Serializable> extends
    HibernateDaoSupport {
    /** The entity class. */
    private Class<T> entityClass;

    @Resource(name = "sessionFactory")
    public void setSuperSessionFactory(SessionFactory sessionFactory) {
        super.setSessionFactory(sessionFactory);
    }

    public HibernateDao() {
        Type t = getClass().getGenericSuperclass();
        if (t instanceof ParameterizedType) {
            Type[] params = ((ParameterizedType) t).getActualTypeArguments();
            if (params[0] instanceof Class) {
                entityClass = (Class<T>) params[0];
                return;
            }
        }

        entityClass = (Class<T>) Object.class;
    }

    public HibernateDao(Class<T> clazz) {
        entityClass = clazz;
    }

    public void save(Object object) {
        this.getHibernateTemplate().save(object);
    }

    public void update(Object object) {
        this.getHibernateTemplate().update(object);
    }

    public T load(Object id) {
        return (T) this.getHibernateTemplate().get(entityClass, (ID) id);
    }

    public void delete(Object object) {
        this.getHibernateTemplate().delete(object);
    }

    public T findById(Object id) {
        if (id == null) {
            throw new NullPointerException("Null Id.");
        }

        Object object = this.getHibernateTemplate().get(
            this.entityClass.getName(), (ID) id);
        if (object == null)
            return null;
        return (T) object;
    }

    public List<T> findAll() {
        String hql = "from " + this.entityClass.getName();
        return this.getHibernateTemplate().find(hql);
    }

    public Query createQuery(final String str, Object... params) {
        Query query = getCurrentSession().createQuery(str);
        if (params != null) {
            for (int i = 0; i < params.length; i++) {
                query.setParameter(i, params[i]);
            }
        }
        return query;
    }

    public Query createQuery(final String str, Map<String, ?> params) {
        Query query = null;
        query = getCurrentSession().createQuery(str);
        if (params != null) {
            for (Map.Entry<String, ?> entry : params.entrySet()) {
                if (entry.getValue() != null
                    && entry.getValue() instanceof Collection) {
                    query.setParameterList(entry.getKey(),
                        (Collection) entry.getValue());
                } else if (entry.getValue() != null
                    && entry.getValue().toString().trim().length() > 0) {
                    query.setParameter(entry.getKey(), entry.getValue());
                }
            }
        }

        return query;
    }

    public <X> X findUnique(String queryString, Map<String, ?> params) {
        return (X) createQuery(queryString, params).uniqueResult();
    }

    public PagingResult findPage(PagingForm pf, String hql,
        Map<String, Object> params) {
        Query query = createQuery(hql, params);
        int total = query.list().size();
        query.setFirstResult(pf.getSkip());
        query.setMaxResults(pf.getPageSize());
        List<T> results = query.list();
        return new PagingResult<T>(results, total);
    }

    public PagingResult findPage(PagingForm pf, Criteria criteria) {
        int total = criteria.list().size();
        criteria.setFirstResult(pf.getSkip());
        criteria.setMaxResults(pf.getPageSize());
        List<T> results = criteria.list();
        return new PagingResult<T>(results, total);
    }

    public Session getCurrentSession() {
        return this.getHibernateTemplate().getSessionFactory()
            .getCurrentSession();
    }

    public Class<T> getEntityClass() {
        return (Class<T>) entityClass;
    }

    public void setEntityClass(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

}
