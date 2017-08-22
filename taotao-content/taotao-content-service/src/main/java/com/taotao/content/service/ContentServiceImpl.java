package com.taotao.content.service;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.mapper.TbContentDao;
import com.taotao.pojo.TbContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by Administrator on 2017-8-22.
 */
@Service
public class ContentServiceImpl implements ContentService {

    @Autowired
    private TbContentDao contentDao;

    @Override
    public TaotaoResult addContent(TbContent content) {
        content.setCreated(new Date());
        content.setUpdated(new Date());
        contentDao.insert(content);
        return TaotaoResult.ok();
    }
}
