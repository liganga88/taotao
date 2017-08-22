package com.taotao.controller;

import com.taotao.common.pojo.EasyUITreeNode;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.content.service.ContentCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by Administrator on 2017-8-22.
 */
@Controller
public class ContentCategoryController {

    @Autowired
    private ContentCategoryService contentCategoryService;

    @RequestMapping("content/category/list")
    @ResponseBody
    public List<EasyUITreeNode> getContentCategoryList(@RequestParam(value = "id", defaultValue = "0") Long parentId) {
        return contentCategoryService.getContentCategoryList(parentId);
    }

    @RequestMapping("content/category/create")
    @ResponseBody
    public TaotaoResult addContentCategory(Long parentId, String name){
        return contentCategoryService.addContentCategory(parentId, name);
    }

    @RequestMapping("content/category/update")
    @ResponseBody
    public TaotaoResult updateContentCategory(Long id, String name){
        return contentCategoryService.updateContentCategory(id, name);
    }

    @RequestMapping("content/category/delete")
    @ResponseBody
    public TaotaoResult deleteContentCategory(Long id){
        return contentCategoryService.deleteContentCategory(id);
    }
}
