package pl.pracaInzynierska.model;

import java.util.Arrays;
import java.util.Collection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 *
 * @author m
 */
@Entity
@Table(name = "Uzytkownik")
public class Uzytkownik implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String username;
    private Boolean isEnabled;
    private String user_role;
    @Transient
    private String haslo;
    private String passwordEncrypted;

    private Uzytkownik() {
    }

    public Uzytkownik(String username, Boolean isEnabled, String role, String password) {
        this.username = username;
        this.isEnabled = isEnabled;
        this.user_role = "USER";
        this.haslo = password;
        this.passwordEncrypted = getPasswordEncrypted();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Boolean getIsEnabled() {
        return isEnabled;
    }

    public void setIsEnabled(Boolean isEnabled) {
        this.isEnabled = isEnabled;
    }

    public String getRole() {
        return user_role;
    }

    public void setRole(String role) {
        this.user_role = role;
    }

    @Override
    public String getPassword() {
        return passwordEncrypted;
    }

    public void setPassword(String password) {
        this.passwordEncrypted = password;
    }

    public String getPasswordEncrypted() {
        this.passwordEncrypted = new BCryptPasswordEncoder().encode(getPassword());
        return passwordEncrypted;
    }

    public void setPasswordEncrypted(String passwordencrypted) {
        this.passwordEncrypted = passwordencrypted;
    }

    public String toString(String username) {
        return username;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Arrays.asList(new SimpleGrantedAuthority("USER"));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return isEnabled;
    }
}
