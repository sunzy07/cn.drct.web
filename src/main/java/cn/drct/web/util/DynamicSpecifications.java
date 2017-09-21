
package cn.drct.web.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;





public class DynamicSpecifications {

	public static <T> Specification<T> bySearchFilter(final Collection<SearchFilter> filters, final Class<T> entityClazz) {
		return new Specification<T>() {
			@SuppressWarnings({ "rawtypes", "unchecked" })
			@Override
			public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
				if (filters!=null) {
					List<Predicate> predicates = new ArrayList<Predicate>();
					for (SearchFilter filter : filters) {
						// nested path translate, 如Task的名为"user.name"的filedName, 转换为Task.user.name属性
						String[] names = filter.fieldName.split(".");
						Path expression = null;
						if(names.length>0){
							expression = root.get(names[0]);
							for (int i = 1; i < names.length; i++) {
								expression = expression.get(names[i]);
							}
						}else{
							expression = root.get(filter.fieldName);
						}
						

						// logic operator
						switch (filter.operator) {
							case EQ:{
								if(expression.getJavaType().equals(Boolean.class)){
									filter.value=new Boolean(filter.value.toString());
								}
								predicates.add(builder.equal(expression, filter.value));
								break;
							}
							case NEQ:{
								if(expression.getJavaType().equals(Boolean.class)){
									filter.value=new Boolean(filter.value.toString());
								}
								predicates.add(builder.notEqual(expression, filter.value));
								break;
							}
							case LIKE:
								predicates.add(builder.like(expression, "%" + filter.value + "%"));
								break;
							case NLIKE:
								predicates.add(builder.notLike(expression, "%" + filter.value + "%"));
								break;
							case GT:
								predicates.add(builder.greaterThan(expression, (Comparable) filter.value));
								break;
							case LT:
								predicates.add(builder.lessThan(expression, (Comparable) filter.value));
								break;
							case GTE:
								predicates.add(builder.greaterThanOrEqualTo(expression, (Comparable) filter.value));
								break;
							case LTE:
								predicates.add(builder.lessThanOrEqualTo(expression, (Comparable) filter.value));
								break;
							case IN:
								if(filter.value instanceof Collection){
									if(((Collection)filter.value).size()>0)
										predicates.add(expression.in((Collection)filter.value));
								}else{
									predicates.add(expression.in(filter.value));
									
								}
								break;
							case NIN:
								if(filter.value instanceof Collection){
									if(((Collection)filter.value).size()>0)
										predicates.add(builder.not(expression.in((Collection)filter.value)));
								}else{
									predicates.add(builder.not(expression.in(filter.value)));
									
								}
								break;	
						}
					}

					// 将所有条件用 and 联合起来
					if (!predicates.isEmpty()) {
						return builder.and(predicates.toArray(new Predicate[predicates.size()]));
					}
				}

				return builder.conjunction();
			}
		};
	}
}
