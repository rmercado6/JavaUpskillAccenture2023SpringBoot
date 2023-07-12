package com.example.springBootDemo.models.input;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
public class EventCreateRequest {

    @Getter @Setter
    private String timeString;

}
