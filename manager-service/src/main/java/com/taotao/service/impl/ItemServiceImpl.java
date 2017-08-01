package com.taotao.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.mapper.TbItemDao;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemQuery;
import com.taotao.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    @Override
    public EasyUIDataGridResult getItemList(int page, int rows) {
        //设置分页信息
        PageHelper.startPage(page, rows);
        //执行查询
        TbItemQuery query = new TbItemQuery();
        List<TbItem> list = itemDao.selectByExample(query);
        //取查询结果
        PageInfo<TbItem> pageInfo = new PageInfo<TbItem>(list);
        EasyUIDataGridResult result = new EasyUIDataGridResult();
        result.setTotal(pageInfo.getTotal());
        result.setRows(list);
        //返回结果
        return result;
    }
}
