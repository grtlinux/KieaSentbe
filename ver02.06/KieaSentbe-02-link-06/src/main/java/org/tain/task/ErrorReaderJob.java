package org.tain.task;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.tain.data.LnsError;
import org.tain.properties.ProjEnvJsonProperties;
import org.tain.properties.ProjEnvParamProperties;
import org.tain.utils.CurrentInfo;
import org.tain.utils.Flag;
import org.tain.utils.JsonPrint;
import org.tain.utils.Sleep;
import org.tain.utils.StringTools;

import com.fasterxml.jackson.core.type.TypeReference;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class ErrorReaderJob {

	private static final int LOOP_SLEEP_SEC = 10;
	
	private String param;
	
	@Autowired
	private ProjEnvParamProperties projEnvParamProperties;
	
	@Autowired
	private ProjEnvJsonProperties projEnvJsonProperties;
	
	private Map<String, LnsError> mapError = new HashMap<>();
	
	private LnsError error999 = null;
	
	///////////////////////////////////////////////////////////////////////////
	
	public Map<String, LnsError> getMap() {
		return this.mapError;
	}
	
	public LnsError get(String key) {
		if (key == null)
			return this.error999;
		
		return this.mapError.get(key);
	}
	
	public LnsError get() {
		return this.get(null);
	}
	
	public LnsError search(String message) {
		if (message != null) {
			for (Map.Entry<String, LnsError> entry : this.mapError.entrySet()) {
				LnsError lnsError = entry.getValue();
				//if (Flag.flag) log.info(">>>>> " + lnsError.getError_regex());
				
				boolean isMatch = message.matches(lnsError.getError_regex());
				if (isMatch) {
					if (Flag.flag) log.info("# SEARCH_REGEX: '{}' -> '{}'", lnsError.getError_regex(), message);
					return lnsError;
				}
			}
		}
		
		if (Flag.flag) log.info("# SEARCH_REGEX: '{}' -> '{}'", this.error999.getError_regex(), message);
		return this.error999;
	}
	
	public LnsError search() {
		return this.search(null);
	}
	
	///////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////
	
	@Async(value = "async_errorReaderJob")
	public void errorReaderJob(String param) throws Exception {
		this.param = param;
		log.info("KANG-20200721 >>>>> {} {} {}", this.param, CurrentInfo.get());
		
		String errorFile = null;
		if (Flag.flag) {
			String home = this.projEnvParamProperties.getHome();
			String base = this.projEnvParamProperties.getBase();
			String infoPath = this.projEnvParamProperties.getInfoPath();
			String errorInfoFile = this.projEnvJsonProperties.getErrorInfoFile();
			errorFile = home + base + infoPath + File.separator + errorInfoFile;
			
			log.info(">>>>> errorFile: " + errorFile);
		}
		
		if (Flag.flag) {
			//
			this.mapError.clear();
			long oldLastModified = 0L;
			File fileError = new File(errorFile);
			
			while (true) {
				long lastModified = fileError.lastModified();
				//if (Flag.flag) log.info("============ sleep 10 sec ================" + fileError.lastModified());
				
				if (oldLastModified != lastModified) {
					if (Flag.flag) log.info(">>>>> update file error.json and doing some job... {} {} ", lastModified, this.param);
					oldLastModified = lastModified;
					
					List<LnsError> lstLnsError = JsonPrint.getInstance().getObjectMapper().readValue(StringTools.stringFromFile(errorFile), new TypeReference<List<LnsError>>() {});
					if (Flag.flag) lstLnsError.forEach(lnsError -> {
						if (Flag.flag) log.info(">>>>> {} = {}", lnsError.getKey(), lnsError);
						if (lnsError.getError_code().equals("999")) {
							this.error999 = lnsError;
						} else {
							this.mapError.put(lnsError.getKey(), lnsError);
						}
					});
				}
				
				Sleep.run(LOOP_SLEEP_SEC * 1000);
			}
		}
	}
}