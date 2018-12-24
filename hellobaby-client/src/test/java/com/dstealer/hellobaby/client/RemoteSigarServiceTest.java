package com.dstealer.hellobaby.client;

import com.dstealer.hellobaby.client.biz.RemoteSigarService;
import com.dstealer.hellobaby.client.biz.RemoteSigarServiceImpl;
import com.dstealer.hellobaby.client.biz.ServiceManager;
import com.dstealer.hellobaby.client.common.CommUtil;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Arrays;

/**
 * Created by LiShiwu on 05/25/2017.
 */
public class RemoteSigarServiceTest {
    private static final Logger LOGGER = Logger.getLogger(RemoteSigarServiceTest.class);

    @BeforeClass
    public static void beforeClass() throws Exception {
        //加载本地类库
        String osName = System.getProperty("os.name").toLowerCase();
        if (osName.startsWith("window")) {
            System.load(Thread.currentThread().getContextClassLoader().getResource("sigar-amd64-winnt.dll").getPath());
        } else if (osName.startsWith("linux")) {
            System.load(Thread.currentThread().getContextClassLoader().getResource("libsigar-amd64-linux.so").getPath());
        }
    }

    @Test
    public void tt1() throws Exception {
        Registry registry = LocateRegistry.getRegistry(Registry.REGISTRY_PORT);
        System.out.println("Remote service:" + Arrays.toString(registry.list()));
        RemoteSigarService service =
                (RemoteSigarService) registry.lookup(RemoteSigarService.class.getSimpleName());
        System.out.println(service.getOperatingSystem());
    }

    @Test
    public void tt2() throws Exception {
        Registry registry = LocateRegistry.createRegistry(Registry.REGISTRY_PORT);
        registry.bind(RemoteSigarService.class.getSimpleName(), new RemoteSigarServiceImpl());
        System.out.println("Registry service:" + Arrays.toString(registry.list()));
        System.in.read();
    }

    @Test
    public void tt3() throws Exception {
        System.out.println(CommUtil.getLocalIPList());
    }

    @Test
    public void tt4() throws Exception {
        Registry registry = LocateRegistry.getRegistry("121.41.53.178", Registry.REGISTRY_PORT);
        System.out.println("Remote service:" + Arrays.toString(registry.list()));
        RemoteSigarService service =
                (RemoteSigarService) registry.lookup(RemoteSigarService.class.getSimpleName());
        System.out.println(service.getOperatingSystem());
    }

    @Test
    public void tt5() throws Exception {
        Registry registry = LocateRegistry.getRegistry("121.41.53.178", Registry.REGISTRY_PORT);
        System.out.println("Remote service:" + Arrays.toString(registry.list()));
        RemoteSigarService service =
                (RemoteSigarService) registry.lookup(RemoteSigarService.class.getSimpleName());
        System.out.println("Net info:" + service.getNetInfo());
        System.out.println("CPU info:");
        Arrays.stream(service.getCpuInfoList()).forEach(System.out::println);
        System.out.println("OS info:" + service.getOperatingSystem());
        System.out.println("File system:" + service.getFileSystemMap());
        System.out.println("Load average:" + Arrays.toString(service.getLoadAverage()));
        System.out.println("Route list:" + Arrays.toString(service.getNetRouteList()));
        System.out.println("Net interface:" + service.getNetInterfaceConfig());
        System.out.println("");
    }

    @Test
    public void tt6() throws Exception {
        RemoteSigarService service = ServiceManager.locate("127.0.0.1", 1099);
        System.out.println(service.getPid());
    }

    @Test
    public void tt7() throws Exception {
        Assert.assertArrayEquals(new RemoteSigarService[]{ServiceManager.locateLocal(), ServiceManager.locateLocal()},
                new RemoteSigarService[]{ServiceManager.locateLocal(), ServiceManager.locateLocal()});
        System.out.println(ServiceManager.locateLocal().getPid());
    }
}