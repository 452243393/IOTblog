package com.yj.Controller;

import com.yj.entity.Link;
import com.yj.entity.vo.LinkVo;
import com.yj.service.LinkService;
import com.yj.utils.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/content/link")
public class LinkController {
    @Autowired
    private LinkService linkService;
    @GetMapping("/list")
    public ResponseResult queryList(Integer pageNum, Integer pageSize, String name, String description, String address, String status, String logo){
        return linkService.queryLink(pageNum,pageSize,name,description,address,status,logo);
    }
    @PostMapping
    public ResponseResult addLink(@RequestBody Link link){
        return linkService.addLink(link);
    }

    @DeleteMapping("/{id}")
    public ResponseResult deleteLink(@PathVariable("id") Long id){
        return linkService.deleteLink(id);
    }

    @GetMapping("/{id}")
    public ResponseResult selectById(@PathVariable("id") Long id){
        return linkService.selectById(id);
    }

    @PutMapping
    public ResponseResult updateLink(@RequestBody Link link){
        return linkService.updateLink(link);
    }

    @GetMapping("/listAllLink")
    public ResponseResult listAllTag(){
        List<LinkVo> list = linkService.listAllLink();
        return ResponseResult.successResult(list);
    }
}
