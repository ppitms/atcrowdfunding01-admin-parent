package com.zhijia.crowd.service.api;

import com.zhijia.crowd.entity.Menu;

import java.util.List;

/**
 * @author zhijia
 * @create 2022-03-19 16:25
 */
public interface MenuService {
    List<Menu> getAll();

    void saveMenu(Menu menu);

    void updateMenu(Menu menu);

    void removeMenu(Integer id);
}
