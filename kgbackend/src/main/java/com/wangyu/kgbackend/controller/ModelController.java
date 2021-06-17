package com.wangyu.kgbackend.controller;

import com.wangyu.kgbackend.pojo.Model;
import com.wangyu.kgbackend.pojo.ModelAttribute;
import com.wangyu.kgbackend.result.Result;
import com.wangyu.kgbackend.result.ResultFactory;
import com.wangyu.kgbackend.service.ModelAttributeService;
import com.wangyu.kgbackend.service.ModelService;
import com.wangyu.kgbackend.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @author wongy
 * 管理模板的类
 */
@RestController
public class ModelController {
    @Autowired
    ModelService modelService;
    @Autowired
    ModelAttributeService modelAttributeService;
    @Value("${my.address}")
    private String address;
    @Value("${server.port}")
    private String port;
    @Value("${folder.address}")
    private String folderAddress;

    /**
     * 添加一个模型类
     * @param model 传入的模型类
     * @return 添加之后的结果
     */
    @PostMapping("/api/model/add_model")
    public Result addModel(@RequestBody Model model){
        modelService.addModel(model);
        return ResultFactory.buildSuccessResult(model);
    }

    /**
     * 按照所属模型文件和节点类型列出模型类
     * @param model 包含模型文件的id和类别
     * @return 对应的列表结果
     */
    @PostMapping("/api/model/list_all")
    public Result listAll(@RequestBody Model model){
        return ResultFactory.buildSuccessResult(modelService.listByModelFileNodeAndJudgeNode(model));
    }

    /**
     * 添加属性到列表中
     * @param attributeList 属性的列表
     * @return 添加之后属性的列表
     */
    @PostMapping("/api/model/add_attribute")
    public  Result addAttribute(@RequestBody List<ModelAttribute> attributeList){
        Model model = attributeList.get(0).getModel();
        modelService.setAttribute(model);
        modelAttributeService.deleteByModel(model);
        modelAttributeService.addAttributes(attributeList);
        return ResultFactory.buildSuccessResult(attributeList);
    }

    /**
     * 根据模型初始化属性
     * @param model 对应的模型
     * @return 包含对应属性列表的结果
     */
    @PostMapping("/api/model/init_attribute")
    public Result initAttribute(@RequestBody Model model){
        return ResultFactory.buildSuccessResult(modelAttributeService.listByModel(model));
    }

    /**
     * 上传图片文件
     * @param file 对应的文件
     * @return 存放的url
     * @throws Exception 对应的异常
     */
    @PostMapping("/api/model/img")
    public String coversUpload(MultipartFile file) throws Exception {
        String folder = folderAddress;
        File imageFolder = new File(folder);
        String OriginalFilename = StringUtils.getRandomString(6) + file.getOriginalFilename();
        File f = new File(imageFolder, StringUtils.getRandomString(6) + "." + OriginalFilename
                .split("\\.")[OriginalFilename.split("\\.").length - 1]);
        if (!f.getParentFile().exists()) {
            f.getParentFile().mkdirs();
        }
        try {
            file.transferTo(f);
            String imgURL = "http://" + address + ":" + port + "/api/file/" + f.getName();
            return imgURL;
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 修改图片的url
     * @param model 对应的模型
     * @return 修改图片url的model
     */
    @PostMapping("/api/model/change_url")
    public Result changeUrl(@RequestBody Model model){
        return ResultFactory.buildSuccessResult(modelService.changeUrl(model));
    }

    /**
     * 删除对应的模型
     * @param model 对应的模型
     * @return 被删除的模型
     */
    @PostMapping("/api/model/delete_model")
    public Result deleteModel(@RequestBody Model model){
        return ResultFactory.buildSuccessResult(modelService.deleteModel(model));
    }
}
