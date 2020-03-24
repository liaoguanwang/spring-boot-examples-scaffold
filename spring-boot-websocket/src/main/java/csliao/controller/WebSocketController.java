package csliao.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * @Classname WebSocketController
 * @Description
 * @Date 2020/2/26 20:28
 * @Created by csliao
 */

@RestController
@RequestMapping("websocket")
public class WebSocketController {

    private static final Logger logger = LoggerFactory.getLogger(WebSocketController.class);
    public static final String INDEX = "index";

    @Value("${server.port}")
    private String port;


    @GetMapping("/hello")
    public String hello(){
        return "hello";
    }

    @RequestMapping("/{id}")
    public ModelAndView index(@PathVariable("id")String id){
        logger.info("WebSocketController.index ===> start");
        ModelAndView mav = new ModelAndView(INDEX);
        mav.addObject("port", port);
        mav.addObject("id", id);
        return mav;
    }
}
