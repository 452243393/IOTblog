package com.yj.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yj.entity.Role;
import com.yj.entity.Role;
import com.yj.entity.Tag;
import com.yj.entity.User;
import com.yj.entity.vo.*;
import com.yj.exception.SystemException;
import com.yj.mapper.RoleMapper;
import com.yj.service.RoleService;
import com.yj.utils.AppHttpCodeEnum;
import com.yj.utils.BeanCopyUtils;
import com.yj.utils.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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

    @Override
    public ResponseResult addRole(Role role) {
        if(!StringUtils.hasText(role.getRoleName())){
            throw new SystemException(AppHttpCodeEnum.TAG_NAME_NOT_NULL);
        }
        save(role);
        return ResponseResult.successResult();
    }

    @Override
    public ResponseResult deleteRole(Long id) {
        LambdaQueryWrapper<Role> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Role::getId, id);
        if(roleMapper.delete(wrapper)>0)
        {
            return ResponseResult.successResult();
        }else{
            return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
        }
    }

    @Override
    public ResponseResult selectById(Long id) {
        Role role =  roleMapper.selectById(id);
        RoleVo roleVo = BeanCopyUtils.copyBean(role, RoleVo.class);
        return ResponseResult.successResult(roleVo);
    }

    @Override
    public ResponseResult updateRole(Role role) {
        LambdaQueryWrapper<Role> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Role::getId,role.getId());
        roleMapper.update(role,wrapper);
        return ResponseResult.successResult();
    }
    @Override
    public List<RoleVo> listAllRole() {
        LambdaQueryWrapper<Role> wrapper = new LambdaQueryWrapper<>();
        wrapper.select(Role::getId,Role::getRoleName);
        List<Role> list = list(wrapper);
        List<RoleVo> roleVos = BeanCopyUtils.copyBeanList(list, RoleVo.class);
        return roleVos;
    }
}
