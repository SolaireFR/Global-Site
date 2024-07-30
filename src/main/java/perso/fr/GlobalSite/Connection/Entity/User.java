package perso.fr.GlobalSite.Connection.Entity;

import java.security.SecureRandom;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Calendar;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="users")
public class User
{
    // ##### STATIC ######

    private static final int EXPIRATION = 60 * 24;
    private static final SecureRandom SECURE_RANDOM = new SecureRandom(); //threadsafe
    private static final Base64.Encoder BASE64_ENCODER = Base64.getUrlEncoder(); //threadsafe

    public static String generateNewToken() {
        byte[] randomBytes = new byte[24];
        User.SECURE_RANDOM.nextBytes(randomBytes);
        return User.BASE64_ENCODER.encodeToString(randomBytes);
    }

    // ###############

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false)
    private String displayName;

    @Column(nullable=false, unique=true)
    private String email;

    @Column(nullable=false)
    private String password;

    @Column(nullable=false, insertable=false, updatable=false, columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime creation;

    private Date lastConnection;

    @Column(nullable=false, columnDefinition="BOOLEAN DEFAULT FALSE")
    private boolean locked;

    @Column(nullable=false, columnDefinition="BOOLEAN DEFAULT FALSE")
    private boolean isEnabled;

    @Column(nullable=false)
    private String role;

    private String token;

    private Date tokenExpiryDate;

    public List<Role> getRoles() {
        return new ArrayList<Role>(){{new Role(role);}};
    }

    public void setRoles(List<Role> roles) {
        this.role = roles.get(0).getName();
    }

    private Date calculateExpiryDate(int expiryTimeInMinutes) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Timestamp(cal.getTime().getTime()));
        cal.add(Calendar.MINUTE, expiryTimeInMinutes);
        return new Date(cal.getTime().getTime());
    }

    public void VerificationToken() {
        this.token = User.generateNewToken();
        this.tokenExpiryDate = calculateExpiryDate(User.EXPIRATION);
    }
}