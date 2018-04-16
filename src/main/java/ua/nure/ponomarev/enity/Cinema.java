package ua.nure.ponomarev.enity;

import lombok.*;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.List;

/**
 * @author Bogdan_Ponamarev.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder


@Entity
@Table(name = "cinema")
@Component
@Scope("prototype")
@NamedQueries({
        @NamedQuery(name = "Cinema.getAll", query = "FROM Cinema"),
})
public class Cinema {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private int id;

    @Pattern(regexp = "[\\w]{1}[\\w0-9\\- ]+", flags = Pattern.Flag.UNICODE_CASE, message = "Name is invalid")
    @Column(name = "name", unique = true, nullable = false)
    @NotNull
    @NotEmpty
    private String name;

    @Column(name = "franchises")
    @JoinColumn
    @OneToMany(cascade = CascadeType.ALL, targetEntity = Franchise.class)
    private List<Franchise> franchises;

    @Email
    @Column(name = "email", unique = true, nullable = false)
    @NotNull
    @NotEmpty
    private String email;

    @Pattern(regexp = "\\d{10}|(?:\\d{3}-){2}\\d{4}|\\(\\d{3}\\)\\d{3}-?\\d{4}", message = "Phone number is invalid")
    @Column(name = "phone_number", unique = true, nullable = false)
    @NotNull
    @NotEmpty
    private String phoneNumber;

    @ManyToOne(targetEntity = User.class)
    @JoinColumn
    private User currentAdmin;

    @Column(name = "description")
    @Lazy
    private String description;
}
