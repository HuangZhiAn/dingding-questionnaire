package com.fsl.questionnaire;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    final static int PROT = 7788;
    public static void main(String[] args) {
        try(ServerSocket server = new ServerSocket(PROT)) {
            System.out.println(" server start .. ");
            //进行阻塞
            Socket socket = null;
            //新建一个线程执行客户端的任务
            while(true){
                socket = server.accept();
                //一个请求一个线程
                //new Thread(new ServerHandler(socket)).start();
                //使用线程池，共享线程，限制上限和下限
                HandlerExecutorPool executorPool = new HandlerExecutorPool(50, 1000);
                executorPool.execute(new ServerHandler(socket));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
