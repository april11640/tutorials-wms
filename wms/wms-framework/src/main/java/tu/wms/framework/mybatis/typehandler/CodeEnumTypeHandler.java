package tu.wms.framework.mybatis.typehandler;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import tu.wms.framework.model.CodeEnum;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 *
 * @param <E>
 */
public class CodeEnumTypeHandler<E extends Enum<E> & CodeEnum<E>> extends BaseTypeHandler<CodeEnum> {

	private Class<CodeEnum> type;
	private Map<Integer, CodeEnum> cache;

	public CodeEnumTypeHandler(Class<CodeEnum> type) {
		Objects.requireNonNull(type, "The type argument cannot be null");

		Map<Integer, CodeEnum> map = new HashMap<>();
		CodeEnum[] enumConstants = type.getEnumConstants();
		for (CodeEnum item : enumConstants) {
			map.put(item.getCode(), item);
		}

		this.type = type;
		this.cache = map;
	}

	@Override
	public CodeEnum getNullableResult(ResultSet rs, String columnName)
			throws SQLException {
		int code = rs.getInt(columnName);
		if(rs.wasNull()) {
			return null;
		} else {
			return cache.get(code);
		}
	}

	@Override
	public CodeEnum getNullableResult(ResultSet rs, int columnIndex)
			throws SQLException {
		int code = rs.getInt(columnIndex);
		if(rs.wasNull()) {
			return null;
		} else {
			return cache.get(code);
		}
	}

	@Override
	public CodeEnum getNullableResult(CallableStatement cs, int columnIndex)
			throws SQLException {
		int code = cs.getInt(columnIndex);
		if(cs.wasNull()) {
			return null;
		} else {
			return cache.get(code);
		}
	}

	@Override
	public void setNonNullParameter(PreparedStatement ps, int i,
                                    CodeEnum enumObj, JdbcType jdbcType) throws SQLException {
		ps.setInt(i, enumObj.getCode());
	}

}
