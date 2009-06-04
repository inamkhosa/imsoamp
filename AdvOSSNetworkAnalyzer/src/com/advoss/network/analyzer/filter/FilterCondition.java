/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.advoss.network.analyzer.filter;

/**
 *
 * @author alam.sher
 */
public class FilterCondition {
    FilterConditionTypes conditionType = FilterConditionTypes.TRAP_FILTER_CONDITION;

    
    public FilterConditionTypes getConditionType() {
        return conditionType;
    }

    public void setConditionType(FilterConditionTypes conditionType) {
        this.conditionType = conditionType;
    }
}
