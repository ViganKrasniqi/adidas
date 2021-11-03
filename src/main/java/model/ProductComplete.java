package model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductComplete {
	 String name;
	 String description;
	 String id;
	 String currency;
	 int price;
}
