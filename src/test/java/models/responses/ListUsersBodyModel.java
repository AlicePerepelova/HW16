package models.responses;

import lombok.Data;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Data
public class ListUsersBodyModel
{
  @JsonIgnoreProperties(ignoreUnknown = true)
  private Integer page;
  private Integer per_page;
  private Integer total;
  private Integer total_pages;
  private List<DataModel> data; // Изменено на список пользователей
  private SupportModel support; // Поддержка, если потребуется

  @Data
  public static class DataModel {
    private Integer id;
    private String name;
    private Integer year;
    private String color;
    private String pantone_value;
  }

  @Data
  public static class SupportModel {
    private String url;
    private String text;
  }
}