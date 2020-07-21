package Socket;


import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/*
 *   服务器线程的作用主要是:
 *   1.接收来自客户端的信息
 *   2.将接收到的信息解析，并转发给目标客户端
 * */
public class ServerThread extends Thread {

    private User user;
    private List<User> list;

    public ServerThread(User user, List<User> list) {
        this.user = user;
        this.list = list;
    }

    public void run() {
        try {
            while (true) {
                // 信息的格式：客户端标签，绑定的ID，消息内容
                //不断地读取客户端发过来的信息
                String msg= user.getBr().readLine();
                String[] str = msg.split(",");
                if(str.length == 3)
                    System.out.println(str[0] + str[1] + ":" + str[2]);
                switch (str[0]) {
                    case "SenderClient":
                        user.setType(str[0]);
                        user.setBindId(str[1]);
                        sendToClient("ReceiverClient", str[1], str[2]); // 转发信息给特定的用户
                        break;
                    case "ReceiverClient":
                        user.setType(str[0]);
                        user.setBindId(str[1]);
                        sendToClient("SenderClient", str[1], str[2]); // 转发信息给特定的用户
                        break;
                    case "logout":
                        remove(user);
                    case "keepAlive":
                        break;
                    default:
                        break;
                }
            }
        } catch (Exception e) {
            System.out.println("异常:" + e);
        } finally {
            try {
                user.getBr().close();
                user.getSocket().close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void sendToClient(String userType, String bindId, String msg) {

        for (User user : list) {
            if (user.getType() != null && user.getBindId() != null && user.getType().equals(userType) && user.getBindId().equals(bindId)) {
            
                try {
                    PrintWriter pw =user.getPw();
                    String sourceType;
                    if(userType.equals("SenderClient"))
                        sourceType = "ReceiverClient";
                    else
                        sourceType = "SenderClient";
                    pw.println("来自" + sourceType + bindId + "的信息：" + msg);
                    pw.flush();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void remove(User user2) {
        list.remove(user2);
    }
}
