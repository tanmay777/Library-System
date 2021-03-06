package com.example.library_system.dto;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class StudentDto {
  Integer id;
  String name;
  Integer issuedBookId;
}
