package com.lzpeng.springboot.websocket.controller;

import com.lzpeng.springboot.websocket.config.SocketServer;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class IndexController {

    @GetMapping("")
    public String index() {
        return "index";
    }

    @GetMapping("admin")
    public String tongji(Model model) {
        model.addAttribute("num", SocketServer.getOnlineNum());
        model.addAttribute("users", SocketServer.getOnlineUsers());
        return "admin";
    }

	@GetMapping("sendmsg")
	@ResponseBody
	public String sendmsg(String username, String msg){
		SocketServer.sendMessage(msg, username);
		return "success";
	}

	@GetMapping("sendAll")
	@ResponseBody
	public String sendAll(String msg){
		SocketServer.sendAll(msg);
		return "success";
	}
}
