package com.taotao.search.dao;

import com.taotao.common.pojo.SearchItem;

import java.util.List;

/**
 * Created by Administrator on 2017-8-25.
 */
public interface SearchItemDao {
    List<SearchItem> getItemList();
}
