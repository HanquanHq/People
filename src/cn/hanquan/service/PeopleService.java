package cn.hanquan.service;

import java.io.IOException;
import java.util.List;

import cn.hanquan.pojo.PageInfo;
import cn.hanquan.pojo.People;

public interface PeopleService {
	/**
	 * 显示所有人信息
	 * 
	 * @return 所有人信息的集合
	 */	
	PageInfo showPage(int pageSize,int pageNum) throws IOException;
}
