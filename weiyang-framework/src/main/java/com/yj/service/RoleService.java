package com.yj.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yj.entity.Role;
import com.yj.entity.User;
import com.yj.entity.vo.RoleVo;
import com.yj.entity.vo.TagVo;
import com.yj.utils.ResponseResult;

import java.util.List;

public interface RoleService extends IService<Role> {
    List<String> selectRoleKeyByUserId(Long id);

    ResponseResult queryRole(Integer pageNum, Integer pageSize, String roleName, String roleKey, String status);

    ResponseResult addRole(Role role);

    ResponseResult deleteRole(Long id);

    ResponseResult selectById(Long id);

    ResponseResult updateRole(Role role);

    List<RoleVo> listAllRole();
}
