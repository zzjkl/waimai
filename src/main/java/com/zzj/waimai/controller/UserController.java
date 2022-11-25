package com.zzj.waimai.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zzj.waimai.common.R;
import com.zzj.waimai.dto.UserDto;
import com.zzj.waimai.pojo.User;
import com.zzj.waimai.service.UserService;
import com.zzj.waimai.util.ValidateCodeUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.concurrent.TimeUnit;

/**
 * 用户信息 前端控制器
 */
@RestController
@Slf4j
@RequestMapping("/user")
public class UserController {
    @Autowired
   private UserService userService;
    @Autowired
    private RedisTemplate redisTemplate;


    /**
     * 发送手机短信验证码
     * @param user
     * @return
     */
    @PostMapping("/sendMsg")
    public R<String> sendMsg(@RequestBody User user, HttpSession session){
        String phone = user.getPhone();
        if(StringUtils.isNotEmpty(phone)){
            //生成随机4位验证码
            String code = ValidateCodeUtils.generateValidateCode(4).toString();
            log.info("code={}",code);
           // 调用阿里云提供的短信服务API完成发送短信
                    //SMSUti1s..sendMessage("..... "，，phone,,code);
            //需要将生成的验证码保存到Session
           // session.setAttribute(phone,code);
            // return R.success("手机验证码短信发送成功");
            redisTemplate.opsForValue().set(phone, code, 5, TimeUnit.MINUTES);
            return R.success("验证码发送成功，有效时间为5分钟");
        }
        return  R.error("短信发送失败");
    }
    /**
     * 前台登陆功能
     * @param userDto 对User类进行了扩展，原有user类没有code属性
     * @param codeInSession 从session中拿code（验证码），方便后需验证
     * @return
     */
    @PostMapping("/login")
    public R<String> login(@RequestBody UserDto userDto, HttpSession codeInSession) {
        //拿到验证码和手机号
        String code = userDto.getCode();
        String phone = userDto.getPhone();
        //从session中拿到对应的验证码
        //String tempCode = (String) codeInSession.getAttribute(phone);
        //从Redis中拿验证
        String tempCode = (String) redisTemplate.opsForValue().get(phone);
        //验证码相等
        if (code.equals(tempCode) && codeInSession != null) {
            //是否为新用户，如果是新用户顺手注册了
            LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
            lambdaQueryWrapper.eq(User::getPhone, phone);
            //只能用getOne来匹配，不能用getById，因为没有Id给你匹配，都是空的
            User user = userService.getOne(lambdaQueryWrapper);
            if (user==null){
                //用户不存在，注册一下，注册完放行
                //用户的ID是有自动生成策略的，不用管
                user = new User();
                user.setPhone(phone);
                user.setStatus(1);
                userService.save(user);
            }
            //已经可以登陆放行了，就删了session中的code再放行，减轻session压力
           // codeInSession.removeAttribute(phone);
            //把用户的ID存入Session，留给过滤器进行验证放行
            codeInSession.setAttribute("user", user.getId());
            //此时已经登陆成功，向Redis中存入userId的信息留给过滤器进行验证放行
            redisTemplate.opsForValue().set("user", user.getId());
            //再删掉验证码
            redisTemplate.delete(phone);
            return R.success("登陆成功，欢迎~");
        }
        return R.error("验证码错误");
    }
}
