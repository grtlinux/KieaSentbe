package org.tain.utils;

import java.io.File;

import com.fasterxml.jackson.databind.ObjectMapper;

public interface JsonPrintImpl {

	public ObjectMapper getObjectMapper();
	public String toJson(Object object);
	public String toPrettyJson(Object object);
	public void printJson(Object object);
	public void printJson(String title, Object object);
	public void printPrettyJson(Object object);
	public void printPrettyJson(String title, Object object);
	public void saveJson(File file, Object object);
	public void savePrettyJson(File file, Object object);
}
