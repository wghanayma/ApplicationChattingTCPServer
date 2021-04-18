import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;

public class TCPServer{
    private JTextField textFieldPortServer;
    private JTextField textFieldIPServer;
    private JList<Object> listClient;
    private JButton buttonRunServer;
    private JPanel jPanelMain;
    boolean RunServer=true;

    TCPServer() {
        textFieldIPServer.setText("127.0.0.1");
        textFieldPortServer.setText("8888");
        buttonRunServer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!textFieldIPServer.getText().isEmpty() ) {
                    if (!textFieldPortServer.getText().isEmpty() ) {
                        Server mServer;
                        CMessageEvent messageEvent = new CMessageEvent();

                        mServer = new Server(messageEvent,Integer.parseInt(textFieldPortServer.getText()),textFieldIPServer.getText());

                       // if(RunServer) {
                         //   buttonRunServer.setText("Stop Server");


                                mServer.start();
                            RunServer=false;
    System.out.println("Run Server ");

//} else {
    //RunServer=true;
     //mServer.stop();
                            //mServer=null;
    //buttonRunServer.setText("Run Server");
   // textFieldIPServer.setText(null);
   // textFieldPortServer.setText(null);
//System.out.println("Stop Server ");
//}
                    }
                    else {
                        JOptionPane.showMessageDialog(jPanelMain, "Please put  Server Port", "Server Port", JOptionPane.ERROR_MESSAGE);

                    }
                }

                 else {
                    JOptionPane.showMessageDialog(jPanelMain, "Please put IP Server", "Server IP", JOptionPane.ERROR_MESSAGE);
                     }
        }
        });


        textFieldIPServer.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent ke) {
                if (ke.getKeyChar() >= '0' && ke.getKeyChar() <= '9' ||ke.getKeyChar() == '.') {
                    textFieldIPServer.setEditable(true);
                } else {
                    JOptionPane.showMessageDialog(jPanelMain, "Enter only numeric digits(0-9) and '.'", "Server IP", JOptionPane.ERROR_MESSAGE);
                    textFieldIPServer.setText(null);

                }
            }
        });
        textFieldPortServer.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent ke) {
                if (ke.getKeyChar() >= '0' && ke.getKeyChar() <= '9' ) {
                    textFieldPortServer.setEditable(true);


                } else {
                    JOptionPane.showMessageDialog(jPanelMain, "Enter only numeric digits(0-9)", "Server Port", JOptionPane.ERROR_MESSAGE);
                    textFieldPortServer.setText(null);

                }
            }
        });
    }

    public static void main(String[] args) {
        JFrame jFrame = new JFrame("TCP Server");

        jFrame.setContentPane(new TCPServer().jPanelMain);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.pack();
        jFrame.setVisible(true);
        //jFrame.setResizable(false);
        jFrame.setLocationRelativeTo(null);
    }
    class CMessageEvent implements MessageEvent {

        @Override
        public void onMessageReceived(List<listClient> message) {
            listClient.setListData(message.toArray());
         }
    }
}