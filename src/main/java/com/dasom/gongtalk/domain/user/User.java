package com.dasom.gongtalk.domain.user;

import com.dasom.gongtalk.util.RandomStringGenerator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.UniqueElements;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.Random;

@Entity
@Getter
@NoArgsConstructor
@JsonIgnoreProperties({"deviceNum", "username", "password"})
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @Column(unique = true)
    private String username;

    @NotNull
    @Column(unique = true)
    private String password;


    @Setter
    @NotNull
    private String deviceNum;

    @Setter
    @OneToOne
    private Setting setting;

    public void setUsername()
    {
        RandomStringGenerator rsg = new RandomStringGenerator();
        DateFormat formatter = new SimpleDateFormat("yyMMdd");
        String usnm = rsg.generate(4,0);
        String date = formatter.format(new Date());
        this.username = (usnm + date);
    }

    public void setPassword()
    {
        RandomStringGenerator rsg = new RandomStringGenerator();
        String pswd = rsg.generate(8,8);
        this.password = (pswd);
    }





}
