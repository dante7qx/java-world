/**
 * style 1: 
 * com.epolleo.bp.repo.jpa.SysParamDao,<br/> 
 * com.epolleo.bp.repo.jpa.RoleFunctionDao,<br/>
 * com.epolleo.bp.repo.jdbc.SysParamDao,<br/> 
 * com.epolleo.bp.repo.jdbc.RoleFunctionDao<br/>
 * 
 * com.epolleo.bp.repo.jpa.po.SysParam,<br/>
 * com.epolleo.bp.repo.jpa.po.RoleFunction<br/>
 * 
 * style 2: 
 * com.epolleo.hp.sysparam.dao.jpa.SysParamDao,
 * com.epolleo.hp.sysparam.dao.jdbc.SysParamDao, 
 * com.epolleo.hp.role.dao.jpa.RoleFunctionDao,
 * com.epolleo.hp.role.dao.jdbc.RoleFunctionDao,
 * com.epolleo.hp.sysparam.po.SysParam,
 * com.epolleo.hp.role.po.RoleFunction
 * 
 * style1的缺点是看上去模块话不明确，所有的dao都在同一个包下，不过用了hibernate，dao层的代码并不会很多。
 * style2的缺点是po(persistent object)类的生成通常是一起生成到一个地方的，这样分开到不同的包名下管理会比较麻烦。
 * 
 */
package com.epolleo.bp.repo.jpa.dao;
