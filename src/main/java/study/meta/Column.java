package study.meta;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@ToString
@AllArgsConstructor
@Getter
public class Column {

	private String name;
	private int type;
	private String typeName;
	private int precision;

}
