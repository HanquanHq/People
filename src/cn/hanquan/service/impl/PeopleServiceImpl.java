package cn.hanquan.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.log4j.Logger;

import cn.hanquan.pojo.PageInfo;
import cn.hanquan.pojo.People;
import cn.hanquan.service.PeopleService;

/**
 * 在数据访问层、控制器中处理异常，在service中只抛出异常
 * 
 * @author Buuug
 *
 */
public class PeopleServiceImpl implements PeopleService {
	Logger logger = Logger.getLogger(PeopleServiceImpl.class);

	@Override
	public PageInfo showPage(int pageSize, int pageNum) throws IOException {
		InputStream is = Resources.getResourceAsStream("mybatis.xml");
		SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(is);
		SqlSession session = factory.openSession();

		PageInfo pi = new PageInfo();
		pi.setPageNum(pageNum);
		pi.setPageSize(pageSize);

		// 查询结果
		Map<String, Object> map = new HashMap<>();
		map.put("pageStart", pageSize * (pageNum - 1));
		map.put("pageSize", pageSize);
		List<People> list = session.selectList("cn.hanquan.mapper.PeopleMapper.selByPage", map);
		pi.setDataList(list);
		logger.debug(list);

		// 总页数
		int count = session.selectOne("cn.hanquan.mapper.PeopleMapper.selCount");
		pi.setTotal((count % pageSize == 0 ? count / pageSize : count / pageSize + 1));
		logger.debug("总人数："+pi.getTotal());
		return pi;
	}
}
