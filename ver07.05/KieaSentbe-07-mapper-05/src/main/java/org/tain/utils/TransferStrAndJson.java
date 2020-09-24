package org.tain.utils;

import java.lang.reflect.Field;
import java.text.DecimalFormat;

import org.tain.annotation.StreamAnnotation;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TransferStrAndJson {

	private static boolean flagTest = !true;
	
	///////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////
	
	public static String getStream(Object object) {
		StringBuffer sb = new StringBuffer();
		
		if (Flag.flag) {
			// simple test
		}
		
		if (Flag.flag) {
			try {
				Field[] fields = object.getClass().getDeclaredFields();
				for (Field field : fields) {
					//log.trace("FIELD: '{}'", field.getName());
					field.setAccessible(true);
					
					StreamAnnotation annotation = field.getAnnotation(StreamAnnotation.class);
					if (annotation != null && annotation.usable()) {
						int length = annotation.length();
						
						Class<?> type = field.getType();
						if (type == String.class) {
							String value = (String) field.get(object);
							if (value == null) value = "";
							log.trace("getStream().FIELD: '{}', String: '{}', length: {}", field.getName(), value, length);
							sb.append(String.format("%-" + length + "." + length + "s", value));
						} else if (type == String[].class) {
							String[] value = (String[]) field.get(object);
							if (value == null) value = null;
							log.trace("getStream().FIELD: '{}', String[]: '{}', length: {}", field.getName(), value, length);
							// TODO: sb.append(String.format("%-" + length + "." + length + "s", value));
						} else if (type == long.class) {
							long value = (long) field.get(object);
							log.trace("getStream().FIELD: '{}', long: {}, length: {}", field.getName(), value, length);
							sb.append(String.format("%" + length + "." + length + "s", String.valueOf(value)));
						} else if (type == int.class) {
							int value = (int) field.get(object);
							log.trace("getStream().FIELD: '{}', int: {}, length: {}", field.getName(), value, length);
							sb.append(String.format("%" + length + "." + length + "s", String.valueOf(value)));
						} else if (type == double.class) {
							// TO DO: to fixed for scale precision
							double value = (double) field.get(object);
							log.trace("getStream().FIELD: '{}', double: {}, length: {}", field.getName(), value, length);
							DecimalFormat df = new DecimalFormat("#");
							df.setMaximumFractionDigits(5);
							//sb.append(String.format("%" + length + "f", value));
							//System.out.println("2. " + df.format(value));
							sb.append(String.format("%" + length + "." + length + "s", df.format(value)));
						} else if (type == float.class) {
							// TO DO: to fixed for scale precision
							float value = (float) field.get(object);
							log.trace("getStream().FIELD: '{}', float: {}, length: {}", field.getName(), value, length);
							//DecimalFormat df = new DecimalFormat("#");
							//df.setMaximumFractionDigits(5);
							//sb.append(String.format("%" + length + "." + length + "s", df.format(value)));
							sb.append(String.format("%" + length + "." + length + "s", String.valueOf(value)));
						} else if (type == boolean.class) {
							// TO DO: to fixed for scale precision
							boolean value = (boolean) field.get(object);
							log.trace("getStream().FIELD: '{}', boolean: {}, length: {}", field.getName(), value, length);
							//DecimalFormat df = new DecimalFormat("#");
							//df.setMaximumFractionDigits(5);
							//sb.append(String.format("%" + length + "." + length + "s", df.format(value)));
							sb.append(String.format("%" + length + "." + length + "s", String.valueOf(value)));
						} else {
							Object obj = field.get(object);
							sb.append(TransferStrAndJson.getStream(obj));
							// continue;
						}
					} else {
						// sb.append(field.get(this).toString());
					}
					if (flagTest) sb.append('|');
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (flagTest) sb.deleteCharAt(sb.length() - 1);
		
		return sb.toString();
	}
	
	///////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////
	
	public static SubString subString = null;
	
	public static Object getObject(Object object) {
		
		boolean flagNull = true;
		
		if (Flag.flag) {
			// simple test
		}
		
		if (Flag.flag) {
			try {
				Field[] fields = object.getClass().getDeclaredFields();
				for (Field field : fields) {
					//log.trace("FIELD: '{}'", field.getName());
					field.setAccessible(true);
					
					StreamAnnotation annotation = field.getAnnotation(StreamAnnotation.class);
					if (annotation != null && annotation.usable()) {
						int length = annotation.length();
						//boolean useNull = annotation.useNull();
						boolean useNullSpace = annotation.useNullSpace();
						
						Class<?> type = field.getType();
						if (type == String.class) {
							String value = subString.get(length).trim();
							log.trace("getObject().FIELD: '{}', String: '{}', length: {}", field.getName(), value, length);
							if ("".equals(value) && useNullSpace == false)
								value = null;
							field.set(object, value);
							flagNull = false;
						} else if (type == String[].class) {
							String value = subString.get(length).trim();
							log.trace("getObject().FIELD: '{}', String: '{}', length: {}", field.getName(), value, length);
							if ("".equals(value) && useNullSpace == false)
								value = null;
							field.set(object, value);
							flagNull = false;
						} else if (type == long.class) {
							long value = Long.parseLong(subString.get(length).trim());
							log.trace("getObject().FIELD: '{}', long: {}, length: {}", field.getName(), value, length);
							field.setLong(object, value);
							flagNull = false;
						} else if (type == int.class) {
							int value = Integer.parseInt(subString.get(length).trim());
							log.trace("getObject().FIELD: '{}', int: {}, length: {}", field.getName(), value, length);
							field.setInt(object, value);
							flagNull = false;
						} else if (type == double.class) {
							double value = Double.parseDouble(subString.get(length).trim());
							log.trace("getObject().FIELD: '{}', double: {}, length: {}", field.getName(), value, length);
							field.setDouble(object, value);
							flagNull = false;
						} else if (type == float.class) {
							float value = Float.parseFloat(subString.get(length).trim());
							log.trace("getObject().FIELD: '{}', float: {}, length: {}", field.getName(), value, length);
							field.setFloat(object, value);
							flagNull = false;
						} else if (type == boolean.class) {
							boolean value = Boolean.parseBoolean(subString.get(length).trim());
							log.trace("getObject().FIELD: '{}', boolean: {}, length: {}", field.getName(), value, length);
							field.setBoolean(object, value);
							flagNull = false;
						} else {
							Object obj = field.get(object);
							obj = TransferStrAndJson.getObject(obj);
							field.set(object, obj);
							if (obj != null) flagNull = false;
						}
					} else {
						//
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		if (flagNull) object = null;
		
		return object;
	}
	
	///////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////
	
	public static String getCStruct(Object object) {
		StringBuffer sb = new StringBuffer();
		
		if (Flag.flag) {
			try {
				Field[] fields = object.getClass().getDeclaredFields();
				for (Field field : fields) {
					log.info("FIELD: '{}'", field.getName());
					field.setAccessible(true);
					
					StreamAnnotation annotation = field.getAnnotation(StreamAnnotation.class);
					if (annotation != null && annotation.usable()) {
						int length = annotation.length();
						String fieldName = field.getName();
						int fieldSize = annotation.length();
						
						Class<?> type = field.getType();
						if (type == String.class) {
							String value = (String) field.get(object);
							if (value == null) value = "";
							log.trace("getStream().FIELD: '{}', String: '{}', length: {}", field.getName(), value, length);
							//sb.append(String.format("%-" + length + "." + length + "s", value));
							sb.append(String.format("\tchar %-50.50s [%3d];", fieldName, fieldSize));
							sb.append(String.format(" /* %s */", "COMMENT"));
							sb.append("\n");
						} else if (type == String[].class) {
							String[] value = (String[]) field.get(object);
							if (value == null) value = null;
							log.trace("getStream().FIELD: '{}', String[]: '{}', length: {}", field.getName(), value, length);
							// TODO: sb.append(String.format("%-" + length + "." + length + "s", value));
						} else if (type == long.class) {
							long value = (long) field.get(object);
							log.trace("getStream().FIELD: '{}', long: {}, length: {}", field.getName(), value, length);
							//sb.append(String.format("%" + length + "." + length + "s", String.valueOf(value)));
							sb.append(String.format("\tchar %-50.50s [%3d];", fieldName, fieldSize));
							sb.append(String.format(" /* %s */", "COMMENT"));
							sb.append("\n");
						} else if (type == int.class) {
							int value = (int) field.get(object);
							log.trace("getStream().FIELD: '{}', int: {}, length: {}", field.getName(), value, length);
							//sb.append(String.format("%" + length + "." + length + "s", String.valueOf(value)));
							sb.append(String.format("\tchar %-50.50s [%3d];", fieldName, fieldSize));
							sb.append(String.format(" /* %s */", "COMMENT"));
							sb.append("\n");
						} else if (type == double.class) {
							// TO DO: to fixed for scale precision
							double value = (double) field.get(object);
							log.trace("getStream().FIELD: '{}', double: {}, length: {}", field.getName(), value, length);
							DecimalFormat df = new DecimalFormat("#");
							df.setMaximumFractionDigits(5);
							//sb.append(String.format("%" + length + "f", value));
							//System.out.println("2. " + df.format(value));
							//sb.append(String.format("%" + length + "." + length + "s", df.format(value)));
							sb.append(String.format("\tchar %-50.50s [%3d];", fieldName, fieldSize));
							sb.append(String.format(" /* %s */", "COMMENT"));
							sb.append("\n");
						} else if (type == float.class) {
							// TO DO: to fixed for scale precision
							float value = (float) field.get(object);
							log.trace("getStream().FIELD: '{}', float: {}, length: {}", field.getName(), value, length);
							//DecimalFormat df = new DecimalFormat("#");
							//df.setMaximumFractionDigits(5);
							//sb.append(String.format("%" + length + "." + length + "s", df.format(value)));
							//sb.append(String.format("%" + length + "." + length + "s", String.valueOf(value)));
							sb.append(String.format("\tchar %-50.50s [%3d];", fieldName, fieldSize));
							sb.append(String.format(" /* %s */", "COMMENT"));
							sb.append("\n");
						} else if (type == boolean.class) {
							// TO DO: to fixed for scale precision
							boolean value = (boolean) field.get(object);
							log.trace("getStream().FIELD: '{}', boolean: {}, length: {}", field.getName(), value, length);
							//DecimalFormat df = new DecimalFormat("#");
							//df.setMaximumFractionDigits(5);
							//sb.append(String.format("%" + length + "." + length + "s", df.format(value)));
							//sb.append(String.format("%" + length + "." + length + "s", String.valueOf(value)));
							sb.append(String.format("\tchar %-50.50s [%3d];", fieldName, fieldSize));
							sb.append(String.format(" /* %s */", "COMMENT"));
							sb.append("\n");
						} else {
							Object obj = field.get(object);
							sb.append(TransferStrAndJson.getCStruct(obj));
							// continue;
						}
					} else {
						// sb.append(field.get(this).toString());
					}
					if (flagTest) sb.append('|');
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (flagTest) sb.deleteCharAt(sb.length() - 1);
		
		return sb.toString();
	}
}
