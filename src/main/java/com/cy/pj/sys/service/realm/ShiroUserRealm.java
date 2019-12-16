package com.cy.pj.sys.service.realm;

import com.cy.pj.sys.dao.SysUserDao;
import com.cy.pj.sys.entity.SysUser;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ShiroUserRealm extends AuthorizingRealm {
	@Autowired(required = false)
	private SysUserDao sysUserDao;
	
	/**
	 * 此方法负责用户信息的获取以及封装
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		//1.获取用户端输入的用户信息
		UsernamePasswordToken upToken = (UsernamePasswordToken) token;
		String username = upToken.getUsername();
		//2.基于用户输入的用户名查询用户信息
		SysUser user = sysUserDao.findUserByUserName(username);
		//3.对用户信息进行基本校验
		if (user == null)
			throw new UnknownAccountException();
		//4.判定用户是否已被禁用。
		if (user.getValid() == 0)
			throw new LockedAccountException();
		//5.封装用户信息并返回
		ByteSource credentialsSalt = ByteSource.Util.bytes(user.getSalt().getBytes());
		//记住：构建什么对象要看方法的返回值
		SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user,//principal 身份
				user.getPassword(),//hashedCredentials 已加密的密码
				credentialsSalt,//credentialsSalt
				this.getName());//realName
		//6.返回封装结果
		return info;//返回值会传递给认证管理器(SecurityManager)(后续认证管理器会通过此信息完成认证操作)
	}
	
	/**
	 * 设置加密算法
	 * 设置凭证匹配器(与用户添加操作使用相同的加密算法)
	 */
	@Override
	public void setCredentialsMatcher(CredentialsMatcher credentialsMatcher) {
		//构建凭证匹配对象
		HashedCredentialsMatcher cMatcher = new HashedCredentialsMatcher();
		//设置加密算法
		cMatcher.setHashAlgorithmName("MD5");
		//设置加密次数
		cMatcher.setHashIterations(1);
		super.setCredentialsMatcher(cMatcher);
	}
	
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		return null;
	}
}
