package org.tmf.dsmapi.productOrder;

import org.tmf.dsmapi.productOrder.model.State;
import org.tmf.dsmapi.commons.workflow.StateModelBase;

/**
 *
 * @author maig7313
 */
public class StateModelImpl extends StateModelBase<State> {
    
    /**
     *
     */
    public StateModelImpl() {
        super(State.class);
    }    

    /**
     *
     */
    @Override
    protected void draw() {
        // First
        fromFirst(State.INITIAL).to(
                State.REJECTED,
                State.ACKNOWLEDGED);

        // Somewhere
        from(State.ACKNOWLEDGED).to(
                State.IN_PROGRESS);
        from(State.IN_PROGRESS).to(
                State.HELD,
                State.PENDING,
                State.PARTIAL,
                State.FAILED,
                State.COMPLETED);       
        from(State.HELD).to(
                State.IN_PROGRESS,
                State.CANCELLED);
        from(State.PENDING).to(
                State.IN_PROGRESS,
                State.CANCELLED);

        // Final
        from(State.COMPLETED);
        from(State.PARTIAL);
        from(State.COMPLETED);
        from(State.REJECTED);
        from(State.CANCELLED);
    }
}
