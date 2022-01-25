package ru.job4j.model;


import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "tasks")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    private String description;

    @Temporal(TemporalType.TIMESTAMP)
    private Date created;
    private boolean done;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    Set<Category> categories = new HashSet<>();

    public Task() {
    }

    public Task(String description, Date created, boolean done) {
        this.description = description;
        this.created = created;
        this.done = done;
    }

    public Task(User user, String description, Date created, boolean done) {
        this.user = user;
        this.description = description;
        this.created = created;
        this.done = done;
    }

    public Task(User user, String description, Date created, boolean done, Set<Category> categories) {
        this.user = user;
        this.description = description;
        this.created = created;
        this.done = done;
        this.categories = categories;
    }

    public void addCategory(Category category) {
        this.categories.add(category);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<Category> getCategories() {
        return categories;
    }

    public void setCategories(Set<Category> categories) {
        this.categories = categories;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Task task = (Task) o;
        return id == task.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Task{" + "id=" + id + ", user=" + user
                + ", description='" + description
                + ", created=" + created + ", done=" + done
                + ", categories=" + categories + '}';
    }
}