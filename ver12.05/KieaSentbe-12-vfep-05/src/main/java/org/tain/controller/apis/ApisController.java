package org.tain.controller.apis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.tain.service.apis.ApisService;
import org.tain.utils.CurrentInfo;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping(value = {"/apis"})
@Slf4j
public class ApisController {

	@Autowired
	private ApisService apisService;
	
	@RequestMapping(value = {"/list"}, method = {RequestMethod.GET, RequestMethod.POST})
	public String list(Pageable pageable, Model model) {
		log.info("KANG-20200730 >>>>> {} {}", CurrentInfo.get());
		model.addAttribute("apisList", this.apisService.findApisList(pageable));
		return "web/apis/list";
	}
	
	@RequestMapping(value = {""}, method = {RequestMethod.GET, RequestMethod.POST})
	public String form(@RequestParam(value = "id", defaultValue = "0") Long id, Model model) {
		log.info("KANG-20200730 >>>>> {} {}", CurrentInfo.get());
		model.addAttribute("apis", this.apisService.findApisById(id));
		return "web/apis/form";
	}
	
	///////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////
	
	@RequestMapping(value = {"/checkUserForm"}, method = {RequestMethod.GET, RequestMethod.POST})
	public String authForm(@RequestParam(value = "id", defaultValue = "0") Long id, Model model) {
		log.info("KANG-20200730 >>>>> {} {}", CurrentInfo.get());
		model.addAttribute("apis", this.apisService.findApisById(id));
		return "web/apis/checkUserForm";
	}
	
	@RequestMapping(value = {"/getCalculationForm"}, method = {RequestMethod.GET, RequestMethod.POST})
	public String detailForm(@RequestParam(value = "id", defaultValue = "0") Long id, Model model) {
		log.info("KANG-20200730 >>>>> {} {}", CurrentInfo.get());
		model.addAttribute("apis", this.apisService.findApisById(id));
		return "web/apis/getCalculationForm";
	}
	
	@RequestMapping(value = {"/historiesForm"}, method = {RequestMethod.GET, RequestMethod.POST})
	public String historiesForm(@RequestParam(value = "id", defaultValue = "0") Long id, Model model) {
		log.info("KANG-20200730 >>>>> {} {}", CurrentInfo.get());
		model.addAttribute("apis", this.apisService.findApisById(id));
		return "web/apis/historiesForm";
	}
	
	@RequestMapping(value = {"/validateForm"}, method = {RequestMethod.GET, RequestMethod.POST})
	public String validateForm(@RequestParam(value = "id", defaultValue = "0") Long id, Model model) {
		log.info("KANG-20200730 >>>>> {} {}", CurrentInfo.get());
		model.addAttribute("apis", this.apisService.findApisById(id));
		return "web/apis/validateForm";
	}
	
	@RequestMapping(value = {"/commitForm"}, method = {RequestMethod.GET, RequestMethod.POST})
	public String commitForm(@RequestParam(value = "id", defaultValue = "0") Long id, Model model) {
		log.info("KANG-20200730 >>>>> {} {}", CurrentInfo.get());
		model.addAttribute("apis", this.apisService.findApisById(id));
		return "web/apis/commitForm";
	}
	
	@RequestMapping(value = {"/amendForm"}, method = {RequestMethod.GET, RequestMethod.POST})
	public String amendForm(@RequestParam(value = "id", defaultValue = "0") Long id, Model model) {
		log.info("KANG-20200730 >>>>> {} {}", CurrentInfo.get());
		model.addAttribute("apis", this.apisService.findApisById(id));
		return "web/apis/amendForm";
	}
	
	@RequestMapping(value = {"/refundForm"}, method = {RequestMethod.GET, RequestMethod.POST})
	public String refundForm(@RequestParam(value = "id", defaultValue = "0") Long id, Model model) {
		log.info("KANG-20200730 >>>>> {} {}", CurrentInfo.get());
		model.addAttribute("apis", this.apisService.findApisById(id));
		return "web/apis/refundForm";
	}
	
	@RequestMapping(value = {"/customerForm"}, method = {RequestMethod.GET, RequestMethod.POST})
	public String customerForm(@RequestParam(value = "id", defaultValue = "0") Long id, Model model) {
		log.info("KANG-20200730 >>>>> {} {}", CurrentInfo.get());
		model.addAttribute("apis", this.apisService.findApisById(id));
		return "web/apis/customerForm";
	}
}
