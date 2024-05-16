package com.yj.Controller;

import com.yj.service.LinkService;
import com.yj.utils.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/content/link")
public class LinkController {
    @Autowired
    private LinkService linkService;
    @GetMapping("/list")
    public ResponseResult queryList(Integer pageNum, Integer pageSize, String name, String description, String address, String status, String logo){
        return linkService.queryLink(pageNum,pageSize,name,description,address,status,logo);
    }
}
