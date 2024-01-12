package com.ruoyi.project.system.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.framework.aspectj.lang.annotation.Excel;
import com.ruoyi.framework.web.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;

/**
 * 员工绩效对象 emp_perf
 *
 * @author ruoyi
 * @date 2023-12-20
 */
public class EmpPerf extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键id */
    private Long empId;

    /** 员工姓名 */
    @Excel(name = "员工姓名")
    private String employeeName;

    /** 员工工号 */
    @Excel(name = "员工工号")
    private String employeeNumber;

    /** 员工部门ID */
    @Excel(name = "员工部门ID")
    private Long empDeptid;

    /** 工作类型（数据字典） */
    @Excel(name = "工作类型", dictType = "work_type")
    private String workType;
    private String workTypeName;

    /** 项目类型（数据字典） */
    @Excel(name = "项目类型", dictType = "project_type")
    private String projectType;

    /** 项目说明 */
    @Excel(name = "项目说明")
    private String projectDescription;

    /** 目标 */
    @Excel(name = "目标")
    private String goal;

    /** 完成比例（0-100%） */
    @Excel(name = "完成比例", suffix="%")
    private Integer completionRatio;

    /** 完成结果 */
    @Excel(name = "完成结果")
    private String completionResult;

    /** 完成日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "完成日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date completionDate;
    private String searchDate;

    /** 工作时长（h） */
    @Excel(name = "工作时长", suffix="h")
    private Integer  workDuration;

    /** 扩展字段1 */
    @Excel(name = "扩展字段1")
    private String extensionField1;

    /** 扩展字段2 */
    @Excel(name = "扩展字段2")
    private String extensionField2;

    /** 扩展字段3 */
    @Excel(name = "扩展字段3")
    private String extensionField3;

    public EmpPerf() {
    }

    public void setEmpId(Long empId)
    {
        this.empId = empId;
    }

    public Long getEmpId()
    {
        return empId;
    }
    public void setEmployeeName(String employeeName)
    {
        this.employeeName = employeeName;
    }

    public String getEmployeeName()
    {
        return employeeName;
    }
    public void setEmployeeNumber(String employeeNumber)
    {
        this.employeeNumber = employeeNumber;
    }

    public String getEmployeeNumber()
    {
        return employeeNumber;
    }
    public void setEmpDeptid(Long empDeptid)
    {
        this.empDeptid = empDeptid;
    }

    public Long getEmpDeptid()
    {
        return empDeptid;
    }
    public void setWorkType(String workType)
    {
        this.workType = workType;
    }

    public String getWorkType()
    {
        return workType;
    }

    public String getWorkTypeName() {
        return workTypeName;
    }

    public void setWorkTypeName(String workTypeName) {
        this.workTypeName = workTypeName;
    }

    public void setProjectType(String projectType)
    {
        this.projectType = projectType;
    }

    public String getProjectType()
    {
        return projectType;
    }
    public void setProjectDescription(String projectDescription)
    {
        this.projectDescription = projectDescription;
    }

    public String getProjectDescription()
    {
        return projectDescription;
    }
    public void setGoal(String goal)
    {
        this.goal = goal;
    }

    public String getGoal()
    {
        return goal;
    }
    public void setCompletionRatio(Integer completionRatio)
    {
        this.completionRatio = completionRatio;
    }

    public Integer getCompletionRatio()
    {
        return completionRatio;
    }
    public void setCompletionResult(String completionResult)
    {
        this.completionResult = completionResult;
    }

    public String getCompletionResult()
    {
        return completionResult;
    }
    public void setCompletionDate(Date completionDate)
    {
        this.completionDate = completionDate;
    }

    public Date getCompletionDate()
    {
        return completionDate;
    }
    public void setWorkDuration(Integer workDuration)
    {
        this.workDuration = workDuration;
    }

    public Integer getWorkDuration()
    {
        return workDuration;
    }
    public void setExtensionField1(String extensionField1)
    {
        this.extensionField1 = extensionField1;
    }

    public String getExtensionField1()
    {
        return extensionField1;
    }
    public void setExtensionField2(String extensionField2)
    {
        this.extensionField2 = extensionField2;
    }

    public String getExtensionField2()
    {
        return extensionField2;
    }
    public void setExtensionField3(String extensionField3)
    {
        this.extensionField3 = extensionField3;
    }

    public String getExtensionField3()
    {
        return extensionField3;
    }
    public String getSearchDate() {
        return searchDate;
    }

    public void setSearchDate(String searchDate) {
        this.searchDate = searchDate;
    }
    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                .append("empId", getEmpId())
                .append("employeeName", getEmployeeName())
                .append("employeeNumber", getEmployeeNumber())
                .append("empDeptid", getEmpDeptid())
                .append("workType", getWorkType())
                .append("workTypeName", getWorkTypeName())
                .append("projectType", getProjectType())
                .append("projectDescription", getProjectDescription())
                .append("goal", getGoal())
                .append("completionRatio", getCompletionRatio())
                .append("completionResult", getCompletionResult())
                .append("completionDate", getCompletionDate())
                .append("workDuration", getWorkDuration())
                .append("extensionField1", getExtensionField1())
                .append("extensionField2", getExtensionField2())
                .append("extensionField3", getExtensionField3())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .append("remark", getRemark())
                .append("searchDate", getSearchDate())
                .toString();
    }
}
