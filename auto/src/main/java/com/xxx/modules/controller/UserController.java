package com.xxx.modules.controller;


import com.github.pagehelper.PageInfo;
import com.xxx.modules.entity.User;
import com.xxx.modules.service.UserService;
import com.xxx.modules.utils.Result;
import com.xxx.modules.utils.ResultUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author XXX
 * @since 2021-03-16
 */
@RestController
@RequestMapping("/personnel/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/getUserInfo")
    public Result getUserInfo(User user, @RequestParam(value="pageNum",defaultValue = "1") Integer pageNum,
                              @RequestParam(value="pageSize",defaultValue = "10") Integer pageSize,
                              HttpServletRequest request){
        try{
            StringBuilder imageUrl = new StringBuilder();
            imageUrl.append(request.getScheme()).append("://")
                    .append(request.getServerName()).append(":")
                    .append(request.getServerPort()).append("/image/");
            String baseUrl = imageUrl.toString();
            PageInfo<User> data = userService.selectUserInfo(user,pageNum,pageSize);
            return ResultUtil.successForDataAndImage(1,"成功",data,baseUrl);
        }catch (Exception e){
            e.printStackTrace();
            return ResultUtil.error(-1,"失败");
        }
    }

    @PostMapping("/saveAndUpdateUserInfo")
    public Result saveAndUpdateUserInfo(User user){
        try {
                if(user.getJobId()==null){
                    return ResultUtil.error(-1,"失败");
                }
            Integer count = userService.saveAndUpdateUserInfo(user);
            return ResultUtil.success(1,"成功",count);
        }catch (Exception e){
            e.printStackTrace();
            return ResultUtil.error(-1,"失败");
        }
    }

    @DeleteMapping("/delUserInfo")
    public Result delUserInfo(Integer id){
        try {
            Integer count = userService.delUserInfo(id);
            return ResultUtil.success(1,"成功",count);
        }catch (Exception e){
            e.printStackTrace();
            return ResultUtil.error(-1,"失败");
        }
    }

    @GetMapping("/getUserInfoById")
    public Result getUserInfoById(Integer id,HttpServletRequest request){
        try {
            StringBuilder imageUrl = new StringBuilder();
            imageUrl.append(request.getScheme()).append("://")
                    .append(request.getServerName()).append(":")
                    .append(request.getServerPort()).append("/image/");
            String baseUrl = imageUrl.toString();
            User user = userService.selectUserInfoById(id);
            return ResultUtil.successForDataAndImage(1,"成功",user,baseUrl);
        }catch (Exception e){
            e.printStackTrace();
            return ResultUtil.error(-1,"失败");
        }
    }

    @PostMapping("/login")
    @ApiOperation("用户登录接口")
    public Result login(String userName, String password){
        try {
            User user = userService.login(userName,password);
            if (user == null){
                return ResultUtil.error(-1,"登录失败,用户名或者密码错误");
            }else {
                return ResultUtil.success(1,"登录成功",user);
            }
        }catch (Exception e){
            e.printStackTrace();
            return ResultUtil.error(-1,"发生异常!");
        }
    }

    @PostMapping("/releaseBonus")
    public Result releaseBonus(User user){
        try{
            userService.releaseBonus(user);
            return ResultUtil.success(1,"成共",null);
        }catch (Exception e){
            e.printStackTrace();
            return ResultUtil.error(-1,"发生异常!");
        }
    }

//    @GetMapping("/getUserNewMessageInfo")
//    public Result getUserNewMessageInfo(Integer userId){
//        try{
//            PageInfo<User> data = userService.selectNewMessageInfo(userId);
//            return ResultUtil.success(1,"成功",data);
//        }catch (Exception e){
//            e.printStackTrace();
//            return ResultUtil.error(-1,"失败");
//        }
//    }

}

