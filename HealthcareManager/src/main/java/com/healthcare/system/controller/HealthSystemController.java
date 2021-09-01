package com.healthcare.system.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.healthcare.system.domain.HealthCareServices;
import com.healthcare.system.service.ProcessService;

@Controller
public class HealthSystemController {

	@Autowired
	private ProcessService service;

	@RequestMapping("/")
	public String viewHomePage(Model model) {
		List<HealthCareServices> serviceList = service.listAll();
		model.addAttribute("serviceList", serviceList);
		return "index";
	}

	@RequestMapping(value = "/new", method = RequestMethod.GET)
	public String showNServicePage(Model model) {
		HealthCareServices healthCareServices = new HealthCareServices();
		model.addAttribute("service", healthCareServices);

		return "new_service";
	}

	@RequestMapping(value = "/services/{id}", method = RequestMethod.GET)
	public ResponseEntity<HealthCareServices> get(@PathVariable Integer id) {
		try {
			HealthCareServices services = service.get(id);
			return new ResponseEntity<HealthCareServices>(services, HttpStatus.OK);
		} catch (DataAccessException e) {
			return new ResponseEntity<HealthCareServices>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String add(@ModelAttribute("service") HealthCareServices newService) {
		service.addNewService(newService);
		return "redirect:/";
	}

	@RequestMapping("/edit/{id}")
	public ModelAndView showEditProductPage(@PathVariable(name = "id") int id) {
		ModelAndView mav = new ModelAndView("edit_service");
		HealthCareServices serviceTobeUpdated = service.get(id);
		mav.addObject("serviceDetails", serviceTobeUpdated);

		return mav;
	}

	@PutMapping("/services")
	public ResponseEntity<?> update(@RequestBody HealthCareServices services) {
		try {
			service.updateService(services);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (DataAccessException e) {
			return new ResponseEntity<HealthCareServices>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping("/delete/{id}")
	public String deleteProduct(@PathVariable(name = "id") int id) {
		service.delete(id);
		return "redirect:/";
	}
}
