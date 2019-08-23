package cn.qydx.hmdj.controller;

import cn.qydx.hmdj.config.CustomConfig;
import cn.qydx.hmdj.constant.ResponseCode;
import cn.qydx.hmdj.dto.PhoneNumberDto;
import cn.qydx.hmdj.service.PhoneNumberService;
import cn.qydx.hmdj.utils.ExcelReader;
import cn.qydx.hmdj.utils.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("phonenumber")
public class PhoneNumberController {

    @Autowired
    private CustomConfig config;

    @Autowired
    private PhoneNumberService service;

    @GetMapping("")
    public String downLoadPage() {
        return "phonenumber";
    }

    @GetMapping("query")
    public String queryPage() {
        return "query";
    }

    @GetMapping("operation")
    public String operation() {
        return "operation";
    }

    @GetMapping("uploadPage")
    public String uploadPage() {
        return "upload";
    }

    @GetMapping("echart")
    public String echart() {
        return "echart";
    }

    @GetMapping("download")
    public void downLoad(HttpServletResponse res) throws UnsupportedEncodingException {
        service.downloadPhoneNumber(res);
    }

    @GetMapping("download/{manager}")
    public void downLoad(@PathVariable String manager, HttpServletResponse res) throws UnsupportedEncodingException {
        service.downloadPhoneNumberByManager(res, manager);
    }

    @GetMapping("list/{manager}")
    @ResponseBody
    public Response<List<PhoneNumberDto>> list(int page, int limit, @PathVariable String manager, HttpServletResponse res) throws UnsupportedEncodingException {
        int start = (page - 1) * limit;
        List<PhoneNumberDto> dtos = service.listPhoneNumberByManagerLimit(manager, start, limit);
        return new Response<>(dtos, 0, service.listPhoneNumberByManager(manager).size());
    }

    @PostMapping("save")
    @ResponseBody
    public Response<PhoneNumberDto> save(PhoneNumberDto dto){
        return service.savePhoneNumber(dto);
    }

    @PostMapping("update")
    @ResponseBody
    public Response<String> update(PhoneNumberDto dto){
        return service.updatePhoneNumber(dto);
    }

    @PostMapping("/upload")
    @ResponseBody
    public Response<String> upload(@RequestParam("fileUpload")MultipartFile fileUpload){
        //获取文件名
        String fileName = fileUpload.getOriginalFilename();
        //获取文件后缀名
        String suffixName = fileName.substring(fileName.lastIndexOf("."));
        //重新生成文件名
        fileName = UUID.randomUUID()+suffixName;
        try {
            //将图片保存到static文件夹里
            File targetFile = new File(config.getFilePath() + fileName);
            fileUpload.transferTo(targetFile);
            ExcelReader reader = new ExcelReader();
            List<PhoneNumberDto> list = reader.read(targetFile);
            return service.savePhoneNumberMuilti(list);
        } catch (Exception e) {
            e.printStackTrace();
            return new Response<>("上传失败", ResponseCode.REQUEST_FAILED);
        }
    }

    @RequestMapping("managers")
    @ResponseBody
    public List<String> managers(){
        return service.managers();
    }

    @RequestMapping("counts")
    @ResponseBody
    public List<Integer> counts(){
        return service.counts();
    }
}
