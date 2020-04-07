# seia-data-jpa-auditing
Library project for auditing entities with Spring Boot

## Introduction

This library is made as both a class project (in Boston University) with intention of solving Roger's business problems. This online dashboard is made for him so he can manage his workers and customers easier.


## Development

```$xslt
// Run postgres on mac
pg_ctl -D /usr/local/var/postgres start
cd frontend
npm start
// then start the spring boot backend
```

## Branching

`dev` is the main development branch. Code in this branch are deployed to the staging server for internal reviewing/testing. Staging server is at https://staging-business-dashboard.stevemu.com


`master` is the production branch. Once code is tested and approved on staging server, code in this branch are deployed to production. Production server is at https://prod-business-dashboard.stevemu.com

