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
import com.karakoum.mowers.domain.roundorders.RoundOrders;
import com.karakoum.mowers.service.RoundOrdersService;

/**
 * 
 * @author Jean-Baptiste
 *
 */

@Controller
@RequestMapping("/roundorders")
public class RoundOrdersController extends BaseController {
			
		@Autowired 
	    RoundOrdersService roundOrderService;
		
		public RoundOrdersService getRoundOrdersService() {
			return roundOrderService;
		}

		public void setRoundOrdersService(RoundOrdersService roundOrderService) {
			this.roundOrderService = roundOrderService;
		}
		
	    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
	    public @ResponseBody List<RoundOrders> list() {
	    	return roundOrderService.findAll();
	    }

	    @RequestMapping(value = "{objectId}", method = RequestMethod.GET, consumes="application/json", produces = "application/json")
	    public @ResponseBody RoundOrders getById(@PathVariable String objectId) {
	    	return roundOrderService.findById(objectId);
	    }
	    
	    @RequestMapping(value = "{objectId}", method = RequestMethod.PUT, consumes="application/json")
	    @ResponseStatus(HttpStatus.NO_CONTENT)
	    public void update(@PathVariable String objectId,@RequestBody RoundOrders roundOrders) {
	    	roundOrders.setId(objectId);
	    	roundOrderService.saveOrUpdate(roundOrders);
	    }

	    @RequestMapping(method = RequestMethod.POST, consumes="application/json")
	    @ResponseStatus(HttpStatus.NO_CONTENT)
	    public void create(@RequestBody RoundOrders roundOrders) {
	    	roundOrderService.saveOrUpdate(roundOrders);
	    }
	    
	    @RequestMapping(value = "{objectId}", method = RequestMethod.DELETE)
	    @ResponseStatus(HttpStatus.NO_CONTENT)
	    public void delete(@PathVariable String objectId) {
	    	roundOrderService.deleteById(objectId);
	    }

}
