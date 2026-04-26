package com.scm.peopledesk.forms;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class UserForm {
    private String name;
    private String email;
    private String password;
    private String about;
    private String phoneNumber;
}
