package com.dstealer.hellobaby.client.common;

import org.hyperic.sigar.CpuPerc;
import org.hyperic.sigar.Sigar;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;

/**
 * 通用工具类，如果不知道写到哪里合适，可以写到这里
 * Created by LiShiwu on 02/18/2017.
 */
public class CommUtil {
    /**
     * 格式化byte显示
     *
     * @param l
     * @return
     */
    public static String formatByte(long l) {
        return Sigar.formatSize(l);
    }

    /**
     * 格式化byte显示
     *
     * @param l
     * @return
     */
    public static String formatKB(long l) {
        return Sigar.formatSize(l * 1024L);
    }

    /**
     * 将小数类型格式化为百分号形式
     *
     * @param p
     * @return
     */
    public static String formatPercent(double p) {
        return CpuPerc.format(p);
    }

    /**
     * 计算平均值,排除一个最大值和一个最小值
     *
     * @return
     */
    public static double avgExMaxMin(double[] params) {
        if (params == null || params.length <= 2) {
            return 0D;
        } else {
            Arrays.sort(params);
            double sum = 0D;
            for (int i = 1; i < params.length - 1; i++) {
                sum += params[i];
            }
            return sum / (params.length - 2);
        }
    }

    /**
     * 获取本地所有网络Ip
     *
     * @return
     * @throws SocketException
     */
    public static List<String> getLocalIPList() throws SocketException {
        List<String> ipList = new ArrayList<>();
        Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
        Enumeration<InetAddress> inetAddresses;
        InetAddress inetAddress;
        while (networkInterfaces.hasMoreElements()) {
            inetAddresses = networkInterfaces.nextElement().getInetAddresses();
            while (inetAddresses.hasMoreElements()) {
                inetAddress = inetAddresses.nextElement();
                ipList.add(inetAddress.getHostAddress());
            }
        }
        return ipList;
    }

    /**
     * 解压输入流到磁盘文件
     *
     * @param name
     * @return
     * @throws IOException
     */
    public static File explodeToTemporaryFile(String name) throws IOException {
        File file = File.createTempFile(name, ".tmp");
        try (FileOutputStream output = new FileOutputStream(file);
             InputStream input = Thread.currentThread().getContextClassLoader().getResourceAsStream(name)) {
            byte[] buffer = new byte[1024 * 4];
            int n;
            while (-1 != (n = input.read(buffer))) {
                output.write(buffer, 0, n);
            }
            output.flush();
            return file;
        }
    }
}
