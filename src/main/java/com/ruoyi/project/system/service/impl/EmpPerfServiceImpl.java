package com.ruoyi.project.system.service.impl;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.PageUtils;
import com.ruoyi.project.system.domain.SysRole;
import com.ruoyi.project.system.domain.SysUser;
import com.ruoyi.project.system.mapper.SysDictDataMapper;
import com.ruoyi.project.system.service.ISysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.project.system.mapper.EmpPerfMapper;
import com.ruoyi.project.system.domain.EmpPerf;
import com.ruoyi.project.system.service.IEmpPerfService;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.project.system.service.ISysUserService;

/**
 * 员工绩效Service业务层处理
 * 
 * @author ruoyi
 * @date 2023-12-14
 */
@Service
public class EmpPerfServiceImpl implements IEmpPerfService 
{
    @Autowired
    private EmpPerfMapper empPerfMapper;

    @Autowired
    private ISysUserService userService;

    @Autowired
    private ISysRoleService sysRoleService;

    @Autowired
    private SysDictDataMapper dictDataMapper;

    /**
     * 查询员工绩效
     * 
     * @param empId 员工绩效主键
     * @return 员工绩效
     */
    @Override
    public EmpPerf selectEmpPerfByEmpId(Long empId)
    {
        canUpdateDel(empId);
        return empPerfMapper.selectEmpPerfByEmpId(empId);
    }

    private void canUpdateDel(Long empId) {
        String YG = "emp";
        String rk = "";
        Long userid = SecurityUtils.getUserId();
        SysRole role = sysRoleService.selectRoleByUserId(userid);
        if(role != null){
            rk = role.getRoleKey();
        }
        if(YG.equals(rk)) {
            String userName = SecurityUtils.getUsername();
            EmpPerf emp = empPerfMapper.selectEmpPerfByEmpId(empId);
            if (emp.getEmployeeNumber() != null && !emp.getEmployeeNumber().equals(userName)) {
                throw new ServiceException("只能处理自己的数据!");
            }
        }
    }

    /**
     * 查询员工绩效列表
     * 
     * @param empPerf 员工绩效
     * @return 员工绩效
     */
    @Override
    public List<EmpPerf> selectEmpPerfList(EmpPerf empPerf)
    {
        String YG = "emp";
        String SZ = "SZ";
        String rk = "";
        String en = "";
        if(empPerf.getEmployeeNumber() != null){
            en = empPerf.getEmployeeNumber();
        }
        Long userid = SecurityUtils.getUserId();
        PageUtils.clearPage();
        SysRole role = sysRoleService.selectRoleByUserId(userid);
        if(role != null){
            rk = role.getRoleKey();
        }
        if(YG.equals(rk) && en.isEmpty()){
            empPerf.setEmployeeNumber(SecurityUtils.getUsername());
        }else if(YG.equals(rk) && !en.isEmpty()){
            empPerf.setEmployeeNumber(en);
        }
        else if(SZ.equals(rk)){
            Long deptId = SecurityUtils.getDeptId();
            empPerf.setEmpDeptid(deptId);
        }

        PageUtils.startPage();
        return empPerfMapper.selectEmpPerfList(empPerf);
    }

    /**
     * 按类型查询员工绩效列表
     */
    public List<EmpPerf> personList(EmpPerf empPerf){
        if(empPerf.getWorkType() != null && !empPerf.getWorkType().isEmpty()){
            String workType = empPerf.getWorkType();
            String workTypeValue =  dictDataMapper.selectDictValue("work_type",workType);
            empPerf.setWorkType(workTypeValue);
        }
        return empPerfMapper.selectEmpPerfList(empPerf);
    }

    /**
     * 新增员工绩效
     * 
     * @param empPerf 员工绩效
     * @return 结果
     */
    @Override
    public int insertEmpPerf(EmpPerf empPerf)
    {
        String finished = "CR001";
        if(empPerf.getCompletionResult() != null && empPerf.getCompletionResult().equals(finished)){
            int finish = 100;
            empPerf.setCompletionRatio(finish);
        }

        String username = SecurityUtils.getUsername();
        Long userId = SecurityUtils.getUserId();
        SysUser sysuser =  userService.selectUserById(userId);
        Long deptId = sysuser.getDeptId();
        String nickName = sysuser.getNickName();
        empPerf.setEmployeeNumber(username);
        empPerf.setEmployeeName(nickName);
        empPerf.setEmpDeptid(deptId);
        empPerf.setCreateBy(username);
        empPerf.setCreateTime(DateUtils.getNowDate());

        return empPerfMapper.insertEmpPerf(empPerf);
    }

    /**
     * 修改员工绩效
     * 
     * @param empPerf 员工绩效
     * @return 结果
     */
    @Override
    public int updateEmpPerf(EmpPerf empPerf)
    {
        String finished = "CR001";
        if(empPerf.getCompletionResult() != null && empPerf.getCompletionResult().equals(finished)){
            int finish = 100;
            empPerf.setCompletionRatio(finish);
        }
        empPerf.setUpdateBy(SecurityUtils.getUsername());
        empPerf.setUpdateTime(DateUtils.getNowDate());
        return empPerfMapper.updateEmpPerf(empPerf);
    }

    /**
     * 批量删除员工绩效
     * 
     * @param empIds 需要删除的员工绩效主键
     * @return 结果
     */
    @Override
    public int deleteEmpPerfByEmpIds(Long[] empIds)
    {
        for (Long empId : empIds) {
            canUpdateDel(empId);
        }
        return empPerfMapper.deleteEmpPerfByEmpIds(empIds);
    }

    /**
     * 删除员工绩效信息
     * 
     * @param empId 员工绩效主键
     * @return 结果
     */
    @Override
    public int deleteEmpPerfByEmpId(Long empId)
    {
        canUpdateDel(empId);
        return empPerfMapper.deleteEmpPerfByEmpId(empId);
    }

    /**
     *  获取该部门所有员工的月工作时间
     *
     * @param deptID 部门ID
     * @return 结果
     */
    @Override
    public List<EmpPerf> getMonthWorkHour(String deptID) { return empPerfMapper.getMonthWorkHour(deptID); }

    /**
     *  获取该部门所有员工的月工作时间
     *
     * @param deptID 部门ID
     * @return 结果
     */
    @Override
    public List<EmpPerf> getEmpNameAndId(String deptID) { return empPerfMapper.getEmpNameAndId(deptID); }
}
