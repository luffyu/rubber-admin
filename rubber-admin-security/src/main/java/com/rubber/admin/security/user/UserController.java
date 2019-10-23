package com.rubber.admin.security.user;

import cn.hutool.core.util.StrUtil;
import com.luffyu.piece.utils.result.ResultMsg;
import com.rubber.admin.security.user.bean.UserInfo;
import com.rubber.admin.security.user.service.RubberUserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @author luffyu
 * Created on 2019-10-23
 */
@RestController
@RequestMapping("/u")
public class UserController {

    @Resource
    private RubberUserService rubberUserService;


    /**
     * 获取用户的全部信息接口
     * @param request http请求的接口
     * @return
     */
    @GetMapping("/info")
    public ResultMsg info(HttpServletRequest request){
        UserInfo loginUserInfo = rubberUserService.getLoginUserInfo(request);
        return ResultMsg.success(loginUserInfo);
    }

}
