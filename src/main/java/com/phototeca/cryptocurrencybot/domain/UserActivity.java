package com.phototeca.cryptocurrencybot.domain;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;


@ToString
@Entity
@Setter
@Getter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class UserActivity {
    @Id
    private long chatId;
    private String firstName;
    private String lastName;
    private String userName;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifyDate;
    private State state;
    @Lob
    private String userPrice;

}
