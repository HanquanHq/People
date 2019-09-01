package cn.hanquan.test;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import cn.hanquan.pojo.People;

public class Test {
	public static void main(String[] args) throws IOException {
		InputStream is = Resources.getResourceAsStream("mybatis.xml");
		// 工厂模式+构建者模式
		SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(is);
		SqlSession session = factory.openSession();

		// 查询语句
		// 1、查询语句无参数
		List<People> list1 = session.selectList("cn.hanquan.mapper.PeopleMapper.selAll");
		System.out.println(list1 + "\n");

		// 2、查询语句含一个参数
		People p2 = session.selectOne("cn.hanquan.mapper.PeopleMapper.selOne", 2);
		System.out.println(p2 + "\n");

		// 3、查询语句含多个参数：使用Map
		Map<String, Object> m3 = new HashMap<String, Object>();
		m3.put("name", "张三");
		m3.put("age", 25);
		People p3 = (People) session.selectOne("cn.hanquan.mapper.PeopleMapper.selList", m3);
		System.out.println(p3 + "\n");

		// 4、查询语句在xml文件中出现< , > ,双引号等特殊字符
		List<People> list4 = session.selectList("cn.hanquan.mapper.PeopleMapper.selSpecial", 3);
		System.out.println(list4 + "\n");

		// 5、实现分页
		int pgSize = 2;
		int pgNum = 3;
		Map<String, Object> m5 = new HashMap<String, Object>();
		m5.put("pgSize", pgSize);
		m5.put("pgStart", pgSize * (pgNum - 1));
		List<People> list5 = session.selectList("cn.hanquan.mapper.PeopleMapper.selPage", m5);
		System.out.println(list5 + "\n");

		// 6、在mybatis.xml中给类起别名
		List<People> list6 = session.selectList("cn.hanquan.mapper.PeopleMapper.selAlias");
		System.out.println(list6 + "\n");

		// 7、在mybatis.xml中通过类名引用
		List<People> list7 = session.selectList("cn.hanquan.mapper.PeopleMapper.selAliasPkg");
		System.out.println(list7 + "\n");

		// 插入语句
		People p8 = new People();
		p8.setName("小李");
		p8.setAge(30);
		int line8 = session.insert("cn.hanquan.mapper.PeopleMapper.ins", p8);
		System.out.println(line8);

		session.commit();// mybatis默认关闭了JDBC的自动提交功能。增删改的时候，需要手动提交事务
		// session.rollback();//在try..catch中使用rollback回滚事务
		session.close();
	}
}
