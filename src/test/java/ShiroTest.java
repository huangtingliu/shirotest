import com.alibaba.druid.pool.DruidDataSource;
import com.huangtl.entity.User;
import com.huangtl.realm.CustomRealm;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.SimpleAccountRealm;
import org.apache.shiro.realm.jdbc.JdbcRealm;
import org.apache.shiro.realm.text.IniRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Assert;
import org.junit.Test;

import java.util.*;


public class ShiroTest {
    DruidDataSource datasource = new DruidDataSource();

    {
        datasource.setUrl("jdbc:mysql://localhost:3306/test");
        datasource.setUsername("root");
        datasource.setPassword("root");
    }

    @Test
    public void simpelAccountRealmTest(){
        SimpleAccountRealm simpleAccountRealm = new SimpleAccountRealm();
        simpleAccountRealm.addAccount("test","123456","admin");

        DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();
        defaultSecurityManager.setRealm(simpleAccountRealm);
        SecurityUtils.setSecurityManager(defaultSecurityManager);
        Subject subject = SecurityUtils.getSubject();
        User user = new User();
        user.setUserName("test");
        user.setPassword("123456");
        UsernamePasswordToken token = new UsernamePasswordToken(user.getUserName(),user.getPassword());

        subject.login(token);
        System.out.println("认证是否成功："+subject.isAuthenticated());
        System.out.println("是否拥有角色："+subject.hasRole("admin"));
        //subject.checkRole("admin1");
        subject.logout();
        //System.out.println(subject.isAuthenticated());
    }

    @Test
    public void iniRealmTest(){
        IniRealm realm = new IniRealm("classpath:user.ini");

        DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();
        defaultSecurityManager.setRealm(realm);
        SecurityUtils.setSecurityManager(defaultSecurityManager);
        Subject subject = SecurityUtils.getSubject();
        User user = new User();
        user.setUserName("test");
        user.setPassword("123456");
        UsernamePasswordToken token = new UsernamePasswordToken(user.getUserName(),user.getPassword());

        subject.login(token);
        System.out.println("认证是否成功："+subject.isAuthenticated());
        System.out.println("是否拥有角色："+subject.hasRole("admin"));
        //subject.checkRole("admin1");
        subject.checkPermission("delete");
        subject.checkPermission("update");
        subject.logout();
        //System.out.println(subject.isAuthenticated());
    }

    @Test
    public void jdbcRealmTest(){
        JdbcRealm jdbcRealm = new JdbcRealm();
        jdbcRealm.setDataSource(datasource);
        jdbcRealm.setPermissionsLookupEnabled(true);
        jdbcRealm.setAuthenticationQuery("select password from users where user_name = ?");
        jdbcRealm.setPermissionsQuery("select p.permission_url " +
                "from permissions p\n" +
                "        LEFT JOIN roles_permissions rp on p.permission_id=rp.permission_id\n" +
                "where rp.role_id = ? and p.permission_url is not null and p.permission_url !=''");
        jdbcRealm.setUserRolesQuery("select r.role_id from roles r\n" +
                "        LEFT JOIN user_roles ur on r.role_id = ur.role_id\n" +
                "        LEFT JOIN users u on ur.user_id = u.user_id\n" +
                "        where u.user_name = ?");

        DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();
        defaultSecurityManager.setRealm(jdbcRealm);
        SecurityUtils.setSecurityManager(defaultSecurityManager);
        Subject subject = SecurityUtils.getSubject();
        User user = new User();
        user.setUserName("test");
        user.setPassword("123456");
        UsernamePasswordToken token = new UsernamePasswordToken(user.getUserName(),user.getPassword());

        subject.login(token);
        System.out.println("认证是否成功："+subject.isAuthenticated());
        System.out.println("是否拥有角色1："+subject.hasRole("1"));
        System.out.println("是否拥有角色2："+subject.hasRole("2"));
        subject.checkRole("1");
        String permissionName = "/userManage";
        try {
            subject.checkPermission(permissionName);//要开启权限检查功能才能生效jdbcRealm.setPermissionsLookupEnabled(true);
        }catch (Exception e){
            System.out.println("是否拥有权限["+permissionName+"]：false");
            return;
        }
        System.out.println("是否拥有权限["+permissionName+"]：true");
        //subject.checkPermission("update");
        subject.logout();
        //System.out.println(subject.isAuthenticated());
    }

    /**
     * 自定义realm
     */
    @Test
    public void customRealmTest(){
        CustomRealm customRealm = new CustomRealm();

        DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();
        defaultSecurityManager.setRealm(customRealm);
        SecurityUtils.setSecurityManager(defaultSecurityManager);
        Subject subject = SecurityUtils.getSubject();
        User user = new User();
        user.setUserName("huangtl");
        user.setPassword("123456");
        UsernamePasswordToken token = new UsernamePasswordToken(user.getUserName(),user.getPassword());

        subject.login(token);
        System.out.println("认证是否成功："+subject.isAuthenticated());
        System.out.println("是否拥有角色admin："+subject.hasRole("admin"));
        System.out.println("是否拥有角色admin1："+subject.hasRole("admin1"));
        //subject.checkRole("admin1");
        subject.checkPermission("delete");//要开启权限检查功能才能生效jdbcRealm.setPermissionsLookupEnabled(true);
        //subject.checkPermission("update");
        subject.logout();
        //System.out.println(subject.isAuthenticated());
    }

    /**
     * md5加密
     */
    @Test
    public void md5Test(){
        System.out.println(new Md5Hash("123456","huangtl"));
    }

}
