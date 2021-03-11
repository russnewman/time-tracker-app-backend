package com.example.TimeTracker.model;

import com.example.TimeTracker.service.Impl.Utils;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "Statistics")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Statistic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Person user;

    @Column
    private LocalDate date;

    @Column
    private byte[] effective;

    @Column
    private byte[] neutral;

    @Column
    private byte[] ineffective;

    @Column
    private byte[] without;



//    public Statistic(Person user,
//                     LocalDate date,
//                     int[] effective,
//                     int[] neutral,
//                     int[] ineffective,
//                     int[] without){
//        this.user = user;
//        this.date = date;
//        this.effective = Utils.convertIntegersToBytes(effective);
//        this.neutral = Utils.convertIntegersToBytes(neutral);
//        this.ineffective = Utils.convertIntegersToBytes(ineffective);
//        this.without = Utils.convertIntegersToBytes(without);
//    }
}
