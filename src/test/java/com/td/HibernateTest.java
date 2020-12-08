package com.td;


import com.td.config.HibernateConfig;
import com.td.entity.Person;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.annotations.Persister;
import org.hibernate.query.Query;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;
import java.util.Iterator;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { HibernateConfig.class })
public class HibernateTest {

    @Autowired
    private SessionFactory sessionFactory;

    //@Transactional
    @Test
    public void test() {
        Session session = sessionFactory.openSession();

//        Person person = Person.builder().name("zhang").age(30).build();
//
//        Transaction transaction = session.getTransaction();
//        transaction.begin();

        // 会先填充ID值
//        session.save(person);

//        Person person2 = Person.builder().name("zhang2").age(30).build();
        // 不会检测会话是否激活
//        session.persist(person2);

//        transaction.commit();

        Query query = session.createQuery("FROM Person");
        Iterator iterate = query.iterate();
        while (iterate.hasNext()) {
            Person p = (Person)iterate.next();
            p.getName();
            System.out.println(1);
        }

        session.close();
    }


    @PersistenceContext
    private EntityManager entityManager;

    @Test
    @Transactional // JPA不能用Session事务，只能用Spring事务
    public void test2() {
        Person person = Person.builder().name("zhang").age(30).build();
        entityManager.persist(person);
        System.out.println(person);
    }
}
