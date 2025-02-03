package models.responses;

import lombok.Data;

@Data
public class ListUsersBodyModel {
  Integer userSize;
  String usersId;
}
