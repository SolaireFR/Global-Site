package perso.fr.GlobalSite.Connection.Entity;

import java.security.SecureRandom;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Base64;
import java.util.Calendar;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class VerificationToken {
    
    // ##### STATIC ######

    private static final int EXPIRATION = 60 * 24;
    private static final SecureRandom SECURE_RANDOM = new SecureRandom(); //threadsafe
    private static final Base64.Encoder BASE64_ENCODER = Base64.getUrlEncoder(); //threadsafe

    public static String generateNewToken() {
        byte[] randomBytes = new byte[24];
        VerificationToken.SECURE_RANDOM.nextBytes(randomBytes);
        return VerificationToken.BASE64_ENCODER.encodeToString(randomBytes);
    }

    // ###################

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @Column(unique=true, nullable=false)
    private String token;
  
    @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "id")
    private User user;
    
    private Date expiryDate;
   
    private Date calculateExpiryDate(int expiryTimeInMinutes) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Timestamp(cal.getTime().getTime()));
        cal.add(Calendar.MINUTE, expiryTimeInMinutes);
        return new Date(cal.getTime().getTime());
    }

    public VerificationToken(User user) {
        this.user = user;
        this.token = VerificationToken.generateNewToken();
        this.expiryDate = calculateExpiryDate(VerificationToken.EXPIRATION);
    }
}
