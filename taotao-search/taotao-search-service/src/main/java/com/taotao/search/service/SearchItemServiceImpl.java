package com.taotao.search.service;

import com.taotao.common.pojo.SearchItem;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.search.dao.SearchItemDao;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2017-8-25.
 */
@Service
public class SearchItemServiceImpl implements SearchItemService {

    @Autowired
    private SearchItemDao searchItemDao;
    @Autowired
    private SolrServer solrServer;

    @Override
    public TaotaoResult importItemsToIndex() {
        try {
            //1.先查询所有商品数据
            List<SearchItem> itemList = searchItemDao.getItemList();

            //2.遍历商品数据添加到索引库
            for (SearchItem item : itemList) {
                //创建文档对象
                SolrInputDocument document = new SolrInputDocument();
                //向文档中添加域
                document.addField("id", item.getId());
                document.addField("item_title", item.getTitle());
                document.addField("item_sell_point", item.getSellPoint());
                document.addField("item_price", item.getPrice());
                document.addField("item_image", item.getImage());
                document.addField("item_category_name", item.getCategoryName());
                document.addField("item_desc", item.getItemDesc());

                //把文档写入索引库
                solrServer.add(document);
            }
            //3.提交
            solrServer.commit();
        } catch (Exception e) {
            e.printStackTrace();
            return TaotaoResult.build(500, "数据导入失败");
        }
        //4.返回添加成功
        return TaotaoResult.ok();
    }
}
