package com.example.springBootDemo.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@RequiredArgsConstructor
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter
    private Integer id;

    @Getter @Setter @NonNull
    private LocalDateTime beginning;

    @Getter @Setter @NonNull
    private LocalDateTime ending;

}
