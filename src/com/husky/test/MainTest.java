package com.husky.test;

import com.husky.domain.User;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Iterator;
import java.util.List;

/**
 * Created by songshiwen on 18/1/8.
 */
public class MainTest {
    /*数据库连接的工厂对象*/
    private SessionFactory sessionFactory;
    /*数据库连接对象 正真用于数据库的crud操作的*/
    private Session session;
    /*数据库事务对象*/
    private Transaction transaction;

    @Before
    public void init() {
        /*1.构建配置对象*/
        Configuration configuration = new Configuration();
        /*默认加载src下的配置文件Hibernate.cfg.xml*/
        configuration.configure();
        /*2.初始化工厂对象*/
        sessionFactory = configuration.buildSessionFactory();
        /*3.得到一个连接对象*/
        session = sessionFactory.openSession();
        /*4.开启一个事务对象*/
        transaction = session.beginTransaction();

    }


    @After
    public void destory() {
        /*关闭数据库连接*/
        /*6.提交事务*/
        transaction.commit();
        /*7.关闭连接*/
        session.close();
    }

    @Test
    public void add() {
        User user = new User("库", "124");
        /*save是保存一个实体类对象 是以插入insert的方式调用
        * 即要求改对象的主键id不能有值 否则会报错*/
//        session.save(user);
        /*save方法插入时不考虑id 只将费主键的值执行insert
        *
        * saveOrUpdate 方法先去建厂当前对象的主键id是否存在
        * 如果存在执行非主键的update更新操作
        * 如果主键当前实体类对象的主键id不存在 则执行的是save操作 即insert插入*/
//        user.setId(4);
        session.saveOrUpdate(user);
    }

    @Test
    public void delete() {
//        User user =session.get(User.class,4);
        /*删除某个对象 如果传入的实体类对象没有设置主键id
        * 则不进行任何操作*/
        User user = new User("库", "124");
        user.setId(12);
        session.delete(user);
        /*session自带的delete方法只根据id进行删除
        * 不考虑其他删除条件*/

    }

    @Test
    public void update() {
        User user = new User("lll", "1234");
        /*update更新方法要求主键id有值 指明要更新的对象*/
        user.setId(12);
        session.update(user);
    }

    @Test
    public void query() {
        /*1.根据主键id查询单个对象
        * 2.根据sql执行查询*/

        /*根据id查询单个对象*/
        /*get:第一个参数是要查询的实体类类名
        *       第二个参数是主键id*/
        User user = session.get(User.class, 13);
        System.out.println(user);
        /*以实体类名*/
        User user1 = (User) session.get(User.class.getName(), 11);
        System.out.println("实体类名" + User.class.getName());
        System.out.println(user1);

        /*根据sql语句*/
        /*创建一个query对象 createQuery中的参数是从from开始 不需要加入前面的select*/
        /*sql语句中涉及的类名和属性名都指的是实体类中的,不是数据库中的
        * hibernate内部会自动进行转换*/
//        String sql = "from User where  name = ? and password = ?";
//        /*设置sql语句中问号所对应的值*/
//        Query query = session.createQuery(sql);
//        query.setString(0,"库");// 代表第一个问号的替换值
//        query.setString(1,"124");//代表第二个问号的替换值
        /*给条件语句中的条件设置别名 根据别名设置对应的参数*/
        String sql = "from User where  name =:name and password =:pwd";
        Query query = session.createQuery(sql);
        query.setString("name","库");
        query.setString("pwd","124");

        List<User> users = query.list();

        System.out.println(users);
    }

    @Test
    public void query2(){
        Query query = session.createQuery("from User where name = ?");
        /*设置参数 即sql语句中的问号对应的值
        * setString 内部实际上还是调用的setParameter
        * 只不过是直接给定参数的类型
        * 而setParameter会根据属性类型自动匹配   */
        query.setParameter(0,"库");
        /*返回一个迭代器,默认返回的知识符合条件的对象主键id
        * 当进行结果遍历的时候需要进行二次查询(根据id)*/
        Iterator<User> iterator = query.iterate();
        while (iterator.hasNext()){
            User user = iterator.next();
            System.out.println(user);
        }
    }

    @Test
    public void query3(){
        Query query = session.createQuery("from  User");
        /*分页处理*/
        int start = 2;//起始页
        int pageSize = 2;//每页的大小
        /*设置返回结果的最大条数 用它来控制每页的数目*/
        query.setMaxResults(pageSize);
        /*设置返回结果集的偏移量*/
        query.setFirstResult((start-1)*pageSize);
        /*符合条件的结果集*/
        List<User> userList = query.list();
        System.out.println(userList);
    }
}
