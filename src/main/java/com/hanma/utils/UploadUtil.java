package com.hanma.utils;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import org.apache.commons.net.ftp.FTPClient;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class UploadUtil {
    private static ChannelSftp sftp = null;
    /**
     * Description: 向FTP服务器上传文件
     * @param filename 上传到FTP服务器上的文件名
     * @param input    输入流
     * @return 成功返回true，否则返回false
     */
    public static boolean uploadFile(String filename, InputStream input) {
        boolean result = false;
        FTPClient ftp = new FTPClient();
        File file = null;
        try {
            JSch jsch = new JSch();
            //获取sshSession  账号-ip-端口
            Session sshSession = jsch.getSession("admin", "10.125.9.1", 9663);//服务器用户名,服务器IP,端口号
            //添加密码
            sshSession.setPassword("Hmkjdms3879");//服务器密码
            Properties sshConfig = new Properties();
            //严格主机密钥检查
            sshConfig.put("StrictHostKeyChecking", "no");
            sshSession.setConfig(sshConfig);
            //开启sshSession链接
            sshSession.connect();
            //获取sftp通道
            Channel channel = sshSession.openChannel("sftp");
            //开启
            channel.connect();
            sftp = (ChannelSftp) channel;
            //服务器路径
            file = new File("/usr/local/part/");
            //设置为被动模式
            ftp.enterLocalPassiveMode();
            //设置上传文件的类型为二进制类型
            //进入到要上传的目录  然后上传文件
            sftp.cd("/usr/local/part/");
            sftp.put(input, filename);
            input.close();
            result = true;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (ftp.isConnected()) {
                try {
                    ftp.disconnect();
                } catch (IOException ioe) {
                }
            }
        }
        return result;
    }

}

