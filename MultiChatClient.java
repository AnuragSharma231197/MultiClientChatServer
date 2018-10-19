import java.util.*;
import java.io.*;
import java.net.*;
public class MultiChatClient{
	Socket s;
	DataInputStream din;
	DataOutputStream dout;
	String str;
public MultiChatClient(){
	try{
	s=new Socket("localhost",10);	
	din=new DataInputStream(s.getInputStream());
	dout=new DataOutputStream(s.getOutputStream());
	MyClient();
	}
	catch(Exception e){}
}
	public void MyClient(){
		try{
			My m=new My(din);
			Thread t=new Thread(m);
			t.start();
			String str1;
			BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
			do{
			str=br.readLine();
			dout.writeUTF(str);
			dout.flush();
			}
			while(!str.equals("stop"));
		}
		catch(Exception e){}
	}
public static void main(String []args){
		new MultiChatClient();
	}
}
class My implements Runnable{
	DataInputStream din;
	My(DataInputStream din){
		this.din=din;
	}
	public void run(){
		String s2="";
		do{
		try{
			s2=din.readUTF();
			System.out.println(s2);
			}
		
		catch(Exception e){}
		}
		while(!s2.equals("stop"));
	}
}