package cn.wyslkl.util;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import javax.servlet.http.HttpServletRequest;

import org.dom4j.io.SAXReader;

import cn.wyslkl.po.News;
import cn.wyslkl.po.NewsMessage;
import cn.wyslkl.po.TextMessage;

import com.thoughtworks.xstream.XStream;
public class MessageUtil {
	public static final String MESSAGE_NEWS="news";
	public static final String MESSAGE_TEXT="text";
	public static final String MESSAGE_IMAGE="image";
	public static final String MESSAGE_VOICE="voice";
	public static final String MESSAGE_VIDEO="video";
	public static final String MESSAGE_LINK="link";
	public static final String MESSAGE_LOCATION="location";
	public static final String MESSAGE_EVENT="event";
	public static final String MESSAGE_SUBSCRIBE="subscribe";
	public static final String MESSAGE_UNSUBSCRIBE="unsubscribe";
	public static final String MESSAGE_CLICK="CLICK";
	public static final String MESSAGE_VIEW="VIEW";
	
	
	
	/*
	 * xmlתΪmap����
	 */
	public static Map<String,String> xmlToMap(HttpServletRequest request)throws IOException,DocumentException{
		Map<String,String> map=new HashMap<String,String>();
		SAXReader reader=new SAXReader();
		
		InputStream ins=request.getInputStream();
		
		Document doc=reader.read(ins);
		
		Element root=doc.getRootElement();
		
		List<Element> list=root.elements();
		for(Element e :list){
			map.put(e.getName(), e.getText());
		}
		ins.close();
		return map;
		
		
	}
public static String textMessageToXml(TextMessage textMessage){
	/*
	 * ���ı���Ϣ����ת��Ϊxml
	 */
	XStream xstream=new XStream();
	xstream.alias("xml", textMessage.getClass());
	return xstream.toXML(textMessage);
}
 public static String initText(String toUserName,String fromUserName,String content){
	    TextMessage text=new TextMessage();
		text.setFromUserName(toUserName);
		text.setToUserName(fromUserName);
		text.setMsgType(MessageUtil.MESSAGE_TEXT);
		text.setCreateTime(new Date().getTime());
		text.setContent(content);
		return textMessageToXml(text);
	 
 }
 /**
  * ���˵�
  * @return
  */
 public static String menuText(){ 
	 StringBuffer sb=new StringBuffer();
	 sb.append("��ӭ���Ĺ�ע���밴�ղ˵���ʾ���в�����\n\n");
	 sb.append("1,SpringBoot����\n\n");
	 sb.append("2,С��������\n\n");
	 sb.append("�ظ��������˲˵���");
	 return sb.toString();
	 
 }
 /**
  * FirstMneu
  * @return
  */
 public static String FirstMenu(){ 
	 StringBuffer sb=new StringBuffer();
	 sb.append("SpringBoot�������");
	 return sb.toString();
	 
 }
 /**
  * SecondMenu
  * @return
  */
 public static String SecondMenu(){ 
	 StringBuffer sb=new StringBuffer();
	 sb.append("С�����������");
	 return sb.toString();
	 
 }
 public static String newsMessageToXml(NewsMessage newsMessage){
		/*
		 * ��ͼ����Ϣ����ת��Ϊxml
		 */
		XStream xstream=new XStream();
		xstream.alias("xml", newsMessage.getClass());
		xstream.alias("item", new News().getClass());
		return xstream.toXML(newsMessage);
	}
 public static String initNewsMessage(String toUserName,String fromUserName){
	 String message=null;
	 List<News> newsList=new ArrayList<News>();
	 NewsMessage newsMessage=new NewsMessage();
	 News news=new News();
	 news.setTitle("SpringBoot");
	 news.setDescriiption("SpringBoot�������");
	 news.setPicUrl("http://wyslkl.ngrok.xiaomiqiu.cn/weixinone/images/1.png");
	 news.setUrl("www.imooc.com");
	 newsList.add(news);
	 
	 newsMessage.setToUserName(fromUserName);
	 newsMessage.setFromUserName(toUserName);
	 newsMessage.setCreateTime(new Date().getTime());
	 newsMessage.setMsgType(MESSAGE_NEWS);
	 newsMessage.setArticles(newsList);
	 newsMessage.setArticleCount(newsList.size());
	 message=newsMessageToXml(newsMessage);
	 return message;
	 
	 
	 
 }
 
}

