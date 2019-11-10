FROM jboss/wildfly:18.0.0.Final

ADD Web/target/LibrisWeb.war /opt/jboss/wildfly/standalone/deployments/