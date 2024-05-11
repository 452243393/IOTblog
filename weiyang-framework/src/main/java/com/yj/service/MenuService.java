package com.yj.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yj.entity.Menu;

import java.util.List;

public interface MenuService extends IService<Menu> {
    List<String> selectPermsByUserId(Long id);

    List<Menu> selectMenuTreeByUserId(Long userId);
}
