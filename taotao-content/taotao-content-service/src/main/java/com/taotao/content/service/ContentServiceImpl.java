package com.taotao.content.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.jedis.JedisClient;
import com.taotao.mapper.TbContentDao;
import com.taotao.pojo.TbContent;
import com.taotao.pojo.TbContentQuery;
import com.taotao.pojo.TbContentQuery.Criteria;
import common.taotao.utils.JsonUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017-8-22.
 */
@Service
public class ContentServiceImpl implements ContentService {

    @Autowired
    private TbContentDao contentDao;
    @Autowired
    private JedisClient jedisClient;
    @Value("${INDEX_CONTENT}")
    private String INDEX_CONTENT;

    @Override
    public TaotaoResult addContent(TbContent content) {
        content.setCreated(new Date());
        content.setUpdated(new Date());
        contentDao.insert(content);

        //同步缓存
        //删除对应的缓存信息
        jedisClient.hdel(INDEX_CONTENT, content.getCategoryId().toString());
        return TaotaoResult.ok();
    }

    @Override
    public EasyUIDataGridResult getContentList(int page, int rows, long categoryId) {
        //设置分页信息
        PageHelper.startPage(page, rows);
        //执行查询
        TbContentQuery query = new TbContentQuery();
        Criteria criteria = query.createCriteria();
        criteria.andCategoryIdEqualTo(categoryId);
        List<TbContent> list = contentDao.selectByExampleWithBLOBs(query);
        //取查询结果
        PageInfo<TbContent> pageInfo = new PageInfo<TbContent>(list);
        EasyUIDataGridResult result = new EasyUIDataGridResult();
        result.setTotal(pageInfo.getTotal());
        result.setRows(list);
        //返回结果
        return result;
    }

    @Override
    public TaotaoResult updateContent(TbContent content) {
        contentDao.updateByPrimaryKey(content);
        //同步缓存
        //删除对应的缓存信息
        jedisClient.hdel(INDEX_CONTENT, content.getCategoryId().toString());
        return TaotaoResult.ok();
    }

    @Override
    public TaotaoResult deleteContent(String ids) {
        List<Long> idlist = new ArrayList<>();
        String[] strIds = ids.split(",");
        for(String id : strIds){
            Long lId = Long.parseLong(id);
            idlist.add(lId);

            //同步缓存
            //删除对应的缓存信息
            TbContent content = contentDao.selectByPrimaryKey(lId);
            if (content != null) {
                jedisClient.hdel(INDEX_CONTENT, content.getCategoryId().toString());
            }
        }

        TbContentQuery query = new TbContentQuery();
        Criteria criteria = query.createCriteria();
        criteria.andIdIn(idlist);
        contentDao.deleteByExample(query);

        return TaotaoResult.ok();
    }

    @Override
    public List<TbContent> getContentByCid(long cid) {
        //先查询缓存
        try {
            String json = jedisClient.hget(INDEX_CONTENT, cid + "");
            if(StringUtils.isNotBlank(json)){
                List<TbContent> list = JsonUtils.jsonToList(json, TbContent.class);
                return list;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        //添加缓存不能影响正常业务逻辑
        //缓存中没有，需要查询数据库
        TbContentQuery query = new TbContentQuery();
        Criteria criteria = query.createCriteria();
        criteria.andCategoryIdEqualTo(cid);
        List<TbContent> list = contentDao.selectByExample(query);
        //把结果添加到缓存
        try {
            jedisClient.hset(INDEX_CONTENT, cid + "", JsonUtils.objectToJson(list));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
