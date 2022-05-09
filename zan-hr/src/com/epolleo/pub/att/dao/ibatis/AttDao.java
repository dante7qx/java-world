package com.epolleo.pub.att.dao.ibatis;

import java.io.Serializable;
import java.util.List;

import com.epolleo.bp.pub.AbstractDao;
import com.epolleo.bp.pub.PagingForm;
import com.epolleo.bp.pub.PagingResult;
import com.epolleo.pub.att.bean.AttBean;

public class AttDao extends AbstractDao<AttBean> {
	/**
	 * @param entity
	 *            新增附件表对象
	 * @return 新增成功的附件表
	 * @see AttBean
	 */
	@Override
	public AttBean save(AttBean entity) {
		save("insertAtt", entity);
		return find(entity.getId());
	}

	/**
	 * @param entityId
	 *            待删除附件表的关键值（比如主键Key）
	 * @return 如果删除成功，返回1
	 */
	@Override
	public int delete(Serializable entityId) {
		return delete("deleteAtt", entityId);
	}

	/**
	 * @param entity
	 *            修改附件表对象
	 * @return 如果修改成功，返回1
	 * @see AttBean
	 */
	@Override
	public int update(AttBean entity) {
		return update("updateAtt", entity);
	}

	public int updateForName(AttBean entity) {
		return this.getSqlMapClientTemplate().update("updateForName", entity);
	}

	/**
	 * @param lotId
	 *            附件批次号,查询某个批次的所有附件
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<AttBean> queryByLotId(String lotId) {
		return this.getSqlMapClientTemplate().queryForList("queryBylotId",
				lotId);
	}

	/**
	 * @param pagingForm
	 *            分页及查询过滤参数
	 * @return 指定页及条件的查询结果
	 * @see PagingForm
	 */
	@Override
	public PagingResult<AttBean> findPaging(PagingForm pagingForm, boolean fuzzy) {
		return findPaging("queryAttPage", pagingForm, false);
	}

	/**
	 * @return 查询附件表的所有记录
	 * @see AttBean
	 */
	@Override
	public List<AttBean> findAll() {
		return findAll("queryAttList");
	}

	/**
	 * @return 返回指定关键值（比如主键Key）的附件表
	 * @see AttBean
	 */
	@Override
	public AttBean find(Serializable id) {
		return find("queryAttById", id);
	}

	@SuppressWarnings("unchecked")
	public List<Long> getIdByLotId(String lotId) {
		return this.getSqlMapClientTemplate().queryForList("selectByLotId",
				lotId);
	}

	public List<AttBean> queryByLotIds(List<String> lotids) {
		return (List<AttBean>) getSqlMapClientTemplate().queryForListArgs(
				"queryAttByLotIds", lotids);
	}
}
