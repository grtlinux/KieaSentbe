package org.tain.object.lns;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonInclude(value = Include.NON_NULL)
public class LnsStream implements Cloneable {

	private String data;
	private int length;           // data_length
	
	private String strLength;     // 4 = (length - 4) = (7 + content)
	private String typeCode;      // 7 0200300/0210300 <- validate-REQ/RES
	private String content;
	
	public LnsStream(String data) {
		this.data   = data;
		this.length = data.length();  // data_length
		
		this.strLength    = data.substring(0, 4);   // info_length = data_length - 4
		this.typeCode     = data.substring(4, 11);  // 7 0200300/0210300 <- validate-REQ/RES
		this.content      = data.substring(11);     // to transfer stream to json
	}
	
	@JsonFormat(shape = Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
	//@CreationTimestamp  // use when repo.save
	private Timestamp creDate = new Timestamp(System.currentTimeMillis());

	////////////////////////////////////////////////////////////////////////
	
	@Override
	public LnsStream clone() throws CloneNotSupportedException {
		return (LnsStream) super.clone();
	}
	
	////////////////////////////////////////////////////////////////////////
	
	public String combind() {
		StringBuffer sb = new StringBuffer();
		
		sb.append(String.format("%-7s", this.typeCode));
		sb.append(this.content);
		
		this.length = sb.length() + 4;  // the value included the length size
		sb.insert(0, String.format("%04d", this.length - 4));
		
		//this.length = sb.length();      // the value excluded the length size
		//sb.insert(0, String.format("%04d", this.length));
		
		this.data = sb.toString();
		
		return this.data;
	}
}
