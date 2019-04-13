package com.ty.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import com.ty.dispatcher.ProcessDispatcher;

/** 此类主要是为了模拟一个http服务器，并且简单起便，使用main方法来启动整个http容器
 */
public class Server {
	private static boolean shutDown = false;
    
    /** 为了简单实现，这里直接使用main方法启动
     * 
     */
    public static void main(String[] args) throws IOException {
        Server server = new Server();
        server.startServer();
    }

    public void startServer() throws IOException {
        ServerSocket serverSocket = null;
        try {
            /** 创建一个serverSocket，并且绑定本地端口
             */
            serverSocket = new ServerSocket(8088);        
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
        
        while(!shutDown) {
            Socket socket = null;
            InputStream input = null;
            OutputStream output = null;
            try {
                /** socket是对TCP/IP的封装，提供给程序员对TCP/IP传输层进行操作
                                           * 服务端监听客户端是否有请求过来
                 */                
                socket = serverSocket.accept();                
                
                //从socket中获取客户端传输内容
                input = socket.getInputStream();
                
                //从socket中获取传输给客户端的输出流对象
                output = socket.getOutputStream();
                
                ProcessDispatcher processDispatcher = new ProcessDispatcher(input, output);
                processDispatcher.service();
                
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if(socket != null) {
                    socket.close();
                }
                
                if(input != null) {
                    input.close();
                }
                
                if(output != null) {
                    output.close();
                }
            }
        }
    }
}
