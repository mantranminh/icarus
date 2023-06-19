# Icarus
Provide some useful annotations

## Usage:
1. Lookup {version_tag}
2. Update POM.xml
```xml
	<repositories>
		<repository>
		    <id>jitpack.io</id>
		    <url>https://jitpack.io</url>
		</repository>
	</repositories>
```
```xml
	<dependency>
	    <groupId>com.github.mantranminh</groupId>
	    <artifactId>icarus</artifactId>
	    <version>{version_tag}</version>
	</dependency>
```

3. Create Component Scan config
```Java
@Configuration
@ComponentScan("com.github.mantranminh.icarus.annotations")
```