package org.tain.data;

public class LnsData {

	private static LnsData instance = null;
	
	public static LnsData getInstance() {
		if (instance == null) {
			instance = new LnsData();
		}
		return instance;
	}
	
	///////////////////////////////////////////////////////////////////////////
	
	private String accessToken = null;
	
	public synchronized void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	
	public synchronized String getAccessToken() {
		return this.accessToken;
	}
	
	///////////////////////////////////////////////////////////////////////////
	
	private String srcTransactionId = null;
	
	public synchronized void setSrcTrId(String srcTransactionId) {
		this.srcTransactionId = srcTransactionId;
	}
	
	public synchronized String getSrcTrId() {
		return this.srcTransactionId;
	}
	
	///////////////////////////////////////////////////////////////////////////
	
	private String lnsTransactionId = null;
	
	public synchronized void setLnsTrId(String lnsTransactionId) {
		this.lnsTransactionId = lnsTransactionId;
	}
	
	public synchronized String getLnsTrId() {
		return this.lnsTransactionId;
	}
	
	///////////////////////////////////////////////////////////////////////////
	
	private String dstTransactionId = null;
	
	public synchronized void setDstTrId(String dstTransactionId) {
		this.dstTransactionId = dstTransactionId;
	}
	
	public synchronized String getDstTrId() {
		return this.dstTransactionId;
	}
	
	///////////////////////////////////////////////////////////////////////////
}
