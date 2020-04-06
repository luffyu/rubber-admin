package com.rubber.admin.test;

import com.rubber.admin.core.system.model.RoleOptionAuthorize;
import com.rubber.admin.core.system.service.ISysRoleService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RubberAdminTestApplicationTests {


    @Resource
    private ISysRoleService iSysRoleService;



    @Test
    public void contextLoads() {
        Set<Integer> ids = new HashSet<>();
        ids.add(1);
        List<RoleOptionAuthorize> roleOptionAuthorizes = iSysRoleService.queryRoleAuthorizeKeys(ids);

        System.out.println(roleOptionAuthorizes);


    }

}
