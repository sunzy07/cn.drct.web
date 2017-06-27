package cn.gcks.web.service;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;


import cn.gcks.web.domain.BaseRepository;

@Service
public abstract class AbsService<T,K extends Serializable,R extends BaseRepository<T,K>> {
	@Autowired
	protected R repository;

	public long count(Specification<T> spec) {
		return repository.count(spec);
	}

	public boolean exists(Specification<T> spec) {
		return repository.findOne(spec)!=null;
	}

	public Page<T> findAll(Specification<T> spec, Pageable arg1) {
		return repository.findAll(spec, arg1);
	}

	public <S extends T> S save(S entity) {
		return repository.save(entity);
	}

	public <S extends T> S findOne(Example<S> example) {
		return repository.findOne(example);
	}


	public List<T> findAll() {
		return repository.findAll();
	}

	public T findOne(K id) {
		return repository.findOne(id);
	}

	public List<T> findAll(Sort sort) {
		return repository.findAll(sort);
	}

	public List<T> findAll(Iterable<K> ids) {
		return repository.findAll(ids);
	}

	public boolean exists(K id) {
		return repository.exists(id);
	}

	public <S extends T> List<S> save(Iterable<S> entities) {
		return repository.save(entities);
	}

	public void flush() {
		repository.flush();
	}

	public <S extends T> S saveAndFlush(S entity) {
		return repository.saveAndFlush(entity);
	}

	public long count() {
		return repository.count();
	}
	
	public Page<T> findAll(Pageable pageable) {
		return repository.findAll(pageable);
	}
	
	public <S extends T> Page<S> findAll(Example<S> example, Pageable pageable) {
		
		return repository.findAll(example, pageable);
	}

	public void delete(K id) {
		repository.delete(id);
	}

	public <S extends T> long count(Example<S> example) {
		return repository.count(example);
	}


	public void deleteAll() {
		repository.deleteAll();
	}

	public <S extends T> boolean exists(Example<S> example) {
		return repository.exists(example);
	}

	
	
	
}
