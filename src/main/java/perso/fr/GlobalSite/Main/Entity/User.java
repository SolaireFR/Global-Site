package perso.fr.GlobalSite.Main.Entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.security.SecureRandom;
import java.sql.Date;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/** Les données personnels et principales de l'utilisateur. */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "main_users")
public class User
{
    // ##### STATIC ######
    private static final SecureRandom SECURE_RANDOM = new SecureRandom(); //threadsafe
    private static final Base64.Encoder BASE64_ENCODER = Base64.getUrlEncoder(); //threadsafe
    private static final int BYTE_LENGHT = 24;

    /** Génére un nouveau token.
     *
     * @return Un nouveau token.
     */
    public static String generateNewToken() {
        byte[] randomBytes = new byte[BYTE_LENGHT];
        User.SECURE_RANDOM.nextBytes(randomBytes);
        return User.BASE64_ENCODER.encodeToString(randomBytes);
    }

    // ###############

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String displayName;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(
        nullable = false,
        insertable = false,
        updatable = false,
        columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime creation;

    private Date lastConnection;

    @Column(nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
    private boolean locked;

    @Column(nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
    private boolean isEnabled;

    @Column(nullable = false)
    private String role;

    private String token;

    private Date tokenExpiryDate;

    @OneToOne(mappedBy = "user", cascade = CascadeType.PERSIST)
    private UserData userData = new UserData(this);

    /** Transforme et renvoie le rôle unique de l'utilisateur sous forme d'une liste pour Spring-Security.
     *
     * @return La liste de {@link Role}.
     */
    public List<Role> getRoles() {
        return new ArrayList<Role>() { { 
                new Role(role); 
            }};
    }

    /** Récupère le 1er rôle de la liste pour l'insèrer dans l'attribut role.
     *
     * @param roles Une liste de {@link Role}.
     */
    public void setRoles(List<Role> roles) {
        this.role = roles.get(0).getName();
    }
}
