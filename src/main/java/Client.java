import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

class Client implements Runnable {
    private Semaphore           semaphore;
    private DataOutputStream    out;
    private DataInputStream     in;
    private Socket              client;
    public Client(Socket client, Semaphore semaphore) {
        this.client = client;
        this.semaphore = semaphore;

    }
    public void run(){
        try{
            out = new DataOutputStream(client.getOutputStream());
            in = new DataInputStream(client.getInputStream());
            while (!client.isClosed())
                commandHandler();
        } catch (IOException e){
            e.printStackTrace();

        }
    }
    private void commandHandler(){
        try{
            String ans = in.readUTF();
            switch (ans){
                case "semaphore -take": take(); break;
                case "semaphore -release" : release(); break;
                case "dir" : sendDir(); break;
            }
        } catch (Exception e){
        }
    }
    private void sendDir() throws IOException{
        out.writeUTF(System.getProperty("user.dir") + "\\bin\\dat\\");
    }
    private void take(){
        semaphore.take();
        try{
            out.writeUTF("OK");
        } catch (Exception e){
            System.out.println("can't send OK");
        }
    }
    private void release(){
        semaphore.release();
    }
}