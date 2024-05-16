package com.yj.Controller;

import com.yj.service.RoleService;
import com.yj.utils.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/system/role")
public class RoleController {
    @Autowired
    private RoleService roleService;
    @GetMapping("/list")
    public ResponseResult queryUser(Integer pageNum, Integer pageSize, String roleName, String roleKey, String status){
        return roleService.queryRole(pageNum,pageSize,roleName,roleKey,status);
    }
}
