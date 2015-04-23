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
        fromFirst(State.Initial).to(
                State.Rejected,
                State.Acknowledged);

        // Somewhere
        from(State.Acknowledged).to(
                State.InProgress);
        from(State.InProgress).to(
                State.Held,
                State.Pending,
                State.Partial,
                State.Failed,
                State.Completed);       
        from(State.Held).to(
                State.InProgress,
                State.Cancelled);
        from(State.Pending).to(
                State.InProgress,
                State.Cancelled);

        // Final
        from(State.Completed);
        from(State.Partial);
        from(State.Rejected);
        from(State.Cancelled);
    }
}
