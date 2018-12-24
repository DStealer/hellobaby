package com.dstealer.hellobaby.unknown;

import com.dstealer.hellobaby.client.biz.RemoteSigarService;
import com.dstealer.hellobaby.client.biz.ServiceManager;
import org.junit.Test;

/**
 * Created by LiShiwu on 05/19/2017.
 */
public class RmiTest {
    @Test
    public void tt1() throws Exception {
        RemoteSigarService service = ServiceManager.locateLocal();
        System.out.println(service.getPid());
    }
}