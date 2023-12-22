package com.ruoyi.project.system.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.project.system.domain.SysRole;
import com.ruoyi.project.system.domain.SysUser;
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
    /**
     * 查询员工绩效
     * 
     * @param empId 员工绩效主键
     * @return 员工绩效
     */
    @Override
    public EmpPerf selectEmpPerfByEmpId(Long empId)
    {
        return empPerfMapper.selectEmpPerfByEmpId(empId);
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

        Long userid = SecurityUtils.getUserId();
        SysRole role = sysRoleService.selectRoleByUserId(userid);
        if(role != null){
            rk = role.getRoleKey();
        }
        if(YG.equals(rk)){
            empPerf.setEmployeeNumber(SecurityUtils.getUsername());
        }else if(SZ.equals(rk)){
            SysUser sysuser =  userService.selectUserById(userid);
            Long deptId = sysuser.getDeptId();
            empPerf.setEmpDeptid(deptId);
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
        return empPerfMapper.deleteEmpPerfByEmpId(empId);
    }
}
