package com.tibco.fabric.server;

import java.io.Serializable;
import java.util.logging.Logger;

import com.datasynapse.fabric.admin.AdminManager;
import com.datasynapse.fabric.admin.ComponentAdmin;
import com.datasynapse.fabric.broker.userartifact.condition.AbstractCustomRuleCondition;

public class AutoDisableCustomCondition extends AbstractCustomRuleCondition implements Serializable {

    private static final long serialVersionUID = 2443519394918129355L;
    
    private String componentName = null;
    private String autoDisable = "false";
    private String waitFor = "10";
    private int intWaitCount = 0;
    private final ComponentAdmin ca = AdminManager.getComponentAdmin();
    private String description = null;
    private String lastModifiedBy = null;

    @Override
    public String getDescription() {
        return "Component " + componentName + " will activated for " +waitFor + " minutes and then shut down again";
    }

    @Override
    public boolean isSatisfied() {
        boolean satisfied = false;
        try {
            // Allocated Engine Count in ComponentAdmin only returns successfully started Engines
            int remEngineCountCA = ca.getAllocatedEngineCount(componentName);
            if (autoDisable.equalsIgnoreCase("true") && remEngineCountCA == 0){
                satisfied = true;
                intWaitCount = Integer.parseInt(waitFor);
            }
            if (remEngineCountCA > 0){
                // our component is now active stop waiting for activation and, begin the autodisable count down...
                autoDisable = "false";
                Logger.getLogger(getClass().getSimpleName()).fine("Counting down to disablement of " + componentName + " " + intWaitCount);
                intWaitCount--;
                if (intWaitCount > 0) {
                    satisfied = true;
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return satisfied;
    }

    public String getComponentName() {
        return componentName;
    }

    public void setComponentName(String componentName) {
        this.componentName = componentName;
    }

    public String getAutoDisable() {
        return autoDisable;
    }

    public void setAutoDisable(String autoDisable) {
        this.autoDisable = autoDisable;
    }

    public String getWaitFor() {
        return waitFor;
    }

    public void setWaitFor(String waitFor) {
        this.waitFor = waitFor;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

}
