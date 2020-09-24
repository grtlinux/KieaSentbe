package org.tain.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.tain.utils.CurrentInfo;
import org.tain.utils.Sample;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping(value = {"/test"})
@Slf4j
public class TestController {

	@RequestMapping(value = {"", "/kang"}, method = {RequestMethod.GET, RequestMethod.POST})
	public String kang(Model model) {
		log.info("KANG-20200808 >>>>> {} {}", CurrentInfo.get());
		model.addAttribute("data", Sample.getMap());
		return "test/kang";
	}
}
