package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.HibernateException;
import org.hibernate.Session;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        Session session = null;
        try {
            session = Util.getSessionFactory().openSession();
            session.beginTransaction();
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
            if (session != null) session.getTransaction().rollback();
        } finally {
            if (session != null) session.close();
        }
    }

    @Override
    public void dropUsersTable() {
        Session session = null;
        try {
            session = Util.getSessionFactory().openSession();
            session.beginTransaction();
            String sql = "DROP TABLE IF EXISTS users";
            session.createSQLQuery(sql).executeUpdate();
            session.getTransaction().commit();
            System.out.println("Таблица успешно создана");
        } catch (HibernateException e) {
            System.out.println("Произошла ошибка при создании таблицы");
            e.printStackTrace();
            if (session != null) session.getTransaction().rollback();
        } finally {
            if (session != null) session.close();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Session session = null;
        try {
            session = Util.getSessionFactory().openSession();
            session.beginTransaction();
            session.save(new User(name, lastName, age));
            session.getTransaction().commit();
            System.out.println("User с именем – " + name + " добавлен в базу данных");
        } catch (HibernateException e) {
            System.out.println("Не удалось добавить User в таблицу.");
            if (session != null) session.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            if (session != null) session.close();
        }
    }

    @Override
    public void removeUserById(long id) {
        Session session = null;
        try {
            session = Util.getSessionFactory().openSession();
            session.beginTransaction();
            String sql = "DELETE FROM users WHERE id = " + id;
            session.createSQLQuery(sql).executeUpdate();
            session.getTransaction().commit();
            System.out.println("Таблица успешно создана");
        } catch (HibernateException e) {
            System.out.println("Произошла ошибка при создании таблицы");
            e.printStackTrace();
            if (session != null) session.getTransaction().rollback();
        } finally {
            if (session != null) session.close();
        }
    }

    @Override
    public List<User> getAllUsers() {
        Session session = null;
        List<User> users = null;
        try {
            session = Util.getSessionFactory().openSession();
            session.beginTransaction();
            String sql = "SELECT * FROM users";
            users = session.createSQLQuery(sql).addEntity(User.class).list();
            System.out.println(users.size());
            session.getTransaction().commit();
        } catch (HibernateException e) {
            System.out.println("Произошла ошибка при создании таблицы");
            e.printStackTrace();
            if (session != null) session.getTransaction().rollback();
        } finally {
            if (session != null) session.close();
        }
        return users;
    }

    @Override
    public void cleanUsersTable() {
        Session session = null;
        try {
            session = Util.getSessionFactory().openSession();
            session.beginTransaction();
            String hql = "DELETE FROM User";
            session.createQuery(hql).executeUpdate();
            session.getTransaction().commit();
            System.out.println("Таблица users успешно очищена");
        } catch (HibernateException e) {
            System.out.println("Произошла ошибка при создании таблицы");
            e.printStackTrace();
            if (session != null) session.getTransaction().rollback();
        } finally {
            if (session != null) session.close();
        }
    }
}
