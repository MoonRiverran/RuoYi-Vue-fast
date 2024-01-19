package com.ruoyi.project.system.controller;

import java.util.*;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.project.system.domain.SysRole;
import com.ruoyi.project.system.domain.SysUser;
import com.ruoyi.project.system.service.ISysUserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.framework.aspectj.lang.annotation.Log;
import com.ruoyi.framework.aspectj.lang.enums.BusinessType;
import com.ruoyi.project.system.domain.EmpPerf;
import com.ruoyi.project.system.service.IEmpPerfService;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.web.page.TableDataInfo;

/**
 * 员工绩效Controller
 * 
 * @author ruoyi
 * @date 2023-12-14
 */
@RestController
@RequestMapping("/system/perf")
public class EmpPerfController extends BaseController
{
    @Autowired
    private IEmpPerfService empPerfService;
    @Autowired
    private ISysUserService userService;
    /**
     * 查询员工绩效列表
     */
    @PreAuthorize("@ss.hasPermi('system:perf:list')")
    @GetMapping("/list")
    public TableDataInfo list(EmpPerf empPerf)
    {
        startPage();
        List<EmpPerf> list = empPerfService.selectEmpPerfList(empPerf);
        return getDataTable(list);
    }

    /**
     * 查询员工绩效列表
     */
    @PreAuthorize("@ss.hasPermi('system:perf:list')")
    @GetMapping("/charts")
    public AjaxResult getMonthWorkHour(EmpPerf empPerf) {
        startPage();
        String deptID = "";
        if(empPerf.getEmpDeptid() != null){
            deptID = String.valueOf(empPerf.getEmpDeptid());
        }else{
            deptID = String.valueOf(SecurityUtils.getDeptId());
        }
        List<EmpPerf> nameIds = empPerfService.getEmpNameAndId(deptID);
        List<EmpPerf> list = empPerfService.getMonthWorkHour(deptID);

        Map<String, Map<String, Object>> employeeInfoMap = new LinkedHashMap<>();

        for (EmpPerf emp : nameIds) {
            Map<String, Object> employee = new HashMap<>();
            employee.put("name", emp.getEmployeeName());
            employee.put("id", emp.getEmployeeNumber());

            List<Map<String, Object>> workerData = new ArrayList<>();
            employee.put("workerData", workerData);

            employeeInfoMap.put(emp.getEmployeeNumber(), employee);
        }

        for (EmpPerf emp : list) {
            String id = emp.getEmployeeNumber();
            Map<String, Object> employee = employeeInfoMap.get(id);
            if (employee != null) {
                List<Map<String, Object>> workerData = (List<Map<String, Object>>) employee.get("workerData");
                Map<String, Object> project = new HashMap<>();
                project.put("name", emp.getWorkTypeName());
                project.put("value", emp.getWorkDuration());
                workerData.add(project);
            }
        }

        List<Map<String, Object>> employeeInfo = new ArrayList<>(employeeInfoMap.values());

        return success(employeeInfo);
    }


    /**
     * 查询员工绩效列表
     */
    @PreAuthorize("@ss.hasPermi('system:perf:list')")
    @GetMapping("/userList")
    public AjaxResult userList() {
        Long userid = SecurityUtils.getUserId();
        SysUser sysuser =  userService.selectUserById(userid);
        String deptId = String.valueOf(sysuser.getDeptId());
        List<SysUser> nameIds = userService.getEmpNameAndId(deptId);
        return success(nameIds);
    }
    /**
     * 导出员工绩效列表
     */
    @PreAuthorize("@ss.hasPermi('system:perf:export')")
    @Log(title = "员工绩效", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, EmpPerf empPerf)
    {
        List<EmpPerf> list = empPerfService.selectEmpPerfList(empPerf);
        ExcelUtil<EmpPerf> util = new ExcelUtil<EmpPerf>(EmpPerf.class);
        util.exportExcel(response, list, "员工绩效数据");
    }

    /**
     * 获取员工绩效详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:perf:query')")
    @GetMapping(value = "/{empId}")
    public AjaxResult getInfo(@PathVariable("empId") Long empId)
    {
        return success(empPerfService.selectEmpPerfByEmpId(empId));
    }

    /**
     * 新增员工绩效
     */
    @PreAuthorize("@ss.hasPermi('system:perf:add')")
    @Log(title = "员工绩效", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody EmpPerf empPerf)
    {
        return toAjax(empPerfService.insertEmpPerf(empPerf));
    }

    /**
     * 修改员工绩效
     */
    @PreAuthorize("@ss.hasPermi('system:perf:edit')")
    @Log(title = "员工绩效", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody EmpPerf empPerf)
    {
        return toAjax(empPerfService.updateEmpPerf(empPerf));
    }

    /**
     * 删除员工绩效
     */
    @PreAuthorize("@ss.hasPermi('system:perf:remove')")
    @Log(title = "员工绩效", businessType = BusinessType.DELETE)
	@DeleteMapping("/{empIds}")
    public AjaxResult remove(@PathVariable Long[] empIds)
    {
        return toAjax(empPerfService.deleteEmpPerfByEmpIds(empIds));
    }
}
