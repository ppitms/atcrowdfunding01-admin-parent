package com.zhijia.crowd.service.impl;

import com.zhijia.crowd.entity.Menu;
import com.zhijia.crowd.entity.MenuExample;
import com.zhijia.crowd.mapper.MenuMapper;
import com.zhijia.crowd.service.api.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zhijia
 * @create 2022-03-19 16:26
 */
@Service
public class MenuServiceImpl implements MenuService {
    @Autowired
    private MenuMapper menuMapper;

    @Override
    public List<Menu> getAll() {
        MenuExample menuExample = new MenuExample();
        menuExample.createCriteria().getAllCriteria();
        return menuMapper.selectByExample(menuExample);
    }

    @Override
    public void saveMenu(Menu menu) {
        menuMapper.insert(menu);
    }

    @Override
    public void updateMenu(Menu menu) {
        //有选择更新
        menuMapper.updateByPrimaryKeySelective(menu);
    }

    @Override
    public void removeMenu(Integer id) {
        menuMapper.deleteByPrimaryKey(id);
    }
}
