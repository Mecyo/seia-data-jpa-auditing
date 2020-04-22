<!-- ⚠️ This README has been generated from the file(s) "blueprint.md" ⚠️-->seia-data-jpa-auditing
en-US:
Library project for auditing entities with Spring Data JPA

Introduction
This library was implemented in order to create, in a generic way, a standardized abstraction for the audit of entities in the SEMA/INEMA projects.

Development instructions
```$xslt
// To audit an entity, it will need to extend 'BaseAuditableEntity':
```
```$xslt
@Entity
public class File extends BaseAuditableEntity {
```

```$xslt
// In audited entities, if it is necessary to ignore the serialization of relationships, use:
```
```$xslt
	@OneToOne
	@JsonIgnore
    	private Relationship relatedEntity;
```

```$xslt
// If you want to serialize only the id (the primary key) of the relationships use:
```
```$xslt
	@OneToOne
	@JsonSerialize(using = CustomEntitySerializer.class)
	private Relationship relatedEntity;

/** CustomEntitySerializer.class retorna apenas o id da classe relacionada ao ser serializado**/
```

```$xslt
// Create a class implementing AuditorAwareInformations to provide the username, the ip address and the host from client:
```
```$xslt
@Component("auditorAware")
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
public class AuditorAwareImpl implements AuditorAwareInformations {

	@Override
	public String getCurrentAuditor() {
		return "Seia-Sema-User";
		// Can use Spring Security to return currently logged in user
        	// return ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
    	}

	@Override
	public String getClientIpAddress() {
		return "127.0.0.1";
		// Can use the request to return currently remote ip address
		// HttpServletRequest request = getRequest();
		// return request.getRemoteAddr();
	}

	@Override
	public String getClientHost() {
		return "http://localhost:4200/";
		// Can use the request to return currently remote host
		// HttpServletRequest request = getRequest();
		// return request.getRemoteHost();
	}
}


```$xslt
[@Component("auditorAware")]
It should be annotated with the name 'auditorAware' so that your Bean is created and it can be loaded by the EntityListener.

[@EnableJpaAuditing(auditorAwareRef = "auditorAware")]
It must be noted in this way to enable the Spring Data JPA audit functions and inform the auditor user data.
```
```

```$xslt
The main application class should be noted as shown below:
[@Import(SharedConfigurations.class)] --This will provide the internal settings of the library that will be used in the main application--
[@EntityScan(basePackages = {"yourEntityPackage", "br.gov.ba.seia.auditing.model"})]--Here we must inform all the packages where the entities of the system are. The package: 'br.gov.ba.seia.auditing.model' needs to be included for auditing use--
```
//-----------------------------------------------------------------------------------//
pt-BR:
Projeto de biblioteca para auditar entidades com o Spring Data JPA

Introdução
Esta biblioteca foi implementada com o objetivo de criar, de maneira genérica, uma abstração padronizada para a auditoria de entidades nos projetos SEMA / INEMA.

Instruções de desenvolvimento
```$xslt
// Para auditar uma entidade, ela precisará estender 'BaseAuditableEntity':
```
```$xslt
@Entity
public class File extends BaseAuditableEntity {
```

```$xslt
// Nas entidades auditadas, se for necessário ignorar a serialização de relacionamentos, use:
```
```$xslt
	@OneToOne
	@JsonIgnore
    	private Relationship relatedEntity;
```

```$xslt
// Se você deseja serializar apenas o ID (a chave primária) dos relacionamentos, use:
```
```$xslt
	@OneToOne
	@JsonSerialize(using = CustomEntitySerializer.class)
	private Relationship relatedEntity;

/** CustomEntitySerializer.class retorna apenas o id da classe relacionada ao ser serializado**/
```

```$xslt
// Crie uma classe implementando AuditorAwareInformations para fornecer o nome de usuário, o endereço IP e o host do cliente:
```
```$xslt
@Component("auditorAware")
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
public class AuditorAwareImpl implements AuditorAwareInformations {

	@Override
	public String getCurrentAuditor() {
		return "Seia-Sema-User";
		// Pode usar o Spring Security para retornar o usuário autenticado
        	// return ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
    	}

	@Override
	public String getClientIpAddress() {
		return "127.0.0.1";
		// Pode usar o request para retornar o endereço IP remoto
		// HttpServletRequest request = getRequest();
		// return request.getRemoteAddr();
	}

	@Override
	public String getClientHost() {
		return "http://localhost:4200/";
		// Pode usar o request para retornar o host remoto
		// HttpServletRequest request = getRequest();
		// return request.getRemoteHost();
	}
}


```$xslt
[@Component ("auditorAware")]
Ele deve ser anotado com o nome 'auditorAware' para que seu Bean seja criado e possa ser carregado pelo EntityListener.

[@EnableJpaAuditing (auditorAwareRef = "auditorAware")]
Deve-se observar dessa maneira para ativar as funções de auditoria do Spring Data JPA e informar os dados do usuário do auditor.
```
```

```$xslt
A classe principal do aplicativo deve ser observada como mostrado abaixo:
[@Import (SharedConfigurations.class)] - Isso fornecerá as configurações internas da biblioteca que serão usadas no aplicativo principal--
[@EntityScan (basePackages = {"yourEntityPackage", "br.gov.ba.seia.auditing.model"})] - Aqui devemos informar todos os pacotes onde estão as entidades do sistema. O pacote: 'br.gov.ba.seia.auditing.model' precisa ser incluído para uso de auditoria -
```