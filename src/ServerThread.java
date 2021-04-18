import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.util.ArrayList;

public class ServerThread extends Thread {
    private int port;
    private String SIPAddress;
    private ServerSocket welcomeSocket ;
    private Socket connectionSocket ;
    boolean isStopped = false;

    public ServerThread( int port,String SIPAddress) {

        this.port = port;
        this.SIPAddress = SIPAddress;


    }

    @Override
    public void run() {
         String clientSentence;
        String capitalizedSentence;


   /*     try {
            InetAddress IPAddress = null;

            IPAddress = InetAddress.getByName(SIPAddress);

        } catch (UnknownHostException e) {
            e.printStackTrace();
        }*/

        try {
            welcomeSocket = new ServerSocket(8888);

            while (!isStopped) {

                connectionSocket = welcomeSocket.accept();
                  BufferedReader inFromClient  ;
                  DataOutputStream outToClient  ;

                inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
                  outToClient = new DataOutputStream(connectionSocket.getOutputStream());
                clientSentence = inFromClient.readLine();
                capitalizedSentence = clientSentence.toUpperCase() +"ssss"+ '\n';
                outToClient.writeBytes(capitalizedSentence);
                connectionSocket.close();

            }
        }catch (BindException e) {
                try {
                    if(welcomeSocket!=null)
                        welcomeSocket.close();
                   } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
         catch (IOException ignored) {
             ignored.printStackTrace();

        }





    }

    public void stopS(){
        isStopped=true;
      /*   if (inFromClient != null  ) {
            try {
                inFromClient.close();
            } catch (IOException ie)
            {
                ie.printStackTrace(System.err);
            }
        }
        if (outToClient != null  ) {
            try {
                outToClient.close();
            } catch (IOException ie)
            {
                ie.printStackTrace(System.err);
            }
        }
*/
        if (welcomeSocket != null && !welcomeSocket.isClosed()) {
            try {
                welcomeSocket.close();
            } catch (IOException ie)
            {
                ie.printStackTrace(System.err);
            }
        }
        if (connectionSocket != null && !connectionSocket.isClosed()) {
            try {
                connectionSocket.close();
            } catch (IOException ie)
            {
                ie.printStackTrace(System.err);
            }
        }
    }


}
