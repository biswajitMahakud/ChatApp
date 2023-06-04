import java.net.*;
import java.io.*;

public class Server {
    ServerSocket server;
    Socket socket;

    BufferedReader br;
    PrintWriter out;

    public Server() {
            try {
                this.server = new ServerSocket(7777);
                System.out.println("Server is ready to accept connection");
                System.out.println("Waiting.....");
                socket = server.accept();
                br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream());

                startreading();
                startwriting();
    
            } catch (Exception e) {
                e.printStackTrace();
            }
        
    }

    public void startreading(){
        Runnable r1=()->{
            System.out.println("reader started.....");
                try{
                while(true){
                    String msg = br.readLine();
                    if(msg.equals("exit")){
                        System.out.println("Client terminated the chat....");
                        socket.close();
                        break;
                    }
                    System.out.println("Client: "+ msg);
                
            }
        }catch(Exception e){
            e.printStackTrace();
        } 
        };
        new Thread(r1).start();
    }

    public void startwriting(){
       Runnable r2=()->{
        System.out.println("Writer started....");
        try{
        while(!socket.isClosed()){
                BufferedReader br1 = new BufferedReader(new InputStreamReader(System.in));
                String content = br1.readLine();
                out.println(content);
                out.flush();
                if(content.equals("exit")){
                    socket.close();
                    break;
                 }
        }
    } catch(Exception e){
        e.printStackTrace();
    }
       };
       new Thread(r2).start();
    }

    public static void main(String[] args) {
        System.out.println("Server is going to start.......");
        new Server();
    }
}