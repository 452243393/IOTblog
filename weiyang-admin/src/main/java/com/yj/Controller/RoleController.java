package com.yj.Controller;

import com.yj.entity.vo.RoleVo;
import com.yj.entity.vo.TagVo;
import com.yj.service.RoleService;
import com.yj.utils.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.yj.entity.Role;

import java.util.List;

@RestController
@RequestMapping("/system/role")
public class RoleController {
    @Autowired
    private RoleService roleService;
    @GetMapping("/list")
    public ResponseResult queryRole(Integer pageNum, Integer pageSize, String roleName, String roleKey, String status){
        return roleService.queryRole(pageNum,pageSize,roleName,roleKey,status);
    }
    @PostMapping
    public ResponseResult addRole(@RequestBody Role role){
        return roleService.addRole(role);
    }

    @DeleteMapping("/{id}")
    public ResponseResult deleteRole(@PathVariable("id") Long id){
        return roleService.deleteRole(id);
    }

    @GetMapping("/{id}")
    public ResponseResult selectById(@PathVariable("id") Long id){
        return roleService.selectById(id);
    }

    @PutMapping
    public ResponseResult updateRole(@RequestBody Role role){
        return roleService.updateRole(role);
    }

    @GetMapping("/listAllRole")
    public ResponseResult listAllTag(){
        List<RoleVo> list = roleService.listAllRole();
        return ResponseResult.successResult(list);
    }
}
