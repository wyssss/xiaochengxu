package cn.wyslkl.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.xml.ws.Response;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import cn.wyslkl.menu.Button;
import cn.wyslkl.menu.ClickButton;
import cn.wyslkl.menu.Menu;
import cn.wyslkl.menu.ViewButton;
import cn.wyslkl.po.AccessToken;
import net.sf.json.JSONObject;

public class WeiXinUtil {
	private static String APPID="wxa2ef2111256615be";
	private static String APPSECRET="ff2ac8ef40deffa2d778b88e852137ab";
	private static final String ACCESS_TOKEN_URL="https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
	private static final String CREATE_MENU_URL="https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";
	/*
	 * get请求
	 */
	public static JSONObject doGetStr(String url){
		DefaultHttpClient httpClient=new DefaultHttpClient();
		HttpGet httpGet=new HttpGet(url);
		JSONObject jsonObject=null;
		try{
			HttpResponse response=httpClient.execute(httpGet);
			HttpEntity entity=response.getEntity();
			if(entity!=null){
				String result=EntityUtils.toString(entity,"UTF-8");
				jsonObject=JSONObject.fromObject(result);
			}
		}catch(ClientProtocolException e){
			e.printStackTrace();
		}catch(IOException e){
			e.printStackTrace();
		}
		return jsonObject;
	}
	/*
	 * post请求
	 */
	public static JSONObject doPostStr(String url,String outStr){
		DefaultHttpClient httpClient=new DefaultHttpClient();
		HttpPost httpPost=new HttpPost(url);
		JSONObject jsonObject=null;
		try {
			httpPost.setEntity(new StringEntity(outStr,"UTF-8"));
			HttpResponse response=httpClient.execute(httpPost);
			String result=EntityUtils.toString(response.getEntity(),"UTF-8");
			jsonObject=JSONObject.fromObject(result);
			} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jsonObject;
		
	}
	/*
	 * 获取access_token
	 */
	public static AccessToken getAccessToken(){
		
		AccessToken token=new AccessToken();
		String url=ACCESS_TOKEN_URL.replace("APPID", APPID).replace("APPSECRET", APPSECRET);
		JSONObject jsonObject=doGetStr(url);
		if(jsonObject!=null){
			token.setToken(jsonObject.getString("access_token"));
			token.setExpiresIn(jsonObject.getInt("expires_in"));
		}
	return token;
	
	}
	/*
	 * 组装菜单
	 */
	public static Menu initMenu(){
		Menu menu=new Menu();
		ClickButton button11=new ClickButton();
		button11.setName("click菜单");
		button11.setType("click");
		button11.setKey("11");
		
		ViewButton button21=new ViewButton();
		button21.setName("view菜单");
		button21.setType("view");
		button21.setUrl("http://www.imooc.com");
		
		ClickButton button31=new ClickButton();
		button31.setName("扫码事件");
		button31.setType("scancode_push");
		button31.setKey("31");
		
		ClickButton button32=new ClickButton();
		button32.setName("地理位置");
		button32.setType("location_select");
		button32.setKey("32");
		
		Button button=new Button();
		button.setName("菜单");
		button.setSub_button(new Button[]{button31,button32});
		
		menu.setButton(new Button[]{button11,button21,button});
		return menu;
	}
	public static int createMenu(String token,String menu){
		int result=0;
		String url=CREATE_MENU_URL.replace("ACCESS_TOKEN", token);
		JSONObject jsonObject=doPostStr(url,menu);
		if(jsonObject!=null){
			result=jsonObject.getInt("errcode");
		}
		return result;
	}

}
