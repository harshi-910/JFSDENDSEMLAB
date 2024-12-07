package com.klef.jfsd.exam.EndSemLabExam;

import org.hibernate.*;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Projections;

import java.util.List;

public class ClientDemo {
    public static void main(String[] args) {
        // Step 1: Configure Hibernate
        Configuration config = new Configuration();
        config.configure("hibernate.cfg.xml");
        SessionFactory sessionFactory = config.buildSessionFactory();

        // Step 2: Insert records
        insertProjects(sessionFactory);

        // Step 3: Perform Aggregate Functions
        performAggregateOperations(sessionFactory);

        // Close session factory
        sessionFactory.close();
    }

    public static void insertProjects(SessionFactory sessionFactory) {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();

        Project p1 = new Project("Project A", 12, 1000000, "John Doe");
        Project p2 = new Project("Project B", 8, 750000, "Jane Smith");
        Project p3 = new Project("Project C", 18, 1500000, "Michael Brown");

        session.save(p1);
        session.save(p2);
        session.save(p3);

        tx.commit();
        session.close();

        System.out.println("Projects inserted successfully!");
    }

    public static void performAggregateOperations(SessionFactory sessionFactory) {
        Session session = sessionFactory.openSession();

        // Count
        Criteria countCriteria = session.createCriteria(Project.class);
        countCriteria.setProjection(Projections.rowCount());
        System.out.println("Total Projects: " + countCriteria.uniqueResult());

        // Max Budget
        Criteria maxCriteria = session.createCriteria(Project.class);
        maxCriteria.setProjection(Projections.max("budget"));
        System.out.println("Maximum Budget: " + maxCriteria.uniqueResult());

        // Min Budget
        Criteria minCriteria = session.createCriteria(Project.class);
        minCriteria.setProjection(Projections.min("budget"));
        System.out.println("Minimum Budget: " + minCriteria.uniqueResult());

        // Sum Budget
        Criteria sumCriteria = session.createCriteria(Project.class);
        sumCriteria.setProjection(Projections.sum("budget"));
        System.out.println("Total Budget: " + sumCriteria.uniqueResult());

        // Average Budget
        Criteria avgCriteria = session.createCriteria(Project.class);
        avgCriteria.setProjection(Projections.avg("budget"));
        System.out.println("Average Budget: " + avgCriteria.uniqueResult());

        session.close();
    }
}
