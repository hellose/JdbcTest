package study.meta;

import lombok.Setter;
import lombok.ToString;

@ToString
@Setter
public class Column {

	private String name;
	private int type;
	private String typeName;
	private int precision;

}
