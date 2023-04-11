package com.wenqi.mvc.baseannotation;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author liangwenqi
 * @date 2023/4/10
 */
@Controller
@RequestMapping("helloAnnoController.anno")
public class AnyTypeYouLikeController {

    @RequestMapping(method = {RequestMethod.GET, RequestMethod.POST})
    public String processWebRequest() {
        return "anno/helloAnnoController";
    }
}
