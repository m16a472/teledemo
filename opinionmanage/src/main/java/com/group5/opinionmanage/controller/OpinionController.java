package com.group5.opinionmanage.controller;

import com.group5.opinionmanage.entity.Opinions;
import com.group5.opinionmanage.formentity.GetVo;
import com.group5.opinionmanage.service.OpinionsService;
import org.python.util.PythonInterpreter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author 10569
 * @version 1.0
 * @description     响应前端请求
 * @Date 2022/8/24 14:17
 */

@RestController
public class OpinionController {

    @Autowired
    OpinionsService opinionsServiceImpl;

    @GetMapping("/formGet")
    public GetVo getForm(HttpServletRequest request) {
        int limit = Integer.parseInt(request.getParameter("limit"));
        int page = Integer.parseInt(request.getParameter("page"));
        Sort sort = Sort.by(Sort.Order.asc("oid"));
        Pageable pageable = PageRequest.of(page - 1, limit, sort);
        Page<Opinions> info = opinionsServiceImpl.findAll(pageable);
        Long count = opinionsServiceImpl.count();
        return new GetVo(0, "success", count, info);
    }


    @GetMapping("/ConditionSelect")
    public GetVo findCondition(HttpServletRequest request){
        String conditionSelect = String.valueOf(request.getParameter("conditionSelect"));
        String conditionInput = String.valueOf(request.getParameter("conditionInput"));
        int limit=Integer.parseInt(request.getParameter("limit"));
        int page=Integer.parseInt(request.getParameter("page"));
        Sort sort=Sort.by(Sort.Order.asc("oid"));
        Pageable pageable=PageRequest.of(page-1,limit,sort);
        Page<Opinions> info = null;
        Long count =opinionsServiceImpl.count();
        switch (conditionSelect) {
            case "cxt":
                info = opinionsServiceImpl.findByContext(pageable, conditionInput);
                break;
            case "tp":
                info = opinionsServiceImpl.findByType(pageable, conditionInput);
                break;
            case "kw":
                info = opinionsServiceImpl.findByKeyWord(pageable, conditionInput);
                break;
            default:
        }
        return new GetVo(0,"success",count,info);
    }

    @GetMapping("/spider")
    public String getNewData(HttpServletRequest request) throws IOException {
        String python="D:\\Anaconda\\anaconda3\\envs\\learning\\python.exe";
        String script="C:\\Users\\10569\\Desktop\\teledemo\\teledemo\\spider\\test.py";
        String arg1=3+"";
        String arg2=4+"";
        String[] argument=new String[]{python,script,arg1,arg2};
        Process process =Runtime.getRuntime().exec(argument);
        BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line;
        StringBuilder result= new StringBuilder();
        while((line=in.readLine())!=null){
            result.append(line);
            System.out.println(line);
        }
        in.close();
        System.out.println("?");
        return result.toString();
    }

}


