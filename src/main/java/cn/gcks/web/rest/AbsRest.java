package cn.gcks.web.rest;


import java.io.Serializable;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;







import cn.gcks.web.domain.BaseRepository;
import cn.gcks.web.Constant;
import cn.gcks.web.WsResult;
import cn.gcks.web.service.AbsService;
import cn.gcks.web.util.DynamicSpecifications;
import cn.gcks.web.util.Reflections;
import cn.gcks.web.util.SearchFilter;
import cn.gcks.web.util.RequestUtil;
@CrossOrigin
public class AbsRest <T,K extends Serializable, R extends BaseRepository<T,K>,S extends AbsService<T, K, R>>  {
	@Autowired
	protected S service ;
	
	@SuppressWarnings("unchecked")
	private Class<T> TClass = (Class<T>)Reflections.getClassGenricType(this.getClass(), 0);

	@ResponseBody
	@RequestMapping(method = RequestMethod.GET)
	public Object list(
			 @PageableDefault(size=20,page=0,direction=Direction.DESC) Pageable pageable,
			HttpServletRequest request, HttpServletResponse resp) {
		WsResult result = new WsResult();
		Map<String, Object> searchParams = RequestUtil.getParametersStartingWith(request, "");
		Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
		try {
			return service.findAll(DynamicSpecifications.bySearchFilter(filters.values(), TClass),pageable);
		} catch (Exception e) {
			result.setStatus(Constant.Status.FAILED);
			result.setMsg(e.getMessage());
			resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}
		return result;
	}

	@ResponseBody
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public Object get(@PathVariable("id") K id, HttpServletRequest request, HttpServletResponse resp) {
		WsResult result = new WsResult();
		try {
			return service.findOne(id);
		} catch (Exception e) {
			result.setStatus(Constant.Status.FAILED);
			result.setMsg(e.getMessage());
			resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}
		return result;
	}


	@ResponseBody
	@RequestMapping(method = RequestMethod.POST)
	public Object save(@RequestBody T object, HttpServletResponse resp) {
		WsResult result = new WsResult();
		try {
			service.saveAndFlush(object);
		} catch (Exception e) {
			result.setStatus(Constant.Status.FAILED);
			result.setMsg(e.getMessage());
			resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}
		return result;
	};
	
	@ResponseBody
	@RequestMapping(method = RequestMethod.PUT)
	public Object update(@RequestBody T object,  HttpServletResponse resp) {
		WsResult result = new WsResult();
		try {
			service.save(object);
		} catch (Exception e) {
			result.setStatus(Constant.Status.FAILED);
			result.setMsg(e.getMessage());
			resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}
		return result;
	};

	@ResponseBody
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public Object delete(@PathVariable("id") K id, HttpServletRequest request, HttpServletResponse resp) {
		WsResult result = new WsResult();
		try {
			service.delete(id);
		} catch (Exception e) {
			result.setStatus(Constant.Status.FAILED);
			result.setMsg(e.getMessage());
			resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}
		return result;
	}

}
