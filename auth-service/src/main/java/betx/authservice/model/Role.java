package betx.authservice.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "role_gen")
    private Long id;

    @Column(unique = true)
    @EqualsAndHashCode.Include
    private String name;
}
