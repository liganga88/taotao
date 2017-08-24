package com.taotao.content.service;

import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbContent;

import java.util.List;

/**
 * Created by Administrator on 2017-8-22.
 */
public interface ContentService {
    TaotaoResult addContent(TbContent content);

    EasyUIDataGridResult getContentList(int page, int rows, long categoryId);

    TaotaoResult updateContent(TbContent content);

    TaotaoResult deleteContent(String ids);

    List<TbContent> getContentByCid(long cid);
}
