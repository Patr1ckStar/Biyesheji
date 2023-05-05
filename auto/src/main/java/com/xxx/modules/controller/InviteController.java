package com.xxx.modules.controller;;
import com.xxx.modules.service.InviteService;
import com.xxx.modules.entity.Invite;
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
 * invite
 *
 * @author Mark sunlightcs@gmail.com
 * @since 1.0.0 2023-02-07
 */
@RestController
@RequestMapping("/common/invite")
@Api(tags="invite")
public class InviteController {
    @Autowired
    private InviteService inviteService;

    /**
    *  获取所有招聘人员
    * @param pageNum
    * @param pageSize
    * @param invite
    * @return
    */

    @GetMapping("/getInviteInfo")
    @ApiOperation("获取所有招聘人员")
    public Result getInviteInfo(Invite invite, @RequestParam(value="pageNum",defaultValue = "1") Integer pageNum,
                                      @RequestParam(value="pageSize",defaultValue = "10") Integer pageSize){
        PageInfo<Invite> data = inviteService.selectInviteInfo(invite,pageNum,pageSize);
        return ResultUtil.success(1,"成功",data);
    }

    /**
    * 保存招聘人员
    * @param invite
    * @return
    */
    @PostMapping("/saveInviteInfo")
    @ApiOperation("保存招聘人员")
    public Result saveInviteInfo(@RequestBody Invite invite){
        inviteService.saveInviteInfo(invite);
        return ResultUtil.success(1,"成功",null);
    }


    /**
     * 更新招聘人员
     * @param invite
     * @return
     */

    @PutMapping("/updateInviteInfo")
    @ApiOperation("更新招聘人员")
    public Result updateInviteInfo(@RequestBody Invite invite){
        inviteService.updateInviteInfo(invite);
        return ResultUtil.success(1,"成功",null);
    }

    /**
     * 根据id删除招聘人员
     * @param id
     * @return
     */
    @DeleteMapping("/delInviteInfo")
    @ApiOperation("根据id删除招聘人员")
    public Result delInviteInfo(Integer id){
        inviteService.delInviteInfo(id);
        return ResultUtil.success(1,"成功",null);
    }

    /**
     * 根据id集合批量删除招聘人员
     * @param idList
     * @return
     */
    @DeleteMapping("/delBatchInviteInfo")
    @ApiOperation("根据id集合批量删除招聘人员")
    public Result delBatchInviteInfo(String idList){
        inviteService.delBatchInviteInfo(idList);
        return ResultUtil.success(1,"成功",null);
    }

}