import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

class Server {
    private Semaphore semaphore;
    public Server(){
        semaphore = new Semaphore();
    }
    public void start(){
        while(true){
            try{
                runServer();
            } catch (Exception e){
                println("[Server] Error with server, trying to relaunch...");
            }
        }
    }
    private void runServer() throws Exception{
        ServerSocket server = new ServerSocket(1234);
        println("[Server] Semaphore launched");
        println("[Server] Current Path - " + System.getProperty("user.dir"));
        println("[Server] Waiting for errors...");
        while (!server.isClosed()) {
            Socket client = server.accept();
            new Thread(new Client(client,semaphore)).start();
        }
    }
    private void println(String action){
        System.out.println(
                (new SimpleDateFormat("[HH:mm:ss] ")).format(new Date()) + action
        );
    }
}
