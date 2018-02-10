package com.henu.controller;

import com.henu.bean.MessAge;
import com.henu.util.RedisUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2017/10/20.
 */
@ServerEndpoint(value = "/websocket/{username}")
@Component
public class MyWebSocket {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    private static int onlineCount = 0;
    private static HashMap<String,List<MyWebSocket>> webSocketMap = new HashMap<String,List<MyWebSocket>>();
    private String username;
    private Session session;
    private static HashMap<String,List<MessAge>> records = new HashMap<>();
    private static ApplicationContext applicationContext;
    private RedisUtil redisUtil;
    public static void setApplicationContext(ApplicationContext applicationContext) {
        MyWebSocket.applicationContext = applicationContext;
    }
    public void addWebscoketMap(String name,MyWebSocket myWebSocket){
        if(webSocketMap.get(name)!=null){
            webSocketMap.get(name).add(myWebSocket);
        }else{
            ArrayList<MyWebSocket> myWebSockets = new ArrayList<>();
            myWebSockets.add(myWebSocket);
            webSocketMap.put(name,myWebSockets);
        }
    }
    public void removeOneWebscoket(String name,MyWebSocket myWebSocket){
        if(webSocketMap.get(name)!=null){
            webSocketMap.get(name).remove(myWebSocket);
        }else{
            return;
        }
    }

    public boolean isLogin(String username)throws IOException{
        if(this.redisUtil==null){
            this.redisUtil = (RedisUtil)applicationContext.getBean(RedisUtil.class);
        }
        if(redisUtil.get(username)==null){
            return false;
        }
        return true;
    }
    @OnOpen
    public void onOpen(@PathParam(value = "username")String username, Session session) throws IOException{
        if(!isLogin(username)){
            this.sendMessage(username + "ÇëµÇÂ¼",session);
            return;
        }
        this.username = username;
        this.session = session;
        addWebscoketMap(username,this);
        incrOnlineCount();
//        sendMessage(username + "ÒÑµÇÂ¼",this.session);
        List<MessAge> MessAges = records.get(username);
        if(MessAges !=null){
            for (MessAge messAge:MessAges) {
                sendMessage(messAge.toString(),this.session);
            }
        }

        logger.info("new connection...current online count: {}", getOnlineCount());
    }

    @OnClose
    public void onClose() throws IOException{
        if(webSocketMap.get(username)!=null) {
            removeOneWebscoket(this.username,this);
            decOnlineCount();
        }else{
            for (MyWebSocket myWebSocket:webSocketMap.get(username)) {
                myWebSocket.sendMessage(username + "Î´µÇÂ¼",myWebSocket.session);
            }
        }
        logger.info("one connection closed...current online count: {}", getOnlineCount());
    }

    @OnMessage
    public void onMessage(String message, Session session) throws IOException {
        if(message.equals("")){
            return;
        }
        logger.info("message received: {}", message);
        if(!message.contains(",")){
            List<MessAge> messAges = records.get(this.username);
            if(messAges==null||messAges.size()<=0){
                return;
            }
            for(int i =0;i<messAges.size();i++){
                if(messAges.get(i).getMyUserName().equals(message)){
                    messAges.get(i).setReadflag(1);
                }
            }
            return;
        }
        // broadcast received message
        String[] split = message.split(",");
        String myUser = split[0];
        String toUser = split[1];
        String text = split[2];
        Date now = new Date();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String datenowString = df.format(now);
        MessAge messAge = new MessAge(myUser,toUser,datenowString,0,text);
        List<MessAge> records1 = records.get(this.username);
        if(records1==null){
            records1 = new ArrayList<>();
        }
        records1.add(messAge);
        records.put(username,records1);
        if(!username.equals(toUser)) {
            List<MessAge> records2 = records.get(toUser);
            if (records2 == null) {
                records2 = new ArrayList<>();
            }
            records2.add(messAge);
            records.put(toUser,records2);
        }
        for (MyWebSocket myWebSocket:webSocketMap.get(username)) {
            sendMessage(messAge.toString(),myWebSocket.session);
        }
        if(webSocketMap.get(toUser)==null){
            logger.info(toUser+"ÓÃ»§Ã»µÇÂ¼");
        }else {
            for (MyWebSocket myWebSocket:webSocketMap.get(toUser)) {
                sendMessage(messAge.toString(),myWebSocket.session);
            }
        }
    }

    public void sendMessage(String message,Session session) throws IOException {
        try {
            session.getBasicRemote().sendText(message);
        }
        catch (Exception e){
            logger.info(e.toString());
        }
    }

    public static synchronized int getOnlineCount(){
        return MyWebSocket.onlineCount;
    }

    public static synchronized void incrOnlineCount(){
        MyWebSocket.onlineCount++;
    }

    public static synchronized void decOnlineCount(){
        MyWebSocket.onlineCount--;
    }
}
