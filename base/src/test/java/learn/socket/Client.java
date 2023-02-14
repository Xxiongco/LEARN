package learn.socket;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

/**本类用来做回声案例的客户端
 * 1.连接指定的服务器
 * 2.给服务器发送数据
 * 3.接收服务器响应的数据
 * */
public class Client {
	public static void main(String[] args) {
			//1.连接指定的服务器,同时指定服务器的IP和端口号
			try (Socket socket = new Socket("127.0.0.1", 8000)) {
				//2.给服务器发送数据
				while (true) {
					try{
						PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
						BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
						//3.通过流对象进行发送和读取的操作
						System.out.println("请输入您想给服务器发送的数据:");
						String input = new Scanner(System.in).nextLine();
						//向服务端发送指定数据
						out.println(input);
						//把数据刷出去
						out.flush();
						//读取回声数据
						String line = in.readLine();
						System.out.println("服务器端响应的数据是:" + line);
					}catch (Exception e) {
						e.printStackTrace();
						break;
					}
				}
			}catch (Exception e) {
				e.printStackTrace();

		}
	}
}
