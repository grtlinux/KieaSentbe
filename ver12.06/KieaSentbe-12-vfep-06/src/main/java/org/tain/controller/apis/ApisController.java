package org.tain.controller.apis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.tain.properties.ProjEnvUrlProperties;
import org.tain.service.apis.ApisService;
import org.tain.utils.CurrentInfo;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping(value = {"/apis"})
@Slf4j
public class ApisController {

	@Autowired
	private ProjEnvUrlProperties projEnvUrlProperties;
	
	@Autowired
	private ApisService apisService;
	
	@RequestMapping(value = {"/list"}, method = {RequestMethod.GET, RequestMethod.POST})
	public String list(Pageable pageable, Model model) {
		log.info("KANG-20200730 >>>>> {} {}", CurrentInfo.get());
		model.addAttribute("apisList", this.apisService.findApisList(pageable));
		model.addAttribute("urlOnline", this.projEnvUrlProperties.getOnline());
		return "web/apis/list";
	}
	
	@RequestMapping(value = {""}, method = {RequestMethod.GET, RequestMethod.POST})
	public String form(@RequestParam(value = "id", defaultValue = "0") Long id, Model model) {
		log.info("KANG-20200730 >>>>> {} {}", CurrentInfo.get());
		model.addAttribute("apis", this.apisService.findApisById(id));
		model.addAttribute("urlOnline", this.projEnvUrlProperties.getOnline());
		return "web/apis/form";
	}
	
	///////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////
	
	@RequestMapping(value = {"/pingForm"}, method = {RequestMethod.GET, RequestMethod.POST})
	public String pingForm(@RequestParam(value = "id", defaultValue = "0") Long id, Model model) {
		log.info("KANG-20200730 >>>>> {} {}", CurrentInfo.get());
		//model.addAttribute("apis", this.apisService.findApisById(id));
		//return "web/apis/pingForm";
		return "redirect:/apis/list";
		//return new RedirectView("/apis/list");
	}
	
	@RequestMapping(value = {"/encryptForm"}, method = {RequestMethod.GET, RequestMethod.POST})
	public String encryptForm(@RequestParam(value = "id", defaultValue = "0") Long id, Model model) {
		log.info("KANG-20200730 >>>>> {} {}", CurrentInfo.get());
		//model.addAttribute("apis", this.apisService.findApisById(id));
		//return "web/apis/encryptForm";
		return "redirect:/apis/list";
		//return new RedirectView("/apis/list");
	}
	
	@RequestMapping(value = {"/decryptForm"}, method = {RequestMethod.GET, RequestMethod.POST})
	public String decryptForm(@RequestParam(value = "id", defaultValue = "0") Long id, Model model) {
		log.info("KANG-20200730 >>>>> {} {}", CurrentInfo.get());
		//model.addAttribute("apis", this.apisService.findApisById(id));
		//return "web/apis/decryptForm";
		return "redirect:/apis/list";
		//return new RedirectView("/apis/list");
	}
	
	///////////////////////////////////////////////////////////////////////////
	
	@RequestMapping(value = {"/getCalculationForm"}, method = {RequestMethod.GET, RequestMethod.POST})
	public String detailForm(@RequestParam(value = "id", defaultValue = "0") Long id, Model model) {
		log.info("KANG-20200730 >>>>> {} {}", CurrentInfo.get());
		model.addAttribute("apis", this.apisService.findApisById(id));
		model.addAttribute("urlOnline", this.projEnvUrlProperties.getOnline());
		return "web/apis/getCalculationForm";
	}
	
	@RequestMapping(value = {"/getWebviewIdForm"}, method = {RequestMethod.GET, RequestMethod.POST})
	public String getWebviewIdForm(@RequestParam(value = "id", defaultValue = "0") Long id, Model model) {
		log.info("KANG-20200730 >>>>> {} {}", CurrentInfo.get());
		model.addAttribute("apis", this.apisService.findApisById(id));
		model.addAttribute("urlOnline", this.projEnvUrlProperties.getOnline());
		return "web/apis/getWebviewIdForm";
	}
	
	@RequestMapping(value = {"/getResultForm"}, method = {RequestMethod.GET, RequestMethod.POST})
	public String getResultForm(@RequestParam(value = "id", defaultValue = "0") Long id, Model model) {
		log.info("KANG-20200730 >>>>> {} {}", CurrentInfo.get());
		model.addAttribute("apis", this.apisService.findApisById(id));
		model.addAttribute("urlOnline", this.projEnvUrlProperties.getOnline());
		return "web/apis/getResultForm";
	}
	
	@RequestMapping(value = {"/getVerificationForm"}, method = {RequestMethod.GET, RequestMethod.POST})
	public String getVerificationForm(@RequestParam(value = "id", defaultValue = "0") Long id, Model model) {
		log.info("KANG-20200730 >>>>> {} {}", CurrentInfo.get());
		model.addAttribute("apis", this.apisService.findApisById(id));
		model.addAttribute("urlOnline", this.projEnvUrlProperties.getOnline());
		return "web/apis/getVerificationForm";
		//return "redirect:/apis/list";
		//return new RedirectView("/apis/list");
	}
	
	@RequestMapping(value = {"/checkUserForm"}, method = {RequestMethod.GET, RequestMethod.POST})
	public String authForm(@RequestParam(value = "id", defaultValue = "0") Long id, Model model) {
		log.info("KANG-20200730 >>>>> {} {}", CurrentInfo.get());
		model.addAttribute("apis", this.apisService.findApisById(id));
		model.addAttribute("urlOnline", this.projEnvUrlProperties.getOnline());
		return "web/apis/checkUserForm";
	}
	
	@RequestMapping(value = {"/deleteUserForm"}, method = {RequestMethod.GET, RequestMethod.POST})
	public String historiesForm(@RequestParam(value = "id", defaultValue = "0") Long id, Model model) {
		log.info("KANG-20200730 >>>>> {} {}", CurrentInfo.get());
		model.addAttribute("apis", this.apisService.findApisById(id));
		model.addAttribute("urlOnline", this.projEnvUrlProperties.getOnline());
		return "web/apis/deleteUserForm";
	}
	
	@RequestMapping(value = {"/createUserForm"}, method = {RequestMethod.GET, RequestMethod.POST})
	public String createUserForm(@RequestParam(value = "id", defaultValue = "0") Long id, Model model) {
		log.info("KANG-20200730 >>>>> {} {}", CurrentInfo.get());
		model.addAttribute("apis", this.apisService.findApisById(id));
		model.addAttribute("urlOnline", this.projEnvUrlProperties.getOnline());
		return "web/apis/createUserForm";
	}
	
	@RequestMapping(value = {"/migrationUserForm"}, method = {RequestMethod.GET, RequestMethod.POST})
	public String migrationUserForm(@RequestParam(value = "id", defaultValue = "0") Long id, Model model) {
		log.info("KANG-20200730 >>>>> {} {}", CurrentInfo.get());
		model.addAttribute("apis", this.apisService.findApisById(id));
		model.addAttribute("urlOnline", this.projEnvUrlProperties.getOnline());
		return "web/apis/migrationUserForm";
	}
}
