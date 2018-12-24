package com.dstealer.hellobaby.unknown;

import com.alibaba.fastjson.JSON;
import com.dstealer.hellobaby.common.CommUtil;
import com.dstealer.hellobaby.AppTest;
import com.dstealer.hellobaby.business.job.vo.FormattedFileSystemUsage;
import com.dstealer.hellobaby.database.pojo.DirMonitorPojo;
import org.hyperic.sigar.*;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

/**
 * Created by LiShiwu on 03/02/2017.
 */
public class SigarTest extends AppTest {
    private static Sigar sigar;

    @BeforeClass
    public static void beforeClass() throws Exception {
        //加载本地类库
        String osName = System.getProperty("os.name").toLowerCase();
        if (osName.startsWith("window")) {
            System.load(Thread.currentThread().getContextClassLoader().getResource("sigar-amd64-winnt.dll").getPath());
        } else if (osName.startsWith("linux")) {
            System.load(Thread.currentThread().getContextClassLoader().getResource("libsigar-amd64-linux.so").getPath());
        }
        sigar = new Sigar();
    }

    @AfterClass
    public static void afterClass() throws Exception {
        sigar.close();
    }

    @Test
    public void tt1() throws Exception {
        //系统CPU信息
        for (CpuInfo cpuInfo : sigar.getCpuInfoList()) {
            System.out.println(cpuInfo.toString());
        }
        //单个CPU信息
        for (Cpu cpu : sigar.getCpuList()) {
            System.out.println(cpu.toString());
        }
        System.out.println("************************");
        //CPU总信息
        System.out.println(sigar.getCpu().toString());
        for (int i = 0; i < 10; i++) {
            CpuPerc cpuPerc = sigar.getCpuPerc();
            System.out.println(cpuPerc.toString());
            Thread.sleep(1000);
        }
        //每个CPU
        for (int i = 0; i < 10; i++) {
            CpuPerc[] cpuPercs = sigar.getCpuPercList();
            for (CpuPerc cpuPerc : cpuPercs) {
                System.out.println(cpuPerc.toString());
            }
            Thread.sleep(1000);
        }
    }

    @Test
    public void tt2() throws Exception {
        for (int i = 0; i < 10; i++) {
            Mem mem = sigar.getMem();
            System.out.println(mem.toString());
            Thread.sleep(1000);
        }
    }

    @Test
    public void tt3() throws Exception {
        System.out.println(sigar.getNetInfo().getHostName());
        for (FileSystem fileSystem : sigar.getFileSystemList()) {
            System.out.println(fileSystem.toString());
            FileSystemUsage usage = sigar.getFileSystemUsage(fileSystem.getDirName());
            System.out.println(usage.toString());
        }
    }

    @Test
    public void tt4() throws Exception {
        Who[] whoes = sigar.getWhoList();
        for (Who who : whoes) {
            System.out.println(who);
        }
    }

    @Test
    public void tt5() throws Exception {
        //操作系统信息
        OperatingSystem OS = OperatingSystem.getInstance();
        System.out.println(OS);
    }

    @Test
    public void tt6() throws Exception {
        String ifNames[] = sigar.getNetInterfaceList();
        for (String ifName : ifNames) {
            NetInterfaceConfig ifconfig = sigar.getNetInterfaceConfig(ifName);
            System.out.println(ifName);
            System.out.println(ifconfig.getAddress());
            System.out.println(ifconfig.getNetmask());
            if ((ifconfig.getFlags() & 1L) <= 0L) {
                System.out.println("!IFF_UP...skipping getNetInterfaceStat");
                continue;
            }
            NetInterfaceStat ifstat = sigar.getNetInterfaceStat(ifName);
            System.out.println(ifstat);
        }
    }

    @Test
    public void tt7() throws Exception {
        for (int i = 0; i < 10; i++) {
            System.out.println("+++++++++第" + i + "次取样+++++++++++");
            print(sigar);
            TimeUnit.SECONDS.sleep(2L);
        }
    }

