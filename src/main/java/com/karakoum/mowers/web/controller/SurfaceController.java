package com.karakoum.mowers.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.karakoum.mowers.common.controller.BaseController;
import com.karakoum.mowers.domain.surface.impl.Surface;
import com.karakoum.mowers.service.SurfaceService;

/**
 * 
 * @author Jean-Baptiste
 *
 */

@Controller
@RequestMapping("/surface")
public class SurfaceController extends BaseController {
			
		@Autowired 
	    SurfaceService surfaceService;
		
		Surface surface;
		
		public SurfaceService getSurfaceService() {
			return surfaceService;
		}

		public void setSurfaceService(SurfaceService surfaceService) {
			this.surfaceService = surfaceService;
		}
		
	    @RequestMapping(value = "/surface", method = RequestMethod.GET, produces = "application/json")
	    public @ResponseBody List<Surface> list() {
	    	return surfaceService.findAll();
	    }

	    @RequestMapping(value = "/surface/{id}", method = RequestMethod.GET, produces = "application/json")
	    public @ResponseBody Surface getById(@PathVariable String objectId) {
	    	return surfaceService.findById(objectId);
	    }

	    @RequestMapping(value = "/surface", method = RequestMethod.PUT)
	    @ResponseStatus(HttpStatus.NO_CONTENT)
	    public void create(@RequestBody Surface surface) {
	    	surfaceService.saveOrUpdate(surface);
	    }

	    @RequestMapping(value = "/surface/{id}", method = RequestMethod.DELETE)
	    @ResponseStatus(HttpStatus.NO_CONTENT)
	    public void delete(@PathVariable String objectId) {
	    	surfaceService.deleteById(objectId);
	    }

}
