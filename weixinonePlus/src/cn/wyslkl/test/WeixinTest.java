package cn.wyslkl.test;

import net.sf.json.JSONObject;
import cn.wyslkl.po.AccessToken;
import cn.wyslkl.util.WeiXinUtil;

public class WeixinTest{
public static void main(String[] args){
	try {
		AccessToken token=WeiXinUtil.getAccessToken();
		System.out.println("Ʊ�ݣ�"+token.getToken());
		System.out.println("��Чʱ�䣺"+token.getExpiresIn());
		
		String menu=JSONObject.fromObject(WeiXinUtil.initMenu()).toString();
		int result=WeiXinUtil.createMenu(token.getToken(), menu);
		if(result==0){
			System.out.println("�����˵��ɹ�");
			
		}else{
			System.out.println("������"+result);
		}
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
}
}