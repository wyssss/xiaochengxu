package cn.wyslkl.servlet;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dom4j.DocumentException;

import cn.wyslkl.po.TextMessage;
import cn.wyslkl.util.*;

public class WeixinServlet extends HttpServlet{
	@Override
	protected void  doGet(HttpServletRequest req,HttpServletResponse resp)
			throws ServletException,IOException{
		String signature=req.getParameter("signature");
		String timestamp=req.getParameter("timestamp");
		String nonce=req.getParameter("nonce");
		String echostr=req.getParameter("echostr");
		
		PrintWriter out=resp.getWriter();
		if(CheckUtil.checkSignature(signature, timestamp, nonce)){
			out.print(echostr);
		}
		
		
		
	}
	protected void doPost(HttpServletRequest req,HttpServletResponse resp)
	throws ServletException,IOException{
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");
		PrintWriter out=resp.getWriter();
		try{
			Map<String,String> map=MessageUtil.xmlToMap(req);
			String fromUserName=map.get("FromUserName");
			String toUserName=map.get("ToUserName");
			String msgType=map.get("MsgType");
			String content=map.get("Content");
			String message=null;
			if(MessageUtil.MESSAGE_TEXT.equals(msgType)){
				if("1".equals(content)){
					message=MessageUtil.initNewsMessage(toUserName, fromUserName);
				}else if("2".equals(content)){
					message=MessageUtil.initText(toUserName, fromUserName, MessageUtil.SecondMenu());
				}else if("?".equals(content)||"£¿".equals(content)){
					message=MessageUtil.initText(toUserName, fromUserName, MessageUtil.menuText());
				}
				
				System.out.println(message);
			}else if(MessageUtil.MESSAGE_EVENT.equals(msgType)){
				String eventType=map.get("Event");
				if(MessageUtil.MESSAGE_SUBSCRIBE.equals(eventType)){
					message=MessageUtil.initText(toUserName, fromUserName, MessageUtil.menuText());	
				}
			}
			
			out.print(message);
		}catch(DocumentException e){
			e.printStackTrace();	
		}finally{
			out.close();
		}
	}
	

}
