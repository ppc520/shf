package com.ppc.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ppc.entity.HouseImage;
import com.ppc.result.Result;
import com.ppc.service.HouseImageService;
import com.ppc.util.QiniuUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping("/houseImage")
public class HouseImageController {
    @Reference
    private HouseImageService houseImageService;

    @RequestMapping("/uploadShow/{houseId}/{type}")
    public String goUploadPage(Map map, @PathVariable("houseId") Long houseId,@PathVariable("type") Integer type){
        map.put("houseId",houseId);
        map.put("type",type);
        return "house/upload";
    }

    @ResponseBody
    @RequestMapping("/upload/{houseId}/{type}")
    public Result upload( @PathVariable("houseId") Long houseId,@PathVariable("type") Integer type,
                        @RequestParam ("file") MultipartFile[] files){
        try{
            if (files!=null&&files.length>0){
                for (MultipartFile file : files) {
                    byte[] bytes = file.getBytes();
                    //原始名
                    String originalFilename = file.getOriginalFilename();
                    //原始名字有可能重复
                    String uuidName = UUID.randomUUID().toString();
                    QiniuUtil.upload2Qiniu(bytes,uuidName);

                    HouseImage houseImage=new HouseImage();
                    houseImage.setHouseId(houseId);
                    houseImage.setType(type);
                    houseImage.setImageName(originalFilename);
                    houseImage.setImageUrl("http://ru6fapo6u.hn-bkt.clouddn.com/"+uuidName);

                    houseImageService.insert(houseImage);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return Result.ok();
    }

    @RequestMapping("/delete/{houseId}/{id}")
    public String delete(@PathVariable("houseId") Long houseId,@PathVariable("id") Long id){
        houseImageService.delete(id);
        return "redirect:/house/"+houseId;
    }
}
