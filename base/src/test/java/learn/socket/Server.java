package learn.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;


public class Server {

    public static void main(String[] args) {

		//5.1 启动服务器,设置端口号为8000并等待客户端连接
		try (ServerSocket serverSocket = new ServerSocket(8000)) {

			System.out.println("服务器启动成功!");
			while (true) {
				try {
					// 会阻塞在DualStackPlainSocketImpl#accept0
					Socket socket = serverSocket.accept();
					System.out.println("客户端连接成功!");
					//读取数据
					BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
					PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));

					String line;
					//定义变量,记录读取到的一行数据
					while ((line = in.readLine()) != null) {
						System.out.println("客户端发来的数据:" + line);
						//7.1可以给客户端作出响应-接收键盘输入的响应
						System.out.println("请输入您的回应:");
						String input = new Scanner(System.in).nextLine();
						//7.2发出作为服务器的响应
						out.println(input);
						//把数据刷出去
						out.flush();
					}
				}catch (Exception e){
					e.printStackTrace();
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

    }
}