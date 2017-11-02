package luckyweb.seagull.spring.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import luckyweb.seagull.spring.dao.TestClientDao;
import luckyweb.seagull.spring.entity.TestClient;



@Service("testclientService")
public class TestClientServiceImpl implements TestClientService{
	
	private TestClientDao tcdao;
	
	public TestClientDao getTestClientDao() {
		return tcdao;
	}

	@Resource(name = "testclientDao")
	public void setTestClientDao(TestClientDao tcDao) {
		this.tcdao = tcDao;
	}
	
	@Override
	public TestClient load(int id) throws Exception {
		// TODO Auto-generated method stub		
		return this.tcdao.load(id);
	}
	
	public int add(TestClient tc) throws Exception{
		return this.tcdao.add(tc);
	}
	
	public void modify(TestClient tc) throws Exception{
		this.tcdao.modify(tc);
	}
	
	public void delete(TestClient tc) throws Exception{
		this.tcdao.delete(tc);
	}

	private String where(TestClient tc) {
		String where = " where ";
		if (null!=tc.getClientip()&&!"".equals(tc.getClientip())) {
			where += " clientip like :clientip  or ";
		}
		if (null!=tc.getName()&&!"".equals(tc.getName())) {
			where += " name like :name  or ";
		}
		if (where.length() == 7) {
			where = "";
		} 
		else{
			where = where.substring(0, where.length() - 5);
		}

		return where;
	}
	
	private static String  orderBy=" order by id desc";
	
	@Override
	public List findByPage(Object value, int offset, int pageSize) {
		String	hql=" from TestClient  "+where((TestClient)value)+orderBy;
		List list= tcdao.findByPage(hql, value, offset, pageSize);
		return list;
	}

	@Override
	public int findRows(TestClient tc) {
		String hql="select count(*) from TestClient "+where(tc);
		return tcdao.findRows(tc, hql);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<TestClient> getClientListForProid(int projectid) throws Exception {
		String hql="from TestClient where projectper like '%,"+projectid+",%' order by id asc";
		return tcdao.listsql(hql);
	}
	
}