package org.elemental.databases

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.jdbc.DataSourceBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import javax.sql.DataSource

@Configuration
class DatabaseConfig {
    @Bean(name = arrayOf("postgresqlSource"))
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource")
    fun postgresqlSource(): DataSource = DataSourceBuilder.create().build()

    @Bean(name = arrayOf("postgresqlTemplate"))
    @Primary
    @Autowired
    fun postgresqlTemplate(@Qualifier("postgresqlSource") source: DataSource):
            NamedParameterJdbcTemplate = NamedParameterJdbcTemplate(source)
}