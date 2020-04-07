<!-- ⚠️ This README has been generated from the file(s) "blueprint.md" ⚠️-->
[![-----------------------------------------------------](https://raw.githubusercontent.com/andreasbm/readme/master/assets/lines/colored.png)](#seia-data-jpa-auditing)

# ➤ seia-data-jpa-auditing
Library project for auditing entities with Spring Data JPA


[![-----------------------------------------------------](https://raw.githubusercontent.com/andreasbm/readme/master/assets/lines/colored.png)](#introduction)

## ➤ Introduction

This library was implemented in order to create, in a generic way, a standardized abstraction for the audit of entities in the SEMA / INEMA systems.



[![-----------------------------------------------------](https://raw.githubusercontent.com/andreasbm/readme/master/assets/lines/colored.png)](#development)

## ➤ Development

```$xslt
// To audit an entity, it will need to extend 'BaseAuditableEntity':

@Entity
public class File extends BaseAuditableEntity {


// In audited entities, if it is necessary to ignore the serialization of relationships, use:
@OneToOne
    @JsonSerialize(using = CustomEntitySerializer.class)/**Retorna apenas o id da classe referenciada ao ser serializado**/
    private Teste teste;
```


[![-----------------------------------------------------](https://raw.githubusercontent.com/andreasbm/readme/master/assets/lines/colored.png)](#branching)

## ➤ Branching

`dev` is the main development branch. Code in this branch are deployed to the staging server for internal reviewing/testing. Staging server is at https://staging-business-dashboard.stevemu.com


`master` is the production branch. Once code is tested and approved on staging server, code in this branch are deployed to production. Production server is at https://prod-business-dashboard.stevemu.com

