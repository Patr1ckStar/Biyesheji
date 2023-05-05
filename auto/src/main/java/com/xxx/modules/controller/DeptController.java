package com.xxx.modules.controller;;
import com.xxx.modules.service.DeptService;
import com.xxx.modules.entity.Dept;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;
import com.github.pagehelper.PageInfo;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;
import com.xxx.modules.utils.Result;
import com.xxx.modules.utils.ResultUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * dept
 *
 * @author Mark sunlightcs@gmail.com
 * @since 1.0.0 2023-02-07
 */
@RestController
@RequestMapping("/common/dept")
@Api(tags="dept")
public class DeptController {
    @Autowired
    private DeptService deptService;

    /**
    *  获取所有部门信息
    * @param pageNum
    * @param pageSize
    * @param dept
    * @return
    */

    @GetMapping("/getDeptInfo")
    @ApiOperation("获取所有部门信息")
    public Result getDeptInfo(Dept dept, @RequestParam(value="pageNum",defaultValue = "1") Integer pageNum,
                                      @RequestParam(value="pageSize",defaultValue = "10") Integer pageSize){
        PageInfo<Dept> data = deptService.selectDeptInfo(dept,pageNum,pageSize);
        return ResultUtil.success(1,"成功",data);
    }

    /**
    * 保存部门信息
    * @param dept
    * @return
    */
    @PostMapping("/saveDeptInfo")
    @ApiOperation("保存部门信息")
    public Result saveDeptInfo(@RequestBody Dept dept){
        deptService.saveDeptInfo(dept);
        return ResultUtil.success(1,"成功",null);
    }


    /**
     * 更新部门信息
     * @param dept
     * @return
     */

    @PutMapping("/updateDeptInfo")
    @ApiOperation("更新部门信息")
    public Result updateDeptInfo(@RequestBody Dept dept){
        deptService.updateDeptInfo(dept);
        return ResultUtil.success(1,"成功",null);
    }

    /**
     * 根据id删除部门信息
     * @param id
     * @return
     */
    @DeleteMapping("/delDeptInfo")
    @ApiOperation("根据id删除部门信息")
    public Result delDeptInfo(Integer id){
        deptService.delDeptInfo(id);
        return ResultUtil.success(1,"成功",null);
    }

    /**
     * 根据id集合批量删除部门信息
     * @param idList
     * @return
     */
    @DeleteMapping("/delBatchDeptInfo")
    @ApiOperation("根据id集合批量删除部门信息")
    public Result delBatchDeptInfo(String idList){
        deptService.delBatchDeptInfo(idList);
        return ResultUtil.success(1,"成功",null);
    }

}