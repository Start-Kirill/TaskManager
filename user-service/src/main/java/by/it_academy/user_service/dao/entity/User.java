package by.it_academy.user_service.dao.entity;

import by.it_academy.task_manager_common.enums.UserRole;
import by.it_academy.user_service.core.enums.UserStatus;
import jakarta.persistence.*;
import org.hibernate.annotations.DynamicUpdate;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "users")
@DynamicUpdate
public class User implements Serializable {

    private static final long serialVersionUID = 42L;

    @Id
    private UUID uuid;

    @Column(name = "dt_create")
    private LocalDateTime dateTimeCreate;

    @Version
    @Column(name = "dt_update")
    private LocalDateTime dateTimeUpdate;

    private String mail;

    private String fio;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    @Enumerated(EnumType.STRING)
    private UserStatus status;

    private String password;


    public User() {
    }

    public User(UUID uuid, LocalDateTime dateTimeCreate, LocalDateTime dateTimeUpdate, String mail, String fio, UserRole role, UserStatus status, String password) {
        this.uuid = uuid;
        this.dateTimeCreate = dateTimeCreate;
        this.dateTimeUpdate = dateTimeUpdate;
        this.mail = mail;
        this.fio = fio;
        this.role = role;
        this.status = status;
        this.password = password;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public LocalDateTime getDateTimeCreate() {
        return dateTimeCreate;
    }

    public void setDateTimeCreate(LocalDateTime dateTimeCreate) {
        this.dateTimeCreate = dateTimeCreate;
    }

    public LocalDateTime getDateTimeUpdate() {
        return dateTimeUpdate;
    }

    public void setDateTimeUpdate(LocalDateTime dateTimeUpdate) {
        this.dateTimeUpdate = dateTimeUpdate;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getFio() {
        return fio;
    }

    public void setFio(String fio) {
        this.fio = fio;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public UserStatus getStatus() {
        return status;
    }

    public void setStatus(UserStatus status) {
        this.status = status;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User that = (User) o;
        return Objects.equals(uuid, that.uuid) && Objects.equals(dateTimeCreate, that.dateTimeCreate) && Objects.equals(dateTimeUpdate, that.dateTimeUpdate) && Objects.equals(mail, that.mail) && Objects.equals(fio, that.fio) && role == that.role && status == that.status && Objects.equals(password, that.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, dateTimeCreate, dateTimeUpdate, mail, fio, role, status, password);
    }

    @Override
    public String toString() {
        return "UserCreate{" +
                "uuid=" + uuid +
                ", mail='" + mail + '\'' +
                ", fio='" + fio + '\'' +
                ", role=" + role +
                '}';
    }
}
