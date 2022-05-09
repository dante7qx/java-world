package com.epolleo.hr.dictmnt.bean;

import java.util.Date;

/**
 * @Description: 基础数据字典表的Bean
 * 
 * @author dante
 * @date 2015-09-23 下午11:45:59
 *
 */
public class CodeDictBean {

    /**
     * @Fields id : Id
     */
    private Long id;
       
    /**
     * @Fields type : 类型
     */
    private String type;
       
    /**
     * @Fields typeName : 类型名称
     */
    private String typeName;
       
    /**
     * @Fields code : 编码
     */
    private String code;
       
    /**
     * @Fields value : 名称
     */
    private String value;
       
    /**
     * @Fields showOrder : 显示顺序
     */
    private Integer showOrder;
       
    /**
     * @Fields updateBy : 更新人
     */
    private String updateBy;
       
    /**
     * @Fields updateDate : 更新时间
     */
    private Date updateDate;
       

    public Long getId() {
        return this.id;
    }

	public void setId(Long id) {
	    this.id = id;
	}
    
    public String getType() {
        return this.type;
    }
    
	public void setType(String type) {
	    this.type = type;
	}
    
    public String getTypeName() {
        return this.typeName;
    }
    
	public void setTypeName(String typeName) {
	    this.typeName = typeName;
	}
    
    public String getCode() {
        return this.code;
    }
    
	public void setCode(String code) {
	    this.code = code;
	}
    
    public String getValue() {
        return this.value;
    }
    
	public void setValue(String value) {
	    this.value = value;
	}
    
    public Integer getShowOrder() {
        return this.showOrder;
    }
    
	public void setShowOrder(Integer showOrder) {
	    this.showOrder = showOrder;
	}
    
    public String getUpdateBy() {
        return this.updateBy;
    }
    
	public void setUpdateBy(String updateBy) {
	    this.updateBy = updateBy;
	}
    
    public Date getUpdateDate() {
        return this.updateDate;
    }
    
	public void setUpdateDate(Date updateDate) {
	    this.updateDate = updateDate;
	}
    
}
