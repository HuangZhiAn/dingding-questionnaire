package com.fsl.questionnaire;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class Client {

    private static final String ADDRESS = "127.0.0.1";
    private static final int PORT = 7788;
    public static void main(String[] args){
        /*try(Socket socket = new Socket(ADDRESS, PORT);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {
            //向服务器端发送数据
            out.println("客户端的请求数据...");
            out.println("客户端的请求数据1111...");
            String response = in.readLine();
            System.out.println("Client: " + response);
        } catch (IOException e) {
            e.printStackTrace();
        }*/

        InetSocketAddress address = new InetSocketAddress("127.0.0.1", 7788);//创建连接的地址

        ByteBuffer buf = ByteBuffer.allocate(1024);//建立缓冲区
        //声明连接并打开通道
        SocketChannel sc = null;
        try {
            sc= SocketChannel.open();
            sc.connect(address);//进行连接
            //while(true){
                //定义一个字节数组，然后使用系统录入功能：
                byte[] bytes = new byte[1024];
                System.in.read(bytes);
                buf.put(bytes);//把数据放到缓冲区中
                buf.flip();//对缓冲区进行复位
                sc.write(buf);//写出数据
                buf.clear();//清空缓冲区数据
            //}
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
