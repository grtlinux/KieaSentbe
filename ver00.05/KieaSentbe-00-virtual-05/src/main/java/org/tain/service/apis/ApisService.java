package org.tain.service.apis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.tain.domain.apis.Apis;
import org.tain.repository.apis.ApisRepository;
import org.tain.utils.CurrentInfo;
import org.tain.utils.Flag;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ApisService {

	@Autowired
	private ApisRepository apisRepository;
	
	public Page<Apis> findApisList(Pageable pageable) {
		if (Flag.flag) {
			int pageNumber = pageable.getPageNumber() <= 0 ? 0 : pageable.getPageNumber() - 1;
			int pageSize = pageable.getPageSize();
			Sort sort = pageable.getSort();
			
			pageSize = 20;
			sort = Sort.by("id").ascending();
			//sort = Sort.by("id").descending();
			
			pageable = PageRequest.of(pageNumber, pageSize, sort);
		}
		log.info("KANG-20200808 >>>>> {} {}", CurrentInfo.get());
		return this.apisRepository.findAll(pageable);
	}
	
	public Apis findApisById(Long id) {
		log.info("KANG-20200808 >>>>> {} {}", CurrentInfo.get());
		return this.apisRepository.findById(id).orElse(new Apis());
	}
}
