package com.taotao.solrJ;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;

/**
 * Created by Administrator on 2017-8-25.
 */
public class TestSolrJ {
    @Test
    public void testAddDocument() throws Exception{
        //创建一个SolrServer对象.创建一个HttpSolrServer对象
        //需要指定solr服务的url
        SolrServer solrServer = new HttpSolrServer("http://192.168.156.128:8080/solr/collection1");
        //创建一个文档对象SolrInputDocument
        SolrInputDocument document = new SolrInputDocument();
        //向文档中添加域，必须有id域，域的名称必须在schema.xml中定义
        document.addField("id", "test004");
        document.addField("item_title", "测试商品4");
        document.addField("item_price", 1000);
        //把文档对象些人索引库
        solrServer.add(document);
        //提交
        solrServer.commit();
    }

    @Test
    public void detleteDocumentById() throws Exception{
        SolrServer solrServer = new HttpSolrServer("http://192.168.156.128:8080/solr/collection1");
        solrServer.deleteById("test001");
        //提交
        solrServer.commit();
    }

    @Test
    public void deleteDecomentByQuery() throws Exception{
        SolrServer solrServer = new HttpSolrServer("http://192.168.156.128:8080/solr/collection1");
        solrServer.deleteByQuery("id:test003");
        solrServer.commit();
    }
}
