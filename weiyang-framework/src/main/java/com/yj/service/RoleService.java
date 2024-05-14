package com.yj.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yj.entity.Role;
import com.yj.utils.ResponseResult;

import java.util.List;

public interface RoleService extends IService<Role> {
    List<String> selectRoleKeyByUserId(Long id);

    ResponseResult queryRole(Integer pageNum, Integer pageSize, String roleName, String roleKey, String status);
}
