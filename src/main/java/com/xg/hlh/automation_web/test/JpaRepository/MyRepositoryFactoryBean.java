package com.xg.hlh.automation_web.test.JpaRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactory;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactoryBean;
import org.springframework.data.repository.core.RepositoryMetadata;
import org.springframework.data.repository.core.support.RepositoryFactorySupport;

import javax.persistence.EntityManager;
import java.io.Serializable;

/**
 * 创建一个自定义的RepositoryFactoryBean来代替默认的RepositoryFactoryBean
 * RepositoryFactoryBean负责返回一个RepositoryFactory
 * Spring Data Jpa 将使用RepositoryFactory来创建Repository具体实现
 * 用MyCustomerRepository代替SimpleJpaRepository作为Repository接口的实现
 * 这样我们就能够达到为所有Repository添加自定义方法的目的。
 */
public class MyRepositoryFactoryBean<R
        extends JpaRepository<T,I>,T,I extends Serializable>
        extends JpaRepositoryFactoryBean<R,T,I> {

    /**
     * Creates a new {@link JpaRepositoryFactoryBean} for the given repository interface.
     *
     * @param repositoryInterface must not be {@literal null}.
     */
    public MyRepositoryFactoryBean(Class<? extends R> repositoryInterface) {
        super(repositoryInterface);
    }

    @Override
    protected RepositoryFactorySupport createRepositoryFactory(EntityManager em) {
        return new MyRepositoryFactory(em);
    }

    private static class MyRepositoryFactory<T,I extends Serializable> extends JpaRepositoryFactory {

        private final EntityManager em;

        public MyRepositoryFactory(EntityManager em) {
            super(em);
            this.em = em;
        }

        @SuppressWarnings("unchecked")
        protected Object getTargetRepository(RepositoryMetadata metadata) {
            return new MyCustomerRepository<T, I>((Class<T>) metadata.getDomainType(), em);
        }

        @Override
        protected Class<?> getRepositoryBaseClass(RepositoryMetadata metadata) {
            return MyCustomerRepository.class;
        }
    }
}
