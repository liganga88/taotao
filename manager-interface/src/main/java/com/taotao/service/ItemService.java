package com.taotao.service;

import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.pojo.TbItem;

/**
 * Created by Administrator on 2017-7-18.
 */
public interface ItemService {
    TbItem getItemById(long itemId);

    EasyUIDataGridResult getItemList(int page, int rows);


}
