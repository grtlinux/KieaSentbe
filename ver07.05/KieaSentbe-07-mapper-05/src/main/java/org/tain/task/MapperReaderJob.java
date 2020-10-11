package org.tain.task;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.tain.mapper.LnsMstInfo;
import org.tain.properties.ProjEnvParamProperties;
import org.tain.utils.CurrentInfo;
import org.tain.utils.Flag;
import org.tain.utils.Sleep;
import org.tain.utils.StringTools;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class MapperReaderJob {

	private String param;
	
	private Map<String, LnsMstInfo> mapInfo = new HashMap<>();
	
	@Autowired
	private ProjEnvParamProperties projEnvParamProperties;
	
	public LnsMstInfo get(String key) {
		return this.mapInfo.get(key);
	}
	
	@Async(value = "async_mapperReaderJob")
	public void mapperReaderJob(String param) throws Exception {
		this.param = param;
		log.info("KANG-20200721 >>>>> {} {} {}", this.param, CurrentInfo.get());
		
		String basePath = null;
		if (Flag.flag) {
			String home = this.projEnvParamProperties.getHome();
			String base = this.projEnvParamProperties.getBase();
			basePath = home + base;
			if (Flag.flag) log.info(">>>>>> BasePath name is " + basePath);
		}
		
		if (Flag.flag) {
			File fileBasePath = new File(basePath);
			for (final File fileEntry : fileBasePath.listFiles()) {
				if (fileEntry.isFile()) {
					if (Flag.flag) log.info(">>>>> [{}] [{}]", fileEntry.getParent(), fileEntry.getName());
					
					if (!StringTools.isExtension(fileEntry.getName(), "json"))
						continue;
					
					LnsMstInfo lnsMstInfo = new LnsMstInfo(fileEntry.getParent(), fileEntry.getName());
					String reqResType = lnsMstInfo.getReqResType();
					String fileName = lnsMstInfo.getFileName();
					mapInfo.put(reqResType, lnsMstInfo);
					mapInfo.put(fileName, lnsMstInfo);
					
					if (Flag.flag) {
						log.info("======================= START: {} ==========================", lnsMstInfo.getFileName());
						log.info(">>>>> basePath = {}", lnsMstInfo.getBasePath());
						log.info(">>>>> fileName = {}", lnsMstInfo.getFileName());
						log.info(">>>>> filePath = {}", lnsMstInfo.getFilePath());
						log.info(">>>>> lastModified = {}", lnsMstInfo.getLastModfied());
						log.info(">>>>> strJsonInfo = {}", lnsMstInfo.getStrJsonInfo());
						log.info(">>>>> reqResType = {}", lnsMstInfo.getReqResType());
						log.info(">>>>> infoNode = {}", lnsMstInfo.getInfoNode().toPrettyString());
						log.info(">>>>> headBaseInfoNode = {}", lnsMstInfo.getHeadBaseInfoNode().toPrettyString());
						log.info(">>>>> headDataInfoNode = {}", lnsMstInfo.getHeadDataInfoNode().toPrettyString());
						log.info(">>>>> bodyBaseInfoNode = {}", lnsMstInfo.getBodyBaseInfoNode().toPrettyString());
						log.info(">>>>> bodyDataInfoNode = {}", lnsMstInfo.getBodyDataInfoNode().toPrettyString());
						log.info("======================= END: {} ==========================", lnsMstInfo.getFileName());
					}
				}
			}
		}
		
		if (Flag.flag) {
			File fileBasePath = new File(basePath);
			while (true) {
				for (final File fileEntry : fileBasePath.listFiles()) {
					if (fileEntry.isDirectory()) {
						// subdirectory
					} else if (fileEntry.isFile()) {
						// file
						if (!StringTools.isExtension(fileEntry.getName(), "json"))
							continue;
						
						LnsMstInfo lnsMstInfo = mapInfo.get(fileEntry.getName());
						if (lnsMstInfo.checkAndUpdate(fileEntry)) {
							if (Flag.flag) log.info(">>>>> {} file is updated.", fileEntry.getName());
						}
					}
				}
				
				Sleep.run(10 * 1000);
			}
		}
	}
}