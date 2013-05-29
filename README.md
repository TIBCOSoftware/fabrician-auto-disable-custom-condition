[fabrician.org](http://fabrician.org/)
==========================================================================
Custom Condition - Automatic Disablement
==========================================================================

Introduction
--------------------------------------
A Custom Condition is a Silver Fabric add on module which can be used to determine
if a specfic condition is satisfied.  Custom Conditions may be referenced in Threshold
Activation or Enablement Condition Rules to define allocation behavior.  See the Silver
Fabric Developers Guide and the API for more details.

Use Case: Run a component for a fixed period of time to perform a task, and then it 
automatically disables itself.

For example, one could perform load testing for a fixed period of time and then stop, 
or perhaps run something like Puppet to apply changes to an operating system or an application 
environment, and then once that’s done it can shut itself down.

The Auto Disable Custom Condition is a simplified variation Allocation Failure Custom Condition 
except there is no second component to monitor.  It should start up, and then shut itself down 
after a set period of time.  In this case, it modifies the stack after completion, so to re-run 
the stack, you just publish the stack again.

Installation
--------------------------------------
The required classes for a Custom Condition are packaged in a jar file, which along with the
XML descriptor should be copied to the SF_HOME/webapps/livecluster/deploy/config/ruleConditions
directory of the Silver Fabric Broker.  The Custom Condition will automatically be detected
and loaded.  Once loaded, Custom Conditions will become available in the rule editors of 
the Component Wizard and the Stack Builder.


To build the Custom Condition run the maven project

```bash
mvn package
```

The jar file and the XML deployment descriptor are packaged in AutoDisableCustomCondition-1.0-SNAPSHOT.tar.gz, 
which can simply be extracted in SF_HOME/webapps/livecluster/deploy/config/ruleConditions.
