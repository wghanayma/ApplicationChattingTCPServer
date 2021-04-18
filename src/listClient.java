
public class listClient {
  private String userName;
  private String ipAddress;
  private long port;


  public listClient(String ipAddress, long port, String userName)    {

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
    return
            userName + " - " +ipAddress + " - " + port;

  }

}
