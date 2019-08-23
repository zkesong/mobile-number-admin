package cn.qydx.hmdj.controller;

import cn.qydx.hmdj.constant.ResponseCode;
import cn.qydx.hmdj.dao.UserInfoMapper;
import cn.qydx.hmdj.model.UserInfo;
import cn.qydx.hmdj.utils.RedisUtil;
import cn.qydx.hmdj.utils.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@RequestMapping("index")
@Controller
public class IndexController {

    @Autowired
    private UserInfoMapper mapper;

    @Autowired
    private RedisUtil util;

    @RequestMapping("")
    public String index(@CookieValue(value = "sessionId", required = false) String sessionId,
                        @RequestParam(value = "logout",required = false) Integer logout){
        if(logout!=null&&logout.equals(1)){
            util.del(sessionId);
            return "login";
        }
        if(util.hasKey(sessionId)){
            return "index";
        }
        return "login";
    }

    @RequestMapping("/userInfo")
    @ResponseBody
    public Response<UserInfo> getUserInfo(@CookieValue("sessionId") String sessionId){
        UserInfo userInfo = (UserInfo) util.get(sessionId);
        return new Response<>(userInfo, ResponseCode.REQUEST_SUCCESS);
    }

    @PostMapping("login")
    @ResponseBody
    public Response<String> login(@RequestParam String name, @RequestParam String passwd, HttpServletResponse response){
        UserInfo info = mapper.getUserInfoByName(name);
        if(passwd!=null && info != null &&passwd.equals(info.getPasswd())){
            info.setPasswd("******");
            String id = UUID.randomUUID().toString();
            String head = "jessionid";
            String sessionId = head + ":" + id;
            Cookie cookie = new Cookie("sessionId", sessionId);
            response.addCookie(cookie);
            util.set(sessionId, info, 30 * 60);
            return new Response<>("success", ResponseCode.REQUEST_SUCCESS);
        }
        return new Response<>("failed", ResponseCode.REQUEST_FAILED);
    }
}
