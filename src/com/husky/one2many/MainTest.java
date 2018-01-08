package com.husky.one2many;

import com.husky.domain.Clazz;
import com.husky.domain.Student;
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
public class MainTest {
    protected SessionFactory sessionFactory;
    protected Session session;
    protected Transaction transaction;

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

    @Test
    public void save(){
        Student student = new Student("王文","123");
        Clazz clazz = new Clazz("J1005","Java班");
        /*维护关系的一方 绑定班级*/
        student.setClazz(clazz);
        /*到这student和clazz均是临时状态*/
        /*保存学生对象*/
        session.save(student);
    }


    @Test
    public void query(){
        Student student = session.get(Student.class,1);
        System.out.println(student);
        /*获取学生所在的班级对象*/
        /*在级联关系中 默认情况是懒加载(按需) 即获取学生对象时不会直接调用外键表的查询
        * 只有当需要时才会执行关联表的查询*/
        System.out.println(student.getClazz());
    }


    @Test
    public void test1(){
        /*1.保存班级级联保存学生*/
        Clazz clazz = new Clazz("J1106","Java班级");
        Student student = new Student("朱海仑","234");
        /*学生绑定班级*/
        student.setClazz(clazz);
       /* 在单向的一对多的关系中 如果在地跑到一方维护关系
       则1的一方没办法完成级联的保存*/
        session.save(clazz);//保存班级
        /*需要多调用一次保存 对学生对象的保存*/
        session.save(student);
    }
}
