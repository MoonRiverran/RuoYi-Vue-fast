package com.ruoyi.project.system.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;
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
    public AjaxResult getMonthWorkHour(String deptID) {
        startPage();

        List<EmpPerf> list = empPerfService.getMonthWorkHour(deptID);
        List<EmpPerf> nameIds = empPerfService.getEmpNameAndId(deptID);

        Map<String, Map<String, Object>> employeeInfoMap = new HashMap<>();

        for (EmpPerf empPerf : nameIds) {
            Map<String, Object> employee = new HashMap<>();
            employee.put("name", empPerf.getEmployeeName());
            employee.put("id", empPerf.getEmployeeNumber());

            List<Map<String, Object>> workerData = new ArrayList<>();
            employee.put("workerData", workerData);

            employeeInfoMap.put(empPerf.getEmployeeNumber(), employee);
        }

        for (EmpPerf empPerf : list) {
            String id = empPerf.getEmployeeNumber();
            Map<String, Object> employee = employeeInfoMap.get(id);
            if (employee != null) {
                List<Map<String, Object>> workerData = (List<Map<String, Object>>) employee.get("workerData");
                Map<String, Object> project = new HashMap<>();
                project.put("name", empPerf.getWorkTypeName());
                project.put("value", empPerf.getWorkDuration());
                workerData.add(project);
            }
        }

        List<Map<String, Object>> employeeInfo = new ArrayList<>(employeeInfoMap.values());

        return success(employeeInfo);
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
