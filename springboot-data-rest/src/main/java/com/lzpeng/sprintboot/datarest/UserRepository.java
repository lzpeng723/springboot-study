package com.lzpeng.sprintboot.datarest;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * path="user"： 映射路由
 * User：实体类
 * Long：主键
 * @author 李志鹏
 *
 */
@RepositoryRestResource(path="user")
public interface UserRepository extends JpaRepository<User, Long> {

}
