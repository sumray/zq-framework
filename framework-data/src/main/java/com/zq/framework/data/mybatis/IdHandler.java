package com.zq.framework.data.mybatis;

import com.zq.framework.entity.IdEntity;
import com.zq.framework.mybatis.TypeAlias;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@MappedTypes(value = { TypeAlias.class })
@MappedJdbcTypes(value = { JdbcType.BIGINT })
public class IdHandler<Entity extends IdEntity> extends BaseTypeHandler<Entity> implements TypeAlias {
	private Class<Entity> type;

	public IdHandler(Class<Entity> type) {
		if (type == null) {
			throw new IllegalArgumentException("Type argument cannot be null");
		}
		this.type = type;
	}

	@Override
	public void setNonNullParameter(PreparedStatement ps, int i, Entity parameter, JdbcType jdbcType) throws SQLException {
		ps.setLong(i, parameter.getId());
	}

	@Override
	public Entity getNullableResult(ResultSet rs, String columnName) throws SQLException {
		Long id = rs.getLong(columnName);
		return getEntityInstance(id);
	}

	@Override
	public Entity getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
		Long id = rs.getLong(columnIndex);
		return getEntityInstance(id);
	}

	@Override
	public Entity getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
		Long id = cs.getLong(columnIndex);
		return getEntityInstance(id);
	}

	protected Entity getEntityInstance(Long id) {
		try {
			Entity entity = type.newInstance();
			entity.setId(id);
			return entity;
		} catch (Exception e) {
		}
		return null;
	}
}
