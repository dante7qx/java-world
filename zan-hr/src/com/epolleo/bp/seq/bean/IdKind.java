package com.epolleo.bp.seq.bean;

import com.epolleo.bp.seq.SeqDescr;

/**
 * IdKind定义了所有流水号
 * <p>
 * Date: 2012-06-19,17:44:48 +0800
 * 
 * @version 1.0
 */
public enum IdKind implements SeqDescr {
    AuditLogId("bp.AuditLogId", 100, "审计日志流水号"),
    RoleId("bp.RoleId", 5, "角色编号"),
    OrgTypeId("bp.OrgTypeId", 5, "组织机构类型编号"),
    OrgId("bp.OrgId", 5, "组织机构编号"),
    MenuId("bp.MenuId", 5, "菜单编码"),
    EmployeeId("bp.EmployeeId", 5, "员工编码"),
    TopicId("bp.TopicId", 5, "主题ID"),
    GroupId("bp.GroupId", 5, "板块ID"),
    TaskDefId("bp.TaskDefId", 5, "定时任务ID"),
    AnnounceId("bp.announce.announceId",5,"通告信息"),
    ReportRecordId("bp.ReportRecordId", 5, "报表记录ID"),
    LocId("bp.LocId", 5, "区域ID"),
    HolidayId("bp.HolidayId", 5, "节假日ID"),
    
    
    AttId("pub.att",5,"附件id"),
    HrEmployeeId("hr.employeeId", 5, "员工Id"),
    HrEmployeeCertificateId("hr.employeeCertificateId", 5, "员工资格证书Id"),
    HrEmployeeWorkId("hr.employeeWorkId", 5, "员工工作信息Id"),
    HrEmployeeRelationId("hr.employeeRelationId", 5, "员工家属Id"),
    HrEmployeeBenefitId("hr.employeeBenefitId", 5, "员工福利信息Id"),
    HrEmployeeOtherId("hr.employeeOtherId", 5, "员工补充信息Id")
    ;

    public final String kind;
    public final long delta;
    public final String name;
    private String expr;

    private IdKind(String kind, int delta, String name) {
        this(kind, delta, name, null);
    }

    private IdKind(String kind, int delta, String name, String expr) {
        this.kind = kind;
        this.delta = delta;
        this.name = name;
        this.expr = expr;
    }

    public String getId() {
        return kind;
    }

    public String getName() {
        return name;
    }

    public int getFetchSize() {
        return (int) delta;
    }

    public long getValue() {
        return 1;
    }

    public String getExpression() {
        return expr;
    }

}
