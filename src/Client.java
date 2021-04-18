
public class Client {
  private String userName;
  private String ipAddress;
  private long port;


  public Client(String ipAddress, long port, String userName)    {

    this.userName = userName;
    this.ipAddress = ipAddress;
    this.port = port;

  }



  public long getPort(){
    return port;
  }

  public String getIpAddress(){
    return ipAddress;
  }

  public String getUserName(){
    return userName;
  }
  @Override
  public String toString(){
    return "{" +
            "\"UserName\": \"" + userName + '"' +
            ", \"IPAddress\": \"" + ipAddress + '"' +
            ", \"Port\":" + port +
             '}';

  }
  public String list(){
    return
            userName + "-" +ipAddress + "-" + port;

  }
}
