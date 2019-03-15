package com.example.websocket.controller;

import com.example.websocket.config.WebSocketServer;
import com.example.websocket.until.ApiReturnObject;
import com.example.websocket.until.ApiReturnUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
@Controller
@RequestMapping("/msgcenter")
public class MsgCenterController {
    //页面请求
    @GetMapping("/socket/{cid}")
    public ModelAndView socket(HttpServletRequest request,@PathVariable String cid) {
        ModelAndView mav=new ModelAndView("/socket");
        mav.addObject("cid", cid);
        String wsurl = "ws://"+ request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/websocket";
        mav.addObject("wsurl", wsurl);
        return mav;

    }
    //推送数据接口
    @ResponseBody
    @RequestMapping("/socket/push/{cid}")
    public ApiReturnObject pushToWeb(@PathVariable String cid, String message) {
        try {
            WebSocketServer.sendInfo(message,cid);
        } catch (IOException e) {
            e.printStackTrace();
            return ApiReturnUtil.error(cid+"#"+e.getMessage());
        }
        return ApiReturnUtil.success(cid);
    }
}
