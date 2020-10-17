package org.tain.working.sas;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import org.springframework.stereotype.Component;
import org.tain.utils.CurrentInfo;
import org.tain.utils.Flag;

import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONObject;

@Component
@Slf4j
public class SasWorking {

	public void test01() throws Exception {
		log.info("KANG-20200721 >>>>> {} {}", CurrentInfo.get());
		
		if (Flag.flag) {
			try {
				//String userId = (String) request.getParameter("_userId");
				String userId = "123456";
				JSONObject cred = new JSONObject();
				cred.put("userId", userId.toString());
				
				String sessionUrl = "https://ifrs17.idblife.com/sas/logincheck.json";
				URL object = new URL(sessionUrl);
				HttpURLConnection conn = (HttpURLConnection) object.openConnection();
				conn.setDoOutput(true);
				conn.setDoInput(true);
				conn.setRequestProperty("Content-Type", "application/json");
				conn.setRequestProperty("Accept", "application/json");
				conn.setRequestMethod("POST");
				
				OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream());
				writer.write(cred.toString());
				writer.flush();
				writer.close();
				
				int HttpResult = conn.getResponseCode();
				
				StringBuilder sb = new StringBuilder();
				BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
				String line = null;
				while ((line = reader.readLine()) != null) {
					sb.append(line + "\n");
				}
				reader.close();
				
				if (Flag.flag) System.out.println(">>>>> " + sb.toString());
				
				if (sb.substring(9, 10) != "-") {
					if (Flag.flag) System.out.println(">>>>> " + sb.substring(8, 11));
					
					String username = userId + "@saspw";
					String password = "{SAS002}8E98559A2083C61234994C043A34DA8C";
					
					//final StringBuffer url = request.getRequestURL();
					final String url = "https://www.test.com:80/abc/testcall.jsp";
					String url2 = url. substring(0, url. lastIndexOf('/'));  // https://www.test.com:80/abc
					String url3 = url2.substring(0, url2.lastIndexOf('/'));  // https://www.test.com:80
					url3 = url3 + "/SASLogon/readirect.jsp";  // https://www.test.com:80/SASLogon/redirect.jsp
					
					// targetUrl = https://www.test.com:80/SASLogon/redirect.jsp
					URL targetUrl = new URL(url3);
					// casUrl = https://www.test.com:80/SASLogon
					URL casUrl = new URL(targetUrl.getProtocol(), targetUrl.getHost(), targetUrl.getPort(), "/SASLogon");
					
					AuthenticationClient client = new AuthenticationClient(casUrl.toString());
					client.logon(username, password);
					AuthenticationClientHolder.set(client);
					
					final String ticket = client.acquireTicket(targetUrl.toString());
					
					URL reconnectUrl = new URL(casUrl 
							+ "?direct_authentication_ticket=" + ticket 
							+ "&service=" + URLEncoder.encode(targetUrl.toString()
							, "UTF-8"));
					if (reconnectUrl.toString() != null) {
						response.sendRedirect(reconnectUrl.toString());
					}
				}
			} catch (Exception e) {
				response.sendRedirect("/SASLogon/logout");
			}
		}
	}
}
