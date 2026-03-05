# sku-base


## quick start

1. clone
   ```
   git clone --recurse-submodules <url>
   mvn clean generate-sources
   ```

2. run

    - maven
    ```
    mvn spring-boot:run -Dspring-boot.run.arguments="--spring.config.additional-location=src/main/resources/application-local.yml --spring.profiles.active=local"
    ```
    - java
    ```
    mvn package -Dmaven.test.skip=true
    java -XX:+UseG1GC -jar -Dspring.config.additional-location=src/main/resources/application-local.yml -Dspring.profiles.active=local target/order-base-0.0.1-SNAPSHOT.jar
    ```
    - intellij
      `edit configuration` -> `environment variables` set as
   ```
   spring.profiles.active=local;spring.config.additional-location=src/main/resources/application-local.yml
   ```


## other
https://start.spring.io/#!type=maven-project&language=java&platformVersion=3.1.5&packaging=jar&jvmVersion=17&groupId=com.linkpay&artifactId=order-base&name=order-base&description=order%20base%20service&packageName=com.linkpay.order-base&dependencies=lombok,web,cloud-starter,data-redis,mysql,mybatis,webflux

https://start.spring.io/#!type=maven-project&language=java&platformVersion=3.1.5&packaging=jar&jvmVersion=17&groupId=com.linkpay&artifactId=order-base-migration&name=order-base-migration&description=order%20base%20service%20migration&packageName=com.linkpay.order-base-migration&dependencies=mysql,flyway

https://seata.io/zh-cn/docs/ops/deploy-guide-beginner.html

https://github.com/OpenAPITools/openapi-generator/issues/4680

數據源切換
https://www.cnblogs.com/aheizi/p/7071181.html

swagger
http://localhost:8085/swagger-ui
mvn clean generate-sources

git submodule add https://gitlab.ops-link.com/backend/linkpay-openapi.git src/main/resources/openapi


加入這行
```
            <plugin>
				<groupId>org.apache.maven.plugins</groupId>
				<artifactId>maven-resources-plugin</artifactId>
				<version>3.3.1</version>
				<configuration>
					<resources>
						<resource>
							<directory>${project.basedir}/target/generated-sources/openapi/src/main/java</directory>
							<includes>**/*</includes>
						</resource>
						<resource>
							<directory>${project.basedir}/target/generated-sources/openapi/src/test/java</directory>
							<includes>**/*</includes>
						</resource>
					</resources>
				</configuration>
			</plugin>
```