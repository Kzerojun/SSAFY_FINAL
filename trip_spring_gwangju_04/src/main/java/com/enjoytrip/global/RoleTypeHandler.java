package com.enjoytrip.global;

import com.enjoytrip.user.constants.Role;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RoleTypeHandler extends BaseTypeHandler<Role> {

	@Override
	public void setNonNullParameter(PreparedStatement ps, int i, Role parameter, JdbcType jdbcType) throws SQLException {
		ps.setString(i, parameter.getValue());
	}

	@Override
	public Role getNullableResult(ResultSet rs, String columnName) throws SQLException {
		String role = rs.getString(columnName);
		return role != null ? Role.valueOf(role) : null;
	}

	@Override
	public Role getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
		String role = rs.getString(columnIndex);
		return role != null ? Role.valueOf(role) : null;
	}

	@Override
	public Role getNullableResult(java.sql.CallableStatement cs, int columnIndex) throws SQLException {
		String role = cs.getString(columnIndex);
		return role != null ? Role.valueOf(role) : null;
	}
}
