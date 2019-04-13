package com.ty.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import com.ty.dispatcher.ProcessDispatcher;

/** ������Ҫ��Ϊ��ģ��һ��http�����������Ҽ���㣬ʹ��main��������������http����
 */
public class Server {
	private static boolean shutDown = false;
    
    /** Ϊ�˼�ʵ�֣�����ֱ��ʹ��main��������
     * 
     */
    public static void main(String[] args) throws IOException {
        Server server = new Server();
        server.startServer();
    }

    public void startServer() throws IOException {
        ServerSocket serverSocket = null;
        try {
            /** ����һ��serverSocket�����Ұ󶨱��ض˿�
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
                /** socket�Ƕ�TCP/IP�ķ�װ���ṩ������Ա��TCP/IP�������в���
                                           * ����˼����ͻ����Ƿ����������
                 */                
                socket = serverSocket.accept();                
                
                //��socket�л�ȡ�ͻ��˴�������
                input = socket.getInputStream();
                
                //��socket�л�ȡ������ͻ��˵����������
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
