package com.taotao.service.impl;

import com.taotao.common.pojo.EasyUITreeNode;
import com.taotao.mapper.TbItemCatDao;
import com.taotao.pojo.TbItemCat;
import com.taotao.pojo.TbItemCatQuery;
import com.taotao.service.ItemCatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017-8-1.
 */
@Service
public class ItemCatServiceImpl implements ItemCatService {

    @Autowired
    private TbItemCatDao itemCatDao;

    @Override
    public List<EasyUITreeNode> getItemCatList(long parentId) {
        //根据父节点ID查询子节点列表
        TbItemCatQuery query = new TbItemCatQuery();
        //设置查询条件
        TbItemCatQuery.Criteria criteria = query.createCriteria();
        //设置parentid
        criteria.andParentIdEqualTo(parentId);
        //执行查询
        List<TbItemCat> list = itemCatDao.selectByExample(query);
        //转换成EasyUITreeNode列表
        List<EasyUITreeNode> resultList = new ArrayList<>();
        for(TbItemCat tbItemCat : list){
            EasyUITreeNode node = new EasyUITreeNode();
            node.setId(tbItemCat.getId());
            node.setText(tbItemCat.getName());
            //如果节点下有子节点"closed",如果没有子节点"open"
            node.setState(tbItemCat.getIsParent()?"closed":"open");
            //添加到节点列表
            resultList.add(node);
        }

        return resultList;
    }
}
