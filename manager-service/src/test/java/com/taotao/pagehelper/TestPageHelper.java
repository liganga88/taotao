package com.taotao.pagehelper;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.mapper.TbItemDao;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemQuery;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import java.util.List;

/**
 * Created by Administrator on 2017-7-27.
 */
@RunWith(SpringJUnit4ClassRunner.class)//基于Junit4的Spring测试框架
@ContextConfiguration({"classpath:spring/applicationContext-dao.xml"})//启动Spring容器
public class TestPageHelper {
    @Autowired
    private TbItemDao itemDao;
    @Test
    public void testPageHelper() throws Exception{
        //1.在mybatis的配置文件中配置分页插件
        //2.在执行查询之前配置分页条件。使用PageHelper的静态方法
        PageHelper.startPage(1, 10);
        //3.执行查询
//        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-dao.xml");
//        TbItemDao itemDao = applicationContext.getBean(TbItemDao.class);
        //查询条件
        TbItemQuery query = new TbItemQuery();
        List<TbItem> list = itemDao.selectByExample(query);

        //4.取分页信息。使用PageInfo对象取
        PageInfo<TbItem> pageInfo = new PageInfo<>(list);
        System.out.println("总记录数:" + pageInfo.getTotal());
        System.out.println("总页数:" + pageInfo.getPages());
        System.out.println("返回的记录数:" + list.size());
    }
}
