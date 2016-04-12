package br.edu.ifnmg.avaliar.util;

import br.edu.ifnmg.avaliar.entity.DatabaseVersion;
import java.util.Scanner;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.tool.hbm2ddl.SchemaExport;

/**
 * Hibernate Utility class with a convenient method to get Session Factory
 * object.
 *
 * @author danilo
 */
public class HibernateUtil {

    private static final SessionFactory sessionFactory;
    private static Session session;

    static {
        try {
            StandardServiceRegistry standardRegistry = new StandardServiceRegistryBuilder().configure("hibernate.cfg.xml").build();
            Metadata metadata = new MetadataSources(standardRegistry).getMetadataBuilder().build();
            sessionFactory = metadata.getSessionFactoryBuilder().build();

            Session s = sessionFactory.openSession();
            DatabaseVersion dataVersion = s.get(DatabaseVersion.class, 1);
            if (dataVersion == null) {
                Transaction t = s.beginTransaction();
                try {
                    dataVersion = new DatabaseVersion(1, 1);
                    s.save(dataVersion);
                    Scanner scanner = new Scanner(HibernateUtil.class.getResourceAsStream("/script_inicial.sql"));
                    StringBuilder builder = new StringBuilder();
                    while (scanner.hasNextLine()) {
                        builder.append(scanner.nextLine());

                    }
                    SQLQuery sql = s.createSQLQuery(builder.toString());
                    sql.executeUpdate();
                } catch (Exception e) {
                    t.rollback();
                    e.printStackTrace();
                } finally {
                    t.commit();
                }
            }
            s.disconnect();

        } catch (Throwable ex) {
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static Session getSession() {
//        if(session == null){
//            session = getSessionFactory().openSession();
//        } else if(!session.isOpen()){
//            session = sessionFactory.openSession();
//        }
        return sessionFactory.getCurrentSession();
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
