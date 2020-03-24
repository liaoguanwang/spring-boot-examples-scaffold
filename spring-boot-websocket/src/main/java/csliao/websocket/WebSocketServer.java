package csliao.websocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Classname WebSocketServer
 * @Description
 * @Date 2020/2/25 0:03
 * @Created by csliao
 */
@Component
@ServerEndpoint("/websocket/{id}")
public class WebSocketServer {

    private static final Logger logger = LoggerFactory.getLogger(WebSocketServer.class);

    //静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
    private static int onlineCount = 0;
    //concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。
    private static ConcurrentHashMap<String, WebSocketServer> webSocketMap = new ConcurrentHashMap<>();
    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;
    //接收sid
    private String id="";

    /**
     * 连接建立成功调用的方法*/
    @OnOpen
    public void onOpen(Session session, @PathParam("id")String id) {
        this.session = session;
        webSocketMap.put(id, this);
        addOnlineCount();           //在线数加1
        logger.info("有新窗口开始监听:" + id + ",当前在线人数为" + getOnlineCount());
        this.id = id;
        try {
            sendMessage("连接成功");
        } catch (IOException e) {
            logger.error("websocket IO异常");
        }
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        webSocketMap.remove(id);  //从Map中删除
        subOnlineCount();           //在线数减1
        logger.info("有一连接关闭！当前在线人数为" + getOnlineCount());
    }

    /**
     * 收到客户端消息后调用的方法
     * @param message 客户端发送过来的消息*/
    @OnMessage
    public void onMessage(String message, Session session) {
        logger.info("收到来自窗口" + id + "的信息:" + message);
        Collection<WebSocketServer> values = webSocketMap.values();
        //群发消息
        for (WebSocketServer item : values) {
            try {
                item.sendMessage(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error) {
        logger.error("发生错误");
        error.printStackTrace();
    }

    /**
     * 实现服务器主动推送
     */
    public void sendMessage(String message) throws IOException {
        if(this.session.isOpen()){
            this.session.getBasicRemote().sendText(message);

        }
    }


    /**
     * 群发自定义消息
     * */
    public static void sendInfo(String message, @PathParam("id") String id) throws IOException {
        logger.info("推送消息到窗口"+id+"，推送内容:"+message);
        Collection<WebSocketServer> values = webSocketMap.values();
        for (WebSocketServer item : values) {
            try {
                //这里可以设定只推送给这个id的，为null则全部推送
                if(id == null) {
                    item.sendMessage(message);
                }else if(item.id.equals(id)){
                    item.sendMessage(message);
                }
            } catch (IOException e) {
                continue;
            }
        }
    }

    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    public static synchronized void addOnlineCount() {
        WebSocketServer.onlineCount++;
    }

    public static synchronized void subOnlineCount() {
        WebSocketServer.onlineCount--;
    }
}

