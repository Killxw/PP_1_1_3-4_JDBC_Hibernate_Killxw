package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        Transaction transaction = null;
        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            String sql = """
                    CREATE TABLE IF NOT EXISTS users (id BIGINT NOT NULL AUTO_INCREMENT,
                      name VARCHAR(45) NOT NULL,
                      last_name VARCHAR(45) NOT NULL,
                      age TINYINT NOT NULL,
                      PRIMARY KEY (id))
                    """;
            session.createSQLQuery(sql).executeUpdate();
            session.getTransaction().commit();
            System.out.println("Таблица успешно создана");
        } catch (HibernateException e) {
            System.out.println("Произошла ошибка при создании таблицы");
            e.printStackTrace();
            if (transaction != null)transaction.rollback();
        }
    }

    @Override
    public void dropUsersTable() {
        Transaction transaction = null;
        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            String sql = "DROP TABLE IF EXISTS users";
            session.createSQLQuery(sql).executeUpdate();
            session.getTransaction().commit();
            System.out.println("Таблица успешно создана");
        } catch (HibernateException e) {
            System.out.println("Произошла ошибка при создании таблицы");
            e.printStackTrace();
            if (transaction != null)transaction.rollback();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Transaction transaction = null;
        try (Session session = Util.getSessionFactory().openSession()){
            transaction = session.beginTransaction();
            session.save(new User(name, lastName, age));
            session.getTransaction().commit();
            System.out.println("User с именем – " + name + " добавлен в базу данных");
        } catch (HibernateException e) {
            System.out.println("Не удалось добавить User в таблицу.");
            if (transaction != null)transaction.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public void removeUserById(long id) {
        Transaction transaction = null;
        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            String sql = "DELETE FROM users WHERE id = " + id;
            session.createSQLQuery(sql).executeUpdate();
            session.getTransaction().commit();
            System.out.println("Таблица успешно создана");
        } catch (HibernateException e) {
            System.out.println("Произошла ошибка при создании таблицы");
            e.printStackTrace();
            if (transaction != null)transaction.rollback();
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> userList = null;
        try (Session session = Util.getSessionFactory().openSession()) {
            Query<User> query = session.createQuery("FROM User ", User.class);
            userList = query.list();
        } catch (HibernateException e) {
            System.out.println("Произошла ошибка при создании таблицы");
            e.printStackTrace();
        }
        return userList;
    }

    @Override
    public void cleanUsersTable() {
        Transaction transaction = null;
        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            String hql = "DELETE FROM User";
            session.createQuery(hql).executeUpdate();
            session.getTransaction().commit();
            System.out.println("Таблица users успешно очищена");
        } catch (HibernateException e) {
            System.out.println("Произошла ошибка при создании таблицы");
            e.printStackTrace();
            if (transaction != null)transaction.rollback();
        }
    }
}
