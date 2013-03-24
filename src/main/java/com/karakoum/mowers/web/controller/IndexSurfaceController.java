package com.karakoum.mowers.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.karakoum.mowers.common.controller.BaseController;

/**
 * 
 * @author Jean-Baptiste
 *
 */

@Controller
@RequestMapping("/")
public class IndexSurfaceController extends BaseController {
			
	@RequestMapping
    public String getIndexPage() {
        return "index";
    }
	
	@RequestMapping("/surface")
    public String getSurfacePage() {
        return "index";
    }

}
