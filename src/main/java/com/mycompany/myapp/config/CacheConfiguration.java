package com.mycompany.myapp.config;

import java.time.Duration;
import org.ehcache.config.builders.*;
import org.ehcache.jsr107.Eh107Configuration;
import org.hibernate.cache.jcache.ConfigSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.boot.info.BuildProperties;
import org.springframework.boot.info.GitProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.*;
import tech.jhipster.config.JHipsterProperties;
import tech.jhipster.config.cache.PrefixedKeyGenerator;

@Configuration
@EnableCaching
public class CacheConfiguration {

    private GitProperties gitProperties;
    private BuildProperties buildProperties;
    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache = jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration =
            Eh107Configuration.fromEhcacheCacheConfiguration(
                CacheConfigurationBuilder
                    .newCacheConfigurationBuilder(Object.class, Object.class, ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                    .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
                    .build()
            );
    }

    @Bean
    public HibernatePropertiesCustomizer hibernatePropertiesCustomizer(javax.cache.CacheManager cacheManager) {
        return hibernateProperties -> hibernateProperties.put(ConfigSettings.CACHE_MANAGER, cacheManager);
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            createCache(cm, com.mycompany.myapp.repository.UserRepository.USERS_BY_LOGIN_CACHE);
            createCache(cm, com.mycompany.myapp.repository.UserRepository.USERS_BY_EMAIL_CACHE);
            createCache(cm, com.mycompany.myapp.domain.User.class.getName());
            createCache(cm, com.mycompany.myapp.domain.Authority.class.getName());
            createCache(cm, com.mycompany.myapp.domain.User.class.getName() + ".authorities");
            createCache(cm, com.mycompany.myapp.domain.FabricEditor.class.getName());
            createCache(cm, com.mycompany.myapp.domain.FabricEditor.class.getName() + ".fabrics");
            createCache(cm, com.mycompany.myapp.domain.Fabric.class.getName());
            createCache(cm, com.mycompany.myapp.domain.Fabric.class.getName() + ".fabricTypes");
            createCache(cm, com.mycompany.myapp.domain.Fabric.class.getName() + ".materials");
            createCache(cm, com.mycompany.myapp.domain.Fabric.class.getName() + ".uses");
            createCache(cm, com.mycompany.myapp.domain.Fabric.class.getName() + ".maintenances");
            createCache(cm, com.mycompany.myapp.domain.Fabric.class.getName() + ".labels");
            createCache(cm, com.mycompany.myapp.domain.Fabric.class.getName() + ".sellers");
            createCache(cm, com.mycompany.myapp.domain.Fabric.class.getName() + ".projects");
            createCache(cm, com.mycompany.myapp.domain.FabricSeller.class.getName());
            createCache(cm, com.mycompany.myapp.domain.FabricSeller.class.getName() + ".fabrics");
            createCache(cm, com.mycompany.myapp.domain.Materials.class.getName());
            createCache(cm, com.mycompany.myapp.domain.Materials.class.getName() + ".fabrics");
            createCache(cm, com.mycompany.myapp.domain.FabricTypes.class.getName());
            createCache(cm, com.mycompany.myapp.domain.FabricTypes.class.getName() + ".fabrics");
            createCache(cm, com.mycompany.myapp.domain.FabricUses.class.getName());
            createCache(cm, com.mycompany.myapp.domain.FabricUses.class.getName() + ".fabrics");
            createCache(cm, com.mycompany.myapp.domain.FabricLabels.class.getName());
            createCache(cm, com.mycompany.myapp.domain.FabricLabels.class.getName() + ".fabrics");
            createCache(cm, com.mycompany.myapp.domain.FabricMaintenance.class.getName());
            createCache(cm, com.mycompany.myapp.domain.FabricMaintenance.class.getName() + ".fabrics");
            createCache(cm, com.mycompany.myapp.domain.Patron.class.getName());
            createCache(cm, com.mycompany.myapp.domain.Patron.class.getName() + ".projects");
            createCache(cm, com.mycompany.myapp.domain.Project.class.getName());
            createCache(cm, com.mycompany.myapp.domain.Project.class.getName() + ".fabrics");
            // jhipster-needle-ehcache-add-entry
        };
    }

    private void createCache(javax.cache.CacheManager cm, String cacheName) {
        javax.cache.Cache<Object, Object> cache = cm.getCache(cacheName);
        if (cache != null) {
            cache.clear();
        } else {
            cm.createCache(cacheName, jcacheConfiguration);
        }
    }

    @Autowired(required = false)
    public void setGitProperties(GitProperties gitProperties) {
        this.gitProperties = gitProperties;
    }

    @Autowired(required = false)
    public void setBuildProperties(BuildProperties buildProperties) {
        this.buildProperties = buildProperties;
    }

    @Bean
    public KeyGenerator keyGenerator() {
        return new PrefixedKeyGenerator(this.gitProperties, this.buildProperties);
    }
}
