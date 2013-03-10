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
import com.karakoum.mowers.domain.unit.impl.Unit;
import com.karakoum.mowers.service.UnitService;

/**
 * 
 * @author Jean-Baptiste
 *
 */

@Controller
@RequestMapping("/unit")
public class UnitController extends BaseController {
			
		@Autowired 
	    UnitService unitService;
		
		Unit unit;
		
		public UnitService getUnitService() {
			return unitService;
		}

		public void setUnitService(UnitService unitService) {
			this.unitService = unitService;
		}
		
	    @RequestMapping(value = "/unit", method = RequestMethod.GET, produces = "application/json")
	    public @ResponseBody List<Unit> list() {
	    	return unitService.findAll();
	    }

	    @RequestMapping(value = "/unit/{id}", method = RequestMethod.GET, produces = "application/json")
	    public @ResponseBody Unit getById(@PathVariable String objectId) {
	    	return unitService.findById(objectId);
	    }

	    @RequestMapping(value = "/unit", method = RequestMethod.PUT)
	    @ResponseStatus(HttpStatus.NO_CONTENT)
	    public void create(@RequestBody Unit unit) {
	    	unitService.saveOrUpdate(unit);
	    }

	    @RequestMapping(value = "/unit/{id}", method = RequestMethod.DELETE)
	    @ResponseStatus(HttpStatus.NO_CONTENT)
	    public void delete(@PathVariable String objectId) {
	    	unitService.deleteById(objectId);
	    }

}
