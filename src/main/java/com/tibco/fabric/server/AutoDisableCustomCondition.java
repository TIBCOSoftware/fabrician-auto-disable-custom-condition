package com.tibco.fabric.server;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import com.datasynapse.commons.util.LogUtils;
import com.datasynapse.fabric.admin.AdminManager;
import com.datasynapse.fabric.admin.ComponentAdmin;
import com.datasynapse.fabric.admin.EngineDaemonAdmin;
import com.datasynapse.fabric.admin.StackAdmin;
import com.datasynapse.fabric.admin.info.AllocationInfo;
import com.datasynapse.fabric.admin.info.ComponentAllocationEntryInfo;
import com.datasynapse.fabric.admin.info.ComponentInfo;
import com.datasynapse.fabric.admin.info.FabricEngineDaemonInfo;
import com.datasynapse.fabric.admin.info.RuntimeContextVariableInfo;
import com.datasynapse.fabric.admin.info.StackInfo;
import com.datasynapse.fabric.broker.FabricServerEvent;
import com.datasynapse.fabric.broker.userartifact.condition.AbstractCustomRuleCondition;
import com.datasynapse.gridserver.admin.Property;
import com.datasynapse.gridserver.server.ServerEvent;
import com.datasynapse.gridserver.server.ServerHook;

public class AutoDisableCustomCondition extends AbstractCustomRuleCondition implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2443519394918129355L;
	private String ComponentName = null;
	private String AutoDisable = "false";
	private String WaitFor = "10";
	private int intWaitCount = 0;
	private final ComponentAdmin ca = AdminManager.getComponentAdmin();
	private String description = null;
	private String lastModifiedBy = null;

	@Override
	public String getDescription() {
		return "Component " + ComponentName + " will activated for " +WaitFor + " minutes and then shut down again";
	}

	@Override
	public boolean isSatisfied() {
		boolean satisfied = false;
		try {
			// Allocated Engine Count in ComponentAdmin only returns successfully started Engines
			int remEngineCountCA = ca.getAllocatedEngineCount(ComponentName);
			if (AutoDisable.equalsIgnoreCase("true") && remEngineCountCA == 0){
				satisfied = true;
				intWaitCount = Integer.parseInt(WaitFor);
			}
			if (remEngineCountCA > 0){
				// our component is now active stop waiting for activation and, begin the autodisable count down...
				AutoDisable = "false";
				LogUtils.forObject(this).fine("Counting down to disablement of " + ComponentName + " " + intWaitCount);
				intWaitCount--;
				if (intWaitCount > 0) {
					satisfied = true;
				}

			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return satisfied;
	}

	public String getComponentName() {
		return ComponentName;
	}

	public void setComponentName(String ComponentName) {
		this.ComponentName = ComponentName;
	}

	public String getAutoDisable() {
		return AutoDisable;
	}

	public void setAutoDisable(String autoDisable) {
		AutoDisable = autoDisable;
	}

	public String getWaitFor() {
		return WaitFor;
	}

	public void setWaitFor(String waitFor) {
		this.WaitFor = waitFor;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setLastModifiedBy(String lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}

}
