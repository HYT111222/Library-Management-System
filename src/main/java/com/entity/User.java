package com.entity;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Id;

@Getter
@Setter
public class User {
  @Id
  private String userid;
  private String password;
  private String salt;
}
