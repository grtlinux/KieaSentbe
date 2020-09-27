package org.tain.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.tain.utils.CurrentInfo;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping(value = {"/"})
@Slf4j
public class HomeController {

	@RequestMapping(value = {"", "home"}, method = {RequestMethod.GET, RequestMethod.POST})
	public String home(Model model) {
		log.info("KANG-20200808 >>>>> {} {}", CurrentInfo.get());
		return "home";
	}
}
