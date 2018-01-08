package com.husky.test;

import com.husky.domain.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by songshiwen on 18/1/8.
 */
public class Main2Test {
    private SessionFactory sessionFactory;
    private Session session;
    private Transaction transaction;

    @Before
    public void init() {
        Configuration configuration = new Configuration();
        configuration.configure();
        sessionFactory = configuration.buildSessionFactory();
        session = sessionFactory.openSession();
        transaction = session.beginTransaction();

    }


    @After
    public void destory() {
        transaction.commit();
        session.close();
    }

    /*临时状态
    * new--->临时状态--->save/saveOrUpdate--->持久化状态*/
    @Test
    public void save(){
        User user = new User("王铭","123");//临时状态
        System.out.println(user);
        session.save(user);//持久化状态
        System.out.println(user);
        /*重新设置持久化对象的某个属性
        * 对于持久化对象来说 当更改某个对象的某个属性值时
        * 不用手动调用update进行更新 系统在commit提交时
        * 会自动执行update更新的操作*/
        user.setName("之珩");
        System.out.println(user);
    }


    /*get/load/find/iterator--->持久化对象--->delete--->临时对象*/
    @Test
    public void delete(){
        User user = session.get(User.class,5);
        System.out.println(user);
        /*删除某个对象*/
        session.delete(user);//此时user编程临时状态
        System.out.println(user);
        user.setName("王文");//临时状态对象修改不影响数据库
    }
    /*get/find/save...--->持久化状态--->clear/evict/close--->游离状态*/
    @Test
    public void clear(){
        User user = session.get(User.class,14);//持久化状态
        session.clear();/*清除方法*/
        //user变成游离状态
        System.out.println(user);
        /*处于游离状态的对象 对其属性的修改不会提交到数据库中
        * 临时状态与游离状态区别在于数据库还有没有备份
        * a,从持久化对象通过delete到临时状态 数据库没有此对象 是真正的删除
        * b,从持久化对象通过clear.evict.close到游离状态 数据库还有该对象*/
        user.setName("淼");
        /*从游离状态编程持久化状态*/
        session.update(user);
        /*对持久化对象的更新 即在事务commit时更新对象*/
        user.setPassword("23456");
    }
}
