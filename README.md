GrandUnification
================

The grand unification of java server technologies for rapid development of scalable services.

This project combines Google GUICE (dependency injection), Jersey (web services),
Jackson (JSON serialization/deserialization), Apache Shiro (security), JPA 2.1 with
Hibernate as the provider (persistence) and a smattering of other Java technologies
to create a rapid development environment for scalable web services.

- Requires:
-   PostgreSQL 9.x (tested with 9.3)
-   Maven 3
-   OpenLDAP
-   Tomcat 7.0.x (Tested with 7.0.53)

All builds and testing conducted under Ubuntu 14.04 LTS 64-bit

Installation procedures:

1) Install all prerequisities and configure for your environment
2) Load api/unification.backend.ldif into the LDAP repository
3) Load api/unification.ldif into the LDAP repository
4) Build and deploy the Maven artifacts

To create a new application:

1) Extend
