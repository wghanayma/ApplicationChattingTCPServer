
 import org.json.simple.JSONArray;
 import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
 import java.sql.PreparedStatement;
 import java.util.ArrayList;
import java.util.List;


public class Server {
    static final Object criticalSection = new Object();

    private int port;
    private String SIPAddress;
    public   ArrayList< Client> clients;
    public   ArrayList<listClient> listClients;
    private MessageEvent messageEvent;
    JSONObject sendToClientJson = new JSONObject();
    public Socket socket;

    //ServerThread mThread;
    private ServerSocket welcomeSocket ;
    boolean isStopped = false;
    public Server( MessageEvent messageEvent,int port,String SIPAddress) {

         this.port = port;
        this.SIPAddress = SIPAddress;
        this.messageEvent = messageEvent;

        clients=new ArrayList<>();
        listClients=new ArrayList<>();

    }


    public void start()   {


        new Thread() {
            @Override
            public void run() {
                synchronized (criticalSection) {
                    String clientSentence;
                    JSONObject sendToClient = new JSONObject();

                    try {
                        welcomeSocket = new ServerSocket(8888);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    while (true) {
                        Socket connectionSocket = null;
                        try {
                            connectionSocket = welcomeSocket.accept();

                            BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
                            DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());
                            clientSentence = inFromClient.readLine();
                            JSONObject jsonObject = (JSONObject) JSONValue.parse(clientSentence);

                            if (jsonObject.containsKey("Select")) {
                                JSONArray jsonArray = new JSONArray();
                                jsonArray.addAll(clients);
                                 sendToClient.put("Clients", jsonArray);
                                // System.out.println(sendToClient);
                                messageEvent.onMessageReceived(listClients);
                                outToClient.writeBytes(sendToClient + "\n");
                            }

                            if (jsonObject.containsKey("Insert")) {
                                String userName = (String) jsonObject.get("UserName");
                                String userIP = (String) jsonObject.get("IPAddress");
                                long userPort = (long) jsonObject.get("Port");

                                if (!checkIfIPExist(clients, userIP) ||
                                        !checkIfPortSameIPExist(clients, userIP, userPort) ||
                                        !checkIfUsernameExist(clients, userName)) {
                                    clients.add(new Client(userIP, userPort, userName));
                                    listClients.add(new listClient(userIP, userPort, userName));

                                    System.out.println("  add user ");
                                    sendToClient = new JSONObject();
                                     JSONArray jsonArray = new JSONArray();

                                    jsonArray.addAll(clients);

                                    sendToClient.put("Clients", jsonArray);
                                    // System.out.println(sendToClient);
                                    messageEvent.onMessageReceived(listClients);
                                    outToClient.writeBytes(sendToClient + "\n");


                                } else {
                                    System.out.println("Can't add user ");

                                }
                            }
                            if (jsonObject.containsKey("Close")) {
                                String userName = (String) jsonObject.get("UserName");

                                if (checkIfUsernameExist(clients, userName)) {

                                    for (int i = 0; i < clients.size(); i++) {
                                        if (clients.get(i).getUserName().equals(userName)) {
                                            clients.remove(i);
                                            listClients.remove(i);
                                             JSONArray jsonArray = new JSONArray();

                                            jsonArray.addAll(clients);

                                            sendToClient.put("Clients", jsonArray);
                                            messageEvent.onMessageReceived(listClients);
                                            outToClient.writeBytes(sendToClient + "\n");

                                        }
                                    }

                                    outToClient.writeBytes(sendToClient.toJSONString() + "\n");
                                }
                            }


                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }

                }
            }

        }.start();
    }

    public boolean  checkIfIPExist(ArrayList<Client> clients,String IP){
        for (Client client : clients) {
            if (client.getIpAddress().equals(IP)) {
                return true;

            }
        }
        return false;
    }
    public boolean  checkIfPortSameIPExist(ArrayList<Client> clients,String IP,long Port){
        for (Client client : clients) {
            if (client.getIpAddress().equals(IP) && client.getPort()==Port){
                return true;

            }
        }
        return false;
    }
    public boolean  checkIfPortDifferentIPExist(ArrayList<Client> clients,String IP,long Port){
        return  true;
    }
    public boolean checkIfUsernameExist(ArrayList<Client> clients,String Username){
        for (Client client : clients) {
            if (client.getUserName().equals(Username))
            {
                return true;
            }
        }
        return false;
    }

}
