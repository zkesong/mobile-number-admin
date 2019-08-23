package cn.qydx.hmdj.controller;

import cn.qydx.hmdj.dto.CustomManagerDto;
import cn.qydx.hmdj.service.CustomManagerService;
import cn.qydx.hmdj.utils.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("customManager")
public class CustomManagerController {

    @Autowired
    private CustomManagerService service;

    @GetMapping("list/{groupId}")
    @ResponseBody
    public Response<List<CustomManagerDto>> listCustomManager(@PathVariable Integer groupId) {
        return service.listCustomManagerByGroup(groupId);
    }
}
