package com.taotao.service.impl;

import com.taotao.mapper.TbItemDao;
import com.taotao.pojo.TbItem;
import com.taotao.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2017-7-18.
 */
@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private TbItemDao itemDao;

    @Override
    public TbItem getItemById(long itemId) {
        return itemDao.selectByPrimaryKey(itemId);
    }
}
