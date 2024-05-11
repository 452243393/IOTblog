package com.yj.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yj.entity.Menu;
import com.yj.mapper.MenuMapper;
import com.yj.service.MenuService;
import com.yj.utils.SecurityUtils;
import com.yj.utils.SystemConstants;
import lombok.experimental.Accessors;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {
    @Accessors
    private MenuMapper menuMapper;
    @Override
    public List<String> selectPermsByUserId(Long id) {
        //如果是管理员，返回所有的权限
        if(SecurityUtils.isAdmin()){
            LambdaQueryWrapper<Menu> wrapper = new LambdaQueryWrapper<>();
            wrapper.in(Menu::getMenuType, SystemConstants.MENU,SystemConstants.BUTTON);
            wrapper.eq(Menu::getStatus,SystemConstants.ARTICLE_STATUS_NORMAL);
            List<Menu> menus = list(wrapper);
            List<String> perms = menus.stream()
                    .map(Menu::getPerms)
                    .collect(Collectors.toList());
            return perms;
        }
        //否则返回所具有的权限
        return getBaseMapper().selectPermsByUserId(id);
    }

    @Override
    public List<Menu> selectMenuTreeByUserId(Long userId) {
        MenuMapper menuMapper = getBaseMapper();
        List<Menu> menus = null;
        //判断是否是管理员
        if(SecurityUtils.isAdmin()){
        //如果是，返回所有符合要求的menu
            menus = menuMapper.selectAllRouterMenu();
        }else{
            //否则 返回当前用户的所具有的menu
            menus = menuMapper.selectRouterMenuTreeByUserId(userId);
        }
        //构建menuTree的格式
        //先找出第一层的菜单 然后再去找他们的子菜单设置children属性中
        List<Menu> menuTree = builderTree(menus,0L);
        return menuTree;
    }
    //tree构造子菜单
    private List<Menu> builderTree(List<Menu> menus,Long parentId) {
        List<Menu> menuTree = menus.stream()
                .filter(menu -> menu.getParentId().equals(parentId))
                .map(menu -> menu.setChildren(getChildren(menu,menus)))
                .collect(Collectors.toList());
        return menuTree;
    }

    //获取存如参数的 子menu集合
    private List<Menu> getChildren(Menu menu, List<Menu> menus) {
      List<Menu> childrenList = menus.stream()
                .filter(m->m.getParentId().equals(menu.getId()))
              //找子菜单的子菜单
                .map(m->m.setChildren(getChildren(m,menus)))
                .collect(Collectors.toList());
      return childrenList;
    }
}
