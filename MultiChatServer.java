import java.util.*;
import java.io.*;
import java.net.*;
public class MultiChatServer{
	ArrayList al;
	Socket s;
	public MultiChatServer(){
		try{
		ServerSocket ss=new ServerSocket(10);
		al=new ArrayList();
		while(true){
			s=ss.accept();
			System.out.println("Client Connected");
			al.add(s);
			MyThread my=new MyThread(s,al);
			Thread t1=new Thread(my);
			t1.start();
			}
		}
	catch(Exception e){}		
	}
public static void main(String []args){
	new MultiChatServer();
	}
}
class MyThread implements Runnable{
	ArrayList al;
	Socket s;
	MyThread(Socket s,ArrayList al){
		this.s=s;
		this.al=al;
	}
	public void run(){
		try{
		String str;
		DataInputStream din=new DataInputStream(s.getInputStream());
		do{
			str=din.readUTF();
			System.out.println(Thread.currentThread().getName()+": "+str);
			if(!str.equals("stop")){
				tellEveryOne(str);
			}
			else{
				DataOutputStream dout=new DataOutputStream(s.getOutputStream());
				dout.writeUTF(str);
				dout.flush();
				al.remove(s);
			}
		}
		while(!str.equals("stop"));
		}
		catch(Exception e){}
	}
	public void tellEveryOne(String str){
		try{
			Iterator i=al.iterator();
		while(i.hasNext()){
			Socket sc=(Socket)i.next();
			DataOutputStream dout=new DataOutputStream(sc.getOutputStream());
			dout.writeUTF(str);
			dout.flush();
			System.out.println(Thread.currentThread().getName()+": "+str);
			}
		}
		catch(Exception e){}
	}
}