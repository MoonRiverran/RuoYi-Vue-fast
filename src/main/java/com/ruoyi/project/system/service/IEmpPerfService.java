package com.ruoyi.project.system.service;

import java.util.List;
import com.ruoyi.project.system.domain.EmpPerf;

/**
 * 员工绩效Service接口
 * 
 * @author ruoyi
 * @date 2023-12-14
 */
public interface IEmpPerfService 
{
    /**
     * 查询员工绩效
     * 
     * @param empId 员工绩效主键
     * @return 员工绩效
     */
    public EmpPerf selectEmpPerfByEmpId(Long empId);

    /**
     * 查询员工绩效列表
     * 
     * @param empPerf 员工绩效
     * @return 员工绩效集合
     */
    public List<EmpPerf> selectEmpPerfList(EmpPerf empPerf);

    /**
     * 新增员工绩效
     * 
     * @param empPerf 员工绩效
     * @return 结果
     */
    public int insertEmpPerf(EmpPerf empPerf);

    /**
     * 修改员工绩效
     * 
     * @param empPerf 员工绩效
     * @return 结果
     */
    public int updateEmpPerf(EmpPerf empPerf);

    /**
     * 批量删除员工绩效
     * 
     * @param empIds 需要删除的员工绩效主键集合
     * @return 结果
     */
    public int deleteEmpPerfByEmpIds(Long[] empIds);

    /**
     * 删除员工绩效信息
     * 
     * @param empId 员工绩效主键
     * @return 结果
     */
    public int deleteEmpPerfByEmpId(Long empId);

    /**
     *  获取该部门所有员工的月工作时间
     *
     * @param deptID 部门ID
     * @return 结果
     */
    public List<EmpPerf> getMonthWorkHour(String deptID);

    /**
     *  获取该部门所有员工的id和名称
     *
     * @param deptID 部门ID
     * @return 结果
     */
    public List<EmpPerf> getEmpNameAndId(String deptID);

    /**
     * 按类型查询员工绩效列表
     */
    List<EmpPerf> personList(EmpPerf empPerf);
}
