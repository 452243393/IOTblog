package com.yj.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yj.entity.Tag;
import com.yj.entity.User;
import com.yj.entity.vo.PageVo;
import com.yj.entity.vo.TagListVo;
import com.yj.entity.vo.UserVo;
import com.yj.exception.SystemException;
import com.yj.mapper.UserMapper;
import com.yj.service.UserService;
import com.yj.utils.AppHttpCodeEnum;
import com.yj.utils.BeanCopyUtils;
import com.yj.utils.ResponseResult;
import com.yj.utils.SecurityUtils;
import com.yj.entity.vo.UserInfoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;


@Service
public class UserServiceImpl extends ServiceImpl<UserMapper,User> implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User getById(Long createBy) {
        return userMapper.getById(createBy);
    }

    @Override
    public ResponseResult select(Long id) {
        User user = userMapper.getById(id);
        return ResponseResult.successResult(user);
    }

    @Override
    public ResponseResult userInfo() {
        //获取当前用户id
        Long userId = SecurityUtils.getUserId();
        //根据用户id查询用户信息
        User user = userMapper.getById(userId);
        //封装成UserInfoVo
        UserInfoVo vo = BeanCopyUtils.copyBean(user, UserInfoVo.class);
        return ResponseResult.successResult(vo);
    }

    @Override
    public ResponseResult register(User user) {
        //对数据进行非空判断 抛出异常
        if (!StringUtils.hasText(user.getUserName())) {
            throw new SystemException(AppHttpCodeEnum.USERNAME_NOT_NULL);
        }
        if (!StringUtils.hasText(user.getNickName())) {
            throw new SystemException(AppHttpCodeEnum.NICKNAME_NOT_NULL);
        }
        if (!StringUtils.hasText(user.getPassword())) {
            throw new SystemException(AppHttpCodeEnum.PASSWORD_NOT_NULL);
        }
        if (!StringUtils.hasText(user.getEmail())) {
            throw new SystemException(AppHttpCodeEnum.EMAIL_NOT_NULL);
        }
        //对数据进行是否重复判断
        int count = userMapper.getByUserName(user.getUserName());
        if (count > 0) {
            throw new SystemException(AppHttpCodeEnum.USERNAME_EXIT);
        }
        int count1 = userMapper.getByNickName(user.getNickName());
//        if(count1>0){
//            throw new SystemException(AppHttpCodeEnum.NICKNAME_EXIT)
//        }
        //把数据中密码进行加密
        String encodePassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodePassword);

        //存入数据库user表中
        int count2 = userMapper.insertUser(user);
        if (count2 > 0) {
            return ResponseResult.successResult();
        } else {
            throw new SystemException(AppHttpCodeEnum.SYSTEM_ERROR);
        }
    }

    @Override
    public ResponseResult updateUserInfo(User user) {
        int count = userMapper.updateUserInfo(user);
        if (count > 0) {
            return ResponseResult.successResult();
        } else {
            throw new SystemException(AppHttpCodeEnum.SYSTEM_ERROR);
        }
    }

    //查询全部
    @Override
    public ResponseResult queryUser(Integer pageNum, Integer pageSize, String userName, String phonenumber, String status) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        LambdaQueryWrapper<User> Wrapper = new LambdaQueryWrapper<>();
        if (userName != null && phonenumber != null && status != null) {
            queryWrapper.like(User::getUserName, userName);
            queryWrapper.like(User::getPhonenumber, phonenumber);
            queryWrapper.like(User::getStatus, status);
            Page<User> page = new Page();
            page.setCurrent(pageNum);
            page.setSize(pageSize);
            page(page, queryWrapper);
        }
        Page<User> page = new Page();
        page.setCurrent(pageNum);
        page.setSize(pageSize);
        page(page, Wrapper);
        int total = userMapper.countUser();
        PageVo pageVo = new PageVo(page.getRecords(), total);
        return ResponseResult.successResult(pageVo);
    }

    @Override
    public ResponseResult addUser(User user) {
        if(!StringUtils.hasText(user.getUserName())){
            throw new SystemException(AppHttpCodeEnum.TAG_NAME_NOT_NULL);
        }
        save(user);
        return ResponseResult.successResult();
    }

    @Override
    public ResponseResult deleteUser(Long id) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getId, id);
        if(userMapper.delete(wrapper)>0)
        {
            return ResponseResult.successResult();
        }else{
            return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
        }
    }

    @Override
    public ResponseResult selectById(Long id) {
        User user =  userMapper.selectById(id);
        UserVo userVo = BeanCopyUtils.copyBean(user, UserVo.class);
        return ResponseResult.successResult(userVo);
    }

    @Override
    public ResponseResult updateUser(User user) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getId,user.getId());
        userMapper.update(user,wrapper);
        return ResponseResult.successResult();
    }
}
