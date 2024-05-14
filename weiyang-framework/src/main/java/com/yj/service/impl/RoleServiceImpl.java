package com.yj.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yj.entity.Role;
import com.yj.entity.User;
import com.yj.entity.vo.PageVo;
import com.yj.mapper.RoleMapper;
import com.yj.service.RoleService;
import com.yj.utils.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {
    @Autowired
    private RoleMapper roleMapper;
    @Override
    public List<String> selectRoleKeyByUserId(Long id) {
        //判断是否是管理员 如果是返回集合中只需要有admin
        if(id == 1L){
            List<String> roleKeys = new ArrayList<>();
            roleKeys.add("admin");
            return roleKeys;
        }
        //否则查询用户所具有的角色信息
        return getBaseMapper().selectRoleKeyByUserId(id);
    }

    @Override
    public ResponseResult queryRole(Integer pageNum, Integer pageSize, String roleName, String roleKey, String status) {
        LambdaQueryWrapper<Role> queryWrapper = new LambdaQueryWrapper<>();
        LambdaQueryWrapper<Role> Wrapper = new LambdaQueryWrapper<>();
        if (roleName != null && roleName != null && status != null) {
            queryWrapper.like(Role::getRoleName, roleName);
            queryWrapper.like(Role::getRoleKey, roleKey);
            queryWrapper.like(Role::getStatus, status);
            Page<Role> page = new Page();
            page.setCurrent(pageNum);
            page.setSize(pageSize);
            page(page, queryWrapper);
        }
        Page<Role> page = new Page();
        page.setCurrent(pageNum);
        page.setSize(pageSize);
        page(page, Wrapper);
        int total = roleMapper.countRole();
        PageVo pageVo = new PageVo(page.getRecords(), total);
        return ResponseResult.successResult(pageVo);
    }
}