    @Test
    public void tt8() throws Exception {
        for (int i = 0; i < 100; i++) {
            TimeUnit.SECONDS.sleep(5L);
            System.out.println("+++++++++第" + i + "次取样+++++++++++");
            System.out.println(sigar.getCpuPerc().toString());
            for (CpuPerc cp : sigar.getCpuPercList()) {
                System.out.println(cp.toString());
            }
        }
    }

    @Test
    public void tt9() throws Exception {
        for (int i = 0; i < 100; i++) {
            TimeUnit.SECONDS.sleep(2L);
            System.out.println("+++++++++第" + i + "次取样+++++++++++");
            System.out.println(sigar.getCpuPerc().toString());
        }
    }

    @Test
    public void tt10() throws Exception {
        for (int i = 0; i < 100; i++) {
            TimeUnit.SECONDS.sleep(2L);
            System.out.println("+++++++++第" + i + "次取样+++++++++++");
            System.out.println(sigar.getResourceLimit());
        }
    }

    @Test
    public void tt11() throws Exception {
        for (int i = 0; i < 10; i++) {
            TimeUnit.SECONDS.sleep(2L);
            System.out.println("+++++++++第" + i + "次取样+++++++++++");
            System.out.println(sigar.getUptime());
        }
    }

    @Test
    public void tt12() throws Exception {
        for (int i = 0; i < 10; i++) {
            TimeUnit.SECONDS.sleep(2L);
            System.out.println("+++++++++第" + i + "次取样+++++++++++");
            System.out.println(sigar.getProcStat());
        }
    }

    @Test
    public void tt13() throws Exception {
        System.out.println(Arrays.toString(sigar.getFileSystemList()));
        System.out.println(sigar.getFileSystemUsage("/home"));
        System.out.println(Arrays.toString(sigar.getWhoList()));
    }

    @Test
    public void tt14() throws Exception {
        DirMonitorPojo pojo = new DirMonitorPojo();
        pojo.setId(1);
        pojo.setDirName("/home");
        pojo.setThreshold(0.01);
        pojo.setTemplate("[(${})]");
        pojo.setDesc("磁盘占用预警");
        FileSystemUsage usage = sigar.getFileSystemUsage(pojo.getDirName());
        System.out.println(JSON.toJSON(usage));
        System.out.println(JSON.toJSON(new FormattedFileSystemUsage(pojo, usage)));
    }

    @Test
    public void tt15() throws Exception {
        for (FileSystem fs : sigar.getFileSystemList()) {
            System.out.println(fs.getDevName() + "|" + fs.getDirName() + "|" + fs.getTypeName() + "|" + fs.getOptions());
        }
    }

    @Test
    public void tt16() throws Exception {
        Mem mem = sigar.getMem();
        System.out.println("total:" + CommUtil.formatByte(mem.getTotal()));
        System.out.println("used:" + CommUtil.formatByte(mem.getUsed()));
        System.out.println("actual used:" + CommUtil.formatByte(mem.getActualUsed()));
        System.out.println("free:" + CommUtil.formatByte(mem.getFree()));
        System.out.println("actual free:" + CommUtil.formatByte(mem.getActualFree()));
        System.out.println("FreePercent:" + mem.getFreePercent());
        System.out.println("UsedPercent:" + mem.getUsedPercent());
        System.out.println("Ram:" + CommUtil.formatByte(mem.getRam()));
        System.out.println(mem.toString());
        Swap swap = sigar.getSwap();
        System.out.println(swap.toString());
    }

    @Test
    public void tt17() throws Exception {
        System.out.println(sigar.getDirUsage("/home"));
    }

    @Test
    public void tt18() throws Exception {
        NetInterfaceConfig config = sigar.getNetInterfaceConfig(null);
        System.out.println(config.toString());
    }

    @Test
    public void tt19() throws Exception {
        System.out.println(System.getProperties());
    }

    @Test
    public void tt20() throws Exception {
        Mem mem = sigar.getMem();
        Swap swap = sigar.getSwap();
        System.out.println(mem);
        System.out.println(swap);
    }
}
