package com.taotao.content.service;

import com.taotao.common.pojo.EasyUITreeNode;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.mapper.TbContentCategoryDao;
import com.taotao.pojo.TbContentCategory;
import com.taotao.pojo.TbContentCategoryQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.taotao.pojo.TbContentCategoryQuery.*;

/**
 * Created by Administrator on 2017-8-22.
 */
@Service
public class ContentCategoryServiceImpl implements ContentCategoryService {

    @Autowired
    private TbContentCategoryDao contentCategoryDao;

    @Override
    public List<EasyUITreeNode> getContentCategoryList(long parentId) {
        //根据parentId查询子节点列表
        TbContentCategoryQuery query = new TbContentCategoryQuery();
        //设置查询条件
        Criteria criteria = query.createCriteria();
        criteria.andParentIdEqualTo(parentId);

        //执行查询
        List<TbContentCategory> list = contentCategoryDao.selectByExample(query);
        List<EasyUITreeNode> resultList = new ArrayList<>();
        for (TbContentCategory tbContentCategory : list) {
            EasyUITreeNode node = new EasyUITreeNode();
            node.setId(tbContentCategory.getId());
            node.setText(tbContentCategory.getName());
            node.setState(tbContentCategory.getIsParent() ? "closed" : "open");
            //添加到结果列表
            resultList.add(node);
        }
        return resultList;
    }

    @Override
    public TaotaoResult addContentCategory(Long parentId, String name) {
        //创建一个pojo对象
        TbContentCategory contentCategory = new TbContentCategory();
        //补全对象属性
        contentCategory.setName(name);
        contentCategory.setParentId(parentId);
        //状态。可选值：1（正常），2（删除）
        contentCategory.setStatus(1);
        //排序，默认为1
        contentCategory.setSortOrder(1);
        contentCategory.setIsParent(false);
        contentCategory.setCreated(new Date());
        contentCategory.setUpdated(new Date());
        //插入到数据库
        contentCategoryDao.insert(contentCategory);

        //判断父节点状态
        TbContentCategory parent = contentCategoryDao.selectByPrimaryKey(parentId);
        if (!parent.getIsParent()) {
            parent.setIsParent(true);
            parent.setUpdated(new Date());
            contentCategoryDao.updateByPrimaryKey(parent);
        }
        //返回结果
        return TaotaoResult.ok(contentCategory);
    }

    @Override
    public TaotaoResult updateContentCategory(Long id, String name) {
        TbContentCategory contentCategory = new TbContentCategory();
        contentCategory.setId(id);
        contentCategory.setName(name);
        contentCategory.setUpdated(new Date());
        contentCategoryDao.updateByPrimaryKeySelective(contentCategory);
        return TaotaoResult.ok();
    }

    @Override
    public TaotaoResult deleteContentCategory(Long id) {
        TbContentCategory contentCategory = contentCategoryDao.selectByPrimaryKey(id);
        //判断是否为父节点
        if (contentCategory.getIsParent()) {
            //删除子节点
            TbContentCategoryQuery query = new TbContentCategoryQuery();
            Criteria criteria = query.createCriteria();
            criteria.andParentIdEqualTo(id);
            List<TbContentCategory> contentCategories = contentCategoryDao.selectByExample(query);
            for (TbContentCategory category : contentCategories) {
                deleteContentCategory(category.getId());
            }
        }
        contentCategoryDao.deleteByPrimaryKey(id);

        //判断父节点是否还有子节点
        Long parentId = contentCategory.getParentId();
        TbContentCategoryQuery query = new TbContentCategoryQuery();
        Criteria criteria = query.createCriteria();
        criteria.andParentIdEqualTo(parentId);
        int count = contentCategoryDao.countByExample(query);
        //父节点没有子节点的情况下，父节点改为子节点
        if (count == 0) {
            TbContentCategory parent = new TbContentCategory();
            parent.setId(parentId);
            parent.setIsParent(false);
            parent.setUpdated(new Date());
            contentCategoryDao.updateByPrimaryKeySelective(parent);
        }

        return TaotaoResult.ok();
    }
}
