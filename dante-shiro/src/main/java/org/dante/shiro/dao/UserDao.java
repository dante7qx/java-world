package org.dante.shiro.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashSet;
import java.util.Set;

import org.dante.shiro.domain.Permission;
import org.dante.shiro.domain.Role;
import org.dante.shiro.domain.User;

public class UserDao {
	
	public void addUser(Connection conn, String userName, String password) throws Exception {
		String sql = "insert into T_User(UserName, Password) values (?, ?)";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, userName);
		pstmt.setString(2, password);
		pstmt.executeUpdate();
	}
	
	public User findByUserName(Connection conn, String userName) throws Exception {
		User user = null;
		String sql = "select Id, UserName, Password from T_User where UserName = ?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, userName);
		ResultSet rs = pstmt.executeQuery();
		while (rs.next()) {
			user = new User();
			user.setId(rs.getInt("Id"));
			user.setUserName(rs.getString("UserName"));
			user.setPassword(rs.getString("Password"));
		}
		return user;
	}

	public Set<Role> findUserRolesByUserName(Connection conn, String userName) throws Exception {
		Set<Role> roles = new HashSet<Role>();
		String sql = "select t.Id,t.Name,t.Remark from T_Role t left join T_User_Role t1 on t.Id = t1.RoleId left join T_User t2 on t1.UserId = t2.Id where t2.UserName = ?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, userName);
		ResultSet rs = pstmt.executeQuery();
		while (rs.next()) {
			Role role = new Role();
			role.setId(rs.getInt("Id"));
			role.setName(rs.getString("Name"));
			role.setRemark(rs.getString("Remark"));
			roles.add(role);
		}
		rs.close();
		pstmt.close();
		return roles;
	}

	public Set<Permission> findUserPermissionByUserName(Connection conn, String userName) throws Exception {
		Set<Permission> permissions = new HashSet<Permission>();
		StringBuffer sqlBuf = new StringBuffer();
		sqlBuf.append("select p.Id,p.Name,p.Code from T_Permission p ")
				.append("left join T_Role_Permission rp on p.Id = rp.PermissionId ")
				.append("left join T_Role t on rp.RoleId = t.Id ")
				.append("left join T_User_Role t1 on t.Id = t1.RoleId ")
				.append("left join T_User t2 on t1.UserId = t2.Id where t2.UserName = ?");
		PreparedStatement pstmt = conn.prepareStatement(sqlBuf.toString());
		pstmt.setString(1, userName);
		ResultSet rs = pstmt.executeQuery();
		while (rs.next()) {
			Permission permission = new Permission();
			permission.setId(rs.getInt("Id"));
			permission.setName(rs.getString("Name"));
			permission.setCode(rs.getString("Code"));
			permissions.add(permission);
		}
		rs.close();
		pstmt.close();
		return permissions;
	}
}
